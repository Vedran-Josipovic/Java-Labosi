package hr.java.vjezbe.entitet;
import java.time.LocalDateTime;

public final class Ispit implements Online {
    private Predmet predmet;
    private Student student;
    private Integer ocjena;
    private LocalDateTime datumIVrijeme;
    private Dvorana dvorana;
    private String nazivOnlineSoftvera = null;

    public Ispit(Predmet predmet, Student student, Integer ocjena, LocalDateTime datumIVrijeme, Dvorana dvorana) {
        this.predmet = predmet;
        this.student = student;
        this.ocjena = ocjena;
        this.datumIVrijeme = datumIVrijeme;
        this.dvorana = dvorana;
    }

    public Ispit(Predmet predmet, Student student, Integer ocjena, LocalDateTime datumIVrijeme) {
        this.predmet = predmet;
        this.student = student;
        this.ocjena = ocjena;
        this.datumIVrijeme = datumIVrijeme;
    }

    public Predmet getPredmet() {
        return predmet;
    }
    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }
    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    public Integer getOcjena() {
        return ocjena;
    }
    public void setOcjena(Integer ocjena) {
        this.ocjena = ocjena;
    }
    public LocalDateTime getDatumIVrijeme() {
        return datumIVrijeme;
    }
    public void setDatumIVrijeme(LocalDateTime datumIVrijeme) {
        this.datumIVrijeme = datumIVrijeme;
    }
    public Dvorana getDvorana() {
        return dvorana;
    }
    public void setDvorana(Dvorana dvorana) {
        this.dvorana = dvorana;
    }
    @Override
    public void setNazivOnlineSoftvera(String nazivOnlineSoftvera) {
        this.nazivOnlineSoftvera = nazivOnlineSoftvera;
    }
    public String getNazivOnlineSoftvera() {
        return nazivOnlineSoftvera;
    }

    @Override
    public String toString() {
        return "Ispit{" +
                "predmet=" + predmet +
                ", student=" + student +
                ", ocjena=" + ocjena +
                ", datumIVrijeme=" + datumIVrijeme +
                ", dvorana=" + dvorana +
                ", nazivOnlineSoftvera='" + nazivOnlineSoftvera + '\'' +
                '}';
    }
}
