package mouse.project.event.service;

import mouse.project.event.type.Event;

import java.util.Collection;

public interface EventDeleteRegister {
    void unregister(EventListener listener, Class<? extends Event> eventType);
    void unregister(EventListener listener, Collection<Class<? extends Event>> eventTypes);
    void unregister(EventListener listener);
}
