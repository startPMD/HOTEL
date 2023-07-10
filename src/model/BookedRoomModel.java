package model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BookedRoomModel extends ARoomModel {
    private CustomerModel customerModell;
    private Timestamp startDate;
    private Timestamp endDate;
    public BookedRoomModel() {
        super();
    }
    public BookedRoomModel(String typeRoom, Map<Integer,Integer> numberRooms) {
        super(typeRoom, numberRooms);
    }

    public CustomerModel getCustomerModell() {
        return customerModell;
    }

    public String getFormatStartDate() {
        SimpleDateFormat  sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(new Date(startDate.getTime()));
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public String getFormatEndDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(new Date(endDate.getTime()));
    }
    public int getNumberRoom(){
        return super.numberRoom;
    }
    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }
    public void setCustomerModell(CustomerModel customerModel){
        this.customerModell = customerModel;
    }
}
