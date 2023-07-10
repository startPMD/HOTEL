package service;

import model.ARoomModel;
import model.EmptyRoomModel;
import model.PaymentModel;
import model.RoomBookingFormModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class EmptyRoomService extends ARoomService {

    public EmptyRoomService() {
        super();
    }

    @Override
    public List<ARoomModel> getRoomList() {
        Map<String, Map<Integer, Integer>> map = getTypeRoom(1);
        String[] roomTypes = {"Standard", "Deluxe", "Family", "Suite"};
        List<ARoomModel> emptyRoomModels = new ArrayList<>();
        for (String roomType:roomTypes) {
            if(map.containsKey(roomType)){
                ARoomModel  emptyRoomModel  = new EmptyRoomModel(roomType,map.get(roomType));
                emptyRoomModels.add(emptyRoomModel);
            }
        }
        return emptyRoomModels;
    }

    public void saveBookedRoom(int customer_id, int room_id, RoomBookingFormModel.InforBookedRoomModel inforBookedRoomModel) {
        String query = "INSERT INTO book_a_room (customer_id, room_id, start_date,end_date,check_in_time,stay_duration,num_guest,payment_status)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = super.databaseService.getPreparedStatement(query);
        try {
            statement.setInt(1, customer_id);
            statement.setInt(2, room_id);
            statement.setTimestamp(3, inforBookedRoomModel.getStartDate());
            statement.setTimestamp(4, inforBookedRoomModel.getEndDate());
            statement.setTimestamp(5,inforBookedRoomModel.getCheckInTime());

            statement.setString(6,inforBookedRoomModel.getStayDuration());
            statement.setInt(7,inforBookedRoomModel.getNumGuest());
            statement.setString(8,inforBookedRoomModel.getPaymentStatus());

            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void savePaymentCustomer(PaymentModel paymentModel){
        String sql = "INSERT INTO payment(customer_id,book_a_room_id,payment_date,total_amount) " +
                "VALUES (?,?,?,?)";
        try (PreparedStatement statement = this.databaseService.getPreparedStatement(sql)) {
            statement.setInt(1,paymentModel.getCustomer_id());
            statement.setInt(2,paymentModel.getBook_a_room_id());
            statement.setTimestamp(3,paymentModel.getPayment_date());
            statement.setDouble(4,paymentModel.getTotal_amount());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getIdBookedRoom(int customerID,int roomId,Timestamp checkInTime) {
        String query = "SELECT id FROM book_a_room " +
                    "WHERE customer_id = '"+customerID+"'"
                +"AND room_id = '"+roomId+"'"
                +"AND check_in_time = '"+checkInTime+"'";
        try (ResultSet rs = this.databaseService.executeQuery(query)) {
            if (rs.next())
                return rs.getInt("id");
            else return -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
