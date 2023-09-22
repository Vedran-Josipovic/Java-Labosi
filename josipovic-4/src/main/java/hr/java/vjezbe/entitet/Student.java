package hr.java.vjezbe.entitet;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Predstavlja studenta na obrazovnoj ustanovi.
 */
public class Student extends Osoba {
    private String jmbag;
    private LocalDate datumRodjenja;

    /**
     * @param ime Ime studenta.
     * @param prezime Prezime studenta.
     * @param jmbag JMBAG studenta.
     * @param datumRodjenja Datum rođenja studenta.
     */
    public Student(String ime, String prezime, String jmbag, LocalDate datumRodjenja) {
        super(ime, prezime);
        this.jmbag = jmbag;
        this.datumRodjenja = datumRodjenja;
    }

    public String getJmbag() {
        return jmbag;
    }
    public void setJmbag(String jmbag) {
        this.jmbag = jmbag;
    }
    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }
    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    /**
     * Provjerava jesu li dva objekta klase {@code Student} jednaka na temelju njihovih JMBAG-a.
     *
     * @param o Objekt koji se uspoređuje s trenutnim objektom.
     * @return {@code true} ako su objekti jednaki, inače {@code false}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return getJmbag().equals(student.getJmbag());
    }

    /**
     * Generira hash kod za objekt klase {@code Student} na temelju njegovog JMBAG-a.
     *
     * @return Hash kod za objekt.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getJmbag());
    }

    /**
     * @return string reprezentacija objekta klase {@code Student}
     */
    @Override
    public String toString() {
        return "Student{ " +
                super.toString() + " " +
                "jmbag='" + jmbag + '\'' +
                ", datumRodjenja=" + datumRodjenja +
                '}';
    }
}
