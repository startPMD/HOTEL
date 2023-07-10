package view;

import javax.swing.*;
import java.awt.*;

public class PanelRightView extends JPanel {
    private CardLayout cardLayout;

    public PanelRightView() {
        this.setLayout(cardLayout = new CardLayout());
        setBackground(Color.orange);
    }

    public void setPanelView(JPanel panel) {
        String namePanel = panel.getName();
        if(namePanel.equalsIgnoreCase("TabbebPaneRoomView")){
            this.add(panel,namePanel);
            cardLayout.show(this,namePanel);
            return;
        }
        panel.setVisible(false);
        this.add(panel, namePanel);
    }

    public void visiblePanelFunc(String nameFunc) {
        switch (nameFunc) {
            case "QLP": {
                setVisibleFunc("TabbebPaneRoomView");
                break;
            }
            case "QLDV": {
                setVisibleFunc("PanelManagerServiceView");
                break;
            }
            case "QLNV": {
               setVisibleFunc("PanelManagerEmployee");
                break;
            }
            case "QLTT": {
                setVisibleFunc("PanelManagerPayment");
                break;
            }

        }
    }

    private void setVisibleFunc(String namePanel) {
        cardLayout.show(this, namePanel);
    }

}
