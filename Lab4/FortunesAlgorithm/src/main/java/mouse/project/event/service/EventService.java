package mouse.project.event.service;

import lombok.Data;

@Data
public class EventService {
    private EventRegister eventRegister;
    private EventRegisterManager eventRegisterManager;
    private EventGenerator eventGenerator;
}
