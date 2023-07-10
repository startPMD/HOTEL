package service;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StatesRoomService {
    private DatabaseService databaseService = null;
    public static StatesRoomService instanceStatesRoomService = null;
    public StatesRoomService() {
        databaseService = DatabaseService.getInstanceDatabaseService();
    }
    public static StatesRoomService getInstanceDatabaseService(){
        if(instanceStatesRoomService == null)
            instanceStatesRoomService = new StatesRoomService();
        return instanceStatesRoomService;
    }

    public boolean updateStatesRoom(int numberRoom,int codeState) {
        String query = "UPDATE room SET status = ? WHERE room_number = ?";
        try (PreparedStatement statement = databaseService.getPreparedStatement(query)) {
            statement.setInt(1,codeState);
            statement.setInt(2,numberRoom);
           int count = statement.executeUpdate();
            return  count > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateStateBookedRoom(int idCus,int idRoom) {
        String query = "UPDATE book_a_room SET state = 6 " +
                "WHERE customer_id = ? AND room_id = ? " +
                "AND ISNULL(state)";
        try (PreparedStatement statement = databaseService.getPreparedStatement(query)) {
            statement.setInt(1,idCus);
            statement.setInt(2,idRoom);
            int count = statement.executeUpdate();
            return  count > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
