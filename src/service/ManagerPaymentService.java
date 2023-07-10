package service;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ManagerPaymentService {
    private DatabaseService databaseService = null;

    public ManagerPaymentService() {
        databaseService = DatabaseService.getInstanceDatabaseService();
    }

    public List<Object[]> getBillCurrent() {
        List<Object[]> objects = new ArrayList<>();
        String sql = "SELECT @row := @row + 1  AS row_number, c.`name`,c.phone, p.payment_date, p.total_amount, p.payment_method,p.id " +
                "FROM payment p " +
                "JOIN customer c ON c.id = p.customer_id " +
                "JOIN (SELECT @row := 0) r " +
                "WHERE payment_date = CURDATE()";
        try (ResultSet rs = databaseService.executeQuery(sql)) {
            while (rs.next()) {
                int stt = rs.getInt("row_number");
                String nameCus = rs.getString("name");
                String phoneCus = rs.getString("phone");
                BigDecimal totalPrice = rs.getBigDecimal("total_amount");
                String paymentMethod = rs.getString("payment_method");
                Timestamp timestamp = rs.getTimestamp("payment_date");
                int codeBill = rs.getInt("id");

                objects.add(new Object[]{stt,nameCus,phoneCus ,totalPrice ,paymentMethod ,timestamp ,codeBill  });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return objects;
    }
    public List<Object[]> getBillCusFind(String strNameCus) {
        List<Object[]> objects = new ArrayList<>();
        String sql = "SELECT @row := @row + 1  AS row_number, c.`name`,c.phone, p.payment_date, p.total_amount, p.payment_method,p.id " +
                "FROM payment p " +
                "JOIN customer c ON c.id = p.customer_id " +
                "JOIN (SELECT @row := 0) r " +
                "WHERE c.`name` = '"+strNameCus+"'";
        try (ResultSet rs = databaseService.executeQuery(sql)) {
            while (rs.next()) {
                int stt = rs.getInt("row_number");
                String nameCus = rs.getString("name");
                String phoneCus = rs.getString("phone");
                BigDecimal totalPrice = rs.getBigDecimal("total_amount");
                String paymentMethod = rs.getString("payment_method");
                Timestamp timestamp = rs.getTimestamp("payment_date");
                int codeBill = rs.getInt("id");

                objects.add(new Object[]{stt,nameCus,phoneCus ,totalPrice ,paymentMethod ,timestamp ,codeBill});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return objects;
    }
    public List<Object[]> getBillServices(int idBill) {
        List<Object[]> objects = new ArrayList<>();
        String sql = "SELECT @row := @row + 1 AS row_number,p.id,s.`name`, brs.request_date, brs.quantity, brs.price " +
                "FROM book_a_room_service brs " +
                "JOIN payment p ON p.id = brs.book_a_room_id " +
                "JOIN service s ON s.id = brs.service_id " +
                "JOIN (SELECT @row := 0) r " +
                "WHERE p.id = "+idBill;
        try (ResultSet rs = databaseService.executeQuery(sql)) {
            while (rs.next()) {
                int stt = rs.getInt("row_number");
                String codeBill = rs.getString("id");
                String nameService = rs.getString("name");
                Timestamp resqueTimestamp = rs.getTimestamp("request_date");
                BigDecimal quantity = rs.getBigDecimal("quantity");
                String totalPrice = rs.getString("price");

                objects.add(new Object[]{stt,codeBill,nameService ,resqueTimestamp ,quantity ,totalPrice});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return objects;
    }
    public static void main(String[] args) {
        ManagerPaymentService a = new ManagerPaymentService();
//        for (Object[] d:a.getBillServices("2023-05-08",5)
//        ) {
//            System.out.println(1+" "+Arrays.toString(d));
//        }
//        for (Object[] d:a.getBillServices("2023-05-08",5)
//        ) {
//            System.out.println(2+" "+Arrays.toString(d));
//        }
        for (Object[] d:a.getBillCusFind("")
             ) {
            System.out.println(3+" "+Arrays.toString(d));
        }

    }
}
