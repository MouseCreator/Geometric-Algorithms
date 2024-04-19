package mouse.project.state;

public class DefaultStateHolder implements StateHolder {

    private final ProgramState programState;
    private final GraphicState graphicState;

    public DefaultStateHolder() {
        programState = new ProgramState();
        graphicState = new GraphicState();
    }

    @Override
    public ProgramState getProgramState() {
        return programState;
    }

    @Override
    public GraphicState getGraphicState() {
        return graphicState;
    }
}
