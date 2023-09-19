package hr.java.vjezbe.entitet;

import java.time.LocalDate;
import java.util.Objects;

public class Student extends Osoba {
    private String jmbag;
    private LocalDate datumRodjenja;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return getJmbag().equals(student.getJmbag());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getJmbag());
    }
    @Override
    public String toString() {
        return "Student{ " +
                super.toString() + " " +
                "jmbag='" + jmbag + '\'' +
                ", datumRodjenja=" + datumRodjenja +
                '}';
    }
}
