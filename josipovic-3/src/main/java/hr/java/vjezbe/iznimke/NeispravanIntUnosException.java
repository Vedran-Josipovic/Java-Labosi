package hr.java.vjezbe.iznimke;

public class NeispravanIntUnosException extends RuntimeException{
    public NeispravanIntUnosException() {
    }

    public NeispravanIntUnosException(String message) {
        super(message);
    }

    public NeispravanIntUnosException(String message, Throwable cause) {
        super(message, cause);
    }

    public NeispravanIntUnosException(Throwable cause) {
        super(cause);
    }
}
