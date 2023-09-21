package hr.java.vjezbe.entitet;

/**
 * Sprema podatke o dvorani u kojoj se održava ispit.
 *
 * @param naziv Ime dvorane
 * @param zgrada Zgrada u kojoj se nalazi dvorana
 */
public record Dvorana(String naziv, String zgrada) {
    @Override
    public String toString() {
        return "Dvorana{" +
                "naziv='" + naziv + '\'' +
                ", zgrada='" + zgrada + '\'' +
                '}';
    }
}
