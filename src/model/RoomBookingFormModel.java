package model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

public class RoomBookingFormModel {
    private EmptyRoomInformationModel emptyRoomInformationModel;
    private InforBookedRoomModel inforBookedRoomModel;
    private CustomerModel customerModel;

    public RoomBookingFormModel(){}
    int idBookedRoom;
    public RoomBookingFormModel(EmptyRoomInformationModel emptyRoomInformationModel,InforBookedRoomModel inforBookedRoomModel) {
        this.emptyRoomInformationModel = emptyRoomInformationModel;
        this.inforBookedRoomModel = inforBookedRoomModel;
    }

    public int getIdBookedRoom() {
        return idBookedRoom;
    }

    public void setIdBookedRoom(int idBookedRoom) {
        this.idBookedRoom = idBookedRoom;
    }

    public int getRoomCodeEmptyRoomInformationModel(){
        return emptyRoomInformationModel.getRoomCode();
    }

    public void setCustomer(CustomerModel customerModel) {
        this.customerModel = customerModel;
    }

    public EmptyRoomInformationModel getEmptyRoomInformationModel() {
        return emptyRoomInformationModel;
    }

    public InforBookedRoomModel getInforBookedRoomModel() {
        return inforBookedRoomModel;
    }

    public CustomerModel getCustomerModel(){
       return this.customerModel;
    }

    public class EmptyRoomInformationModel {
        private int id;
        private String location;
        private int roomCode;
        private String roomType;
        private int numGuest;
        private String roomStatus;
        private String equipment;
        private double roomPrice;

        public EmptyRoomInformationModel(int id,String location, int roomCode, String roomType,int numGuest, String roomStatus, String equipment, double roomPrice) {
            this.id = id;
            this.location = location;
            this.roomCode = roomCode;
            this.roomType = roomType;
            this.numGuest = numGuest;
            this.roomStatus = roomStatus;
            this.equipment = equipment;
            this.roomPrice = roomPrice;
        }

        public int getId() {
            return id;
        }

        public String getLocation() {
            return location;
        }
        public int getRoomCode() {
            return roomCode;
        }
        public String getRoomType() {
            return roomType;
        }
        public int getNumGuest() {
            return numGuest;
        }

        public String getRoomStatus() {
            return roomStatus;
        }
        public String getEquipment() {
            return equipment;
        }
        public double getRoomPrice() {
            return roomPrice;
        }
        @Override
        public String toString() {
            return "EmptyRoomInformationModel{" +
                    "location='" + location + '\'' +
                    ", roomCode=" + roomCode +
                    ", roomType='" + roomType + '\'' +
                    ", numGuest=" + numGuest +
                    ", roomStatus='" + roomStatus + '\'' +
                    ", equipment='" + equipment + '\'' +
                    ", roomPrice=" + roomPrice +
                    '}';
        }
    }
    public class InforBookedRoomModel {
        private Timestamp startDate;
        private Timestamp endDate;
        private Timestamp checkInTime;
        private Timestamp tgoTime;
        private String stayDuration;
        private int numGuest;
        private  String paymentStatus;

        public InforBookedRoomModel(int numGuest, String paymentStatus) {
            this.startDate = new Timestamp(System.currentTimeMillis());
            this.numGuest = numGuest;
            this.paymentStatus = paymentStatus;
        }

        public Timestamp getStartDate() {
            return startDate;
        }
        public Timestamp getCheckInTime() {
            return checkInTime;
        }
        public String getStayDuration() {
            return stayDuration;
        }
        public int getNumGuest() {
            return numGuest;
        }
        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setStartDate(Timestamp startDate) {
          this.startDate = startDate;
        }
        public void setCheckInTime(Timestamp checkInTime) {
            this.checkInTime = checkInTime;
        }

        public Timestamp getEndDate() {
            return endDate;
        }

        public void setEndDate(Timestamp endDate) {
            this.endDate = endDate;
        }

        @Override
        public String toString() {
            return "InforBookedRoomModel{" +
                    "startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", checkInTime=" + checkInTime +
                    ", tgoTime=" + tgoTime +
                    ", stayDuration='" + stayDuration + '\'' +
                    ", numGuest=" + numGuest +
                    ", paymentStatus='" + paymentStatus + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RoomBookingFormModel{" +
                "emptyRoomInformationModel=" + emptyRoomInformationModel +
                ", inforBookedRoomModel=" + inforBookedRoomModel +
                ", customerModel=" + customerModel +
                '}';
    }
}
