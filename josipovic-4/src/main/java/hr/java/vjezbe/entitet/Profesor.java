package hr.java.vjezbe.entitet;

/**
 * Predstavlja profesora na obrazovnoj ustanovi.
 * <p>
 * Sadrži unutarnju klasu {@code ProfesorBuilder} za stvaranje objekata klase {@code Profesor} koristeći Builder oblikovni uzorak.
 */
public class Profesor extends Osoba {
    private String sifra, titula;

    /**
     * @param sifra šifra profesora
     * @param ime ime profesora
     * @param prezime prezime profesora
     * @param titula titula profesora
     */
    private Profesor(String sifra, String ime, String prezime, String titula) {
        super(ime, prezime);
        this.sifra = sifra;
        this.titula = titula;
    }

    public String getSifra() {
        return sifra;
    }
    public void setSifra(String sifra) {
        this.sifra = sifra;
    }
    public String getTitula() {
        return titula;
    }
    public void setTitula(String titula) {
        this.titula = titula;
    }

    /**
     * Predstavlja Builder za klasu {@code Profesor}.
     * <p>
     * Omogućuje stvaranje objekata klase {@code Profesor} koristeći Builder oblikovni uzorak.
     */
    public static class ProfesorBuilder {
        private String sifra, ime, prezime, titula;

        public ProfesorBuilder setSifra(String sifra) {
            this.sifra = sifra;
            return this;
        }
        public ProfesorBuilder setIme(String ime) {
            this.ime = ime;
            return this;
        }
        public ProfesorBuilder setPrezime(String prezime) {
            this.prezime = prezime;
            return this;
        }
        public ProfesorBuilder setTitula(String titula) {
            this.titula = titula;
            return this;
        }

        /**
         * Stvara novi objekt klase {@code Profesor} s postavljenim vrijednostima.
         * @return novi objekt klase {@code Profesor}
         */
        public Profesor build() {
            return new Profesor(sifra, ime, prezime, titula);
        }
    }

    /**
     * @return string reprezentacija objekta klase {@code Profesor}
     */
    @Override
    public String toString() {
        return "Profesor{ " +
                super.toString() + " " +
                "sifra='" + sifra + '\'' +
                ", titula='" + titula + '\'' +
                '}';
    }
}
