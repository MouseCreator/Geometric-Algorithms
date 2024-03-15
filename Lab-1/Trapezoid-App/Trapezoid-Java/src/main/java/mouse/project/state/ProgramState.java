package mouse.project.state;

public class ProgramState {
    private ProgramMode mode;

    public ProgramState() {
        this.mode = ProgramMode.IDLE;
    }

    public ProgramMode getMode() {
        return mode;
    }

    public void setMode(ProgramMode mode) {
        this.mode = mode;
    }
}
