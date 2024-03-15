package mouse.project.state;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgramState {

    private ProgramMode mode;
    public ProgramState() {
        this.mode = ProgramMode.IDLE;
    }
}
