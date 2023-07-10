import logging.LoginController;
import logging.LoginView;
import logging.UserEmployeeService;
import logging.UserEmployeeModel;

import java.sql.SQLException;

public class RunMain {

    public static void main(String[] args) throws SQLException {
        UserEmployeeModel userModel = new UserEmployeeModel();
        LoginView loginView = new LoginView();
        UserEmployeeService service = new UserEmployeeService();

        LoginController loginController = new LoginController(userModel, loginView, service);
        loginView.setVisible(true);

    }
}
