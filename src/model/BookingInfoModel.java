package model;

import java.sql.Date;

public class BookingInfoModel {
    private String customerName;
    private String phoneNumber;
    private String idNumber;
    private String email;
    private String country;
    private Date checkInTime;
    private int stayDuration; // number of nights
    private int numberOfGuests;
    private boolean paymentStatus;

    public BookingInfoModel(String customerName, String phoneNumber, String idNumber, String email, String country,
                            Date checkInTime, int stayDuration, int numberOfGuests, boolean paymentStatus) {
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.idNumber = idNumber;
        this.email = email;
        this.country = country;
        this.checkInTime = checkInTime;
        this.stayDuration = stayDuration;
        this.numberOfGuests = numberOfGuests;
        this.paymentStatus = paymentStatus;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public int getStayDuration() {
        return stayDuration;
    }

    public void setStayDuration(int stayDuration) {
        this.stayDuration = stayDuration;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}