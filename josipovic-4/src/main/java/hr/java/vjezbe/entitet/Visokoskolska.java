package hr.java.vjezbe.entitet;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Predstavlja visokoškolsku ustanovu.
 */
public interface Visokoskolska {
    Logger logger = LoggerFactory.getLogger(Visokoskolska.class);

    /**
     * Definira računanje konačne ocjene studija za studenta na temelju ocjena na ispitima,
     * ocjene završnog rada i ocjene obrane završnog rada.
     *
     * @param studentoviIspiti Polje ispita kojima je student pristupio
     * @param zavrsniPismeni Ocjena studenta za pismeni dio završnog rada
     * @param zavrsniObrana Ocjena studenta za obranu završnog rada
     * @return Konačna ocjena studija
     */
    BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] studentoviIspiti, Integer zavrsniPismeni, Integer zavrsniObrana);

    /**
     * Vraća prosječnu ocjenu na ispitima koji su položeni s pozitivnom ocjenom. <p>
     * Ako je student dobio ocjenu 1 na jednom od ispita, baca se iznimka.
     *
     * @param ispitiStudenta Polje ispita kojima je student pristupio
     * @return Prosjek ocjena na ispitima
     * @throws NemoguceOdreditiProsjekStudentaException Ako je student dobio ocjenu '1' na bilo kojem ispitu
     */
    default BigDecimal odrediProsjekOcjenaNaIspitima(Ispit[] ispitiStudenta) throws NemoguceOdreditiProsjekStudentaException {
        int pom = 0;
        for (var i : ispitiStudenta) {
            if (i.getOcjena() == 1) throw new NemoguceOdreditiProsjekStudentaException("Polje ispita sadrži ispit sa ocjenom '1'");
            pom += i.getOcjena();
        }
        return new BigDecimal((double)pom / ispitiStudenta.length);
    }

    /**
     * Vraća prosječnu ocjenu na ispitima koji su položeni s pozitivnom ocjenom.
     *
     * @deprecated Zastarjela i zamijenjena novom metodom.
     * @param ispitiStudenta Polje ispita kojima je student pristupio
     * @return Prosjek ocjena na ispitima. Ako niti jedan ispit nije položen, vraća se BigDecimal.ZERO
     * @throws ArithmeticException Ako niti jedan ispit nije položen
     */
    default BigDecimal odrediProsjekOcjenaNaIspitimaDepreciated(Ispit[] ispitiStudenta) throws ArithmeticException {
        try {
            Ispit[] polozeniIspiti = filtrirajPolozeneIspite(ispitiStudenta);
            if(polozeniIspiti.length == 0)
                throw new ArithmeticException("EXCEPTION: Niti jedan ispit nije položen.");
            Integer pom = 0;
            for (Ispit i : polozeniIspiti) pom += i.getOcjena();
            return new BigDecimal((double)pom / polozeniIspiti.length);
        } catch (ArithmeticException e) {
            logger.warn(e.getMessage());
            return BigDecimal.ZERO;
        }
    }

    /**
     * Prima polje ispita, vraća samo one koji su položeni s pozitivnom ocjenom.
     *
     * @param ispiti Polje ispita kojima je student pristupio
     * @return Polje ispita koje je student položio s pozitivnom ocjenom
     */
    private Ispit[] filtrirajPolozeneIspite(Ispit[] ispiti) {
        ArrayList<Ispit> polozeniIspiti = new ArrayList<>();
        for (Ispit i : ispiti)
            if (i.getOcjena() > 1) polozeniIspiti.add(i);
        return polozeniIspiti.toArray(new Ispit[0]);
    }

    /**
     * Prima polje ispita, vraća samo one kojima je pristupio student.
     *
     * @param ispiti Polje svih ispita
     * @param student Student za kojeg se filtriraju ispiti
     * @return Polje ispita kojima je pristupio određeni student
     */
    default Ispit[] filtrirajIspitePoStudentu(Ispit[] ispiti, Student student) {
        ArrayList<Ispit> pristupljeniIspiti = new ArrayList<>();
        for (Ispit i : ispiti)
            if (i.getStudent().equals(student)) pristupljeniIspiti.add(i);
        return pristupljeniIspiti.toArray(new Ispit[0]);
    }

    /**
     * Prima polje ispita, vraća samo one koji su održani zadane godine.
     *
     * @param ispiti Polje svih ispita
     * @param akademskaGod Akademska godina za koju se filtriraju ispiti
     * @return Polje ispita koji su održani u određenoj akademskoj godini
     */
    default Ispit[] filtrirajIspitePoGodini(Ispit[] ispiti, Integer akademskaGod) {
        ArrayList<Ispit> ispitiGodine = new ArrayList<>();
        for (Ispit i : ispiti)
            if (i.getDatumIVrijeme().getYear() == akademskaGod) ispitiGodine.add(i);
        return ispitiGodine.toArray(new Ispit[0]);
    }
}
