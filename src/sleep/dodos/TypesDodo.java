package sleep.dodos;

import utilitaire.ElementNotFoundException;

public enum TypesDodo {
    PTIDODO("Ptidodo"),
    BONDODO("Bondodo"),
    GRODODO("Grododo");
    private final String m_nom;

    TypesDodo(String nom)
    {
        m_nom = nom;
    }

    public String getNom()
    {
        return m_nom;
    }

    public static TypesDodo searchByName(String name) throws ElementNotFoundException
    {
        for (TypesDodo dodo : TypesDodo.values()) {
            if (dodo.getNom().equalsIgnoreCase(name)) return dodo;
        }
        throw new ElementNotFoundException(name, "Looking for a specific category of Sleep");
    }
}
