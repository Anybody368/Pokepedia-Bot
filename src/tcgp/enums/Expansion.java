package tcgp.enums;

import utilitaire.ElementNotFoundException;

public enum Expansion {
    PROMO_A("Promo-A", "Promo-A", -1, true),
    PUISSANCE_GENETIQUE("Puissance Génétique", "Genetic Apex", 226, true),
    ILE_FABULEUSE("L'Île Fabuleuse", "Mythical Island", 68, false),
    SPATIO_TEMPOREL("Choc Spatio-Temporel", "Space-Time Smackdown", 155, true),
    LUMIERE_TRIOMPHALE("Lumière Triomphale", "Triumphant Light", 75, false),
    REJOUISSANCES_RAYONNANTES("Réjouissances Rayonnantes", "Shining Revelry", 72, false),
    GARDIENS_ASTRAUX("Gardiens Astraux", "Celestial Guardians", 155, true),
    CRISE_INTERDIMENSIONELLE("Crise Interdimensionnelle", "Extradimensional Crisis", 69, false),
    CLAIRIERE_EVOLI("La Clairière d'Évoli", "Eevee Grove", 69, false),
    SAGESSE_CIEL_MER("Sagesse entre Ciel et Mer", "Wisdom of Sea and Sky", 161, true),
    SOURCE_SECRETE("Source Secrète", "Secluded Springs", 0, false);

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

    public static Expansion ExpansionFromEnglishName(String name, String context)
    {
        for(Expansion exp : values())
        {
            if(name.equals(exp.m_enName))
            {
                return exp;
            }
        }
        throw new ElementNotFoundException(name, context);
    }

    public String getNameFr() {
        return m_frName;
    }

    public String getNameEn() {
        return m_enName;
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
