package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChangePasswordForm extends JFrame  {
    private JLabel currentPasswordLabel, newPasswordLabel, confirmPasswordLabel;
    private JPasswordField currentPasswordField, newPasswordField, confirmPasswordField;
    private JButton submitButton, cancelButton;

    public ChangePasswordForm() {
        setTitle("Đổi mật khẩu");
        setSize(400, 200);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);

        currentPasswordLabel = new JLabel("Mật khẩu hiện tại: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(currentPasswordLabel, constraints);

        currentPasswordField = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(currentPasswordField, constraints);

        newPasswordLabel = new JLabel("Mật khẩu mới: ");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(newPasswordLabel, constraints);

        newPasswordField = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(newPasswordField, constraints);

        confirmPasswordLabel = new JLabel("Xác nhận mật khẩu mới: ");
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(confirmPasswordLabel, constraints);

        confirmPasswordField = new JPasswordField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(confirmPasswordField, constraints);

        Cursor c = new Cursor(Cursor.HAND_CURSOR);

        submitButton = new JButton("Đổi mật khẩu");
        submitButton.setCursor(c);
//        submitButton.addActionListener(this);
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        panel.add(submitButton, constraints);

        cancelButton = new JButton("Hủy");
        cancelButton.setCursor(c);
//        cancelButton.addActionListener(this);
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        panel.add(cancelButton, constraints);

        add(panel);
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public boolean hasInvalidatePass(String currentPassword, String newPassword, String confirmPassword) {
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin.", "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
            return true;
        } else if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu mới và xác nhận mật khẩu không trùng khớp.", "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        return false;
    }

    public void messChangeSuccess(boolean hasPass) {
        if (!hasPass)
            JOptionPane.showMessageDialog(this, "Mật khẩu hiện tại không chính xác.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        else {
            JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }

    public String getCurrentPasswordField() {
        return new String(currentPasswordField.getPassword());
    }

    public String getNewPasswordField() {
        return new String(newPasswordField.getPassword());
    }

    public String getConfirmPasswordField() {
        return new String(confirmPasswordField.getPassword());
    }

    public void showForm(boolean visible) {
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(visible);
        if (!visible)
            dispose();
    }
}