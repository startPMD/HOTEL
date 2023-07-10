package logging;

import view.MainView;
import view.PanelLeftView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginController {
    private UserEmployeeModel model;
    private LoginView view;
    private UserEmployeeService userService;
    private PanelLeftView panelLeftView;

    public LoginController(UserEmployeeModel model, LoginView view, UserEmployeeService userService) {
        this.model = model;
        this.view = view;
        this.userService = userService;

        view.addLoginListener(new LoginListener() {
            @Override
            public void loginPerformed(LoginEvent event) {
                String username = view.getUsername();
                String password = view.getPassword();
                UserEmployeeModel userEmployeeModel = userService.authenticateUser(username, password);
                if (userEmployeeModel != null) {
                    if(!userEmployeeModel.hasActive()){
                        view.setStatus("Tài khoản chưa được kích hoạt");
                        return;
                    }
                    view.removePasswordField();

                    MainView main = new MainView();

                    main.getPanelLeft().setInfoEmployee(userEmployeeModel.getFullName(), userEmployeeModel.getPosition());
                    main.showGUI();

                    view.setStatus("");
                    view.dispose();

                    main.getPanelLeft().getLogOut().addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            super.mouseClicked(e);
                            int response = JOptionPane.showConfirmDialog(null, "Bạn có muốn đăng xuất không?", "Xác nhận đăng xuất", JOptionPane.YES_NO_OPTION);
                            if (response == JOptionPane.YES_OPTION) {
                                main.dispose();
                                view.setVisible(true);
                            }
//                                else {
//                                    // Không thực hiện đăng xuất
//                                }

                        }
                    });

                } else {
                    view.setStatus("Tên đăng nhập hoặc mật khẩu không đúng");
                }
            }
        });
    }
}