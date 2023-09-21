package hr.java.vjezbe.entitet;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import hr.java.vjezbe.iznimke.PostojiViseNajmladjihStudenataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FakultetRacunarstva extends ObrazovnaUstanova implements Diplomski {
    private static final Logger logger = LoggerFactory.getLogger(FakultetRacunarstva.class);

    public FakultetRacunarstva(String nazivUstanove, Predmet[] predmeti, Student[] studenti, Ispit[] ispiti) {
        super(nazivUstanove, predmeti, studenti, ispiti);
    }

    /**
     * Vraća studenta koji ima najviše ispita ocijenjenih ocjenom 5.
     * Ako takvih studenata ima više, prednost ima student s većim indeksom unutar polja studenata.
     */
    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(Integer akademskaGod) {
        Student bestStudent = getStudenti()[0];
        int maxBrPetica = 0;
        Ispit[] ispitiGodine = filtrirajIspitePoGodini(getIspiti(), akademskaGod);

        for (Student s : getStudenti()) {
            int brPetica = 0;
            Ispit[] studentoviIspiti = filtrirajIspitePoStudentu(ispitiGodine, s);
            for (Ispit i : studentoviIspiti) if (i.getOcjena() == 5) brPetica++;
            if (brPetica >= maxBrPetica) {
                maxBrPetica = brPetica;
                bestStudent = s;
            }
        }
        return bestStudent;
    }

    /**
     * Vraća studenta s najvišim prosjekom. Ako takvih studenata ima više, prednost ima najmlađi.
     */
    @Override
    public Student odrediStudentaZaRektorovuNagradu() throws PostojiViseNajmladjihStudenataException {
        BigDecimal maxProsjek = new BigDecimal(0);
        Student bestStudent = getStudenti()[0];

        for (Student s : getStudenti()) {
            Ispit[] studentoviIspiti = filtrirajIspitePoStudentu(getIspiti(), s);

            BigDecimal prosjek;
            try {
                prosjek = odrediProsjekOcjenaNaIspitima(studentoviIspiti);
            } catch (NemoguceOdreditiProsjekStudentaException e) {
                logger.warn("Student " + s.getImePrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek „nedovoljan (1)“! " + e);
                //System.out.println("Student " + s.getImePrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek „nedovoljan (1)“!");
                prosjek = BigDecimal.ONE;
            }


            if (prosjek.compareTo(maxProsjek) > 0) {
                maxProsjek = prosjek;
                bestStudent = s;
            }
            else if (prosjek.compareTo(maxProsjek) == 0 && s.getDatumRodjenja().isBefore(bestStudent.getDatumRodjenja())) {
                maxProsjek = prosjek;
                bestStudent = s;
            }
            else if (prosjek.compareTo(maxProsjek) == 0 && s.getDatumRodjenja().isEqual(bestStudent.getDatumRodjenja())) {
                throw new PostojiViseNajmladjihStudenataException(bestStudent.getImePrezime() + " i " + s.getImePrezime());
            }
        }
        return bestStudent;
    }

    /**
     * konačna ocjena = (3 * prosjek ocjena studenta + ocjena diplomskog rada + ocjena obrane diplomskog rada) / 5
     */
    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] studentoviIspiti, Integer diplomskiPismeni, Integer diplomskiObrana) {
        try {
            return BigDecimal.valueOf(3)
                    .multiply(odrediProsjekOcjenaNaIspitima(studentoviIspiti))
                    .add(BigDecimal.valueOf(diplomskiPismeni))
                    .add(BigDecimal.valueOf(diplomskiObrana))
                    .divide(BigDecimal.valueOf(5), 2, RoundingMode.HALF_UP);
        } catch (NemoguceOdreditiProsjekStudentaException e) {
            Student s = studentoviIspiti[0].getStudent();
            logger.warn("Student " + s.getImePrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek „nedovoljan (1)“! " + e);
            System.out.println("Student " + s.getImePrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek „nedovoljan (1)“!");
            return BigDecimal.ONE;
        }
    }
}
