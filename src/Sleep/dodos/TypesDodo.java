package Sleep.dodos;

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
}
