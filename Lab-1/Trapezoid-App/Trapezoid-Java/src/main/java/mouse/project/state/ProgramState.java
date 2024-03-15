package mouse.project.state;

import lombok.Getter;
import lombok.Setter;
import mouse.project.ui.components.draw.DrawManager;
import mouse.project.ui.components.draw.DrawManagerImpl;


public class ProgramState {
    @Getter
    @Setter
    private ProgramMode mode;
    private DrawManager drawManager;
    public ProgramState() {
        this.mode = ProgramMode.IDLE;
    }

    public DrawManager getDrawManager() {
       if (drawManager == null) {
           drawManager = new DrawManagerImpl();
       }
       return drawManager;
    }
}
