package utilitaire;

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
    private final String m_nomType;
    private final String m_nomEn;
    private final String m_nomBaie;

    PokeTypes(String nom, String nomEn, String baie)
    {
        m_nomType = nom;
        m_nomEn = nomEn;
        m_nomBaie = baie;
    }

    public String getNom()
    {
        return m_nomType;
    }

    public String getBaie()
    {
        return m_nomBaie;
    }

    public static PokeTypes typeFromEnglishName(String name, String context)
    {
        for(PokeTypes type : values())
        {
            if(name.equalsIgnoreCase(type.m_nomEn))
            {
                return type;
            }
        }
        throw new ElementNotFoundException(name, context);
    }
}
