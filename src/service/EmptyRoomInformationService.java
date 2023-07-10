package service;

import model.RoomBookingFormModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmptyRoomInformationService {
    public static EmptyRoomInformationService instanceEmptyRoomInformationService = null;
    private DatabaseService databaseService = null;

    public EmptyRoomInformationService() {
        databaseService = DatabaseService.getInstanceDatabaseService();
    }
    public static EmptyRoomInformationService getInstanceEmptyRoomInformationService() {
        if (instanceEmptyRoomInformationService == null)
            instanceEmptyRoomInformationService = new EmptyRoomInformationService();
        return instanceEmptyRoomInformationService;
    }
    public List<RoomBookingFormModel.EmptyRoomInformationModel> getInforRoomModels() {
        List<RoomBookingFormModel.EmptyRoomInformationModel> inforRooms = new ArrayList<>();
        String query = "SELECT r.id,r.location, r.room_number, rt.name AS room_type,rt.num_guest,s.name_status AS room_status, rt.description, rt.price " +
                "FROM room r " +
                "JOIN room_types rt ON r.type_room_id = rt.id " +
                "JOIN status_room s ON r.status = s.id " +
                "WHERE r.status = 1";
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
//                System.out.println(roomLocation + " " + roomCode + " " + roomType + " " + roomStatus + " " + roomEquipment + " " + roomPrice);
                RoomBookingFormModel inforRoomModel = new RoomBookingFormModel();
                RoomBookingFormModel.EmptyRoomInformationModel emptyRoomInformationModeld = inforRoomModel.new EmptyRoomInformationModel(roomId,roomLocation,roomCode,roomType,roomNumGuest,roomStatus,roomEquipment,roomPrice);
                inforRooms.add(emptyRoomInformationModeld);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return inforRooms;
    }
    public  RoomBookingFormModel.EmptyRoomInformationModel getInforRoomModels(int codeRoom) {
        RoomBookingFormModel.EmptyRoomInformationModel emptyRoomInformationModeld = null;
        String query = "SELECT r.id,r.location, r.room_number, rt.name AS room_type,rt.num_guest,s.name_status AS room_status, rt.description, rt.price " +
                "FROM room r " +
                "JOIN room_types rt ON r.type_room_id = rt.id " +
                "JOIN status_room s ON r.status = s.id " +
                "WHERE r.status = 1 AND room_number = "+ codeRoom;
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
//                System.out.println(roomLocation + " " + roomCode + " " + roomType + " " + roomStatus + " " + roomEquipment + " " + roomPrice);
                RoomBookingFormModel inforRoomModel = new RoomBookingFormModel();
                emptyRoomInformationModeld = inforRoomModel.new EmptyRoomInformationModel(roomId,roomLocation,roomCode,roomType,roomNumGuest,roomStatus,roomEquipment,roomPrice);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return emptyRoomInformationModeld;
    }
}
