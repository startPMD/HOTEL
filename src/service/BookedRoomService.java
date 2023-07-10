package service;

import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookedRoomService extends ARoomService {
    public BookedRoomService() {
        super();
    }

    @Override
    public List<ARoomModel> getRoomList() {
        Map<String, Map<Integer, Integer>> map = getTypeRoom(3);
        String[] roomTypes = {"Standard", "Deluxe", "Family", "Suite"};
        List<ARoomModel> emptyRoomModels = new ArrayList<>();
        for (String roomType:roomTypes) {
            if(map.containsKey(roomType)){
                ARoomModel  emptyRoomModel  = new BookedRoomModel(roomType,map.get(roomType));
                emptyRoomModels.add(emptyRoomModel);
            }
        }
        return emptyRoomModels;
    }

    public List<RoomBookingFormModel> getInforBookedRooms() {
        List<RoomBookingFormModel> roomBookingFormModels = new ArrayList<>();
        String query = "SELECT r.id,c.name, c.phone, c.identity_number, rt.name AS room_type, r.room_number,rt.price ,bar.payment_status, bar.start_date, bar.check_in_time, bar.end_date " +
                "FROM customer c " +
                "INNER JOIN book_a_room bar ON c.id = bar.customer_id " +
                "INNER JOIN room r ON r.id = bar.room_id " +
                "INNER JOIN room_types rt ON r.type_room_id = rt.id " +
                "WHERE r.status = 3 AND NOW() < bar.end_date AND ISNULL(bar.state);";
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
                roomBookingFormModels.add(roomBookingFormModel);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return roomBookingFormModels;
    }
}
