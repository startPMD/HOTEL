package model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class ServiceRoomModel {
    private int id;
    private String name;
    private String description;
    private int price;
    private Timestamp dateAt;
    private int quantity;
    private int totalPrice;
    private String dateStr;
    public ServiceRoomModel(int id, String name, String description, int price, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = quantity*price;
        dateAt = createDateAt();
    }
    public ServiceRoomModel(int id, String name, String description, int price,Timestamp dateAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.dateAt = dateAt;
    }
    public ServiceRoomModel(int id, String name, int price,int quantity,String dateStr) {
        this.id = id;
        this.name = name;
        this.totalPrice = price*quantity;
        this.quantity = quantity;
        this.dateStr = dateStr;
    }
    public Timestamp createDateAt(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }
    public Timestamp getDateAt() {
        return this.dateAt;
    }
    public String getDateAtStr() {
        return this.dateStr;
    }
    public int getQuantityService() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
    @Override
    public String toString() {
        return "ServiceRoomModel{" +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", dateAt=" + dateAt +
                ", total=" + totalPrice+
                '}';
    }
}
