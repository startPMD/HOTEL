package view;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;

public class TabbebPaneRoomView extends JPanel {
    JTabbedPane tabbed;
    public TabbebPaneRoomView() {
        setName("TabbebPaneRoomView");
        setLayout(new BorderLayout());


        tabbed = new JTabbedPane(JTabbedPane.TOP);
        add(tabbed, BorderLayout.CENTER);

    }
    public void addTabeb(String nameTab, ARoomView aRoomView,boolean setScroll){
        Dimension dimension = null;
       if(setScroll){
           dimension = new Dimension(500,300);
        }
        this.tabbed.addTab(nameTab,aRoomView.createScrollPane(aRoomView ,dimension));
    }




}
