package view;

import java.awt.event.MouseListener;
import java.util.EventListener;

public interface ButtonListener extends EventListener {
    void buttonPerformed(RoomEvent roomEvent);
}
