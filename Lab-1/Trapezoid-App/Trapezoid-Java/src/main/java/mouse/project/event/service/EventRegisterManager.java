package mouse.project.event.service;

import mouse.project.event.type.Event;

import java.util.Collection;

public class EventRegisterManager implements EventAddRegister, EventDeleteRegister{

    private final EventRegister eventRegister;

    public EventRegisterManager(EventRegister eventRegister) {
        this.eventRegister = eventRegister;
    }

    @Override
    public void register(EventListener listener, Class<? extends Event> eventType) {
        eventRegister.register(listener, eventType);
    }

    @Override
    public void register(EventListener listener, Collection<Class<? extends Event>> eventTypes) {
        eventTypes.forEach(e -> eventRegister.register(listener, e));
    }

    @Override
    public void unregister(EventListener listener, Class<? extends Event> eventType) {
        eventRegister.unregister(listener, eventType);
    }

    @Override
    public void unregister(EventListener listener, Collection<Class<? extends Event>> eventTypes) {
        eventTypes.forEach(e -> eventRegister.unregister(listener, e));
    }

    @Override
    public void unregister(EventListener listener) {
        eventRegister.unregister(listener);
    }
}
