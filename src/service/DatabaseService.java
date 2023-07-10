package service;

import java.sql.*;

public class DatabaseService {
    private Connection connection;
    public static DatabaseService instanceDatabaseService = null;

    public DatabaseService() {
        // Thực hiện kết nối đến database
        String url = "jdbc:mysql://localhost:3306/hotel_manager";
        String user = "root";
        String password = "";
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database successfully");
        } catch (SQLException e) {
            System.out.println("Failed to connect to database");
            e.printStackTrace();
           }

    }

    public static DatabaseService getInstanceDatabaseService(){
        if(instanceDatabaseService == null)
            instanceDatabaseService = new DatabaseService();
        return instanceDatabaseService;
    }

    public ResultSet executeQuery(String query) {
        // Thực hiện truy vấn dữ liệu từ database và trả về ResultSet
        ResultSet rs = null;
        try {
           PreparedStatement pstmt = connection.prepareStatement(query);
            rs = pstmt.executeQuery();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return rs;
    }
    public PreparedStatement getPreparedStatement(String sql)  {
        try {
            return  connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public PreparedStatement getPreparedStatement(String sql,int key)  {
        try {
            return  connection.prepareStatement(sql,key);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void closeConnection() {
        // Đóng kết nối đến database
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connect to database closed");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}