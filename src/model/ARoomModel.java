package model;

import java.util.Map;

public abstract class ARoomModel {
    protected String typeRoom;
    protected int numberRoom;
    protected Map<Integer,Integer> numberRooms;

    public ARoomModel() {
    }

    public ARoomModel(String typeRoom, Map<Integer,Integer> numberRooms) {
        this.typeRoom = typeRoom;
        this.numberRooms = numberRooms;
    }
    public void setNumberRoom(int numberRoom){
        this.numberRoom = numberRoom;
    }
    public String getTypeRoom() {
        return typeRoom;
    }

    public void setTypeRoom(String typeRoom) {
        this.typeRoom = typeRoom;
    }

    public Map<Integer,Integer> getNumberRooms() {
        return numberRooms;
    }

    public void setNumberRooms(Map<Integer,Integer> numberRooms) {
        this.numberRooms = numberRooms;
    }

    @Override
    public String toString() {
        return "ARoomModel{" +
                "typeRoom='" + typeRoom + '\'' +
                ", numberRooms=" + numberRooms +
                '}';
    }

    public int getIdRoom() {
        return 0;
    }
}
