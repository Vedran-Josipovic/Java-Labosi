package hr.java.vjezbe.entitet;

/**
 * Služi kako bi klasa FakultetRacunarstva imala pristup metodama iz ovog sučelja
 * bez mijenjanja funkcionalnosti sučelja Visokoskolska.
 */
public interface Diplomski extends Visokoskolska {
    /**
     * @return Student koji osvaja rektorovu nagradu
     */
    Student odrediStudentaZaRektorovuNagradu();
}
