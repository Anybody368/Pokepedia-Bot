package Sleep.bouffe;

public enum CategoriesRecettes {
    CURRYS_RAGOUTS("Currys et ragoûts"),
    SALADES("Salades"),
    BOISSONS_DESSERTS("Desserts et boissons");
    private final String m_nom;

    CategoriesRecettes(String nom)
    {
        m_nom = nom;
    }

    public String getNom()
    {
        return m_nom;
    }
}
