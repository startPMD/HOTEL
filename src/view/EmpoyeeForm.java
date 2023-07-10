package view;

import model.AccountAndEmployeeModel;
import model.EmployeeAccountModel;
import model.EmployeeModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.EventListenerList;
import java.awt.*;

public class EmpoyeeForm extends JFrame {
    private JLabel accountLabel, passwordLabel, nameLabel, emailLabel, phoneLabel, addressLabel, positionLabel;
    private JTextField accountField, nameField, emailField, phoneField, addressField;
    private JPasswordField passwordField;
    private JComboBox<String> positionComboBox,genderComboBox;
    private JCheckBox activateCheckBox;
    private JButton saveButton;

    public EmpoyeeForm() {
        setTitle("Đăng ký tài khoản");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel p1 = new JPanel();
        accountLabel = new JLabel("Tài khoản*:   ");
        accountField = new JTextField(50);
        accountField.setPreferredSize(new Dimension(300, 26));
        p1.add(accountLabel);
        p1.add(accountField);

        JPanel p2 = new JPanel();
        passwordLabel = new JLabel("Mật khẩu*:     ");
        passwordField = new JPasswordField(50);
        passwordField.setPreferredSize(new Dimension(100, 26));
        p2.add(passwordLabel);
        p2.add(passwordField);

        JPanel p3_p4 = new JPanel(new BorderLayout());
        JPanel p3 = new JPanel();
        nameLabel = new JLabel("Họ và tên*:    ");
        nameField = new JTextField(20);
        nameField.setPreferredSize(new Dimension(100, 26));
        p3.add(nameLabel);
        p3.add(nameField);
        JPanel p4 = new JPanel();
        emailLabel = new JLabel("Email*:     ");
        emailField = new JTextField(20);
        emailField.setPreferredSize(new Dimension(100, 26));
        p4.add(emailLabel);
        p4.add(emailField);
        p3_p4.add(p3, BorderLayout.LINE_START);
        p3_p4.add(p4, BorderLayout.LINE_END);

        JPanel pGender = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel jlbGender  = new JLabel("Giới tính*:     ");
        genderComboBox = new JComboBox<>(new String[]{"Nam","Nữ"});
        pGender.add(jlbGender);
        pGender.add(genderComboBox);

        JPanel p5 = new JPanel();
        phoneLabel = new JLabel("Điện thoại*:   ");
        phoneField = new JTextField(50);
        phoneField.setPreferredSize(new Dimension(300, 26));
        p5.add(phoneLabel);
        p5.add(phoneField);

        JPanel p6 = new JPanel();
        addressLabel = new JLabel("Địa chỉ*:         ");
        addressField = new JTextField(50);
        addressField.setPreferredSize(new Dimension(300, 26));
        p6.add(addressLabel);
        p6.add(addressField);

        JPanel p7 = new JPanel();
        positionLabel = new JLabel("Thuộc nhóm*:");
        positionComboBox = new JComboBox<>(new String[]{"Nhân viên lễ tân", "Nhân viên phục vụ", "Quản lý khách sạn", "Nhân viên bảo vệ"});
        p7.add(positionLabel);
        p7.add(positionComboBox);
        p7.add(new JLabel("                                                                                        " +
                "                         "));
        JPanel p8 = new JPanel();
        activateCheckBox = new JCheckBox();
        activateCheckBox.setHorizontalTextPosition(SwingConstants.RIGHT);
        p8.add(new JLabel("Kích hoạt"));
        p8.add(activateCheckBox);
        p8.add(new JLabel("                                                                         " +
                "                                                                 "));
        saveButton = new JButton("Lưu");
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        mainPanel.add(p1);
        mainPanel.add(p2);
        mainPanel.add(p3_p4);
        mainPanel.add(pGender);
        mainPanel.add(p5);
        mainPanel.add(p6);
        mainPanel.add(p7);
        mainPanel.add(p8);
        mainPanel.add(saveButton);
        add(mainPanel);
    }

    public JButton getSaveButton() {
        return saveButton;
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

    public AccountAndEmployeeModel getInfoEmployeeRegis() {
        String nameAccount = accountField.getText().trim();
        String passAccount = String.valueOf(passwordField.getPassword()).trim();
        if(nameAccount.isEmpty() || passAccount.isEmpty())
            return null;
        boolean isActive = activateCheckBox.isSelected();
        EmployeeAccountModel eA = new EmployeeAccountModel(nameAccount, passAccount, isActive);

        String fullNameCus = nameField.getText().trim();
        String genderCus = String.valueOf(genderComboBox.getSelectedItem());
        String emailCus = emailField.getText().trim();
        String phoneCus = phoneField.getText().trim();
        String addressCus = addressField.getText().trim();
        String positionCus = String.valueOf(positionComboBox.getSelectedItem());
        if(fullNameCus.isEmpty() || emailCus.isEmpty() || phoneCus.isEmpty() || addressCus.isEmpty())
            return null;
        EmployeeModel e = new EmployeeModel(-1, fullNameCus,genderCus, emailCus, phoneCus, addressCus, positionCus,null);

        AccountAndEmployeeModel aAeM = new AccountAndEmployeeModel(eA,e);
        System.out.println(aAeM.toString());

        return aAeM;
    }

    public void showForm(boolean visible) {
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(visible);
    }
    public void offFormCusRegis() {
        dispose();
    }
    public static void main(String[] args) {
        new EmpoyeeForm().showForm(true);
    }
}