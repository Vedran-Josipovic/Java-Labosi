package hr.java.vjezbe.entitet;
import java.util.Arrays;


/**
 * Predstavlja obrazovnu ustanovu.
 */
public abstract class ObrazovnaUstanova {
    private String nazivUstanove;
    private Predmet[] predmeti;
    private Student[] studenti;
    private Ispit[] ispiti;


    /**
     * @param nazivUstanove Naziv obrazovne ustanove.
     * @param predmeti Polje predmeta koji se izvode na obrazovnoj ustanovi.
     * @param studenti Polje studenata koji pohađaju obrazovnu ustanovu.
     * @param ispiti Polje ispita koji se održavaju na obrazovnoj ustanovi.
     */
    public ObrazovnaUstanova(String nazivUstanove, Predmet[] predmeti, Student[] studenti, Ispit[] ispiti) {
        this.nazivUstanove = nazivUstanove;
        this.predmeti = predmeti;
        this.studenti = studenti;
        this.ispiti = ispiti;
    }


    /**
     * Određuje najuspješnijeg studenta za određenu akademsku godinu.
     * @param akademskaGod Akademska godina za koju se određuje najuspješniji student
     * @return najuspješniji student za određenu akademsku godinu
     */
    public abstract Student odrediNajuspjesnijegStudentaNaGodini(Integer akademskaGod);

    public String getNazivUstanove() {
        return nazivUstanove;
    }
    public void setNazivUstanove(String nazivUstanove) {
        this.nazivUstanove = nazivUstanove;
    }
    public Predmet[] getPredmeti() {
        return predmeti;
    }
    public void setPredmeti(Predmet[] predmeti) {
        this.predmeti = predmeti;
    }
    public Student[] getStudenti() {
        return studenti;
    }
    public void setStudenti(Student[] studenti) {
        this.studenti = studenti;
    }
    public Ispit[] getIspiti() {
        return ispiti;
    }
    public void setIspiti(Ispit[] ispiti) {
        this.ispiti = ispiti;
    }

    /**
     * @return string reprezentacija objekta klase {@code ObrazovnaUstanova}
     */
    @Override
    public String toString() {
        return "ObrazovnaUstanova{" +
                "nazivUstanove='" + nazivUstanove + '\'' +
                ", predmeti=" + Arrays.toString(predmeti) +
                ", studenti=" + Arrays.toString(studenti) +
                ", ispiti=" + Arrays.toString(ispiti) +
                '}';
    }
}
