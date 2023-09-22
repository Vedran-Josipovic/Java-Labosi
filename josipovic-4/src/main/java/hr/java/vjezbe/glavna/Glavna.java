package hr.java.vjezbe.glavna;
import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.iznimke.NeispravanIntUnosException;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import hr.java.vjezbe.iznimke.PostojiViseNajmladjihStudenataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Sadrži main metodu i pomoćne metode.
 */
public class Glavna {
    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
    private static final Integer brProf = 2, brPred = 2, brStud = 2, brIspit = 2, akadGod = 2022;

    /**
     * Glavna metoda koja pokreće aplikaciju. <p>
     * Učitava podatke o ispitima, studentima, profesorima i predmetima iz datoteke.
     * Izračunava prosječne ocjene, određuje najuspješnijeg studenta i dodjeljuje Rektorovu nagradu,
     * te zapisuje iste na konzolu.
     *
     * @param args Argumenti naredbenog retka (ne koriste se)
     * @throws FileNotFoundException Ako datoteka s podacima ne može biti pronađena
     */
    public static void main(String[] args) throws FileNotFoundException {
        logger.info("Aplikacija pokrenuta");

        //Scanner scanner = new Scanner(System.in);
        File file = new File("josipovic-4/src/main/java/hr/java/vjezbe/files/currentInput");

        //Provjeriti je li scanner null radi nullpointerexceptiona, te dodaj handlanje FileNotFoundException
        Scanner scanner = new Scanner(file);

        Profesor[] profesori;
        Predmet[] predmeti;
        Student[] studenti;
        Ispit[] ispiti;

        int brObrUstanova = intUnosIznimkeHandler(scanner, "Unesite broj obrazovnih ustanova: ", 1, Integer.MAX_VALUE);
        ObrazovnaUstanova[] obrazovneUstanove = new ObrazovnaUstanova[brObrUstanova];

        for (int i = 0; i < obrazovneUstanove.length; i++) {
            System.out.println("Unesite podatke za " + (i + 1) + ". obrazovnu ustanovu:");

            profesori = unosProfesora(scanner);
            predmeti = unosPredmeta(scanner, profesori);
            studenti = unosStudenata(scanner);
            ispiti = unosIspita(scanner, predmeti, studenti);

            printOdlikasi(ispiti);

            int odabirUstanove = intUnosIznimkeHandler(scanner, "Odaberite obrazovnu ustanovu za navedene podatke koju želite unijeti (1 - Veleučilište Jave, 2 - Fakultet računarstva): ",
                1 ,2);

            System.out.print("Unesite naziv obrazovne ustanove: ");
            String nazivUstanove = scanner.nextLine();

            if (odabirUstanove == 1) {
                obrazovneUstanove[i] = new VeleucilisteJave(nazivUstanove, predmeti, studenti, ispiti);
            }
            else if (odabirUstanove == 2) {
                obrazovneUstanove[i] = new FakultetRacunarstva(nazivUstanove, predmeti, studenti, ispiti);
            }
            //Handlao sam s iznimkama. Mislim da mogu izbaciti
            else {
                System.out.println("Neočekivana greška - Programer je kriv. ABORT");
                logger.error("Neočekivana greška.");
                return;
            }

            ArrayList<Student> nePolazuZavrsni = new ArrayList<>();
            for (Student s : obrazovneUstanove[i].getStudenti()) {
                if (obrazovneUstanove[i] instanceof Visokoskolska visokoskolska) {
                    Ispit[] studentoviIspiti = visokoskolska.filtrirajIspitePoStudentu(obrazovneUstanove[i].getIspiti(), s);
                    try {
                        visokoskolska.odrediProsjekOcjenaNaIspitima(studentoviIspiti); // Ova linija služi samo da baci iznimku
                        Integer zavrsniPismeni = intUnosIznimkeHandler(scanner, "Unesite ocjenu završnog rada za studenta: " + s.getImePrezime() + ": ",1 ,5);
                        Integer zavrsniObrana = intUnosIznimkeHandler(scanner, "Unesite ocjenu obrane završnog rada za studenta: " + s.getImePrezime() + ": ",1 ,5);
                        BigDecimal konacnaOcjena = visokoskolska.izracunajKonacnuOcjenuStudijaZaStudenta(studentoviIspiti, zavrsniPismeni, zavrsniObrana);
                        System.out.println("Konačna ocjena studija studenta " + s.getImePrezime() + " je " + konacnaOcjena);
                    } catch (NemoguceOdreditiProsjekStudentaException e) {
                        logger.warn("Student " + s.getImePrezime() + " zbog negativne ocjene na jednom od ispita ne može polagati završni rad! " + e);
                        nePolazuZavrsni.add(s);
                    }
                }
            }
            for (Student s : nePolazuZavrsni) {
                System.out.println("Student " + s.getImePrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek „nedovoljan (1)“!");
            }

            Student bestStudent = obrazovneUstanove[i].odrediNajuspjesnijegStudentaNaGodini(akadGod);
            System.out.println("Najbolji student " + akadGod + ". godine je " + bestStudent.getImePrezime() + " JMBAG: " + bestStudent.getJmbag());

            if (obrazovneUstanove[i] instanceof Diplomski diplomski) {
                Student rektorovaNagrada;
                try {
                    rektorovaNagrada = diplomski.odrediStudentaZaRektorovuNagradu();
                }
                catch (PostojiViseNajmladjihStudenataException e){
                    logger.error("Pronađeno je više najmlađih studenata (PREKID PROGRAMA) " + e);
                    System.out.println("Program završava s izvođenjem.");
                    System.out.println("Pronađeno je više najmlađih studenata s istim datumom rođenja, a to su "
                    + e.getMessage() + ".");
                    return;
                }
                System.out.println("Student koji je osvojio rektorovu nagradu je: " +
                        rektorovaNagrada.getImePrezime() + " JMBAG: " + rektorovaNagrada.getJmbag());
            }
        }
        scanner.close();
        logger.info("Aplikacija završila s radom");
    }

    /**
     * Unosi cijeli broj s konzole uz obradu iznimki.
     * Ako je unesena vrijednost izvan dopuštenog raspona ili nije cijeli broj, traži se ponovni unos.
     *
     * @param scanner Scanner objekt za unos podataka
     * @param poruka Poruka koja se ispisuje prije unosa
     * @param minVrijednost Minimalna dopuštena vrijednost (Uključujući)
     * @param maxVrijednost Maksimalna dopuštena vrijednost (Uključujući)
     * @return Uneseni cijeli broj
     */
    private static int intUnosIznimkeHandler(Scanner scanner, String poruka, int minVrijednost, int maxVrijednost){
        int uneseniBroj = 0;
        boolean badFormat;
        do {
            try {
                System.out.print(poruka);
                uneseniBroj = scanner.nextInt();
                isBrojInRange(uneseniBroj, minVrijednost, maxVrijednost);
                badFormat = false;
            } catch (InputMismatchException e) {
                logger.error("Unesen string umjesto broja " + e);
                System.out.println("Neispravan unos!");
                badFormat = true;
            }
            catch (NeispravanIntUnosException e){
                logger.error(e.getMessage() + e);
                System.out.println("Unesite broj u dopuštenom rasponu: [" + minVrijednost + "," + maxVrijednost + "].");
                badFormat = true;
            }
            finally {
                scanner.nextLine();
            }
        } while (badFormat);
        return uneseniBroj;
    }


    /**
     * Provjerava je li uneseni broj unutar zadanih granica.
     *
     * @param uneseniBroj Broj koji se provjerava
     * @param minVrijednost Minimalna dopuštena vrijednost (Uključujući)
     * @param maxVrijednost Maksimalna dopuštena vrijednost (Uključujući)
     * @throws NeispravanIntUnosException Ako uneseni broj nije unutar zadanih granica
     */
    private static void isBrojInRange(int uneseniBroj, int minVrijednost, int maxVrijednost) throws NeispravanIntUnosException {
        if (uneseniBroj < minVrijednost || uneseniBroj > maxVrijednost){
            throw new NeispravanIntUnosException("Unesen broj van dopuštenih parametara [" + minVrijednost + "," + maxVrijednost + "]." + " Unos: " + uneseniBroj);
        }
    }

    /**
     * Unosi podatke o profesorima.
     *
     * @param scanner Scanner objekt za unos podataka
     * @return Polje objekata klase Profesor
     */
    static Profesor[] unosProfesora(Scanner scanner) {
        Profesor[] profesori = new Profesor[brProf];
        for (int i = 0; i < profesori.length; i++) {
            System.out.println("Unesite " + (i + 1) + ". profesora: ");

            System.out.print("Unesite šifru profesora: ");
            String sifra = scanner.nextLine();

            System.out.print("Unesite ime profesora: ");
            String ime = scanner.nextLine();

            System.out.print("Unesite prezime profesora: ");
            String prezime = scanner.nextLine();

            System.out.print("Unesite titulu profesora: ");
            String titula = scanner.nextLine();

            profesori[i] = new Profesor.ProfesorBuilder().setSifra(sifra).setIme(ime).setPrezime(prezime).setTitula(titula).build();
        }
        return profesori;
    }

    /**
     * Unosi podatke o predmetima.
     *
     * @param scanner Scanner objekt za unos podataka
     * @param profesori Polje objekata klase {@code Profesor} iz kojeg se odabrani profesor dodaje u objekt klase {@code Predmet}.
     * @return Polje objekata klase Predmet
     */
    static Predmet[] unosPredmeta(Scanner scanner, Profesor[] profesori) {
        Predmet[] predmeti = new Predmet[brPred];
        for (int i = 0; i < predmeti.length; i++) {
            System.out.println("Unesite " + (i + 1) + ". predmet: ");

            System.out.print("Unesite šifru predmeta: ");
            String sifra = scanner.nextLine();

            System.out.print("Unesite naziv predmeta: ");
            String naziv = scanner.nextLine();

            Integer brojEctsBodova = intUnosIznimkeHandler(scanner, "Unesite broj ECTS bodova za predmet '" + naziv + "': ", 0,35);

            System.out.println("Odaberite profesora:");
            for (int j = 0; j < profesori.length; j++)
                System.out.println((j + 1) + ". " + profesori[j].getImePrezime());

            int odabir = intUnosIznimkeHandler(scanner, "Odabir >> ", 1, profesori.length);
            Integer brStudNaPredmetu = intUnosIznimkeHandler(scanner,"Unesite broj studenata za predmetu '" + naziv + "': ", 1, Integer.MAX_VALUE);
            predmeti[i] = new Predmet(sifra, naziv, brojEctsBodova, profesori[odabir - 1]);
        }
        return predmeti;
    }

    /**
     * Unosi podatke o studentima. Ako je unesen neispravan format datuma,
     * u logger će se zapisati iznimka {@code DateTimeParseException}, a
     * unos će se ponavljati dok se ne unese ispravan format.
     *
     * @param scanner Scanner objekt za unos podataka
     * @return Polje objekata klase {@code Student}
     */
    static Student[] unosStudenata(Scanner scanner) {
        Student[] studenti = new Student[brStud];
        for (int i = 0; i < studenti.length; i++) {
            System.out.println("Unesite " + (i + 1) + ". studenta: ");

            System.out.print("Unesite ime studenta: ");
            String ime = scanner.nextLine();

            System.out.print("Unesite prezime studenta: ");
            String prezime = scanner.nextLine();

            LocalDate datumRodjenja = null;
            boolean ispravanFormat;
            do {
                System.out.print("Unesite datum rođenja studenta " + prezime + " " + ime + " u formatu (dd.MM.yyyy.): ");
                try {
                    String stringDatumRodjenja = scanner.nextLine();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
                    datumRodjenja = LocalDate.parse(stringDatumRodjenja, formatter);
                    ispravanFormat = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Neispravan format datuma. Molimo unesite datum u formatu dd.MM.yyyy.");
                    logger.error("Unesen neispravan format datuma rođenja. " + e);
                    ispravanFormat = false;
                }
            } while (!ispravanFormat);

            System.out.print("Unesite JMBAG studenta: ");
            String jmbag = scanner.nextLine();

            studenti[i] = new Student(ime, prezime, jmbag, datumRodjenja);
        }
        return studenti;
    }

    /**
     * Unosi podatke o ispitima. <p>
     * Ako je unesen neispravan format datuma,
     * u logger će se zapisati iznimka {@code DateTimeParseException}, a
     * unos će se ponavljati dok se ne unese ispravan format.
     * Objekte klase {@code Student} koji su pristupili ispitima iz određenih
     * predmeta dodaje se u polje studenata za te predmete.
     *
     * @param scanner Scanner objekt za unos podataka
     * @param predmeti Polje objekata klase {@code Predmet} iz kojeg se odabrani predmet dodaje u objekt klase {@code Ispit}
     * @param studenti Polje objekata klase {@code Student} iz kojeg se odabrani student dodaje u objekt klase {@code Ispit}
     * @return Polje objekata klase Ispit
     */
    static Ispit[] unosIspita(Scanner scanner, Predmet[] predmeti, Student[] studenti) {
        Ispit[] ispiti = new Ispit[brIspit];

        for (int i = 0; i < ispiti.length; i++) {
            System.out.println("Unesite " + (i + 1) + ". ispitni rok: ");

            System.out.println("Odaberite predmet: ");
            for (int j = 0; j < predmeti.length; j++)
                System.out.println((j + 1) + ". " + predmeti[j].getNaziv());

            int odabirPredmeta = intUnosIznimkeHandler(scanner,"Odabir >> ", 1, predmeti.length);

            /*
            System.out.println("Unesite naziv dvorane: ");
            String nazivDvorane = scanner.nextLine();

            System.out.println("Unesite zgradu dvorane: ");
            String zgradaDvorane = scanner.nextLine();

            Dvorana dvorana = new Dvorana(nazivDvorane, zgradaDvorane);*/

            System.out.println("Odaberite studenta: ");
            for (int j = 0; j < studenti.length; j++)
                System.out.println((j + 1) + ". " + studenti[j].getImePrezime());

            int odabirStudenta = intUnosIznimkeHandler(scanner, "Odabir >> ", 1, studenti.length);
            Integer ocjena = intUnosIznimkeHandler(scanner, "Unesite ocjenu na ispitu (1-5): ", 1, 5);

            LocalDateTime datumIVrijeme = null;
            boolean ispravanFormat;
            do {
                System.out.print("Unesite datum i vrijeme ispita u formatu (dd.MM.yyyy.THH:mm): ");
                try {
                    String stringDatumIVrijeme = scanner.nextLine();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm");
                    datumIVrijeme = LocalDateTime.parse(stringDatumIVrijeme, formatter);
                    ispravanFormat = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Neispravan format datuma. Molimo unesite datum u formatu dd.MM.yyyy.THH:mm");
                    logger.error("Unesen neispravan format datuma ispita. " + e);
                    ispravanFormat = false;
                }
            } while (!ispravanFormat);

            //ispiti[i] = new Ispit(predmeti[odabirPredmeta - 1], studenti[odabirStudenta - 1], ocjena, datumIVrijeme, dvorana);
            ispiti[i] = new Ispit(predmeti[odabirPredmeta - 1], studenti[odabirStudenta - 1], ocjena, datumIVrijeme);

            predmeti[odabirPredmeta - 1].addStudent(studenti[odabirStudenta - 1]);
        }
        return ispiti;
    }


    /**
     * Generic metoda koja printa sve elemente u polju.
     *
     * @param items Polje elemenata tipa T. (T treba sadržavati metodu toString).
     */
    public static <T> void printPolje(T[] items) {
        System.out.println("\n\n");
        for (T item : items) System.out.println(item.toString());
        System.out.println("\n\n");
    }


    /**
     * Ispisuje imena i prezimena studenata koji su ostvarili ocjenu 5 iz ispita.
     *
     * @param ispiti Polje objekata klase {@code Ispit} iz kojeg dohvaćamo ocjene.
     */
    static void printOdlikasi(Ispit[] ispiti){
        for (Ispit i : ispiti){
            if (i.getOcjena() == 5){
                System.out.println("Student " + i.getStudent().getImePrezime()
                + " je ostvario ocjenu 'izvrstan' na predmetu '" + i.getPredmet().getNaziv() + "'");
            }
        }
    }

}