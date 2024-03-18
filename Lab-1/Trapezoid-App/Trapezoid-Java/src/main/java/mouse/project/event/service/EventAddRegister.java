package mouse.project.event.service;

import mouse.project.event.type.Event;

import java.util.Collection;

public interface EventAddRegister {
    void register(EventListener listener, Class<? extends Event> eventType);
    void register(EventListener listener, Collection<Class<? extends Event>> eventTypes);
}
