package hr.java.vjezbe.entitet;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class VeleucilisteJave extends ObrazovnaUstanova implements Visokoskolska {
    private static final Logger logger = LoggerFactory.getLogger(VeleucilisteJave.class);
    public VeleucilisteJave(String nazivUstanove, Predmet[] predmeti, Student[] studenti, Ispit[] ispiti) {
        super(nazivUstanove, predmeti, studenti, ispiti);
    }

    /**
     * Vraća studenta koji ima najviši prosjek.
     * Ako takvih studenata ima više, prednost ima student s većim indeksom unutar polja studenata.
     */
    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(Integer akademskaGod) {
        Student bestStudent = getStudenti()[0];
        BigDecimal maxProsjek = new BigDecimal(0);
        Ispit[] ispitiGodine = filtrirajIspitePoGodini(getIspiti(), akademskaGod);

        for (Student s : getStudenti()) {
            Ispit[] studentoviIspiti = filtrirajIspitePoStudentu(ispitiGodine, s);
            BigDecimal prosjek;
            try {
                prosjek = odrediProsjekOcjenaNaIspitima(studentoviIspiti);
            } catch (NemoguceOdreditiProsjekStudentaException e) {
                logger.warn("[VeleucilisteJave.odrediNajuspjesnijegStudentaNaGodini] Student " + s.getImePrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek „nedovoljan (1)“!" + e);
                System.out.println("Student " + s.getImePrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek „nedovoljan (1)“!");
                prosjek = BigDecimal.ONE;
            }
            if (prosjek.compareTo(maxProsjek) >= 0) {
                maxProsjek = prosjek;
                bestStudent = s;
            }
        }
        return bestStudent;
    }

    /**
     * konačna ocjena = (2 * prosjek ocjena studenta + ocjena završnog rada + ocjena obrane završnog rada) / 4
     */
    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] studentoviIspiti, Integer zavrsniPismeni, Integer zavrsniObrana) {
        try {
            return BigDecimal.valueOf(2)
                    .multiply(odrediProsjekOcjenaNaIspitima(studentoviIspiti))
                    .add(BigDecimal.valueOf(zavrsniPismeni))
                    .add(BigDecimal.valueOf(zavrsniObrana))
                    .divide(BigDecimal.valueOf(4), 2, RoundingMode.HALF_UP);
        } catch (NemoguceOdreditiProsjekStudentaException e) {
            Student s = studentoviIspiti[0].getStudent();
            logger.warn("[VeleucilisteJave.izracunajKonacnuOcjenuStudijaZaStudenta] Student " + s.getImePrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek „nedovoljan (1)“!" + e);
            System.out.println("Student " + s.getImePrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek „nedovoljan (1)“!");
            return BigDecimal.ONE;
        }
    }
}
