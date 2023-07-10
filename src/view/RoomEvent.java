package view;

import java.util.EventObject;
public class RoomEvent extends EventObject {
    String nameBtn;
    public RoomEvent(Object source) {
        super(source);

    }
    public RoomEvent(Object source,String nameBtn) {
        super(source);
        this.nameBtn = nameBtn;
    }

    public String getNameBtn() {
        return nameBtn;
    }
}