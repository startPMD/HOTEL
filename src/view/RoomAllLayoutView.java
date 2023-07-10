package view;


import model.LayoutRoomModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.EventListenerList;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RoomAllLayoutView extends ARoomView {
    JPanel panelTop ;
    JPanel panelCenter;
    JPanel paneImg;
    String[] topImg = {"img/home.png","img/emptyRoom.png","img/guestRoom.png","img/bookedRoom.png","img/broom.png","img/repair.png"};
    JLabel[] jlImgLabel = new JLabel[6];
    JButton btnFindCus,btnChangeStateRoom;

    public RoomAllLayoutView() {
        setLayout(new BorderLayout());
        panelTop = new JPanel(new BorderLayout());
        createIconTop();

        btnFindCus = new JButton("Tìm khách hàng đặt phòng");
        btnFindCus.addActionListener(e -> buttonEvent(""));
        panelTop.add(btnFindCus);

        btnChangeStateRoom = new JButton("Tìm khách hàng đặt phòng");
        btnChangeStateRoom.addActionListener(e -> buttonEvent(""));
        panelTop.add(btnChangeStateRoom);


        add(panelTop,BorderLayout.NORTH);


        panelCenter = new JPanel();
        panelCenter.setLayout(new BoxLayout(panelCenter,BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panelCenter);
        scrollPane.setPreferredSize(new Dimension(300,400));
        add(scrollPane,BorderLayout.CENTER);
    }
    public void setTextHome(String s){
        jlImgLabel[0].setText(s);
    }
    public void setTextEmpty(String s){
        jlImgLabel[1].setText(s);
    }
    public void setTextGuest(String s){
        jlImgLabel[2].setText(s);
    }
    public void setTextBooked(String s){
        jlImgLabel[3].setText(s);
    }
    public void setTextBroom(String s){
        jlImgLabel[4].setText(s);
    }
    public void setTextRepair(String s){
        jlImgLabel[5].setText(s);
    }
    private void createIconTop() {
        paneImg = new JPanel();

        jlImgLabel[0] = new JLabel();
        jlImgLabel[0].setBorder(new EmptyBorder(0,0,0,6));
        jlImgLabel[0].setToolTipText("Tổng số phòng");
        jlImgLabel[0].setIcon(setSizeImg(topImg[0],25,23));
        paneImg.add(jlImgLabel[0]);

        jlImgLabel[1] = new JLabel();
        jlImgLabel[1].setBorder(new EmptyBorder(0,0,0,6));
        jlImgLabel[1].setToolTipText("Số phòng trống");
        jlImgLabel[1].setIcon(setSizeImg(topImg[1],20,20));
        paneImg.add(jlImgLabel[1]);

        jlImgLabel[2] = new JLabel();
        jlImgLabel[2].setBorder(new EmptyBorder(0,0,0,6));
        jlImgLabel[2].setToolTipText("Số phòng có khách");
        jlImgLabel[2].setIcon(setSizeImg(topImg[2],20,20));
        paneImg.add(jlImgLabel[2]);

        jlImgLabel[3] = new JLabel();
        jlImgLabel[3].setBorder(new EmptyBorder(0,0,0,6));
        jlImgLabel[3].setToolTipText("Số phòng được đặt");
        jlImgLabel[3].setIcon(setSizeImg(topImg[3],20,20));
        paneImg.add(jlImgLabel[3]);

        jlImgLabel[4] = new JLabel();
        jlImgLabel[4].setBorder(new EmptyBorder(0,0,0,6));
        jlImgLabel[4].setToolTipText("Số phòng chưa dọn");
        jlImgLabel[4].setIcon(setSizeImg(topImg[4],20,20));
        paneImg.add(jlImgLabel[4]);

        jlImgLabel[5] = new JLabel();
        jlImgLabel[5].setBorder(new EmptyBorder(0,0,0,6));
        jlImgLabel[5].setToolTipText("Số phòng đang bảo trì");
        jlImgLabel[5].setIcon(setSizeImg(topImg[5],20,20));
        paneImg.add(jlImgLabel[5]);
        panelTop.add(paneImg,BorderLayout.EAST);
    }
    private ImageIcon setSizeImg(String s,int width,int height){
        return  new ImageIcon(new ImageIcon(s).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
    }
    @Override
    public String getName() {
        return "Sơ đồ phòng";
    }

    @Override
    public ARoomView getObjectRoomView() {
        return null;
    }

    public JPanel createItemRoom(LayoutRoomModel layoutRoomModel) {
        JPanel psub = new JPanel();
        psub.setLayout(new BoxLayout(psub, BoxLayout.Y_AXIS));
        psub.setBackground(Color.GREEN);
        psub.setBorder(new EmptyBorder(5, 5, 5, 5));

        JLabel codeRoom = new JLabel(layoutRoomModel.getNumberRoom() + "");
        codeRoom.setFont(new Font("Serif", Font.BOLD, 20));
        codeRoom.setHorizontalAlignment(JLabel.CENTER);
        codeRoom.setForeground(Color.WHITE);

        JLabel typeRoom = new JLabel("Loại phòng: " + layoutRoomModel.getTypeRoom());
        typeRoom.setForeground(Color.WHITE);
        JLabel numGuestRoom = new JLabel("Khách ở tối đa: " + layoutRoomModel.getNumGuestRoom());
        numGuestRoom.setForeground(Color.WHITE);
        JLabel priceRoom = new JLabel("Giá phòng: " + layoutRoomModel.getPriceRoom() + " VND");
        priceRoom.setForeground(Color.WHITE);

        psub.add(codeRoom);
        psub.add(typeRoom);
        psub.add(numGuestRoom);
        psub.add(priceRoom);

        return psub;
    }
    public void createRoomLayout(String location, JPanel panelContainItem, List<LayoutRoomModel> infoRooms) {
        JPanel panelLayout = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelLayout.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel t1 = new JLabel(location);
        t1.setForeground(Color.decode("#5690E8"));
        t1.setFont(new Font("Serif", Font.BOLD, 15));

        for (LayoutRoomModel layoutRoomModel : infoRooms) {
            panelContainItem.add(createItemRoom(layoutRoomModel));
        }
        panelLayout.add(t1);
        panelLayout.add(panelContainItem);
        panelCenter.add(panelLayout);
    }
    private EventListenerList listenerList = new EventListenerList();

    public void addButtonListener(ButtonListener listener) {
        listenerList.add(ButtonListener.class, listener);
    }

    protected void buttonEvent(String name) {
        ButtonListener[] listeners = listenerList.getListeners(ButtonListener.class);
        for (ButtonListener listener : listeners) {
            listener.buttonPerformed(new RoomEvent(this, name));
        }
    }

}
