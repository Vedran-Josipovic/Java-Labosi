package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface Visokoskolska {
    BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] studentoviIspiti, Integer zavrsniPismeni, Integer zavrsniObrana);


    //Vraća prosječnu ocjenu na ispitima koji su položeni (S pozitivnom ocjenom)
    default BigDecimal odrediProsjekOcjenaNaIspitima(Ispit[] ispitiStudenta) {
        Ispit[] polozeniIspiti = filtrirajPolozeneIspite(ispitiStudenta);
        Integer pom = 0;
        for (Ispit i : polozeniIspiti) pom += i.getOcjena();
        BigDecimal prosjek = new BigDecimal(pom / polozeniIspiti.length);
        return prosjek;
    }


    //Prima sve ispite, vraća samo one koji su položeni s pozitivnom ocjenom
    private Ispit[] filtrirajPolozeneIspite(Ispit[] sviIspitiStudenta) {
        ArrayList<Ispit> polozeniIspiti = new ArrayList<>();
        for (Ispit i : sviIspitiStudenta)
            if (i.getOcjena() > 1) polozeniIspiti.add(i);
        return polozeniIspiti.toArray(new Ispit[0]);
    }

    //Prima sve ispite, vraća samo ispite kojima je pristupio student
    default Ispit[] filtrirajIspitePoStudentu(Ispit[] sviIspiti, Student student) {
        ArrayList<Ispit> pristupljeniIspiti = new ArrayList<>();
        for (Ispit i : sviIspiti)
            if (i.getStudent().equals(student)) pristupljeniIspiti.add(i);
        return pristupljeniIspiti.toArray(new Ispit[0]);
    }

    default Ispit[] filtrirajIspitePoGodini(Ispit[] sviIspiti, Integer akademskaGodina) {
        ArrayList<Ispit> ispitiGodine = new ArrayList<>();
        for (Ispit i : sviIspiti)
            if (i.getDatumIVrijeme().getYear() == akademskaGodina) ispitiGodine.add(i);
        return ispitiGodine.toArray(new Ispit[0]);
    }

}
