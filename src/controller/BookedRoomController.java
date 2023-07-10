package controller;

import model.*;
import service.*;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BookedRoomController extends AManagerRoomController {
    BookedRoomView bookedRoomView;
    BookedRoomService bookedRoomService;
    GuestRoomController guestRoomController;
    EmptyRoomController emptyRoomController;

    RefreshController refreshController;

    public BookedRoomController(ARoomModel aRoomModel, ARoomService aRoomService, ARoomView aRoomView) {
        super(aRoomModel, aRoomService, aRoomView);

    }

    public void setRefreshController(RefreshController refreshController) {
        this.refreshController = refreshController;
    }

    public void setEmptyRoomController(EmptyRoomController emptyRoomController) {
        this.emptyRoomController = emptyRoomController;
    }

    public void setGuestRoomController(GuestRoomController guestRoomController) {
        this.guestRoomController = guestRoomController;
    }

    @Override
    public void construcFirst() {
        bookedRoomView = (BookedRoomView) aRoomView;
        bookedRoomService = (BookedRoomService) aRoomService;
    }

    @Override
    public void loadDataInfoRoom() {
        BookedRoomService bookedRoomService = (BookedRoomService) super.aRoomService;
        for (RoomBookingFormModel roomBookingFormModel : bookedRoomService.getInforBookedRooms()) {
            ((BookedRoomView) this.aRoomView).setDataGuestRoomInformation(roomBookingFormModel);
        }


    }

    @Override
    public void setActionSaveForm() {
        for (ImageRoomView imageRoomView : this.aRoomView.getImageRoomViews()) {
            InfoCusBookedFormView infoCusBookedFormView = imageRoomView.getInfoCusBookedFormView();
            if (infoCusBookedFormView == null) return;
            infoCusBookedFormView.addButtonListener(new ButtonListener() {
                StatesRoomService statesRoomService = StatesRoomService.getInstanceDatabaseService();

                @Override
                public void buttonPerformed(RoomEvent roomEvent) {
                    String nameBtnClick = roomEvent.getNameBtn();
                    int codeRoom = Integer.parseInt(infoCusBookedFormView.getNumRoom());

                    switch (nameBtnClick) {
                        case "btnChangeRoom": {
                            bookedRoomView.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                            statesRoomService.updateStatesRoom(codeRoom, 2);
                            setGuestRoom(codeRoom);
                            bookedRoomView.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                            JOptionPane.showMessageDialog(infoCusBookedFormView, "Đã xác thực nhận phòng");
                            infoCusBookedFormView.dispose();
                            break;
                        }
                        case "btnCheckOut": {
                            bookedRoomView.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                            statesRoomService.updateStatesRoom(codeRoom, 1);
                            // xoa view phong
                            bookedRoomView.removeImgView(codeRoom);
                            emptyRoomController.setEmptyRoom(codeRoom);
                            bookedRoomView.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                            JOptionPane.showMessageDialog(infoCusBookedFormView, "Phòng đã được hủy");
                            infoCusBookedFormView.dispose();
                            break;
                        }
                    }

                }
            });
        }

    }



    private void setGuestRoom(int codeRoom) {
        // xoa view phong
        bookedRoomView.removeImgView(codeRoom);
        refreshController.resetViewRoom(guestRoomController.aRoomService,guestRoomController.guestRoomView, guestRoomController);
    }
}
