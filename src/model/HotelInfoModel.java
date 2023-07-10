package model;

public class HotelInfoModel {
    private String hotelName;
    private String address;
    private String phoneNumber;
    private String websiteDomain;

    public HotelInfoModel(String hotelName, String address, String phoneNumber, String websiteDomain) {
        this.hotelName = hotelName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.websiteDomain = websiteDomain;
    }

    // Getters and setters for all fields
    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsiteDomain() {
        return websiteDomain;
    }

    public void setWebsiteDomain(String websiteDomain) {
        this.websiteDomain = websiteDomain;
    }

    @Override
    public String toString() {
        return "HotelInfoModel{" +
                "hotelName='" + hotelName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", websiteDomain='" + websiteDomain + '\'' +
                '}';
    }
}