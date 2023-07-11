package controller;

import model.AccountAndEmployeeModel;
import service.ManagerEmployeeService;
import view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ManagerEmployeeController {
    private PanelManagerEmployee panelManagerEmployee;
    private ManagerEmployeeService managerEmployeeServicel;

    public ManagerEmployeeController(PanelManagerEmployee panelManagerEmployee, ManagerEmployeeService managerEmployeeServicel) {
        this.panelManagerEmployee = panelManagerEmployee;
        this.managerEmployeeServicel = managerEmployeeServicel;
        loadDataEmployeeToTable();
        eventManagerEmployee();
    }

    private void loadDataEmployeeToTable() {
        for (AccountAndEmployeeModel ae : managerEmployeeServicel.getListEmployee()) {
            panelManagerEmployee.setDataEmployeeTable(ae);
        }
    }
    private void eventManagerEmployee(){
        this.panelManagerEmployee.addButtonListener(new ButtonListener() {
            @Override
            public void buttonPerformed(RoomEvent roomEvent) {
                switch (roomEvent.getNameBtn()) {
                    case "editButton": {
                        if (!panelManagerEmployee.selectedRowOne()){
                            JOptionPane.showMessageDialog(null, "Để thực hiện chức năng này.Vui lòng chỉ chọn một hàng!");
                            return;
                        }
                        else {
                            EditForm editForm = panelManagerEmployee.getEditForm();
                            panelManagerEmployee.loadDataEmployeeToEditForm();
                            editForm.showForm(true);
                            editForm.getSaveButton().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    Map<String,String> values = editForm.valueChanges();

                                    boolean updateSuccess =  managerEmployeeServicel.updateInfoEmployee(values);
                                    if(updateSuccess){
                                        panelManagerEmployee.delAllRow();
                                        loadDataEmployeeToTable();
                                        editForm.messSaveSuccess(updateSuccess);
                                    }
                                }
                            });

                        }
                        break;
                    }
                    case "deleteButton": {
                        List<AccountAndEmployeeModel> accountAndEmployeeModels = panelManagerEmployee.getCustomerSelected();
                        if (accountAndEmployeeModels.size() == 0) {
                            JOptionPane.showMessageDialog(null, "Hãy chọn các hàng muốn xóa.");
                            return;
                        }
                        int choice = JOptionPane.showOptionDialog(null, "Bạn có muốn xóa các hàng đã chọn không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                        if (choice == JOptionPane.YES_OPTION) {
                            boolean delSuccess = false;
                            // xóa account và thông tin nhân viên
                            for (AccountAndEmployeeModel e : accountAndEmployeeModels) {
                                try {
                                    delSuccess = managerEmployeeServicel.delEmployeeSelected(e);
                                } catch (SQLException ex) {
                                    throw new RuntimeException(ex);
                                }
                                if (!delSuccess) {
                                    JOptionPane.showMessageDialog(null, "Thao tác thực hiện không thành công!");
                                    return;
                                }
                                else
                                    // vẽ lại view
                                    panelManagerEmployee.removeRowEmployee(e.getAccount().getNameAccount());
                            }
                            if(delSuccess)
                                JOptionPane.showMessageDialog(null, "Thông tin nhân viên đã được xóa!");
                        }
                        break;
                    }
                    case "saveEmployeeButton": {
                        AccountAndEmployeeModel employeeRegis = panelManagerEmployee.getUserForm().getInfoEmployeeRegis();
                        String nameAccount = employeeRegis.getAccount().getNameAccount();
                        // kích hoạt tài khoản
                        if(employeeRegis.getAccount().isActiveAccount() && employeeRegis.getEmployee().isActiveAccount()){
                            managerEmployeeServicel.activeAccount(nameAccount,employeeRegis.getAccount().getPassAccount());
                            return;
                        }
                        if (employeeRegis == null) {
                            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin !");
                            return;
                        }
                        boolean nameAccountExit = managerEmployeeServicel.hasNameAccount(nameAccount);
                        if(nameAccountExit){
                            JOptionPane.showMessageDialog(null, "Tài khoản đã tồn tại!");
                            return;
                        }
                        boolean success = managerEmployeeServicel.insertAccountAndEmployee(employeeRegis);
                        if (success) {
                            panelManagerEmployee.getUserForm().offFormCusRegis();
                            JOptionPane.showMessageDialog(null, "Thông tin đã được lưu");
                            // set data success to table
                            panelManagerEmployee.setDataEmployeeTable(employeeRegis);
                            return;
                        } else JOptionPane.showMessageDialog(null, "Thông tin không được lưu thành công");
                        break;
                    }
                    case "changePasswordButton": {
                        if (!panelManagerEmployee.selectedRowOne())
                            JOptionPane.showMessageDialog(null, "Để thực hiện chức năng này.Vui lòng chỉ chọn một hàng!");
                        else {
                            ChangePasswordForm changePasswordForm = panelManagerEmployee.getChangePasswordForm();
                            changePasswordForm.showForm(true);

                            changePasswordForm.getSubmitButton().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    String userName = panelManagerEmployee.getUserName();
                                    String currentPassword = changePasswordForm.getCurrentPasswordField();
                                    String newPassword = changePasswordForm.getNewPasswordField();
                                    String confirmPassword = changePasswordForm.getConfirmPasswordField();

                                    boolean hasValidate = changePasswordForm.hasInvalidatePass(currentPassword, newPassword, confirmPassword);
                                    if (!hasValidate) {
                                        boolean changePassSuccess = managerEmployeeServicel.changePassEmployee(userName, currentPassword, newPassword);
                                        changePasswordForm.messChangeSuccess(changePassSuccess);
                                    }
                                }
                            });
                            changePasswordForm.getCancelButton().addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    changePasswordForm.dispose();
                                }
                            });
                        }

                        break;
                    }
                }
            }
        });
    }
    public static void main(String[] args) {
        PanelManagerEmployee employeePanel = new PanelManagerEmployee();
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(employeePanel);
        frame.pack();
        frame.setVisible(true);
        ManagerEmployeeService managerEmployeeService = new ManagerEmployeeService();
        ManagerEmployeeController managerEmployeeController = new ManagerEmployeeController(employeePanel, managerEmployeeService);

    }
}
