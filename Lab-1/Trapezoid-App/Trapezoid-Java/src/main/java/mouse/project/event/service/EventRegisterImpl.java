package mouse.project.event.service;

import mouse.project.event.type.Event;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EventRegisterImpl implements EventRegister {
    private final Map<Class<? extends Event>, Set<EventListener>> listenersMap;

    public EventRegisterImpl() {
        listenersMap = new HashMap<>();
    }

    public void register(EventListener listener, Class<? extends Event> listen) {
        Set<EventListener> eventListeners = listenersMap.computeIfAbsent(listen, k -> new HashSet<>());
        eventListeners.add(listener);
    }

    public void unregister(EventListener listener, Class<? extends Event> listen) {
        Set<EventListener> eventListeners = listenersMap.get(listen);
        if (eventListeners == null) {
            return;
        }
        eventListeners.remove(listener);
    }

    @Override
    public void unregister(EventListener listener) {
        listenersMap.values().forEach(r -> r.remove(listener));
    }

    @Override
    public Set<EventListener> getListeners(Class<? extends Event> event) {
        Set<EventListener> eventListeners = listenersMap.get(event);
        if (eventListeners == null) {
            return new HashSet<>();
        }
        return new HashSet<>(eventListeners);
    }
}
