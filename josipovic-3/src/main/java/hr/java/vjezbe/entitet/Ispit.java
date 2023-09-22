package hr.java.vjezbe.entitet;
import java.time.LocalDateTime;


/**
 * Predstavlja ispit koji student polaže na obrazovnoj ustanovi.
 */
public final class Ispit implements Online {
    private Predmet predmet;
    private Student student;
    private Integer ocjena;
    private LocalDateTime datumIVrijeme;
    private Dvorana dvorana;
    private String nazivOnlineSoftvera = null;


    /**
     * @param predmet predmet koji student polaže
     * @param student student koji polaže ispit
     * @param ocjena ocjena koju je student dobio na ispitu
     * @param datumIVrijeme datum i vrijeme kada se ispit održava
     * @param dvorana dvorana u kojoj se ispit održava
     */
    public Ispit(Predmet predmet, Student student, Integer ocjena, LocalDateTime datumIVrijeme, Dvorana dvorana) {
        this.predmet = predmet;
        this.student = student;
        this.ocjena = ocjena;
        this.datumIVrijeme = datumIVrijeme;
        this.dvorana = dvorana;
    }

    /**
     * @param predmet predmet koji student polaže
     * @param student student koji polaže ispit
     * @param ocjena ocjena koju je student dobio na ispitu
     * @param datumIVrijeme datum i vrijeme kada se ispit održava
     */
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


    /**
     * @param nazivOnlineSoftvera naziv online softvera koji se koristi za ispit
     */
    @Override
    public void setNazivOnlineSoftvera(String nazivOnlineSoftvera) {
        this.nazivOnlineSoftvera = nazivOnlineSoftvera;
    }

    /**
     * @return naziv online softvera koji se koristi za ispit
     */
    public String getNazivOnlineSoftvera() {
        return nazivOnlineSoftvera;
    }

    /**
     * @return string reprezentacija objekta klase {@code Ispit}
     */
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
