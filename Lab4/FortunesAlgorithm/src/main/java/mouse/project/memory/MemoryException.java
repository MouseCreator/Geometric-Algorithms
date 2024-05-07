package mouse.project.memory;

public class MemoryException extends RuntimeException{
    public MemoryException() {
    }

    public MemoryException(String message) {
        super(message);
    }

    public MemoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemoryException(Throwable cause) {
        super(cause);
    }
}
