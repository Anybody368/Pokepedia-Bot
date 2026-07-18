package sleep.pokemon;

import utilitaire.ElementNotFoundException;

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

    public static Specialites SpecialityFromName(String name) throws ElementNotFoundException
    {
        for (Specialites specialite : Specialites.values()) {
            if (specialite.getNom().equalsIgnoreCase(name)) {
                return specialite;
            }
        }
        throw new ElementNotFoundException(name, "Looking for a specific Pokemon speciality");
    }
}
