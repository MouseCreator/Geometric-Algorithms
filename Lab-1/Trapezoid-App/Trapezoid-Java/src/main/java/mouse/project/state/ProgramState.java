package mouse.project.state;

import lombok.Getter;
import mouse.project.ui.components.draw.DrawManager;
import mouse.project.ui.components.draw.DrawManagerImpl;
import mouse.project.ui.components.general.ProgramModeListener;

import java.util.ArrayList;
import java.util.List;


public class ProgramState {
    @Getter
    private ProgramMode mode;
    private DrawManager drawManager;
    private final List<ProgramModeListener> listenerList = new ArrayList<>();
    public ProgramState() {
        this.mode = ProgramMode.IDLE;
    }

    public DrawManager getDrawManager() {
       if (drawManager == null) {
           drawManager = new DrawManagerImpl();
       }
       return drawManager;
    }

    public void registerListener(ProgramModeListener listener) {
        listenerList.add(listener);
    }

    public void updateMode(ProgramMode mode) {
        this.mode = mode;
        listenerList.forEach(e -> e.onProgramModeChange(mode));
    }
}
