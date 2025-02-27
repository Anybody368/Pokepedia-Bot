package tcgp.enums;

public enum Expansion {
    PROMO_A("Promo-A", "Promo-A", -1, -1),
    PUISSANCE_GENETIQUE("Puissance Génétique", "Genetic Apex", 226, 286),
    ILE_FABULEUSE("L'Île Fabuleuse", "Mythical Island", 68, 86),
    SPATIO_TEMPOREL("Choc Spatio-Temporel", "Space-Time Smackdown", 155, 207);

    private final String m_frName;
    private final String m_enName;
    private final int m_nbrCards;
    private final int m_trueNbrCards;

    Expansion(String fr_name, String en_name, int nbrCards, int trueNbrCards)
    {
        m_frName = fr_name;
        m_enName = en_name;
        m_nbrCards = nbrCards;
        m_trueNbrCards = trueNbrCards;
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
        return null;
    }

    public String getNameFr() {
        return m_frName;
    }

    public int getNbrCards()
    {
        return m_nbrCards;
    }

    public int getTrueNbrCards()
    {
        return m_trueNbrCards;
    }

    public boolean isSpecial() {
        return m_nbrCards == -1;
    }
}
