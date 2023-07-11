package service;

import model.*;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ManagerEmployeeService {
    private DatabaseService databaseService = null;

    public ManagerEmployeeService() {
        databaseService = DatabaseService.getInstanceDatabaseService();
    }


    public boolean updateStatesRoom(int numberRoom, int codeState) {
        String query = "UPDATE room SET status = ? WHERE room_number = ?";
        try (PreparedStatement statement = databaseService.getPreparedStatement(query)) {
            statement.setInt(1, codeState);
            statement.setInt(2, numberRoom);
            int count = statement.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateStateBookedRoom(String idNumber, String bookedTime) {
        String query = "UPDATE book_a_room SET state = 6 " +
                "WHERE customer_id IN(SELECT id FROM customer WHERE identity_number = ?) " +
                "AND start_date = ?";
        try (PreparedStatement statement = databaseService.getPreparedStatement(query)) {
            statement.setString(1, idNumber);
            statement.setString(2, bookedTime);
            int count = statement.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<AccountAndEmployeeModel> getListEmployee() {
        List<AccountAndEmployeeModel> l = new ArrayList<>();
        String query = "SELECT e.id,u.username,e.full_name,e.email,e.phone,e.address,e.position,u.active_account,e.salary FROM users u " +
                "JOIN employee e ON e.id = u.employee_id";
        try (ResultSet rs = this.databaseService.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("full_name");
                String email = rs.getNString("email");
                String numberPhone = rs.getString("phone");
                String address = rs.getNString("address");
                String position = rs.getNString("position");
                BigDecimal salary = rs.getBigDecimal("salary");
                EmployeeModel e = new EmployeeModel(id, name, null, email, numberPhone, address, position, salary);

                String nameAccount = rs.getString("username");
                int stateAccount = rs.getInt("active_account");
                EmployeeAccountModel a = new EmployeeAccountModel(nameAccount, null, stateAccount == 1);
                AccountAndEmployeeModel ae = new AccountAndEmployeeModel(a, e);
                l.add(ae);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return l;
    }

    public boolean delEmployeeSelected(AccountAndEmployeeModel ae) throws SQLException {
        try {
            databaseService.getConnection().setAutoCommit(false);

            String sql1 = "DELETE FROM users WHERE username = ?";
            PreparedStatement stmt1 = databaseService.getPreparedStatement(sql1);
            stmt1.setString(1, ae.getAccount().getNameAccount());
            int c2 = stmt1.executeUpdate();

            String sql = "DELETE FROM employee WHERE phone = ? AND email = ?";
            PreparedStatement stmt = databaseService.getPreparedStatement(sql);
            stmt.setString(1, ae.getEmployee().getNumberPhone());
            stmt.setString(2, ae.getEmployee().getEmail());
            int c1 = stmt.executeUpdate();
            databaseService.getConnection().commit();
            if (c2 > 0) {
                return true;
            }
        } catch (SQLException e) {
            if (databaseService.getConnection() != null) {
                databaseService.getConnection().rollback(); // Hoàn tác các thay đổi nếu có lỗi xảy ra
            }
            e.printStackTrace();
        } finally {
            if (databaseService.getConnection() != null) {
                databaseService.getConnection().setAutoCommit(true); // Bật lại chế độ tự động commit
//                databaseService.getConnection().close(); // Đóng kết nối
            }
        }
        return false;
    }

    public boolean insertAccountAndEmployee(AccountAndEmployeeModel employeeRegis) {
        String sql = "INSERT INTO employee (full_name,gender, address, phone, email,position) VALUES (?, ? ,?, ?, ?, ?)";

        try (PreparedStatement statement1 = this.databaseService.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            EmployeeModel employeeModel = employeeRegis.getEmployee();
            statement1.setString(1, employeeModel.getName());
            statement1.setString(2, employeeModel.getGender());
            statement1.setString(3, employeeModel.getAddress());
            statement1.setString(4, employeeModel.getNumberPhone());
            statement1.setString(5, employeeModel.getEmail());
            statement1.setString(6, employeeModel.getPosition());
            statement1.executeUpdate();

            // Lấy ID của bản ghi mới được thêm vào bảng employee
            ResultSet rs = statement1.getGeneratedKeys();
            int employeeId = -1;
            if (rs.next()) {
                employeeId = rs.getInt(1);
                if (employeeId == -1) return false;
            }
            // Thêm một bản ghi mới cho bảng account_employee
            String sql2 = "INSERT INTO users (active_account, username, password, employee_id) VALUES (?, ?, ?, ?)";
            PreparedStatement statement2 = this.databaseService.getPreparedStatement(sql2);
            EmployeeAccountModel accountModel = employeeRegis.getAccount();
            statement2.setInt(1, accountModel.getStateActive());
            statement2.setString(2, accountModel.getNameAccount());
            statement2.setString(3, accountModel.getPassAccount());
            statement2.setInt(4, employeeId);
            int count = statement2.executeUpdate();

            if (count < 1) return false;

            if (statement1 != null) {
                statement1.close();
            }
            if (statement2 != null) {
                statement2.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public boolean changePassEmployee(String userName, String currentPassword, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE username = ? AND password = ?";
        try (PreparedStatement statement = databaseService.getPreparedStatement(sql)) {
            statement.setString(1, newPassword);
            statement.setString(2, userName);
            statement.setString(3, currentPassword);
            int count = statement.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateInfoEmployee(Map<String, String> values) {
        String sql = "UPDATE employee SET ";
        String setClause = values.entrySet().stream()
                .filter(entry -> !entry.getKey().equals("id")) // Lọc ra các cặp khóa-giá trị mà không có khóa "id"
                .map(entry -> entry.getKey() + " = ?")
                .collect(Collectors.joining(", "));
        sql += setClause + " WHERE id = ?";
        System.out.println(sql);
        try (PreparedStatement statement = databaseService.getPreparedStatement(String.valueOf(sql))) {
            int index = 1;
            for (Map.Entry<String, String> entry : values.entrySet()) {
                if(entry.getKey().equals("salary")){
                    statement.setBigDecimal(index,new BigDecimal(entry.getValue().replace(".", "")));
                }
               else if (entry.getKey().equals("id")){
                    statement.setInt(index, Integer.parseInt(entry.getValue()));
                }

               else{
                    System.out.println(entry.getValue().replace(".", ""));
                    statement.setString(index, entry.getValue());
                }

                index++;

            }
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void activeAccount(String nameAccount, String passAccount) {
        String sql = "UPDATE users SET active_account = 1 WHERE username = ? AND password = ?";
        try (PreparedStatement statement = databaseService.getPreparedStatement(sql)) {
            statement.setString(1, nameAccount);
            statement.setString(2, passAccount);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasNameAccount(String nameAccount) {
        String query = "SELECT COUNT(username) AS c FROM users \n" +
                "WHERE username = '"+nameAccount+"'";
        try (ResultSet rs = this.databaseService.executeQuery(query)) {
            while (rs.next()){
                if(rs.getInt("c") == 1)
                    return  true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    public static void main(String[] args) {

              System.out.println(new ManagerEmployeeService().hasNameAccount("user"));

    }



}
