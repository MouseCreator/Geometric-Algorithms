package mouse.project.event.service;

import mouse.project.event.type.Event;

import java.util.Set;

public interface EventRegister {
    void register(EventListener listener, Class<? extends Event> listen);
    void unregister(EventListener listener, Class<? extends Event> listen);
    void unregister(EventListener listener);
    Set<EventListener> getListeners(Class<? extends Event> event);
}
