package sleep.dodos;

public enum Island {
    ILE_VERTEPOUSSE("Île Vertepousse", false),
    PLAGE_CYAN("Plage Cyan", false),
    GROTTE_SEPIA("Grotte Sépia", false),
    PLAINE_PERCENEIGE("Plaine Perce-neige", false),
    RIVAGE_LAPISLAZULI("Rivage Lapis-lazuli", false),
    VIEILLE_CENTRALE_DOREE("Vieille Centrale Dorée", false),
    CANYON_AMBRE("Canyon Ambre", false),

    EX_ILE_VERTEPOUSSE("Île Vertepousse", true);
    private final String m_name;
    private final boolean m_isExpert;

    Island(String name, boolean isExpert)
    {
        m_name = name;
        m_isExpert = isExpert;
    }

    public boolean isExpert()
    {
        return m_isExpert;
    }

    public String getName(boolean getShort) {
        if(!m_isExpert) {
            return m_name;
        } else {
            if(!getShort) {
                return m_name + " (mode expert)";
            } else {
                return m_name + " (expert)";
            }
        }
    }


}
