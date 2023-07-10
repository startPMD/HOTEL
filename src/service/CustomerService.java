package service;

import model.CustomerModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerService {
    public static CustomerService instanceCustomerService = null;
    private DatabaseService databaseService;

    public CustomerService() {
        databaseService = DatabaseService.getInstanceDatabaseService();
    }

    public static CustomerService getInstanceCustomerService() {
        if (instanceCustomerService == null)
            instanceCustomerService = new CustomerService();
        return instanceCustomerService;
    }

    public int getIdNumberCus(String idNumberCus) {
        String query = "SELECT id FROM customer WHERE identity_number = '" + idNumberCus + "'";
        try (ResultSet rs = this.databaseService.executeQuery(query)) {
            if (rs.next())
                return rs.getInt("id");
            else return -1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // return id customer new
    public void insertCustomerNew(CustomerModel customerModel) {
        String sql = "INSERT INTO customer (name, phone, address, email, identity_number,date_at) VALUES (?, ?, ?, ?, ?,?)";
        boolean insertSuccess;
        try (PreparedStatement statement = this.databaseService.getPreparedStatement(sql)) {
            statement.setString(1, customerModel.getName());
            statement.setString(2, customerModel.getPhone());
            statement.setString(3, customerModel.getAddress());
            statement.setString(4, customerModel.getEmail());
            statement.setString(5, customerModel.getIdNumber());
            statement.setTimestamp(6, customerModel.getTimeStartDay());


            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
