package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class Glavna {
    private static final Integer brProf = 2;
    private static final Integer brPred = 2;
    private static final Integer brStud = 2;
    private static final Integer brIspit = 2;
    private static final Integer akadGod = 2022;

    public static void main(String[] args) throws FileNotFoundException {
        //Scanner scanner = new Scanner(System.in);
        File file = new File("josipovic-2/src/main/java/hr/java/vjezbe/files/currentInput");
        Scanner scanner = new Scanner(file);

        Profesor[] profesori;
        Predmet[] predmeti;
        Student[] studenti;
        Ispit[] ispiti;


        System.out.println("Unesite broj obrazovnih ustanova: ");
        Integer brObrazovnihUstanova = scanner.nextInt();
        scanner.nextLine();

        ObrazovnaUstanova[] obrazovneUstanove = new ObrazovnaUstanova[brObrazovnihUstanova];

        for (int j = 0; j < obrazovneUstanove.length; j++) {
            System.out.println("Unesite podatke za " + (j + 1) + ". obrazovnu ustanovu:");

            profesori = unosProfesora(scanner);
            System.out.println("\n\n");
            for (Profesor p : profesori) {
                System.out.println(p.toString());
            }
            System.out.println("\n\n");

            predmeti = unosPredmeta(scanner, profesori);
            System.out.println("\n\n");
            for (Predmet p : predmeti) {
                System.out.println(p.toString());
            }
            System.out.println("\n\n");


            studenti = unosStudenata(scanner);
            System.out.println("\n\n");
            for (Student s : studenti) {
                System.out.println(s.toString());
            }
            System.out.println("\n\n");


            ispiti = unosIspita(scanner, predmeti, studenti);
            System.out.println("\n\n");
            for (Ispit i : ispiti) {
                System.out.println(i.toString());
            }
            System.out.println("\n\n");


            //getOdlikase
            for (Ispit i : ispiti) {
                if (i.getOcjena() == 5) {
                    System.out.println("Student " + i.getStudent().getIme() + " " + i.getStudent().getPrezime() + " je ostvario ocjenu 'izvrstan' na predmetu '" + i.getPredmet().getNaziv() + "'");
                }
            }
            //getOdlikase

            System.out.println("Odaberite obrazovnu ustanovu za navedene podatke koju želite unijeti (1 - Veleučilište Jave, 2 - Fakultet računarstva): ");
            Integer odabirUstanove = scanner.nextInt();
            scanner.nextLine();

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

            System.out.println(obrazovneUstanove[j].toString());


            //Konačna ocjena, najbolji student, rektorova nagrada - Staviti u funkciju kasnije
            for (Student s : obrazovneUstanove[j].getStudenti()) {
                System.out.println("Unesite ocjenu završnog rada za studenta: " + s.getIme() + " " + s.getPrezime() + ": ");
                Integer ocjenaZavrsniRadPismeni = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Unesite ocjenu obrane završnog rada za studenta: " + s.getIme() + " " + s.getPrezime() + ": ");
                Integer ocjenaZavrsniRadObrana = scanner.nextInt();
                scanner.nextLine();

                if (obrazovneUstanove[j] instanceof Visokoskolska visokoskolska) {
                    //Ovo bi bilo idealno staviti u metodu koja handla ispite
                    Ispit[] studentoviIspiti = visokoskolska.filtrirajIspitePoStudentu(obrazovneUstanove[j].getIspiti(), s);

                    BigDecimal konacnaOcjena = visokoskolska.izracunajKonacnuOcjenuStudijaZaStudenta(studentoviIspiti, ocjenaZavrsniRadPismeni, ocjenaZavrsniRadObrana);
                    System.out.println("Konačna ocjena studija studenta " + s.getIme() + " " + s.getPrezime() + " je " + konacnaOcjena);
                }
            }

            Student najuspjesniji = obrazovneUstanove[j].odrediNajuspjesnijegStudentaNaGodini(akadGod);
            System.out.println("Najbolji student " + akadGod + ". godine je " +
                    najuspjesniji.getIme() + " " + najuspjesniji.getPrezime() + " JMBAG: " + najuspjesniji.getJmbag());

            if (obrazovneUstanove[j] instanceof Diplomski diplomski) {
                Student rektorovaNagrada = diplomski.odrediStudentaZaRektorovuNagradu();
                System.out.println("Student koji je osvojio rektorovu nagradu je: " +
                        rektorovaNagrada.getIme() + " " + rektorovaNagrada.getPrezime() + " JMBAG: " + rektorovaNagrada.getJmbag());
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
            Integer brojEctsBodova = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Odaberite profesora:");
            for (int j = 0; j < profesori.length; j++)
                System.out.println((j + 1) + ". " + profesori[j].getIme() + " " + profesori[j].getPrezime());

            System.out.print("Odabir >> ");
            int odabir = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Unesite broj studenata za predmetu 'Programiranje u jeziku Java': ");
            Integer brStudNaPredmetu = scanner.nextInt();
            scanner.nextLine();

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
            int odabirPredmeta = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Unesite naziv dvorane: ");
            String nazivDvorane = scanner.nextLine();

            System.out.println("Unesite zgradu dvorane: ");
            String zgradaDvorane = scanner.nextLine();

            Dvorana dvorana = new Dvorana(nazivDvorane, zgradaDvorane);

            System.out.println("Odaberite studenta: ");
            for (int j = 0; j < studenti.length; j++)
                System.out.println((j + 1) + ". " + studenti[j].getIme() + " " + studenti[j].getPrezime());
            System.out.print("Odabir >> ");
            int odabirStudenta = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Unesite ocjenu na ispitu (1-5): ");
            Integer ocjena = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Unesite datum i vrijeme ispita u formatu (dd.MM.yyyy.THH:mm): ");

            String stringdatumIVrijeme = scanner.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm");
            LocalDateTime datumIVrijeme = LocalDateTime.parse(stringdatumIVrijeme, formatter);

            ispiti[i] = new Ispit(predmeti[odabirPredmeta - 1], studenti[odabirStudenta - 1], ocjena, datumIVrijeme, dvorana);

            //Objekte klase „Student“ koji su pristupili ispitima iz određeniH predmeta treba dodati u polje studenata za taj određeni predmet
            predmeti[odabirPredmeta - 1].addStudent(studenti[odabirStudenta - 1]);
        }

        return ispiti;
    }
}