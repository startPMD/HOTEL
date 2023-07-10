package model;

import java.sql.Timestamp;

public class CustomerModel {
    private int id;
    private String name;
    private String phone;
    private String address;
    private String email;
    private String idNumber;
    private String national;
    private Timestamp timeStartDay;

    public CustomerModel() {

    }

    public CustomerModel(int id, String name, String phone, String address, String email, String idNumber, String national) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.idNumber = idNumber;
        this.national = national;
        this.timeStartDay = new Timestamp(System.currentTimeMillis());
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    @Override
    public String toString() {
        return "CustomerModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", national='" + national + '\'' +
                '}';
    }
    public Timestamp getTimeStartDay() {
        return this.timeStartDay;
    }
//    public void check
}