package hr.java.vjezbe.iznimke;

/**
 * Baca se kada postoji više najmlađih studenata sa istim prosjekom ukoliko je taj prosjek najveći od svih studenata.
 */
public class PostojiViseNajmladjihStudenataException extends RuntimeException {
    public PostojiViseNajmladjihStudenataException() {
    }

    public PostojiViseNajmladjihStudenataException(String message) {
        super(message);
    }

    public PostojiViseNajmladjihStudenataException(String message, Throwable cause) {
        super(message, cause);
    }

    public PostojiViseNajmladjihStudenataException(Throwable cause) {
        super(cause);
    }
}
