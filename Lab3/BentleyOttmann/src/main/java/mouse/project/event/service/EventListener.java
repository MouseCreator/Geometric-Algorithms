package mouse.project.event.service;

import mouse.project.event.type.Event;

public interface EventListener {
    void onEvent(Event event);
    void register(EventAddRegister eventAddRegister);
    void unregister(EventDeleteRegister eventDeleteRegister);
}
