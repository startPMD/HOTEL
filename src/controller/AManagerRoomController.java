package controller;

import model.ARoomModel;
import service.*;
import view.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public abstract class AManagerRoomController {
    protected ARoomModel aRoomModel;
    protected ARoomService aRoomService;
    protected ARoomView aRoomView;

    public AManagerRoomController(ARoomModel aRoomModel, ARoomService aRoomService, ARoomView aRoomView) {
        this.aRoomModel = aRoomModel;
        this.aRoomService = aRoomService;
        this.aRoomView = aRoomView;
        construcFirst();
        loadDataRoomToView(aRoomView);
        loadDataInfoRoom();
        setActionSaveForm();
    }

    public void loadDataRoomToView(ARoomView aRoomView) {
        if (this.aRoomService.getRoomList() != null) {
            for (ARoomModel aRoomModel : this.aRoomService.getRoomList()) {
                JPanel panelViewRoom = aRoomView.imgTypeRooms(aRoomView, aRoomModel.getNumberRooms());
                aRoomView.setListRoom(aRoomView.createPanelRoom(aRoomModel.getTypeRoom(), panelViewRoom));
            }
        }
    }

    public abstract void construcFirst();

    public abstract void loadDataInfoRoom();

    public abstract void setActionSaveForm();

    public void creatAgainRooms() {
        loadDataRoomToView(aRoomView);
        loadDataInfoRoom();
        setActionSaveForm();
    }
}
