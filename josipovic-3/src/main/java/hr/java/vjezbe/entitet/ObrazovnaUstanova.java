package hr.java.vjezbe.entitet;

import java.util.Arrays;

public abstract class ObrazovnaUstanova {
    private String nazivUstanove;
    private Predmet[] predmeti;
    private Student[] studenti;
    private Ispit[] ispiti;

    public ObrazovnaUstanova(String nazivUstanove, Predmet[] predmeti, Student[] studenti, Ispit[] ispiti) {
        this.nazivUstanove = nazivUstanove;
        this.predmeti = predmeti;
        this.studenti = studenti;
        this.ispiti = ispiti;
    }

    public abstract Student odrediNajuspjesnijegStudentaNaGodini(Integer akademskaGodina);

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
