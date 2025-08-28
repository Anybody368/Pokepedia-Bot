package utilitaire;

/**
 * The PokeTypes enumeration represents the different types in regular Pokémon games and their basic data.
 *
 * <p>m_berry represents the berry used in Pokémon Sleep by Pokémon of this type</p>
 *
 * @author Samuel Chanal
 */
public enum PokeTypes {
    NORMAL("Normal", "Normal", "Kika"),
    FEU("Feu", "Fire", "Mepo"),
    EAU("Eau", "Water", "Oran"),
    PLANTE("Plante", "Grass", "Durin"),
    ELECTRIK("Électrik", "Electrik", "Résin"),
    VOL("Vol", "Flying", "Palma"),
    ROCHE("Roche", "Rock", "Sitrus"),
    SOL("Sol", "Ground", "Figuy"),
    GLACE("Glace", "Ice", "Fraive"),
    ACIER("Acier", "Steel", "Myrte"),
    COMBAT("Combat", "Fighting", "Ceriz"),
    PSY("Psy", "Psychic", "Mago"),
    TENEBRES("Ténèbres", "Darkness", "Wiki"),
    SPECTRE("Spectre", "Ghost", "Remu"),
    FEE("Fée", "Fairy", "Pêcha"),
    DRAGON("Dragon", "Dragon", "Nanone"),
    INSECTE("Insecte", "Insect", "Prine"),
    POISON("Poison", "Poison", "Maron");
    private final String m_frName;
    private final String m_enName;
    private final String m_berry;

    PokeTypes(String nom, String nomEn, String baie)
    {
        m_frName = nom;
        m_enName = nomEn;
        m_berry = baie;
    }

    public String getFrenchName()
    {
        return m_frName;
    }

    public String getBerry()
    {
        return m_berry;
    }

    /**
     * Allows you to get the Type corresponding to the given English name (not case-sensitive).
     * @param name the English name for the wanted Type
     * @param context the context where this method is called for debugging purposes
     * @return the corresponding Type
     * @throws ElementNotFoundException if a matching Type isn't found
     */
    public static PokeTypes typeFromEnglishName(String name, String context) throws ElementNotFoundException
    {
        for(PokeTypes type : values())
        {
            if(name.equalsIgnoreCase(type.m_enName))
            {
                return type;
            }
        }
        throw new ElementNotFoundException(name, context);
    }

    /**
     * Allows you to get the Type corresponding to the given French name (not case-sensitive).
     * @param name the French name for the wanted Type
     * @param context the context where this method is called for debugging purposes
     * @return the corresponding Type
     * @throws ElementNotFoundException if a matching Type isn't found
     */
    public static PokeTypes typeFromFrenchName(String name, String context) throws ElementNotFoundException
    {
        for(PokeTypes type : values())
        {
            if(name.equalsIgnoreCase(type.m_frName))
            {
                return type;
            }
        }
        throw new ElementNotFoundException(name, context);
    }
}
