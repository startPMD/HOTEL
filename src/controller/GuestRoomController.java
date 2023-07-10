package controller;

import model.*;
import service.*;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class GuestRoomController extends AManagerRoomController {
    GuestRoomView guestRoomView;
    GuestRoomService guestRoomService;
    EmptyRoomController emptyRoomController;
    ManagerPaymentController managerPaymentController;
    RefreshController refreshController;

    public GuestRoomController(ARoomModel aRoomModel, ARoomService aRoomService, ARoomView aRoomView) {
        super(aRoomModel, aRoomService, aRoomView);
    }

    public void setEmptyRoomController(EmptyRoomController emptyRoomController) {
        this.emptyRoomController = emptyRoomController;
    }

    public void setRefreshController(RefreshController refreshController) {
        this.refreshController = refreshController;
    }

    public void setManagerPaymentController(ManagerPaymentController managerPaymentController) {
        this.managerPaymentController = managerPaymentController;
    }
    @Override
    public void construcFirst() {
        guestRoomView = (GuestRoomView) aRoomView;
        guestRoomService = (GuestRoomService) aRoomService;
    }

    @Override
    public void loadDataInfoRoom() {
        System.out.println("GuestRoomService");
        System.out.println("DataGuestRoomInformation_____ ");
        for (RoomBookingFormModel roomBookingFormModel : guestRoomService.getInforGuestRooms()) {
            ((GuestRoomView) this.aRoomView).setDataGuestRoomInformation(roomBookingFormModel);
            loadDateServiceCus(roomBookingFormModel.getIdBookedRoom());
        }


    }

    public void loadDateServiceCus(int idBooked) {
        ServiceRoomService serviceRoomService = ServiceRoomService.getInstanceServiceRoomService();
        for (Map.Entry<Integer, List<ServiceRoomModel>> services : serviceRoomService.getListServiceCus(idBooked).entrySet()) {
            int idRoom = services.getKey();
            List<ServiceRoomModel> serviceList = services.getValue();
            ((GuestRoomView) this.aRoomView).setDateServiceCus(idRoom, serviceList);
        }
    }

    @Override
    public void setActionSaveForm() {
        for (ImageRoomView imageRoomView : this.aRoomView.getImageRoomViews()) {
            InfoCusBookedFormView infoCusBookedFormView = imageRoomView.getInfoCusBookedFormView();
            if (infoCusBookedFormView == null) return;
            infoCusBookedFormView.addButtonListener(new ButtonListener() {
                @Override
                public void buttonPerformed(RoomEvent roomEvent) {
                    String nameBtnClick = roomEvent.getNameBtn();
                    switch (nameBtnClick) {
                        case "btnChangeRoom": {
                            RoomChangeForm roomChangeForm = infoCusBookedFormView.getRoomChangeForm();
                            roomChangeForm.showDetail();
                            // lay danh sach ma phong con chong de chon doi
                            for (Integer code : guestRoomService.getListEmptyRoom()) {
                                roomChangeForm.setCodeRoom(code);
                            }
                            roomChangeForm.getChangeButton().addActionListener(e -> {
                                int codeRoomCurrent = roomChangeForm.getCodeRoomCurren();
                                int codeRoomNew = roomChangeForm.getCodeRoomNew();
                                //  hoan doi trang thai ma phong
                                guestRoomService.updateStatetEmpty(codeRoomCurrent, 1);
                                guestRoomService.updateStatetEmpty(codeRoomNew, 2);
                                int idRoomCurrent = guestRoomService.getIdRoom(codeRoomCurrent);
                                int idRoomNew = guestRoomService.getIdRoom(codeRoomNew);

                                // doi lai ma phong duoc dat
                                boolean hasUpdateCodeRoom = guestRoomService.updateCodeRoomBooked(idRoomCurrent, idRoomNew);

                                // vẽ lại view
                                updateRoomChange(codeRoomCurrent);

                                guestRoomView.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                                roomChangeForm.dispose();

                                if (hasUpdateCodeRoom) {
                                    guestRoomView.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                    JOptionPane.showMessageDialog(null, "Phòng đã được đổi");
                                    infoCusBookedFormView.dispose();
                                }
                                ;
                            });
                            break;
                        }
                        case "btnCheckOut": {
                            StatesRoomService statesRoomService = StatesRoomService.getInstanceDatabaseService();
                            int codeRoom = Integer.parseInt(infoCusBookedFormView.getNumRoom());
                            if (infoCusBookedFormView.getStatesPayment().equals("Chưa thanh toán")) {
                                HotelInfoModel hotelInfoModel = guestRoomService.getProfileHotel();
                                int numberBill = guestRoomService.getIdBill();
                                CustomerModel customerModel = guestRoomService.getCustomer(infoCusBookedFormView.idCus());

                                FormBill formBill = new FormBill();
                                formBill.setProfileHotel(hotelInfoModel);
                                formBill.setNumberBill(numberBill);
                                formBill.setInfoCus(customerModel);
                                formBill.setInfoBooked(codeRoom + "", infoCusBookedFormView.getBookedTime());
                                formBill.setServicePay(infoCusBookedFormView.lsService());
                                formBill.setService(new ServiceRoomModel(-1, "Tiền phòng", Integer.parseInt(infoCusBookedFormView.getPriceRoom()), 1, infoCusBookedFormView.getBookedTime() + "00.00"));

                                formBill.setTotalPrice(infoCusBookedFormView.getTotalPricePay() + infoCusBookedFormView.getTotalVAT());

                                formBill.setVisible(true);

                                formBill.getBtnCheckOut().addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        // luu lai tong tien thanh toan
                                        // luu phong ma khach hang thanh toan
                                        int idPay = guestRoomService.insertPay(infoCusBookedFormView.idCus(), infoCusBookedFormView.idRoom(), formBill.getDateCreateBill(), formBill.getTotalPrice());
                                        int idBooked = guestRoomService.getIdBooked(infoCusBookedFormView.idCus(), infoCusBookedFormView.idRoom());
                                        guestRoomService.insertCusPay(idBooked, idPay);
                                        guestRoomService.updatePayStateBookedRoom(idBooked);
                                        guestRoomService.updateServicePayed(idBooked);

                                        formBill.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                                        // cap nhat  bill vua thanh toan len view Payment
                                        managerPaymentController.loadBillCusCurrent();
                                        formBill.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                                        // dat trang thai phong da thanh toan
                                        resetRoom(infoCusBookedFormView, imageRoomView, statesRoomService);
                                        removeRoomCheckOn(codeRoom);

                                        formBill.dispose();
                                        infoCusBookedFormView.dispose();
                                    }

                                });
                                formBill.getBtnCheckOut_Print().addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        // if driver connection failed
                                        JOptionPane.showMessageDialog(formBill, "Vui lòng kết nối với máy in", "Mess", JOptionPane.ERROR_MESSAGE);

                                        // else .....

                                    }
                                });
                            } else {
                                int yes = JOptionPane.showConfirmDialog(null, "Bạn có muốn trả phòng hay không?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                if (yes == 0) {
                                    // cap nhat lai phong
                                    resetRoom(infoCusBookedFormView, imageRoomView, statesRoomService);
                                    emptyRoomController.setEmptyRoom(codeRoom);
                                    infoCusBookedFormView.dispose();
                                }
                            }
                            break;
                        }
                    }

                }
            });
        }
    }

    public void resetRoom(InfoCusBookedFormView infoCusBookedFormView, ImageRoomView imageRoomView, StatesRoomService statesRoomService) {
        int numberRoom = imageRoomView.getNumberRoom();
        boolean updateStateSuccess = statesRoomService.updateStatesRoom(numberRoom, 1);

        // cap nhat lai trang thai phong duoc dat
        boolean updateStateBookedSuccess = statesRoomService.updateStateBookedRoom(infoCusBookedFormView.idCus(), infoCusBookedFormView.idRoom());
    }

    private void removeRoomCheckOn(int codeRoom) {
        guestRoomView.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        guestRoomView.removeImgView(codeRoom);
        // uploadview
        emptyRoomController.setEmptyRoom(codeRoom);
        guestRoomView.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    private void updateRoomChange(int codeRoom){
        guestRoomView.removeImgView(codeRoom);
        refreshController.resetViewRoom(aRoomService,guestRoomView, this);
    }
}
