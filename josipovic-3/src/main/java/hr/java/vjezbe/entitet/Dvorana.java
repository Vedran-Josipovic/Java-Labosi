package hr.java.vjezbe.entitet;

public record Dvorana(String naziv, String zgrada) {
    @Override
    public String toString() {
        return "Dvorana{" +
                "naziv='" + naziv + '\'' +
                ", zgrada='" + zgrada + '\'' +
                '}';
    }
}
