package mouse.project.event.service;

public class EventServiceFactory {

    private EventServiceFactory() {
    }

    private static EventServiceFactory instance = null;

    public static EventServiceFactory getInstance() {
        if (instance == null) {
            instance = new EventServiceFactory();
        }
        return instance;
    }

    public EventService getEventService() {
        EventService eventService = new EventService();

        EventRegister eventRegister = new EventRegisterImpl();
        EventRegisterManager manager = new EventRegisterManager(eventRegister);
        EventGenerator eventGenerator = new EventGeneratorImpl(eventRegister);

        eventService.setEventRegister(eventRegister);
        eventService.setEventRegisterManager(manager);
        eventService.setEventGenerator(eventGenerator);

        return eventService;
    }
}
