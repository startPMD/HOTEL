package service;

import model.ARoomModel;
import model.BookedRoomModel;
import model.CustomerModel;
import model.LayoutRoomModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class LayoutRoomService extends ARoomService {
    private DatabaseService databaseService = null;
    public static LayoutRoomService instanceLayoutRoomService = null;
    public LayoutRoomService() {
        databaseService = DatabaseService.getInstanceDatabaseService();
    }

    @Override
    public List<ARoomModel> getRoomList() {
        return null;
    }

    public static LayoutRoomService getInstanceLayoutRoomService(){
        if(instanceLayoutRoomService == null)
            instanceLayoutRoomService = new LayoutRoomService();
        return instanceLayoutRoomService;
    }
    public Map<String, List<LayoutRoomModel>> getInforRoomModels() {
        Map<String, List<LayoutRoomModel>> inforRooms = new TreeMap<>();
        String query = "SELECT r.location,r.room_number,rt.name,rt.num_guest,rt.price FROM room_types rt JOIN room r ON r.type_room_id = rt.id";

        try (ResultSet rs = databaseService.executeQuery(query)) {
            while (rs.next()) {
                String locationRoom = rs.getString("location");
                int numberRoom = rs.getInt("room_number");
                String nameTypeRoom = rs.getString("name");
                int numGuestRoom = rs.getInt("num_guest");
                double priceRoom = rs.getDouble("price");

                LayoutRoomModel layoutRoomModel  = new LayoutRoomModel(numberRoom,nameTypeRoom,numGuestRoom,priceRoom);
                if(!inforRooms.containsKey(locationRoom)){
                    inforRooms.put(locationRoom,new ArrayList<>());
                }
                inforRooms.get(locationRoom).add(layoutRoomModel);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return inforRooms;
    }
    public int[] getDateTop(){
        int[] d = new int[6];
        String query = "SELECT COUNT(*) AS total_rooms," +
                "SUM(CASE WHEN r.status = 1 THEN 1 ELSE 0 END) AS empty_rooms," +
                "SUM(CASE WHEN r.status = 2 THEN 1 ELSE 0 END) AS occupied_rooms," +
                "SUM(CASE WHEN r.status = 3 THEN 1 ELSE 0 END) AS booked_rooms," +
                "SUM(CASE WHEN r.status = 4 THEN 1 ELSE 0 END) AS cleaning_rooms," +
                "SUM(CASE WHEN r.status = 5 THEN 1 ELSE 0 END) AS maintenance_rooms " +
                "FROM room r JOIN status_room sr ON r.status = sr.id";

        try (ResultSet rs = databaseService.executeQuery(query)) {
            while (rs.next()) {
                int totalRoom = rs.getInt("total_rooms");
                int emptyRoom = rs.getInt("empty_rooms");
                int occupiedRoom = rs.getInt("occupied_rooms");
                int bookedRoom = rs.getInt("booked_rooms");
                int cleaningRoom = rs.getInt("cleaning_rooms");
                int maintenanceRoom = rs.getInt("maintenance_rooms");
                d[0]= totalRoom;d[1]= emptyRoom;d[2]= occupiedRoom;d[3]= bookedRoom;d[4]= cleaningRoom;d[5]= maintenanceRoom;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return d;
    }
    public static void main(String[] args) {
    }

    // lay danh sach khach hang da từng đặc phong
    public List<BookedRoomModel> listCusBookedRoom(Date startDate,Date endDate) {
        String sql = " SELECT DISTINCT c.name,c.phone,c.identity_number,c.address,bar.start_date,bar.end_date,r.room_number FROM book_a_room bar " +
                "JOIN customer c ON c.id = bar.customer_id " +
                "JOIN room r ON r.id = bar.room_id "+
                "WHERE bar.start_date >= '"+startDate+"' " +
                "AND bar.end_date <= '"+endDate+"'";
        List<BookedRoomModel> bookedRoomModels =  new ArrayList<>();;
        try (ResultSet rs = databaseService.executeQuery(sql)) {
            while (rs.next()) {
                String nameCus = rs.getString("name");
                String phoneCus = rs.getString("phone");
                String idNumberCus = rs.getString("identity_number");
                String addressCus = rs.getString("address");
                Timestamp startDateCus = rs.getTimestamp("start_date");
                Timestamp endDateCus = rs.getTimestamp("end_date");
                int numberRoom = rs.getInt("room_number");

                BookedRoomModel bookedRoomModel = new BookedRoomModel();
                bookedRoomModel.setNumberRoom(numberRoom);
                bookedRoomModel.setStartDate(startDateCus);
                bookedRoomModel.setEndDate(endDateCus);
                CustomerModel customerModel = new CustomerModel(-1,nameCus,phoneCus,addressCus,null,idNumberCus,null);
                bookedRoomModel.setCustomerModell(customerModel);

                bookedRoomModels.add(bookedRoomModel);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return bookedRoomModels;
    }
    public  List<BookedRoomModel> listCusBookedRoom(Date startDate,Date endDate,int numberRoom) {
        String sql = " SELECT c.name,c.phone,c.identity_number,c.address,bar.start_date,bar.end_date,r.room_number FROM book_a_room bar " +
                "JOIN customer c ON c.id = bar.customer_id " +
                "JOIN room r ON r.id = bar.room_id "+
                "WHERE bar.start_date >= '"+startDate+"' " +
                "AND bar.end_date <= '"+endDate+"' ";
//                +
//                "AND r.room_number = "+numberRoom;
        List<BookedRoomModel> bookedRoomModels =  new ArrayList<>();;
        try (ResultSet rs = databaseService.executeQuery(sql)) {
            while (rs.next()) {
                String nameCus = rs.getString("name");
                String phoneCus = rs.getString("phone");
                String idNumberCus = rs.getString("identity_number");
                String addressCus = rs.getString("address");
                Timestamp startDateCus = rs.getTimestamp("start_date");
                Timestamp endDateCus = rs.getTimestamp("end_date");
                int numRoom = rs.getInt("room_number");

                BookedRoomModel bookedRoomModel = new BookedRoomModel();
                bookedRoomModel.setNumberRoom(numRoom);
                bookedRoomModel.setStartDate(startDateCus);
                bookedRoomModel.setEndDate(endDateCus);
                CustomerModel customerModel = new CustomerModel(-1,nameCus,phoneCus,addressCus,null,idNumberCus,null);
                bookedRoomModel.setCustomerModell(customerModel);

                bookedRoomModels.add(bookedRoomModel);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return bookedRoomModels;
    }
    public List<BookedRoomModel> listCusBookedRoom(Date startDate,Date endDate,String valueFind) {
        String sql = " SELECT c.name,c.phone,c.identity_number,c.address,bar.start_date,bar.end_date,r.room_number FROM book_a_room bar " +
                "JOIN customer c ON c.id = bar.customer_id " +
                "JOIN room r ON r.id = bar.room_id "+
                "WHERE bar.start_date >= '"+startDate+"' " +
                "AND bar.end_date <= '"+endDate+"' "+
                "AND c.name = '"+valueFind+"'";
            List<BookedRoomModel> bookedRoomModels =  new ArrayList<>();;
            try (ResultSet rs = databaseService.executeQuery(sql)) {
                while (rs.next()) {
                    String nameCus = rs.getString("name");
                    String phoneCus = rs.getString("phone");
                    String idNumberCus = rs.getString("identity_number");
                    String addressCus = rs.getString("address");
                    Timestamp startDateCus = rs.getTimestamp("start_date");
                    Timestamp endDateCus = rs.getTimestamp("end_date");
                    int numberRoom = rs.getInt("room_number");

                    BookedRoomModel bookedRoomModel = new BookedRoomModel();
                    bookedRoomModel.setNumberRoom(numberRoom);
                    bookedRoomModel.setStartDate(startDateCus);
                    bookedRoomModel.setEndDate(endDateCus);
                    CustomerModel customerModel = new CustomerModel(-1,nameCus,phoneCus,addressCus,null,idNumberCus,null);
                    bookedRoomModel.setCustomerModell(customerModel);
                    
                    bookedRoomModels.add(bookedRoomModel);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
        }
            return bookedRoomModels;
    }
}
