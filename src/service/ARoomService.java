package service;

import model.ARoomModel;
import model.EmptyRoomModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public abstract class ARoomService {
    protected DatabaseService databaseService = DatabaseService.getInstanceDatabaseService() ;
    public ARoomService() {
    }
    public abstract List<ARoomModel> getRoomList();
    public Map<String, Map<Integer,Integer>> getTypeRoom(int codeTypeRoom) {
        String query = "SELECT r.id, rt.name, r.room_number "
                + "FROM room_types rt "
                + "JOIN room r ON rt.id = r.type_room_id "
                + "WHERE r.status = " + codeTypeRoom;
        Map<String, Map<Integer,Integer>> map = null;
        try (ResultSet rs = this.databaseService.executeQuery(query)) {
            map = new HashMap<>();
            while (rs.next()) {
                int idRoom = rs.getInt("id");
                String typeRoom = rs.getString("name");
                int roomNumber = rs.getInt("room_number");
                if (map.containsKey(typeRoom)) {
                    map.get(typeRoom).put(idRoom,roomNumber);
                } else {
                    Map<Integer,Integer> numberRooms = new HashMap<>();
                    numberRooms.put(idRoom,roomNumber);
                    map.put(typeRoom, numberRooms);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("ARoomService");
        System.out.println("TypeRoom-CodeRoom&IDRoom"+map.toString());
        return map;
    }

    // cap nhat lai trag thai phòng
    public void updateStatestRoom() {
        String query = "UPDATE room SET  `status`= 1 " +
                "WHERE id IN(SELECT room_id FROM book_a_room WHERE CURTIME() > end_date AND ISNULL(state))";
        try (PreparedStatement statement = this.databaseService.getPreparedStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // cap nhat lai trag thai phòng duoc dat,sao khi khach hang tra phog
    public void updateVacantRoom() {
        System.out.println("refresh");
        String query = "UPDATE book_a_room SET state = 6 WHERE CURTIME() > end_date AND ISNULL(state)";
        try (PreparedStatement statement = this.databaseService.getPreparedStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
