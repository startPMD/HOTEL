package service;

import model.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GuestRoomService extends ARoomService {

    public GuestRoomService() {
        super();
    }

    @Override
    public List<ARoomModel> getRoomList() {
        Map<String, Map<Integer, Integer>> map = getTypeRoom(2);
        String[] roomTypes = {"Standard", "Deluxe", "Family", "Suite"};
        List<ARoomModel> emptyRoomModels = new ArrayList<>();
        for (String roomType : roomTypes) {
            if (map.containsKey(roomType)) {
                ARoomModel emptyRoomModel = new GuestRoomModel(roomType, map.get(roomType));
                emptyRoomModels.add(emptyRoomModel);
            }
        }
        return emptyRoomModels;
    }

    public List<RoomBookingFormModel> getInforGuestRooms() {
        List<RoomBookingFormModel> roomBookingFormModels = new ArrayList<>();
        String query = "SELECT r.id,c.name, c.phone, c.identity_number, rt.name AS room_type, r.room_number,rt.price ,bar.payment_status, bar.start_date, bar.check_in_time, bar.end_date,bar.customer_id,bar.id AS barID " +
                "FROM customer c " +
                "INNER JOIN book_a_room bar ON c.id = bar.customer_id " +
                "INNER JOIN room r ON r.id = bar.room_id " +
                "INNER JOIN room_types rt ON r.type_room_id = rt.id " +
                "WHERE r.status = 2 AND NOW() < bar.end_date AND ISNULL(bar.state);";
        try (ResultSet rs = this.databaseService.executeQuery(query)) {
            while (rs.next()) {
                int idRoom = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String identityNumber = rs.getString("identity_number");
                String roomType = rs.getString("room_type");
                String roomNumber = rs.getString("room_number");
                double roomPrice = rs.getDouble("price");
                String paymentStatus = rs.getString("payment_status");
                Timestamp startDate = rs.getTimestamp("start_date");
                Timestamp checkInTime = rs.getTimestamp("check_in_time");
                Timestamp endDate = rs.getTimestamp("end_date");
                int cusId = rs.getInt("customer_id");
                int barID = rs.getInt("barID");
                // Do something with the retrieved data

//                System.out.println(name + " - " + phone + " - " + identityNumber + " - " + roomType + " - " + roomNumber + " - " + roomPrice + " - " + paymentStatus + " - " + startDate + " - " + checkInTime + " - " + stayDuration + " - " + endDate);
                CustomerModel customerModel = new CustomerModel();
                customerModel.setId(cusId);
                customerModel.setName(name);
                customerModel.setPhone(phone);
                customerModel.setIdNumber(identityNumber);
                RoomBookingFormModel.EmptyRoomInformationModel emptyRoomInformationModeld = new RoomBookingFormModel().new EmptyRoomInformationModel(
                        idRoom, null, Integer.parseInt(roomNumber), roomType, 0, null, null, roomPrice);
                RoomBookingFormModel.InforBookedRoomModel inforBookedRoomModel = new RoomBookingFormModel().new InforBookedRoomModel(0, paymentStatus);
                inforBookedRoomModel.setStartDate(startDate);
                inforBookedRoomModel.setCheckInTime(checkInTime);
                inforBookedRoomModel.setEndDate(endDate);
                RoomBookingFormModel roomBookingFormModel = new RoomBookingFormModel(emptyRoomInformationModeld, inforBookedRoomModel);
                roomBookingFormModel.setIdBookedRoom(barID);
                roomBookingFormModel.setCustomer(customerModel);
                roomBookingFormModels.add(roomBookingFormModel);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return roomBookingFormModels;
    }

    public RoomBookingFormModel getInfoBookedRoom(int idRoomCurren) {
        String query = "SELECT r.id,c.name, c.phone, c.identity_number, rt.name AS room_type, r.room_number,rt.price ,bar.payment_status, bar.start_date, bar.check_in_time, bar.end_date " +
                "FROM customer c " +
                "INNER JOIN book_a_room bar ON c.id = bar.customer_id " +
                "INNER JOIN room r ON r.id = bar.room_id " +
                "INNER JOIN room_types rt ON r.type_room_id = rt.id " +
                "WHERE r.status = 2 AND NOW() < bar.end_date AND ISNULL(bar.state) AND r.id = " + idRoomCurren;
        try (ResultSet rs = this.databaseService.executeQuery(query)) {
            while (rs.next()) {
                int idRoom = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String identityNumber = rs.getString("identity_number");
                String roomType = rs.getString("room_type");
                String roomNumber = rs.getString("room_number");
                double roomPrice = rs.getDouble("price");
                String paymentStatus = rs.getString("payment_status");
                Timestamp startDate = rs.getTimestamp("start_date");
                Timestamp checkInTime = rs.getTimestamp("check_in_time");
                Timestamp endDate = rs.getTimestamp("end_date");
                // Do something with the retrieved data

//                System.out.println(name + " - " + phone + " - " + identityNumber + " - " + roomType + " - " + roomNumber + " - " + roomPrice + " - " + paymentStatus + " - " + startDate + " - " + checkInTime + " - " + stayDuration + " - " + endDate);
                CustomerModel customerModel = new CustomerModel();
                customerModel.setName(name);
                customerModel.setPhone(phone);
                customerModel.setIdNumber(identityNumber);
                RoomBookingFormModel.EmptyRoomInformationModel emptyRoomInformationModeld = new RoomBookingFormModel().new EmptyRoomInformationModel(
                        idRoom, null, Integer.parseInt(roomNumber), roomType, 0, null, null, roomPrice);
                RoomBookingFormModel.InforBookedRoomModel inforBookedRoomModel = new RoomBookingFormModel().new InforBookedRoomModel(0, paymentStatus);
                inforBookedRoomModel.setStartDate(startDate);
                inforBookedRoomModel.setCheckInTime(checkInTime);
                inforBookedRoomModel.setEndDate(endDate);
                RoomBookingFormModel roomBookingFormModel = new RoomBookingFormModel(emptyRoomInformationModeld, inforBookedRoomModel);
                roomBookingFormModel.setCustomer(customerModel);
                return roomBookingFormModel;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Integer> getListEmptyRoom() {
        List<Integer> codeRooms = new ArrayList<>();
        List<RoomBookingFormModel> roomBookingFormModels = new ArrayList<>();
        String query = "SELECT room_number FROM room WHERE  status = 1;";
        try (ResultSet rs = this.databaseService.executeQuery(query)) {
            while (rs.next()) {
                int code = rs.getInt("room_number");
                codeRooms.add(code);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return codeRooms;
    }

    public boolean updateStatetEmpty(int roomNumber, int status) {
        String query = "UPDATE room SET status = ? WHERE room_number = ?";
        try(PreparedStatement statement = databaseService.getPreparedStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            statement.setInt(1, status);
            statement.setInt(2, roomNumber);
           int count = statement.executeUpdate();
             return count == 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean updateCodeRoomBooked(int idCurrent, int idNew) {
        String query = "UPDATE book_a_room SET room_id = ? WHERE room_id = ? AND ISNULL(state) AND end_date > NOW();";
        try (PreparedStatement statement = databaseService.getPreparedStatement(query)) {
            statement.setInt(1, idNew);
            statement.setInt(2, idCurrent);
            int count = statement.executeUpdate();
            return count > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public RoomBookingFormModel.EmptyRoomInformationModel getEmptyRoomInformationModel(int idNew) {
        RoomBookingFormModel.EmptyRoomInformationModel emptyRoomInformationModeld = null;
        String query = "SELECT r.id,rt.location, r.room_number, rt.name AS room_type,rt.num_guest,s.name_status AS room_status, rt.description, rt.price " +
                "FROM room r " +
                "JOIN room_types rt ON r.type_room_id = rt.id " +
                "JOIN status_room s ON r.status = s.id " +
                "WHERE r.status = 1 AND r.id = " + idNew;
        try (ResultSet rs = this.databaseService.executeQuery(query)) {
            while (rs.next()) {
                int roomId = rs.getInt("id");
                String roomLocation = rs.getString("location");
                int roomCode = rs.getInt("room_number");
                String roomType = rs.getNString("room_type");
                int roomNumGuest = rs.getInt("num_guest");
                String roomStatus = rs.getNString("room_status");
                String roomEquipment = rs.getNString("description");
                double roomPrice = rs.getDouble("price");

                RoomBookingFormModel inforRoomModel = new RoomBookingFormModel();
                emptyRoomInformationModeld = inforRoomModel.new EmptyRoomInformationModel(roomId, roomLocation, roomCode, roomType, roomNumGuest, roomStatus, roomEquipment, roomPrice);
                return emptyRoomInformationModeld;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return emptyRoomInformationModeld;
    }



    public int getIdRoom(int codeRoom) {
        String sql = "SELECT id FROM  room WHERE room_number ="+codeRoom;
      try(ResultSet rs = databaseService.executeQuery(sql)){
          while (rs.next()){
              return rs.getInt("id");
          }
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
      return -1;
    }
    public HotelInfoModel getProfileHotel() {
        String sql = "SELECT * FROM  hotel_info WHERE id = 1";
        HotelInfoModel hotelInfoModel = null;
        try(ResultSet rs = databaseService.executeQuery(sql)){
            while (rs.next()){
                String nameHotel = rs.getString(2);
                String address = rs.getString(3);
                String phoneNumber = rs.getString(4);
                String websiteDomain = rs.getString(5);
                hotelInfoModel = new HotelInfoModel(nameHotel,address,phoneNumber,websiteDomain);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hotelInfoModel;
    }

    public int getIdBill() {
        String sql = "SELECT  COUNT(id) FROM book_a_room_payment";
        try(ResultSet rs = databaseService.executeQuery(sql)){
            while (rs.next()){
                int idBill = rs.getInt(1);
                return idBill;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public CustomerModel getCustomer(int id) {
        String sql = "SELECT * FROM customer WHERE id ="+ id;
        CustomerModel customerModel = null;
        try(ResultSet rs = databaseService.executeQuery(sql)){
            while (rs.next()){
                String name = rs.getString(2);
                String phone = rs.getString(3);
                String address = rs.getString(4);

                customerModel = new CustomerModel();
                customerModel.setName(name);
                customerModel.setPhone(phone);
                customerModel.setAddress(address);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerModel;
    }

    public int insertCusPay(int idRoom, int idPay) {
        String sql = "INSERT INTO book_a_room_payment(book_a_room_id, payment_id,date_create) VALUES(?, ?, ?)";
        try(PreparedStatement pstmt  = databaseService.getPreparedStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setInt(1, idRoom);
                pstmt.setInt(2, idPay);
                pstmt.setTimestamp(3,new Timestamp(System.currentTimeMillis()));

                pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                return generatedId;
            }
        }
        catch (SQLException e){
            System.out.println(("Lỗi khi chèn dữ liệu: " + e.getMessage()));
        }
        return -1;
    }

    public int insertPay(int idCus, int idNumber,Date date,String totalPrice) {
        String sql = "INSERT INTO payment(customer_id,book_a_room_id,payment_date,total_amount)"
                + "VALUES(?, ?, ?, ?)";
        try(PreparedStatement pstmt  = databaseService.getPreparedStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, idCus);
            pstmt.setInt(2, idNumber);
            pstmt.setDate(3,date);
            pstmt.setBigDecimal(4,new BigDecimal(Long.valueOf(totalPrice)));
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                return generatedId;
            }
        }
        catch (SQLException e){

        }
        return -1;
    }

    public int getIdBooked(int idCus,int idRoom) {
        String sql = "SELECT id FROM book_a_room  WHERE customer_id = "+idCus+" AND room_id = "+idRoom+" AND ISNULL(state)";
        try(ResultSet rs = databaseService.executeQuery(sql)) {
            while (rs.next()) {
                int idBill = rs.getInt(1);
                return idBill;
            }
        }
        catch (SQLException e){
        }
        return -1;
    }
    public void updatePayStateBookedRoom(int idBooked) {
        String query = "UPDATE book_a_room SET payment_status = 'Đã thanh toán' WHERE id = ?";
        try (PreparedStatement statement = databaseService.getPreparedStatement(query)) {
            statement.setInt(1, idBooked);
            int count = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateServicePayed(int idBooked) {
        String query = "UPDATE book_a_room_service SET payed = '1' WHERE book_a_room_id = ?";
        try (PreparedStatement statement = databaseService.getPreparedStatement(query)) {
            statement.setInt(1, idBooked);
            int count = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        GuestRoomService g = new GuestRoomService();
g.getInforGuestRooms();
    }

}
