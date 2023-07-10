package service;

import model.ServiceRoomModel;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class ServiceRoomService extends DatabaseService {
    public static ServiceRoomService instanceServiceRoomService = null;
    private DatabaseService databaseService = null;

    public ServiceRoomService() {
        super();
        databaseService = DatabaseService.getInstanceDatabaseService();
    }

    public static ServiceRoomService getInstanceServiceRoomService() {
        if (instanceServiceRoomService == null)
            instanceServiceRoomService = new ServiceRoomService();
        return instanceServiceRoomService;
    }

    public Map<Integer, List<ServiceRoomModel>> getListServiceCus(int idBooked){
        Map<Integer, List<ServiceRoomModel>> listServiceCus = new TreeMap<>();
        String sql = "SELECT  b.room_id, bs.service_id, s.name,bs.book_a_room_id, SUM(s.price * bs.quantity) AS total_price, SUM(bs.quantity) AS total_quantity\n" +
                "                FROM book_a_room_service AS bs\n" +
                "               JOIN book_a_room b ON b.id = bs.book_a_room_id\n" +
                "                JOIN service AS s ON s.id = bs.service_id \n" +
                "                WHERE bs.request_date BETWEEN b.start_date AND b.end_date AND bs.book_a_room_id = "+idBooked+"\n" +
                "                GROUP BY b.room_id, bs.service_id, s.name";
        try (ResultSet rs = databaseService.executeQuery(sql)) {
            while (rs.next()) {
                int idRoom = rs.getInt("room_id");
                int idService = rs.getInt("service_id");
                String nameService = rs.getString("name");
                int priceService = rs.getInt("total_price");
                int quantityService = rs.getInt("total_quantity");
                ServiceRoomModel serviceRoomModel = new ServiceRoomModel(idService, nameService, null, priceService, quantityService);
                if (!listServiceCus.containsKey(idRoom)) {
                    listServiceCus.put(idRoom, new ArrayList<>());
                }
                listServiceCus.get(idRoom).add(serviceRoomModel);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listServiceCus;
    }

    public List<ServiceRoomModel> getServices(String num) {
        List<ServiceRoomModel> l = new ArrayList<>();
        String sql = "SELECT id,name,price FROM service WHERE type_service_id " + num;
        try (ResultSet rs = databaseService.executeQuery(sql)) {
            while (rs.next()) {
                int idService = rs.getInt("id");
                String nameService = rs.getString("name");
                int priceService = rs.getInt("price");
                ServiceRoomModel serviceRoomModel = new ServiceRoomModel(idService, nameService, null, priceService, 0);
                l.add(serviceRoomModel);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return l;
    }

    public List<Integer> getNumbersRoomGues_Booked() {
        List<Integer> rooms = new ArrayList<>();
        String sql = "SELECT room_number  FROM room  WHERE status IN(2)";
        try (ResultSet rs = databaseService.executeQuery(sql)) {
            while (rs.next()) {
                int roomNumber = rs.getInt("room_number");
                rooms.add(roomNumber);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rooms;
    }

    public boolean insertAddedServices(List<List<Object>> services) {
        String sql = "INSERT INTO book_a_room_service (book_a_room_id, service_id, customer_id, request_date, quantity,price, payed) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int countAdded = -1;
        for (List<Object> service : services) {
            int numRoom = (int) service.get(0);
            String nameService = (String) service.get(1);
            int idRoom = getIdRoom(numRoom);
            int[] id = getIdItemBooked("id", "customer_id", "WHERE room_id = " + idRoom + " AND state IS NULL");
            int idService = getIdService(nameService);

            int idBooked = id[0];
            int idCus = id[1];
            int quantity = (int) service.get(2);
            Timestamp timeAdded = (Timestamp) service.get(3);
            BigDecimal totalPrice = BigDecimal.valueOf((Long) service.get(4));
            try (PreparedStatement statement = this.databaseService.getPreparedStatement(sql)) {
                statement.setInt(1, idBooked);
                statement.setInt(2, idService);
                statement.setInt(3, idCus);
                statement.setTimestamp(4, timeAdded);
                statement.setInt(5, quantity);
                statement.setBigDecimal(6, totalPrice);
                statement.setBoolean(7,false);
                countAdded = statement.executeUpdate();
                if(countAdded < 0)
                    return false;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return countAdded > 0;
    }

    private int getIdService(String nameService) {
        int id = -1;
        String sql = "SELECT id  FROM service  WHERE name = '" + nameService+"'";
        try (ResultSet rs = databaseService.executeQuery(sql)) {
            while (rs.next()) {
                int roomNumber = rs.getInt("id");
                id = roomNumber;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }


    private int[] getIdItemBooked(String colGet1, String colGet2, String where) {
        String sql = "SELECT " + colGet1 + "," + colGet2 + " FROM book_a_room " + where;
        int[] idR = new int[2];
        try (ResultSet rs = databaseService.executeQuery(sql)) {
            while (rs.next()) {
                int idBooked = rs.getInt(colGet1);
                int idCus = rs.getInt(colGet2);
                idR[0] = idBooked;
                idR[1] = idCus;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return idR;
    }

    private int getIdRoom(int numR) {
        String sql = "SELECT id  FROM room  WHERE room_number = " + numR;
        int idR = -1;
        try (ResultSet rs = databaseService.executeQuery(sql)) {
            while (rs.next()) {
                int roomNumber = rs.getInt("id");
                idR = roomNumber;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return idR;
    }

}
