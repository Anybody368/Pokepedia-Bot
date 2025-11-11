package sleep.dodos;

public enum Iles {
    ILE_VERTEPOUSSE("Île Vertepousse", false),
    PLAGE_CYAN("Plage Cyan", false),
    GROTTE_SEPIA("Grotte Sépia", false),
    PLAINE_PERCENEIGE("Plaine Perce-neige", false),
    RIVAGE_LAPISLAZULI("Rivage Lapis-lazuli", false),
    VIEILLE_CENTRALE_DOREE("Vieille Centrale Dorée", false),
    CANYON_AMBRE("Canyon Ambre", false),

    EX_ILE_VERTEPOUSSE("Île Vertepousse", true);
    private final String m_nom;
    private final boolean m_isExpert;

    Iles(String nom, boolean isExpert)
    {
        m_nom = nom;
        m_isExpert = isExpert;
    }

    public String getNom(boolean getShort) {
        if(!m_isExpert) {
            return m_nom;
        } else {
            if(!getShort) {
                return m_nom + " (mode expert)";
            } else {
                return m_nom + " (expert)";
            }
        }
    }


}
