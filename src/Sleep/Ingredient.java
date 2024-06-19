package Sleep;

public class Ingredient {
    private String m_nom;
    private int m_quantiteBase;
    private int m_quantite30;
    private int m_quantite60;

    public Ingredient(String nom, int lvl1, int lvl30, int lvl60)
    {
        m_nom = nom;
        m_quantiteBase = lvl1;
        m_quantite30 = lvl30;
        m_quantite60 = lvl60;
    }

    public String getNom()
    {
        return m_nom;
    }

    public String getQttNv1()
    {
        if(m_quantiteBase < 1) return "–";
        return String.valueOf(m_quantiteBase);
    }
    public String getQttNv30()
    {
        if(m_quantite30 < 1) return "–";
        return String.valueOf(m_quantite30);
    }
    public String getQttNv60()
    {
        if(m_quantite60 < 1) return "–";
        return String.valueOf(m_quantite60);
    }
}
