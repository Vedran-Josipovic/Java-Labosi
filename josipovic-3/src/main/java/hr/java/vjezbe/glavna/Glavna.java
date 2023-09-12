package hr.java.vjezbe.glavna;
import hr.java.vjezbe.entitet.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

//Labos - 3
public class Glavna {
    private static final Integer brProf = 2, brPred = 2, brStud = 2, brIspit = 2, akadGod = 2022;

    public static void main(String[] args) throws FileNotFoundException {
        //Scanner scanner = new Scanner(System.in);
        File file = new File("josipovic-3/src/main/java/hr/java/vjezbe/files/currentInput");
        Scanner scanner = new Scanner(file);

        Profesor[] profesori;
        Predmet[] predmeti;
        Student[] studenti;
        Ispit[] ispiti;

        System.out.println("Unesite broj obrazovnih ustanova: ");
        int brObrUstanova = scanner.nextInt(); scanner.nextLine();
        ObrazovnaUstanova[] obrazovneUstanove = new ObrazovnaUstanova[brObrUstanova];

        for (int j = 0; j < obrazovneUstanove.length; j++) {
            System.out.println("Unesite podatke za " + (j + 1) + ". obrazovnu ustanovu:");

            profesori = unosProfesora(scanner);
            predmeti = unosPredmeta(scanner, profesori);
            studenti = unosStudenata(scanner);
            ispiti = unosIspita(scanner, predmeti, studenti);

            printOdlikasi(ispiti);


            //Exception
            System.out.println("Odaberite obrazovnu ustanovu za navedene podatke koju želite unijeti (1 - Veleučilište Jave, 2 - Fakultet računarstva): ");
            int odabirUstanove = scanner.nextInt(); scanner.nextLine();
            System.out.println("Unesite naziv obrazovne ustanove: ");
            String nazivUstanove = scanner.nextLine();

            if (odabirUstanove == 1) {
                obrazovneUstanove[j] = new VeleucilisteJave(nazivUstanove, predmeti, studenti, ispiti);
            }
            else if (odabirUstanove == 2) {
                obrazovneUstanove[j] = new FakultetRacunarstva(nazivUstanove, predmeti, studenti, ispiti);
            }
            //To be handeled with exceptions
            else {
                System.out.println("GREŠKA - Neispravan unos\n");
                return;
            }
            //Exception


            //Konačna ocjena, najbolji student, rektorova nagrada - Staviti u funkciju kasnije
            for (Student s : obrazovneUstanove[j].getStudenti()) {
                System.out.println("Unesite ocjenu završnog rada za studenta: " + s.getImePrezime() + ": ");
                Integer zavrsniPismeni = scanner.nextInt(); scanner.nextLine();

                System.out.println("Unesite ocjenu obrane završnog rada za studenta: " + s.getImePrezime() + ": ");
                Integer zavrsniObrana = scanner.nextInt(); scanner.nextLine();

                if (obrazovneUstanove[j] instanceof Visokoskolska visokoskolska) {
                    //Ovo bi bilo idealno staviti u metodu koja handla ispite
                    Ispit[] studentoviIspiti = visokoskolska.filtrirajIspitePoStudentu(obrazovneUstanove[j].getIspiti(), s);
                    BigDecimal konacnaOcjena = visokoskolska.izracunajKonacnuOcjenuStudijaZaStudenta(studentoviIspiti, zavrsniPismeni, zavrsniObrana);
                    System.out.println("Konačna ocjena studija studenta " + s.getIme() + " " + s.getPrezime() + " je " + konacnaOcjena);
                }
            }

            Student bestStudent = obrazovneUstanove[j].odrediNajuspjesnijegStudentaNaGodini(akadGod);
            System.out.println("Najbolji student " + akadGod + ". godine je " + bestStudent.getImePrezime() + " JMBAG: " + bestStudent.getJmbag());

            if (obrazovneUstanove[j] instanceof Diplomski diplomski) {
                Student rektorovaNagrada = diplomski.odrediStudentaZaRektorovuNagradu();
                System.out.println("Student koji je osvojio rektorovu nagradu je: " +
                        rektorovaNagrada.getImePrezime() + " JMBAG: " + rektorovaNagrada.getJmbag());
            }
            //Konačna ocjena, najbolji student, rektorova nagrada
        }
        scanner.close();
    }

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

    static Predmet[] unosPredmeta(Scanner scanner, Profesor[] profesori) {
        Predmet[] predmeti = new Predmet[brPred];
        for (int i = 0; i < predmeti.length; i++) {
            System.out.println("Unesite " + (i + 1) + ". predmet: ");

            System.out.print("Unesite šifru predmeta: ");
            String sifra = scanner.nextLine();

            System.out.print("Unesite naziv predmeta: ");
            String naziv = scanner.nextLine();

            System.out.print("Unesite broj ECTS bodova za predmet '" + naziv + "': ");
            Integer brojEctsBodova = scanner.nextInt(); scanner.nextLine();

            System.out.println("Odaberite profesora:");
            for (int j = 0; j < profesori.length; j++)
                System.out.println((j + 1) + ". " + profesori[j].getImePrezime());

            System.out.print("Odabir >> ");
            int odabir = scanner.nextInt(); scanner.nextLine();

            System.out.print("Unesite broj studenata za predmetu '" + naziv + "': ");
            Integer brStudNaPredmetu = scanner.nextInt(); scanner.nextLine();

            predmeti[i] = new Predmet(sifra, naziv, brojEctsBodova, profesori[odabir - 1]);
        }
        return predmeti;
    }

    static Student[] unosStudenata(Scanner scanner) {
        Student[] studenti = new Student[brStud];
        for (int i = 0; i < studenti.length; i++) {
            System.out.println("Unesite " + (i + 1) + ". studenta: ");

            System.out.print("Unesite ime studenta: ");
            String ime = scanner.nextLine();

            System.out.print("Unesite prezime studenta: ");
            String prezime = scanner.nextLine();

            System.out.print("Unesite datum rođenja studenta " + prezime + " " + ime + " u formatu (dd.MM.yyyy.): ");

            String stringDatumRodjenja = scanner.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
            LocalDate datumRodjenja = LocalDate.parse(stringDatumRodjenja, formatter);

            System.out.print("Unesite JMBAG studenta: ");
            String jmbag = scanner.nextLine();

            studenti[i] = new Student(ime, prezime, jmbag, datumRodjenja);
        }
        return studenti;
    }

    static Ispit[] unosIspita(Scanner scanner, Predmet[] predmeti, Student[] studenti) {
        Ispit[] ispiti = new Ispit[brIspit];

        for (int i = 0; i < ispiti.length; i++) {
            System.out.println("Unesite " + (i + 1) + ". ispitni rok: ");

            System.out.println("Odaberite predmet: ");
            for (int j = 0; j < predmeti.length; j++)
                System.out.println((j + 1) + ". " + predmeti[j].getNaziv());
            System.out.print("Odabir >> ");
            int odabirPredmeta = scanner.nextInt(); scanner.nextLine();

            System.out.println("Unesite naziv dvorane: ");
            String nazivDvorane = scanner.nextLine();

            System.out.println("Unesite zgradu dvorane: ");
            String zgradaDvorane = scanner.nextLine();

            Dvorana dvorana = new Dvorana(nazivDvorane, zgradaDvorane);

            System.out.println("Odaberite studenta: ");
            for (int j = 0; j < studenti.length; j++)
                System.out.println((j + 1) + ". " + studenti[j].getImePrezime());
            System.out.print("Odabir >> ");
            int odabirStudenta = scanner.nextInt(); scanner.nextLine();

            System.out.print("Unesite ocjenu na ispitu (1-5): ");
            Integer ocjena = scanner.nextInt(); scanner.nextLine();

            System.out.print("Unesite datum i vrijeme ispita u formatu (dd.MM.yyyy.THH:mm): ");
            String stringDatumIVrijeme = scanner.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm");
            LocalDateTime datumIVrijeme = LocalDateTime.parse(stringDatumIVrijeme, formatter);

            ispiti[i] = new Ispit(predmeti[odabirPredmeta - 1], studenti[odabirStudenta - 1], ocjena, datumIVrijeme, dvorana);

            //Objekte klase „Student“ koji su pristupili ispitima iz određeniH predmeta treba dodati u polje studenata za taj određeni predmet
            predmeti[odabirPredmeta - 1].addStudent(studenti[odabirStudenta - 1]);
        }
        return ispiti;
    }

    /**
     * Generic metoda koja printa sve elemente u polju.
     */
    public static <T> void printPolje(T[] items) {
        System.out.println("\n\n");
        for (T item : items) System.out.println(item.toString());
        System.out.println("\n\n");
    }

    /**
     * @param ispiti
     * Printa ime i prezime studenata koji imaju 5 iz ispita.
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