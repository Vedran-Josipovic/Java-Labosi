package hr.java.vjezbe.entitet;
import java.util.ArrayList;

/**
 * Predstavlja predmet koji se izvodi na obrazovnoj ustanovi.
 */
public class Predmet {
    private String sifra, naziv;
    private Integer brojEctsBodova;
    private Profesor nositelj;
    private ArrayList<Student> studenti = new ArrayList<>();

    /**
     * @param sifra Šifra predmeta.
     * @param naziv Naziv predmeta.
     * @param brojEctsBodova Broj ECTS bodova za predmet.
     * @param nositelj Nositelj predmeta.
     */
    public Predmet(String sifra, String naziv, Integer brojEctsBodova, Profesor nositelj) {
        this.sifra = sifra;
        this.naziv = naziv;
        this.brojEctsBodova = brojEctsBodova;
        this.nositelj = nositelj;
    }

    /**
     * @param sifra Šifra predmeta.
     * @param naziv Naziv predmeta.
     * @param brojEctsBodova Broj ECTS bodova koje predmet nosi.
     * @param nositelj Profesor koji predaje predmet.
     * @param studenti Popis studenata koji pohađaju predmet.
     */
    public Predmet(String sifra, String naziv, Integer brojEctsBodova, Profesor nositelj, ArrayList<Student> studenti) {
        this.sifra = sifra;
        this.naziv = naziv;
        this.brojEctsBodova = brojEctsBodova;
        this.nositelj = nositelj;
        this.studenti = studenti;
    }

    public String getSifra() {
        return sifra;
    }
    public void setSifra(String sifra) {
        this.sifra = sifra;
    }
    public String getNaziv() {
        return naziv;
    }
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    public Integer getBrojEctsBodova() {
        return brojEctsBodova;
    }
    public void setBrojEctsBodova(Integer brojEctsBodova) {
        this.brojEctsBodova = brojEctsBodova;
    }
    public Profesor getNositelj() {
        return nositelj;
    }
    public void setNositelj(Profesor nositelj) {
        this.nositelj = nositelj;
    }
    public ArrayList<Student> getStudenti() {
        return studenti;
    }
    public void setStudenti(ArrayList<Student> studenti) {
        this.studenti = studenti;
    }

    /**
     * Dodaje studenta na popis studenata koji pohađaju predmet. <p>
     * Ako student već pohađa predmet, metoda neće ništa učiniti.
     *
     * @param student student koji se dodaje na popis studenata koji pohađaju predmet
     */
    public void addStudent(Student student) {
        if (studenti.contains(student)) return;
        this.studenti.add(student);
    }

    /**
     * @return string reprezentacija objekta klase {@code Predmet}
     */
    @Override
    public String toString() {
        return "Predmet{" +
                "sifra='" + sifra + '\'' +
                ", naziv='" + naziv + '\'' +
                ", brojEctsBodova=" + brojEctsBodova +
                ", nositelj=" + nositelj +
                ", studenti=" + studenti +
                '}';
    }
}
