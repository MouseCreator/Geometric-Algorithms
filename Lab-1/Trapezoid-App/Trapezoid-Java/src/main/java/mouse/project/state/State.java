package mouse.project.state;

public class State {
    private State() {

    }
    private static StateHolder instance = null;

    public static void use(StateHolder stateHolder) {
        if (instance != null) {
            throw new IllegalStateException("State Holder is already defined");
        }
        instance = stateHolder;
    }
    public static StateHolder getInstance() {
        if (instance == null) {
            throw new IllegalStateException("No state holder defined");
        }
        return instance;
    }
}
