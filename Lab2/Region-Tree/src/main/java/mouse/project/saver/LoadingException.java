package mouse.project.saver;

public class LoadingException extends RuntimeException{
    public LoadingException() {
    }

    public LoadingException(String message) {
        super(message);
    }

    public LoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoadingException(Throwable cause) {
        super(cause);
    }
}
