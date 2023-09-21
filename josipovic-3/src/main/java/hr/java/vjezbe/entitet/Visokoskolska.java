package hr.java.vjezbe.entitet;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface Visokoskolska {
    Logger logger = LoggerFactory.getLogger(Visokoskolska.class);
    BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] studentoviIspiti, Integer zavrsniPismeni, Integer zavrsniObrana);

    /**
     * Vraća prosječnu ocjenu na ispitima koji su položeni s pozitivnom ocjenom.
     * Nova, promijenjena radi zadatka 5 labosa 3
     */
    default BigDecimal odrediProsjekOcjenaNaIspitima(Ispit[] ispitiStudenta) throws NemoguceOdreditiProsjekStudentaException {
        Integer pom = 0;
        for (var i : ispitiStudenta) {
            if (i.getOcjena() == 1) throw new NemoguceOdreditiProsjekStudentaException("Polje ispita sadrži ispit sa ocjenom '1'");
            pom += i.getOcjena();
        }
        return new BigDecimal((double)pom / ispitiStudenta.length);

    }

    /**
     * Vraća prosječnu ocjenu na ispitima koji su položeni s pozitivnom ocjenom.
     * Zastarjela metoda koju sam zamijenio radi 5. Zadatka 3. Labosa.
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
            logger.warn("[Visokoskolska.odrediProsjekOcjenaNaIspitimaDepreciated]" +  e.getMessage());
            return BigDecimal.ZERO;
        }
    }

    /**
     * Prima polje ispita, vraća samo one koji su položeni s pozitivnom ocjenom.
     */
    private Ispit[] filtrirajPolozeneIspite(Ispit[] ispiti) {
        ArrayList<Ispit> polozeniIspiti = new ArrayList<>();
        for (Ispit i : ispiti)
            if (i.getOcjena() > 1) polozeniIspiti.add(i);
        return polozeniIspiti.toArray(new Ispit[0]);
    }

    /**
     * Prima polje ispita, vraća samo one kojima je pristupio student.
     */
    default Ispit[] filtrirajIspitePoStudentu(Ispit[] ispiti, Student student) {
        ArrayList<Ispit> pristupljeniIspiti = new ArrayList<>();
        for (Ispit i : ispiti)
            if (i.getStudent().equals(student)) pristupljeniIspiti.add(i);
        return pristupljeniIspiti.toArray(new Ispit[0]);
    }

    /**
     * Prima polje ispita, vraća samo one koji su održani zadane godine.
     */
    default Ispit[] filtrirajIspitePoGodini(Ispit[] ispiti, Integer akademskaGod) {
        ArrayList<Ispit> ispitiGodine = new ArrayList<>();
        for (Ispit i : ispiti)
            if (i.getDatumIVrijeme().getYear() == akademskaGod) ispitiGodine.add(i);
        return ispitiGodine.toArray(new Ispit[0]);
    }
}
