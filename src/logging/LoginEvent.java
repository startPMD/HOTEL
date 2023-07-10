package logging;

import java.util.EventObject;

public class LoginEvent extends EventObject {
    public LoginEvent(Object source) {
        super(source);
    }
}