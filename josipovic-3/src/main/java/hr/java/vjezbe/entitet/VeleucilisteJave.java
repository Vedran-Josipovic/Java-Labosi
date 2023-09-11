package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

public class VeleucilisteJave extends ObrazovnaUstanova implements Visokoskolska {
    public VeleucilisteJave(String nazivUstanove, Predmet[] predmeti, Student[] studenti, Ispit[] ispiti) {
        super(nazivUstanove, predmeti, studenti, ispiti);
    }

    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(Integer akademskaGodina) {
        BigDecimal maxProsjek = new BigDecimal(0);
        Student najuspjesnijiStudent = getStudenti()[0];

        Ispit[] ispitiGodine = filtrirajIspitePoGodini(getIspiti(), akademskaGodina);

        for (Student s : getStudenti()) {
            Ispit[] studentoviIspiti = filtrirajIspitePoStudentu(ispitiGodine, s);
            BigDecimal prosjek = odrediProsjekOcjenaNaIspitima(studentoviIspiti);
            if (prosjek.compareTo(maxProsjek) >= 0) {
                maxProsjek = prosjek;
                najuspjesnijiStudent = s;
            }
        }

        return najuspjesnijiStudent;
    }


    //„konačna ocjena = (2 * prosjek ocjena studenta + ocjena završnog rada + ocjena obrane završnog rada) / 4
    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] studentoviIspiti, Integer zavrsniPismeni, Integer zavrsniObrana) {
        return (BigDecimal.TWO
                .multiply(odrediProsjekOcjenaNaIspitima(studentoviIspiti))
                .add(BigDecimal.valueOf(zavrsniObrana))
                .add(BigDecimal.valueOf(zavrsniPismeni)))
                .divide(BigDecimal.valueOf(4));
    }
}
