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

    public static void main(String[] args) {
        TabbebPaneRoomView tabbebPaneRoomView = new TabbebPaneRoomView();

        RoomAllLayoutView roomAllLayoutView = new RoomAllLayoutView();
        ARoomView emptyRoomView = new EmptyRoomView();
        ARoomView guestRoomView = new GuestRoomView();
        ARoomView bookedRoomView = new BookedRoomView();
        tabbebPaneRoomView.addTabeb(roomAllLayoutView.getName(), roomAllLayoutView, false);
        tabbebPaneRoomView.addTabeb(emptyRoomView.getName(), emptyRoomView, true);
        tabbebPaneRoomView.addTabeb(guestRoomView.getName(), guestRoomView, true);
        tabbebPaneRoomView.addTabeb(bookedRoomView.getName(), bookedRoomView, true);

        ARoomService layoutRoomService = new LayoutRoomService();
        ARoomService emptyRoomService = new EmptyRoomService();
        ARoomService guestRoomService = new GuestRoomService();
        ARoomService bookedRoomService = new BookedRoomService();


        AManagerRoomController roomAllLayoutController = new RoomAllLayoutController(null, layoutRoomService, roomAllLayoutView);
        AManagerRoomController emptyRoomController = new EmptyRoomController(null, emptyRoomService, emptyRoomView);
        AManagerRoomController guestRoomController = new GuestRoomController(null, guestRoomService, guestRoomView);
        AManagerRoomController bookedRoomController = new BookedRoomController(null, bookedRoomService, bookedRoomView);




        ((EmptyRoomController) emptyRoomController).setGuestRoomController((GuestRoomController) guestRoomController);
        ((EmptyRoomController) emptyRoomController).setBookedRoomController((BookedRoomController) bookedRoomController);

        ((BookedRoomController) bookedRoomController).setGuestRoomController((GuestRoomController) guestRoomController);
        ((BookedRoomController) bookedRoomController).setEmptyRoomController((EmptyRoomController) emptyRoomController);


        ((GuestRoomController) guestRoomController).setEmptyRoomController((EmptyRoomController) emptyRoomController);



//-----------------------------------------------------------------
        List<ARoomView> aRoomViews = new ArrayList<>();
        aRoomViews.add(emptyRoomView);
        aRoomViews.add(guestRoomView);
        aRoomViews.add(bookedRoomView);

        List<AManagerRoomController> aManagerRoomControllers = new ArrayList<>();
        aManagerRoomControllers.add(roomAllLayoutController);
        aManagerRoomControllers.add(emptyRoomController);
        aManagerRoomControllers.add(guestRoomController);
        aManagerRoomControllers.add(bookedRoomController);

        RefreshController refreshController = new RefreshController(null);
        ((GuestRoomController) guestRoomController).setRefreshController(refreshController);
        ((EmptyRoomController) emptyRoomController).setRefreshController(refreshController);
        ((BookedRoomController) bookedRoomController).setRefreshController(refreshController);

//-----------------------------------------------------------------


        JFrame f = new JFrame();
        f.add(tabbebPaneRoomView);
        f.setSize(700, 600);
        f.setVisible(true);

    }
}
