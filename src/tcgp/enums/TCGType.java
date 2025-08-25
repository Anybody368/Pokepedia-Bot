package tcgp.enums;

import utilitaire.ElementNotFoundException;

/**
 * The TCGType enumeration is used to tell the type for cards of Pokémon
 *
 * @author Samuel Chanal
 */
public enum TCGType {
    INCOLORE("Incolore", "Colorless"),
    FEU("Feu", "Fire"),
    EAU("Eau", "Water"),
    PLANTE("Plante", "Grass"),
    ELECTRIK("Électrique", "Lightning"),
    METAL("Métal", "Metal"),
    COMBAT("Combat", "Fighting"),
    PSY("Psy", "Psychic"),
    OBSCURITE("Obscurité", "Darkness"),
    FEE("Fée", "Fairy"),
    DRAGON("Dragon", "Dragon");
    private final String m_frName;
    private final String m_enName;

    /**
     * Constructor
     * @param frName the French name for the type
     * @param enName the English name for the type
     */
    TCGType(String frName, String enName)
    {
        m_frName = frName;
        m_enName = enName;
    }

    public String getNom()
    {
        return m_frName;
    }

    /**
     * Allows you to get the type corresponding to the given English name (case-sensitive).
     * @param name the English name for the wanted type
     * @param context the context where this method is called for debugging purposes
     * @return the corresponding type
     * @throws ElementNotFoundException if a matching type isn't found
     */
    public static TCGType typeFromEnglishName(String name, String context)
    {
        for(TCGType type : values())
        {
            if(name.equals(type.m_enName))
            {
                return type;
            }
        }
        throw new ElementNotFoundException(name, context);
    }
}
