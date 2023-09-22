package hr.java.vjezbe.entitet;

/**
 * Predstavlja tip ispita online.
 */
public sealed interface Online permits Ispit {
    /**
     * @param nazivOnlineSoftvera naziv online softvera koji se koristi za ispit
     */
    void setNazivOnlineSoftvera(String nazivOnlineSoftvera);
}
