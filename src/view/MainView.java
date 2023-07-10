package view;

import controller.*;
import service.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class MainView extends JFrame {
    PanelLeftView panelLeft;
    PanelRightView panelRight;

    public MainView() {
        super("Main View");
        setLayout(new BorderLayout());
        this.panelLeft = new PanelLeftView();
        this.panelRight = new PanelRightView();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //<---------PANEL ROOM--------------------->
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

//<---------PANEL SERVICE--------------------->
        PanelManagerServiceView panelManagerServiceView = new PanelManagerServiceView();
        ServiceRoomService service = new ServiceRoomService();
        ServiceController serviceController = new ServiceController(panelManagerServiceView, service);

//<---------PANEL Manager Employee--------------------->
        PanelManagerEmployee employeePanel = new PanelManagerEmployee();
        ManagerEmployeeService managerEmployeeService = new ManagerEmployeeService();
        ManagerEmployeeController managerEmployeeController = new ManagerEmployeeController(employeePanel, managerEmployeeService);

//<---------PANEL Manager Payment--------------------->
        PanelManagerPayment panelManagerPayment = new PanelManagerPayment();
        ManagerPaymentService paymentService = new ManagerPaymentService();
        ManagerPaymentController managerPaymentController = new ManagerPaymentController(panelManagerPayment, paymentService);


//<---------Refresh view--------------------->
        ((EmptyRoomController) emptyRoomController).setGuestRoomController((GuestRoomController) guestRoomController);
        ((EmptyRoomController) emptyRoomController).setBookedRoomController((BookedRoomController) bookedRoomController);
        ((EmptyRoomController) emptyRoomController).setPanelManagerServiceView(panelManagerServiceView);

        ((BookedRoomController) bookedRoomController).setGuestRoomController((GuestRoomController) guestRoomController);
        ((BookedRoomController) bookedRoomController).setEmptyRoomController((EmptyRoomController) emptyRoomController);


        ((GuestRoomController) guestRoomController).setEmptyRoomController((EmptyRoomController) emptyRoomController);
        ((GuestRoomController) guestRoomController).setManagerPaymentController(managerPaymentController);

        serviceController.setGuestRoomController((GuestRoomController) guestRoomController);


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

        serviceController.setRefreshController(refreshController);

//<---------PANEL Main Right--------------------->
        panelRight.setPanelView(tabbebPaneRoomView);
        panelRight.setPanelView(panelManagerServiceView);
        panelRight.setPanelView(employeePanel);
        panelRight.setPanelView(panelManagerPayment);

//<---------PANEL Main Left--------------------->
        panelLeft.connectEventPanelLeftToPanelRight(panelRight);

        this.add(this.panelLeft, BorderLayout.WEST);
        this.add(this.panelRight, BorderLayout.CENTER);
        setSize(1245, 640);
    }

    public PanelLeftView getPanelLeft() {
        return panelLeft;
    }

    public void showGUI() {
        setVisible(true);
    }

    public static void main(String[] args) {
        MainView m = new MainView();
        m.showGUI();
    }
}