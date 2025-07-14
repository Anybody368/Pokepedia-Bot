package sleep.bouffe;

public class IngredientPoke {
    private final ListeIngredients m_ingredient;
    private final int m_quantiteBase;
    private final int m_quantite30;
    private final int m_quantite60;

    public IngredientPoke(ListeIngredients ingr, int lvl1, int lvl30, int lvl60)
    {
        m_ingredient = ingr;
        m_quantiteBase = lvl1;
        m_quantite30 = lvl30;
        m_quantite60 = lvl60;
    }

    public IngredientPoke(ListeIngredients ingr, int quantite)
    {
        m_ingredient = ingr;
        m_quantiteBase = quantite;
        m_quantite30 = 0;
        m_quantite60 = 0;
    }

    public ListeIngredients getIngredient() {return m_ingredient;}

    public String getNom()
    {
        return m_ingredient.getNom();
    }

    public String getQttNv1()
    {
        if(m_quantiteBase < 1) return "—";
        return String.valueOf(m_quantiteBase);
    }
    public String getQttNv30()
    {
        if(m_quantite30 < 1) return "—";
        return String.valueOf(m_quantite30);
    }
    public String getQttNv60()
    {
        if(m_quantite60 < 1) return "—";
        return String.valueOf(m_quantite60);
    }
}
