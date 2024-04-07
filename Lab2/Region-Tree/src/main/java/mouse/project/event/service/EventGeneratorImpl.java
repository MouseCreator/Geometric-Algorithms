package mouse.project.event.service;

import mouse.project.event.type.Event;

import java.util.Set;

public class EventGeneratorImpl implements EventGenerator {

    private final EventRegister eventRegister;

    public EventGeneratorImpl(EventRegister eventRegister) {
        this.eventRegister = eventRegister;
    }

    @Override
    public void createEvent(Event event) {
        Set<EventListener> listeners = eventRegister.getListeners(event.getClass());
        listeners.forEach(l -> l.onEvent(event));
    }
}
