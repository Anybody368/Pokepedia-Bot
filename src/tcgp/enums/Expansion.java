package tcgp.enums;

public enum Expansion {
    PROMO_A("Promo-A", "Promo-A", -1, true),
    PUISSANCE_GENETIQUE("Puissance Génétique", "Genetic Apex", 226, true),
    ILE_FABULEUSE("L'Île Fabuleuse", "Mythical Island", 68, false),
    SPATIO_TEMPOREL("Choc Spatio-Temporel", "Space-Time Smackdown", 155, true),
    LUMIERE_TRIOMPHALE("Lumière Triomphale", "Triumphant Light", 75, false),
    REJOUISSANCES_RAYONNANTES("Réjouissances Rayonnantes", "Shining Revelry", 72, false);

    private final String m_frName;
    private final String m_enName;
    private final int m_nbrCards;
    private final boolean m_hasMultipleBoosters;

    Expansion(String fr_name, String en_name, int nbrCards, boolean hasMultipleBoosters)
    {
        m_frName = fr_name;
        m_enName = en_name;
        m_nbrCards = nbrCards;
        m_hasMultipleBoosters = hasMultipleBoosters;
    }

    public static Expansion ExpansionFromEnglishName(String name)
    {
        for(Expansion exp : values())
        {
            if(name.equals(exp.m_enName))
            {
                return exp;
            }
        }
        System.err.println("Erreur : Extension pas trouvée");
        return PROMO_A;
    }

    public String getNameFr() {
        return m_frName;
    }

    public int getNbrCards()
    {
        return m_nbrCards;
    }

    public boolean isSpecial() {
        return m_nbrCards == -1;
    }

    public boolean hasMultipleBoosters() { return m_hasMultipleBoosters; }
}
