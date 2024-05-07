package mouse.project.event.service;

import mouse.project.event.type.Event;

public class Events {
    private static EventService eventService = null;

    private static EventService getEventService() {
        if (eventService == null) {
            eventService = EventServiceFactory.getInstance().getEventService();
        }
        return eventService;
    }

    public static void generate(Event event) {
        getEventService().getEventGenerator().createEvent(event);
    }

    public static void register(EventListener eventListener) {
        eventListener.register(getEventService().getEventRegisterManager());
    }

    public static void unregister(EventListener eventListener) {
        eventListener.unregister(getEventService().getEventRegisterManager());
    }
}
