package tcgp.enums;

import utilitaire.ElementNotFoundException;

/**
 * The Expansion enumeration is used to tell where a specific card is from.
 *
 * <p>It needs to be updated whenever new expansions come out before using the program.</p>
 *
 * @author Samuel Chanal
 */
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

    /**
     * Constructor
     * @param fr_name the French name of the expansion
     * @param en_name the English name of the expansion
     * @param nbrCards The amount of regular cards this expansion has (not counting secret cards). -1 for special expansions
     *                 like Promo-A.
     * @param hasMultipleBoosters Whether that expansion has multiple Boosters or not
     */
    Expansion(String fr_name, String en_name, int nbrCards, boolean hasMultipleBoosters)
    {
        m_frName = fr_name;
        m_enName = en_name;
        m_nbrCards = nbrCards;
        m_hasMultipleBoosters = hasMultipleBoosters;
    }

    /**
     * Allows you to get the Expansion corresponding to the given English name (not case-sensitive).
     * @param name the English name for the wanted Expansion
     * @param context the context where this method is called for debugging purposes
     * @return the corresponding Expansion
     * @throws ElementNotFoundException if a matching Expansion isn't found
     */
    public static Expansion ExpansionFromEnglishName(String name, String context) throws ElementNotFoundException
    {
        for(Expansion exp : values())
        {
            if(name.equalsIgnoreCase(exp.m_enName))
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
