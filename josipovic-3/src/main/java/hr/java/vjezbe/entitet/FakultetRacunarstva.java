package hr.java.vjezbe.entitet;
import java.math.BigDecimal;

public class FakultetRacunarstva extends ObrazovnaUstanova implements Diplomski {
    public FakultetRacunarstva(String nazivUstanove, Predmet[] predmeti, Student[] studenti, Ispit[] ispiti) {
        super(nazivUstanove, predmeti, studenti, ispiti);
    }

    /**
     * Vraća studenta koji ima najviše ispita ocijenjenih ocjenom 5.
     * Ako takvih studenata ima više, prednost ima student s manjim indeksom unutar polja studenata.
     */
    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(Integer akademskaGod) {
        Student bestStudent = getStudenti()[0];
        Integer maxBrPetica = 0;
        Ispit[] ispitiGodine = filtrirajIspitePoGodini(getIspiti(), akademskaGod);

        for (Student s : getStudenti()) {
            Integer brPetica = 0;
            Ispit[] studentoviIspiti = filtrirajIspitePoStudentu(ispitiGodine, s);
            for (Ispit i : studentoviIspiti) if (i.getOcjena() == 5) brPetica++;
            if (brPetica > maxBrPetica) {
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
    public Student odrediStudentaZaRektorovuNagradu() {
        BigDecimal maxProsjek = new BigDecimal(0);
        Student bestStudent = getStudenti()[0];

        for (Student s : getStudenti()) {
            Ispit[] studentoviIspiti = filtrirajIspitePoStudentu(getIspiti(), s);
            BigDecimal prosjek = odrediProsjekOcjenaNaIspitima(studentoviIspiti);
            if (prosjek.compareTo(maxProsjek) > 0) {
                maxProsjek = prosjek;
                bestStudent = s;
            }
            else if (prosjek.compareTo(maxProsjek) == 0 && s.getDatumRodjenja().isBefore(bestStudent.getDatumRodjenja())) {
                maxProsjek = prosjek;
                bestStudent = s;
            }
        }
        return bestStudent;
    }

    /**
     * konačna ocjena = (3 * prosjek ocjena studenta + ocjena diplomskog rada + ocjena obrane diplomskog rada) / 5
     */
    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] studentoviIspiti, Integer diplomskiPismeni, Integer diplomskiObrana) {
        return BigDecimal.valueOf(3)
                .multiply(odrediProsjekOcjenaNaIspitima(studentoviIspiti))
                .add(BigDecimal.valueOf(diplomskiPismeni))
                .add(BigDecimal.valueOf(diplomskiObrana))
                .divide(BigDecimal.valueOf(5));
    }
}
