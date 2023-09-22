package hr.java.vjezbe.entitet;

/**
 * Sprema podatke o dvorani u kojoj se održava ispit.
 *
 * @param naziv ime dvorane
 * @param zgrada zgrada u kojoj se nalazi dvorana
 */
public record Dvorana(String naziv, String zgrada) {
    /**
     * @return string reprezentacija objekta zapisa {@code Dvorana}
     */
    @Override
    public String toString() {
        return "Dvorana{" +
                "naziv='" + naziv + '\'' +
                ", zgrada='" + zgrada + '\'' +
                '}';
    }
}
