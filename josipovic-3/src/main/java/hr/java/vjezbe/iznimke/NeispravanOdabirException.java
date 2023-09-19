package hr.java.vjezbe.iznimke;

public class NeispravanOdabirException extends RuntimeException{
    public NeispravanOdabirException() {
    }

    public NeispravanOdabirException(String message) {
        super(message);
    }

    public NeispravanOdabirException(String message, Throwable cause) {
        super(message, cause);
    }

    public NeispravanOdabirException(Throwable cause) {
        super(cause);
    }
}
