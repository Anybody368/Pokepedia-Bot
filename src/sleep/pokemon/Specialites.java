package sleep.pokemon;

public enum Specialites {
    BAIES("Baies"),
    INGREDIENTS("Ingrédients"),
    COMPETENCES("Compétences"),
    TOUTES("Toutes");
    private final String m_nom;

    Specialites(String nom)
    {
        m_nom = nom;
    }

    public String getNom()
    {
        return m_nom;
    }
}
