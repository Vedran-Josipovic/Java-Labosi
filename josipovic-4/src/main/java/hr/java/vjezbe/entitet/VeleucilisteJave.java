package hr.java.vjezbe.entitet;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * Visokoškolska obrazovna ustanova koja je usmjerena na podučavanje Jave.
 */
public class VeleucilisteJave extends ObrazovnaUstanova implements Visokoskolska {
    private static final Logger logger = LoggerFactory.getLogger(VeleucilisteJave.class);

    /**
     * @param nazivUstanove Naziv obrazovne ustanove.
     * @param predmeti Polje predmeta koji se izvode na obrazovnoj ustanovi.
     * @param studenti Polje studenata koji pohađaju obrazovnu ustanovu.
     * @param ispiti Polje ispita koji se održavaju na obrazovnoj ustanovi.
     */
    public VeleucilisteJave(String nazivUstanove, Predmet[] predmeti, Student[] studenti, Ispit[] ispiti) {
        super(nazivUstanove, predmeti, studenti, ispiti);
    }

    /**
     * Vraća studenta s najvišim prosjekom. <p>
     * Ako takvih studenata ima više, prednost ima student s većim indeksom unutar polja studenata. <p>
     *
     * Metoda prolazi kroz sve studente i za svakog studenta izračunava prosjek ocjena na ispitima.
     * Ako student ima negativnu ocjenu na jednom od ispita, obrađuje se iznimka
     * {@code NemoguceOdreditiProsjekStudentaException}, a prosjek se postavlja na 1.
     * U tom slučaju, metoda ispisuje upozorenje u log i na standardni izlaz.
     *
     * @param akademskaGod Akademska godina za koju se određuje najuspješniji student.
     * @return Student koji ima najviši prosjek.
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
                logger.warn("Student " + s.getImePrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek „nedovoljan (1)“! " + e);
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
     * Izračunava konačnu ocjenu studija za studenta na temelju prosjeka ocjena na ispitima,
     * ocjene završnog rada i ocjene obrane završnog rada.
     *
     * @param studentoviIspiti Polje ispita kojima je student pristupio
     * @param zavrsniPismeni Ocjena studenta za pismeni dio završnog rada
     * @param zavrsniObrana Ocjena studenta za obranu završnog rada
     * @return konačna ocjena = (2 * prosjek ocjena studenta + ocjena završnog rada + ocjena obrane završnog rada) / 4.
     * Ako je nemoguće odrediti prosjek ocjena na ispitima (npr. ako student ima negativnu ocjenu na jednom od ispita), vraća se ocjena 1
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
            logger.warn("Student " + s.getImePrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek „nedovoljan (1)“! " + e);
            System.out.println("Student " + s.getImePrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek „nedovoljan (1)“!");
            return BigDecimal.ONE;
        }
    }
}
