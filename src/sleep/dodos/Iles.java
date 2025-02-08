package sleep.dodos;

public enum Iles {
    ILE_VERTEPOUSSE("Île Vertepousse"),
    PLAGE_CYAN("Plage Cyan"),
    GROTTE_SEPIA("Grotte Sépia"),
    PLAINE_PERCENEIGE("Plaine Perce-neige"),
    RIVAGE_LAPISLAZULI("Rivage Lapis-lazuli"),
    VIEILLE_CENTRALE_DOREE("Vieille Centrale Dorée");
    private final String m_nom;

    Iles(String nom)
    {
        m_nom = nom;
    }

    public String getNom() {
        return m_nom;
    }


}
