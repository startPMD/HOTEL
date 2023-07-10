package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class ARoomView extends JPanel {
    public ARoomView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }


    public abstract String getName();
    public JScrollPane createScrollPane(JComponent component, Dimension size) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setPreferredSize(size);
        return scrollPane;
    }
    public JPanel imgTypeRooms(ARoomView aRoomView, Map<Integer,Integer> numberRooms) {
        JPanel panelTypeRoom = new JPanel(new FlowLayout(FlowLayout.LEFT));

        for (Integer idRoom:numberRooms.keySet()) {
            Integer numRoom = numberRooms.get(idRoom);
            ImageRoomView imageRoomView = new ImageRoomView(aRoomView);
            imageRoomView.setIdNumRoom(idRoom);
            imageRoomView.setJlNumberRoom(numRoom);
            panelTypeRoom.add(imageRoomView);
        }
        return panelTypeRoom;
    }
    public void setListRoom(JPanel panel){
        this.add(panel);
    }
    public JPanel createPanelRoom(String typeRoom, JPanel panelLayoutRoom) {
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setLayout(new BorderLayout());

        JLabel jlTypeRoom = new JLabel();
        jlTypeRoom.setFont(new Font("Serif", Font.ROMAN_BASELINE, 19));

        TitledBorder titledBorder = new TitledBorder(typeRoom);
        jlTypeRoom.setBorder(titledBorder);

        panel.add(jlTypeRoom, BorderLayout.NORTH);
        panel.add(panelLayoutRoom, BorderLayout.CENTER);

        return panel;
    }
    public List<ImageRoomView> getImageRoomViews(){
        List<ImageRoomView> imageRoomViews = new ArrayList<>();
        for (int i = 0; i < this.getComponentCount(); i++) {
            ImageRoomView imageRoomView;
            JPanel panelLayout = (JPanel) ((JPanel) this.getComponent(i)).getComponent(1);
            for (int j = 0; j < panelLayout.getComponentCount(); j++) {
                imageRoomView = (ImageRoomView) panelLayout.getComponent(j);
                imageRoomViews.add(imageRoomView);
            }
        }
        return imageRoomViews;
    }
    public void removeImgView(int codeRoom){
        JPanel panelLayout = null;
        for (int i = 0; i < this.getComponentCount(); i++) {
             panelLayout = (JPanel) ((JPanel) this.getComponent(i)).getComponent(1);
            ImageRoomView imgRoom;
            for (int j = 0; j < panelLayout.getComponentCount(); j++) {
                imgRoom = (ImageRoomView) panelLayout.getComponent(j);
                if(imgRoom.getNumberRoom() == codeRoom){
                    imgRoom.setVisible(false);
                    panelLayout.remove(imgRoom);
                    return;
                }
            }
        }
    }
    public abstract ARoomView getObjectRoomView();


}
