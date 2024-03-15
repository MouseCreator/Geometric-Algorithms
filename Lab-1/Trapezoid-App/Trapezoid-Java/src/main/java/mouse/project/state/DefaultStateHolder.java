package mouse.project.state;

public class DefaultStateHolder implements StateHolder {

    private final ProgramState programState;
    private final AlgorithmState algorithmState;

    public DefaultStateHolder() {
        programState = new ProgramState();
        algorithmState = new AlgorithmState();
    }

    @Override
    public ProgramState getProgramState() {
        return programState;
    }

    @Override
    public AlgorithmState getAlgorithmState() {
        return algorithmState;
    }
}
