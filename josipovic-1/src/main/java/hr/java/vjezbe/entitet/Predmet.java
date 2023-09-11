package hr.java.vjezbe.entitet;

import java.util.Arrays;

public class Predmet {
    private String sifra, naziv;
    private Integer brojEctsBodova;
    private Profesor nositelj;
    private Student[] studenti;

    public Predmet(String sifra, String naziv, Integer brojEctsBodova, Profesor nositelj) {
        this.sifra = sifra;
        this.naziv = naziv;
        this.brojEctsBodova = brojEctsBodova;
        this.nositelj = nositelj;
    }

    public Predmet(String sifra, String naziv, Integer brojEctsBodova, Profesor nositelj, Student[] studenti) {
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

    public Student[] getStudenti() {
        return studenti;
    }

    public void setStudenti(Student[] studenti) {
        this.studenti = studenti;
    }

    @Override
    public String toString() {
        return "Predmet{" +
                "sifra='" + sifra + '\'' +
                ", naziv='" + naziv + '\'' +
                ", brojEctsBodova=" + brojEctsBodova +
                ", nositelj=" + nositelj +
                ", studenti=" + Arrays.toString(studenti) +
                '}';
    }
}
