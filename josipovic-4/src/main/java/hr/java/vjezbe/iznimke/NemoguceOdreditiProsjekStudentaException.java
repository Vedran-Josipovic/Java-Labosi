package hr.java.vjezbe.iznimke;

/**
 * Baca se kada student ima bilo koju ocjenu ispita „nedovoljan (1)“ i nije moguće
 * odrediti prosjek studenta.
 */
public class NemoguceOdreditiProsjekStudentaException extends Exception {
    public NemoguceOdreditiProsjekStudentaException() {
    }

    public NemoguceOdreditiProsjekStudentaException(String message) {
        super(message);
    }

    public NemoguceOdreditiProsjekStudentaException(String message, Throwable cause) {
        super(message, cause);
    }

    public NemoguceOdreditiProsjekStudentaException(Throwable cause) {
        super(cause);
    }


}
