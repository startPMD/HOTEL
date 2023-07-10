package controller;

import model.*;
import service.*;
import view.*;

import javax.swing.*;
import java.awt.*;

public class EmptyRoomController extends AManagerRoomController {
    RefreshController refreshController;
    EmptyRoomView emptyRoomView;
    GuestRoomController guestRoomController;
    BookedRoomController bookedRoomController;
    PanelManagerServiceView panelManagerServiceView;
    public EmptyRoomController(ARoomModel aRoomModel, ARoomService aRoomService, ARoomView aRoomView) {
        super(aRoomModel, aRoomService, aRoomView);
        emptyRoomView = (EmptyRoomView) aRoomView;
    }

    public void setGuestRoomController(GuestRoomController guestRoomController) {
        this.guestRoomController = guestRoomController;
    }

    public void setBookedRoomController(BookedRoomController bookedRoomController) {
        this.bookedRoomController = bookedRoomController;
    }
    public void setPanelManagerServiceView(PanelManagerServiceView panelManagerServiceView) {
        this.panelManagerServiceView = panelManagerServiceView;
    }
    @Override
    public void construcFirst() {

    }

    @Override
    public void loadDataInfoRoom() {
        System.out.println("EmptyRoomController");
        EmptyRoomInformationService emptyRoomInformationService = EmptyRoomInformationService.getInstanceEmptyRoomInformationService();
        for (RoomBookingFormModel.EmptyRoomInformationModel emptyRoomInformationModel : emptyRoomInformationService.getInforRoomModels()) {
            ((EmptyRoomView) this.aRoomView).setDataEmptyRoomInformation(emptyRoomInformationModel);
        }
    }
    public void setRefreshController(RefreshController refreshController) {
        this.refreshController = refreshController;
    }
    @Override
    public void setActionSaveForm() {
        for (ImageRoomView imageRoomView : this.aRoomView.getImageRoomViews()) {
            RoomBookingFormView roomBookingFormView = imageRoomView.getRoomBookingForm();
            roomBookingFormView.addButtonSaveInfroCustomerListener(new ButtonListener() {
                @Override
                public void buttonPerformed(RoomEvent roomEvent) {

                    if (roomBookingFormView.hasEmptyValue()){
                        JOptionPane.showMessageDialog(null, "Các trường thông tin: "+ roomBookingFormView.getTxtGuestName()
                                +","+roomBookingFormView.getTxtPhone()+","+roomBookingFormView.getTxtIDNumber()+" là bắt buộc!");
                        return;
                    }

                    EmptyRoomService emptyRoomService = (EmptyRoomService) aRoomService;
                    CustomerService customerService = CustomerService.getInstanceCustomerService();
                    CustomerModel customerModel;
                    String idNumberCus = roomBookingFormView.getInputIDNumber();

                    int idCustomer = customerService.getIdNumberCus(idNumberCus);
                    // khach hang chua ton tai => tao khach hâng moi
                    if (idCustomer == -1) {
                        customerModel = new CustomerModel(-1, roomBookingFormView.getInputGuestName(),
                                roomBookingFormView.getInputPhone(), "null",
                                roomBookingFormView.getInputEmail(), idNumberCus,
                                roomBookingFormView.getSelectedNationality());
                        customerService.insertCustomerNew(customerModel);
                        customerModel.setId(customerService.getIdNumberCus(idNumberCus));
                        // luu khach hang khog thanh cong (luu khach hang moi)
                        if (customerModel.getId() == -1) {
                            JOptionPane.showMessageDialog(null, "Lưu khách hàng thất bại. Vui lòng thử lại sau.");
                            return;
                        }
                    }
                    //khach hang da ton tai
                    else {
                        customerModel = new CustomerModel();
                        customerModel.setId(idCustomer);
                    }
                    // luu lai thong tin khach hang dac phòng
                    RoomBookingFormModel.InforBookedRoomModel inforBookedRoomModel = new RoomBookingFormModel().new InforBookedRoomModel(
                            roomBookingFormView.getInputNumGuest(), roomBookingFormView.getSelectedPaymentStatus());
                    inforBookedRoomModel.setEndDate(roomBookingFormView.getEndDate());
                    inforBookedRoomModel.setCheckInTime(roomBookingFormView.getCheckInTime());

                  // luu phong khach hang dat
                    emptyRoomService.saveBookedRoom(customerModel.getId(), imageRoomView.getIdNumRoom(),inforBookedRoomModel);
                  // cap nhat trang thai phong la dat phong
                    StatesRoomService statesRoomService = StatesRoomService.getInstanceDatabaseService();
                    int numberRoom = imageRoomView.getNumberRoom();

                    emptyRoomView.removeView(numberRoom);

                    if(roomBookingFormView.getJtfCheckInTime() == null){
                        statesRoomService.updateStatesRoom(numberRoom,2);
                        refreshController.resetViewRoom(guestRoomController.aRoomService,guestRoomController.guestRoomView,guestRoomController);
                    }
                    //khach hangg dat phong
                    else {
                        statesRoomService.updateStatesRoom(numberRoom,3);
                        refreshController.resetViewRoom(bookedRoomController.aRoomService,bookedRoomController.bookedRoomView,bookedRoomController);

                    }
                    // cho phep phong duoc phep dat dich vu
                    panelManagerServiceView.setCBDateNumberRoom(numberRoom);
                   // tat form sau khi thuc hien xong
                       JOptionPane.showMessageDialog(null, "Thông tin đã được lưu thành công");
                       roomBookingFormView.offDetail();
                }
            });
        }
    }
    public void setEmptyRoom(int codeRoom) {
        EmptyRoomInformationService emptyRoomInformationService = EmptyRoomInformationService.getInstanceEmptyRoomInformationService();
        emptyRoomView.removeAll();
        loadDataRoomToView(emptyRoomView);
        emptyRoomView.setDataEmptyRoomInformation(emptyRoomInformationService.getInforRoomModels(codeRoom));
    }
}
