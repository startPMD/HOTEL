package model;

public class StatesRoomModel {
    private String position;
    private String roomCode;
    private String roomType;
    private int maxOccupancy;
    private String roomStatus;
    private String roomEquipment;
    private double roomPrice;

    public StatesRoomModel(String position, String roomCode, String roomType, int maxOccupancy, String roomStatus, String roomEquipment, double roomPrice) {
        this.position = position;
        this.roomCode = roomCode;
        this.roomType = roomType;
        this.maxOccupancy = maxOccupancy;
        this.roomStatus = roomStatus;
        this.roomEquipment = roomEquipment;
        this.roomPrice = roomPrice;
    }

    // getter và setter cho các biến
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(int maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getRoomEquipment() {
        return roomEquipment;
    }

    public void setRoomEquipment(String roomEquipment) {
        this.roomEquipment = roomEquipment;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }
}
