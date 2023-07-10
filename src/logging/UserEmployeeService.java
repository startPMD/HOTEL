package logging;

import service.DatabaseService;

import java.sql.*;

public class UserEmployeeService {
    private DatabaseService databaseService;

    public UserEmployeeService() {
        databaseService = DatabaseService.getInstanceDatabaseService();
    }

    public UserEmployeeModel authenticateUser(String username, String password) {
        UserEmployeeModel userModel = null;
        String sql = "SELECT e.full_name,e.position,active_account FROM users u " +
                "JOIN employee e ON e.id = u.employee_id  WHERE username = '"+username+"' AND password = '"+password+"'";
        try (ResultSet rs = databaseService.executeQuery(sql)) {
            while (rs.next()) {
                String name = rs.getString("full_name");
                String position = rs.getString("position");
                int active = rs.getInt("active_account");
                userModel = new UserEmployeeModel(name,position,active);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userModel;
    }
    public static void main(String[] args)  {
        UserEmployeeService u = new UserEmployeeService();
      System.err.println( u.authenticateUser("user","2"));

        }
}