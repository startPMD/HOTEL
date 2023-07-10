package controller;

import service.ARoomService;
import service.DatabaseService;
import service.EmptyRoomService;
import view.*;

import java.util.List;

public class RefreshController {
    ARoomService aRoomService;

    public RefreshController(ARoomService aRoomService) {
        this.aRoomService = aRoomService;

    }

    public void resetViewRoom(ARoomService aRoomService,ARoomView aRoomView, AManagerRoomController aManagerRoomController) {
        // cập nhật lại những phòng  hết thời gian đăng kí
        // cập nhật lại  cả các phòng hết hạn đăng kí
        aRoomService.updateStatestRoom();
        aRoomService.updateVacantRoom();
        // ve lai cac view phong
        aRoomView.removeAll(); //xóa đi view cũ
        if (!(aManagerRoomController instanceof RoomAllLayoutController))
            aManagerRoomController.creatAgainRooms();
        else
            ((RoomAllLayoutController) aManagerRoomController).loadStateRoom();
    }

}
