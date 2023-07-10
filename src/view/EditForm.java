package view;

import model.EmployeeModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class EditForm extends JFrame {
    // Khai báo các thành phần giao diện
    private JLabel nameLabel, emailLabel, phoneLabel, addressLabel, positionLabel, salaryLabel;
    private JLabel nameValue, emailValue, phoneValue, addressValue, positionValue, salaryValue, idValue;
    private JTextField emailInput, phoneInput, addressInput;
    private JButton saveButton;
    private JComboBox<String> positionComboBox;
    private JFormattedTextField salaryInput;

    public EditForm() {
        // Thiết lập tiêu đề của form
        super("Sửa đổi thông tin");

        // Tạo JPanel để chứa các thành phần giao diện
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        // Khởi tạo các thành phần giao diện
        nameLabel = new JLabel("Tên:");
        emailLabel = new JLabel("Email:");
        phoneLabel = new JLabel("Điện thoại:");
        addressLabel = new JLabel("Địa chỉ:");
        positionLabel = new JLabel("Vị trí:");
        salaryLabel = new JLabel("Lương:");

        idValue = new JLabel();
        nameValue = new JLabel(); // Giá trị tên không thay đổi nên sử dụng JLabel để hiển thị
        emailValue = new JLabel();
        phoneValue = new JLabel();
        addressValue = new JLabel();
        positionValue = new JLabel();
        salaryValue = new JLabel();

        emailInput = new JTextField();
        phoneInput = new JTextField();
        addressInput = new JTextField();
        positionComboBox = new JComboBox<>(new String[]{"", "Nhân viên lễ tân", "Nhân viên phục vụ", "Quản lý khách sạn", "Nhân viên bảo vệ"});
        salaryInput = new JFormattedTextField();

        // Tạo một NumberFormatter để định dạng giá trị tiền tệ
        NumberFormat format = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setAllowsInvalid(false);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);

        // Thiết lập NumberFormatter cho JFormattedTextField
        salaryInput.setFormatterFactory(new DefaultFormatterFactory(formatter));

        saveButton = new JButton("Lưu");
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        FlowLayout flowLeft = new FlowLayout(FlowLayout.LEFT);
        // Thêm các thành phần giao diện vào panel
        JPanel p1 = new JPanel(flowLeft);
        p1.add(nameLabel);
        p1.add(nameValue);
        panel.add(p1);
        panel.add(new JLabel());

        JPanel p2 = new JPanel(flowLeft);
        p2.add(emailLabel);
        p2.add(emailValue);
        panel.add(p2);
        panel.add(emailInput);

        JPanel p3 = new JPanel(flowLeft);
        p3.add(phoneLabel);
        p3.add(phoneValue);
        panel.add(p3);
        panel.add(phoneInput);

        JPanel p4 = new JPanel(flowLeft);
        p4.add(addressLabel);
        p4.add(addressValue);
        panel.add(p4);
        panel.add(addressInput);

        JPanel p5 = new JPanel(flowLeft);
        p5.add(positionLabel);
        p5.add(positionValue);
        panel.add(p5);
        panel.add(positionComboBox);

        JPanel p6 = new JPanel(flowLeft);
        p6.add(salaryLabel);
        p6.add(salaryValue);
        panel.add(p6);
        panel.add(salaryInput);


        // Thêm panel vào form
        add(panel, BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);
        // Thiết lập kích thước của form và hiển thị lên màn hình
        eventBtnClose();
    }

    public void setInfoEmployeeCurrent(EmployeeModel employeeModel) {


        String positionState = employeeModel.getPosition().equals("")? "Chưa cập nhật" : employeeModel.getPosition();
        System.out.println(positionState.trim());
        idValue.setText(String.valueOf(employeeModel.getId()));
        nameValue.setText(employeeModel.getName());
        emailValue.setText(employeeModel.getEmail());
        phoneValue.setText(employeeModel.getNumberPhone());
        addressValue.setText(employeeModel.getAddress());
        positionValue.setText(positionState);
        salaryValue.setText(employeeModel.getSalary() == null ? "Chưa cập nhật" : salaryFormat(employeeModel.getSalary()));
    }

    public String salaryFormat(BigDecimal decimal) {
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        String formattedValue = formatter.format(decimal);
        return formattedValue;
    }

    public Map<String, String> valueChanges() {
        Map<String, String> m = new LinkedHashMap<>();
        String email = emailInput.getText();
        String phone = phoneInput.getText();
        String address = addressInput.getText();
        String position = (String) positionComboBox.getSelectedItem();
        String salary = salaryInput.getText();

        if (!email.isEmpty()) m.put("email", email);
        if (!phone.isEmpty()) m.put("phone", phone);
        if (!address.isEmpty()) m.put("address", address);
        if (!position.isEmpty()) m.put("position", position);
        if (!salary.isEmpty()) m.put("salary", salary);
        m.put("id", idValue.getText());
        return m;
    }

    public void showForm(boolean visible) {
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(visible);
    }

    public void eventBtnClose() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                int option = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn đóng cửa sổ này không?", "Xác nhận đóng cửa sổ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.YES_OPTION) {
                    salaryInput.setValue(null);
                    dispose();
                }
            }
        });
    }

    public void messSaveSuccess(boolean sucess) {
        if (sucess) {
            JOptionPane.showMessageDialog(this, "Đã lưu thông tin thành công!");
            dispose();
        } else
            JOptionPane.showMessageDialog(this, "Cập nhật thông tin không thành công", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    public JButton getSaveButton() {
        return saveButton;
    }
}