package hr.java.vjezbe.entitet;

/**
 * Predstavlja osobu koja se implementira kao dio naslijeđenih klasa.
 */
public abstract class Osoba {
    private String ime, prezime;

    /**
     * @param ime Ime osobe.
     * @param prezime Prezime osobe.
     */
    public Osoba(String ime, String prezime) {
        this.ime = ime;
        this.prezime = prezime;
    }

    public String getIme() {
        return ime;
    }
    public void setIme(String ime) {
        this.ime = ime;
    }
    public String getPrezime() {
        return prezime;
    }
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
    public String getImePrezime(){
        return getIme() + " " + getPrezime();
    }

    /**
     * @return string reprezentacija objekta klase {@code Osoba}
     */
    @Override
    public String toString() {
        return "Osoba{" +
                "ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                '}';
    }
}
