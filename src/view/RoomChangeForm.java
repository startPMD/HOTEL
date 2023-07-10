package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RoomChangeForm extends JFrame{
    private JLabel currentRoomLabel,currentCodeRoomLabel, newRoomLabel;
    private JComboBox<Integer> comboBoxCodeRoom;
    private JButton changeButton;

    public void setCodeRoom(Integer codeRoom){
        comboBoxCodeRoom.addItem(codeRoom);
    }
    public RoomChangeForm() {
        // Thiết lập tiêu đề
        setTitle("Đổi phòng");
        // Tạo các thành phần UI

        currentRoomLabel = new JLabel("Phòng hiện tại:");
        currentCodeRoomLabel = new JLabel();
        newRoomLabel = new JLabel("Phòng mới:");
        comboBoxCodeRoom = new JComboBox<>();
        changeButton = new JButton("Đổi phòng");

        // Tạo bố cục
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(5,5,5,5));
        mainPanel.setLayout(new GridLayout(3, 2));
        mainPanel.add(currentRoomLabel);
        mainPanel.add(currentCodeRoomLabel);
        mainPanel.add(newRoomLabel);
        mainPanel.add(comboBoxCodeRoom);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(changeButton);

        // Thêm các thành phần UI vào khung JFrame
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);


    }

    public void setCurrentCodeRoomJlb(int code) {
        this.currentCodeRoomLabel.setText(String.valueOf(code));
    }

    public JButton getChangeButton() {
        return changeButton;
    }
    public void showDetail(){
        // Thiết lập kích thước và vị trí của khung JFrame
        setSize(300, 150);
        setLocationRelativeTo(null); // Hiển thị cửa sổ giữa màn hình
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public int getCodeRoomCurren() {
        return Integer.parseInt(currentCodeRoomLabel.getText());
    }

    public int getCodeRoomNew() {
        return (int) comboBoxCodeRoom.getSelectedItem();
    }
}
