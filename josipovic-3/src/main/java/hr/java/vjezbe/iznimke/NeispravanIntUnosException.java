package hr.java.vjezbe.iznimke;

/**
 * Baca se kada je unesen broj van dopuštenih parametara.
 */
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
