package Sleep;

public enum Specialites {
    BAIES("Baies"),
    INGREDIENTS("Ingrédients"),
    COMPETENCES("Compétences");
    private final String m_nom;

    private Specialites(String nom)
    {
        m_nom = nom;
    }

    public String getNom()
    {
        return m_nom;
    }
}
