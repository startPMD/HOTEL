package model;

public class LayoutRoomModel {
    private int numberRoom;
    private String typeRoom;
    private int numGuestRoom;
    private double priceRoo;

    public LayoutRoomModel( ) {
    }

    public LayoutRoomModel(int numberRoom, String typeRoom, int numGuestRoom, double priceRoo) {
        this.numberRoom = numberRoom;
        this.typeRoom = typeRoom;
        this.numGuestRoom = numGuestRoom;
        this.priceRoo = priceRoo;
    }

    public int getNumberRoom() {
        return numberRoom;
    }

    public String getTypeRoom() {
        return typeRoom;
    }

    public int getNumGuestRoom() {
        return numGuestRoom;
    }

    public double getPriceRoom() {
        return priceRoo;
    }

    @Override
    public String toString() {
        return "LayoutRoomModel{" +
                "numberRoom='" + numberRoom + '\'' +
                ", typeRoom=" + typeRoom +
                ", numGuestRoom=" + numGuestRoom +
                ", priceRoo=" + priceRoo +
                '}';
    }
}
