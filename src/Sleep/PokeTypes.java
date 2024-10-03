package Sleep;

public enum PokeTypes {
    NORMAL("Normal", "Kika"),
    FEU("Feu", "Mepo"),
    EAU("Eau", "Oran"),
    PLANTE("Plante", "Durin"),
    ELECTRIK("Électrik", "Résin"),
    VOL("Vol", "Palma"),
    ROCHE("Roche", "Sitrus"),
    SOL("Sol", "Figuy"),
    GLACE("Glace", "Fraive"),
    ACIER("Acier", "Myrte"),
    COMBAT("Combat", "Ceriz"),
    PSY("Psy", "Mago"),
    TENEBRES("Ténébres", "Wiki"),
    SPECTRE("Spectre", "Remu"),
    FEE("Fée", "Pêcha"),
    DRAGON("Dragon", "Nanone"),
    INSECTE("Prine", "Insecte"),
    POISON("Poison", "Maron");
    private final String m_nomType;
    private final String m_nomBaie;

    PokeTypes(String nom, String baie)
    {
        m_nomType = nom;
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
}
