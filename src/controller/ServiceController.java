package controller;

import model.ServiceRoomModel;
import service.ServiceRoomService;
import view.ButtonListener;
import view.PanelManagerServiceView;
import view.RoomEvent;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ServiceController {
    PanelManagerServiceView panelManagerServiceView;
    ServiceRoomService serviceRoomService;
    GuestRoomController guestRoomController;

    RefreshController refreshController;

    public ServiceController(PanelManagerServiceView panelManagerServiceView, ServiceRoomService serviceRoomService) {
        this.panelManagerServiceView = panelManagerServiceView;
        this.serviceRoomService = serviceRoomService;

        getDataServiceOption("IN(2,3)");
        getNumbersRoomGuest_Booked();
        loadDateServices();
    }
    public void getNumbersRoomGuest_Booked(){
        List<Integer> lsNumberRoom = this.serviceRoomService.getNumbersRoomGues_Booked();
        if(lsNumberRoom.size() < 1){
            this.panelManagerServiceView.offSaveService();

            return;
        }
        this.panelManagerServiceView.setCBDateNumberRoom(lsNumberRoom);
    }
    public void setGuestRoomController(GuestRoomController guestRoomController) {
        this.guestRoomController = guestRoomController;
    }

    public void setRefreshController(RefreshController refreshController) {
        this.refreshController = refreshController;
    }

    public void loadDateServices() {
        this.panelManagerServiceView.addButtonListener(new ButtonListener() {

            @Override
            public void buttonPerformed(RoomEvent roomEvent) {
                switch (roomEvent.getNameBtn()) {
                    case "serviceComboBox": {
                        String option = panelManagerServiceView.getOptionServiceCb();
                        switch (option) {
                            case "Tất cả sản phẩm": {
                                getDataServiceOption("IN(2,3)");
                                break;
                            }
                            case "Đồ ăn": {
                                getDataServiceOption("= 2");
                                break;
                            }
                            case "Nước": {
                                getDataServiceOption("= 3");
                                break;
                            }

                        }
                        break;
                    }
                    case "btnSaveService": {
                        List<List<Object>> serviceRoomModels = panelManagerServiceView.getListAddedService();
                        System.err.println(serviceRoomModels.toString());
                        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có muốn lưu các dịch vụ đã chọn?", "Xác nhận", JOptionPane.OK_CANCEL_OPTION);
                        boolean insertSuccess = false;
                        if (confirm == JOptionPane.OK_OPTION) {
                            insertSuccess = serviceRoomService.insertAddedServices(serviceRoomModels);
                            panelManagerServiceView.delAllDateAddedModel();
                        }
                        if (insertSuccess) {
                            panelManagerServiceView.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                            // hien thi danh sach dich vu moi len view
                            refreshController.resetViewRoom(guestRoomController.aRoomService,guestRoomController.guestRoomView,guestRoomController);
                            JOptionPane.showMessageDialog(null, "Dịch vụ được thêm thành công");
                            panelManagerServiceView.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        } else
                            JOptionPane.showMessageDialog(null, "Dịch vụ được thêm chưa hoàn thành");
                        break;
                    }

                }


            }
        });
    }

    private void getDataServiceOption(String whereFind) {
        // xoa du lieu banag cu va lay lai data
        panelManagerServiceView.delAllDateServiceModel();
        List<ServiceRoomModel> serviceRoomModels = serviceRoomService.getServices(whereFind);
        for (ServiceRoomModel serviceRoomModel : serviceRoomModels) {
            panelManagerServiceView.setDataServiceModel(serviceRoomModel);
        }
    }

    public static void main(String[] args) {
        PanelManagerServiceView panelManagerServiceView = new PanelManagerServiceView();
        JFrame a = new JFrame();
        a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        a.setSize(800, 600);
        a.add(panelManagerServiceView);
        a.setVisible(true);
        ServiceRoomService service = new ServiceRoomService();
        ServiceController serviceController = new ServiceController(panelManagerServiceView, service);
    }
}
