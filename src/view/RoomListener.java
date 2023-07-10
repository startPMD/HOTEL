package view;

import java.util.EventListener;

public interface RoomListener extends EventListener {
    void btnRefreshPerformed(RoomEvent event);
}
