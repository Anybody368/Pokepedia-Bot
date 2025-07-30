package tcgp.enums;

import utilitaire.ElementNotFoundException;

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
    private final String m_nomType;
    private final String m_nomEn;

    TCGType(String nom, String nomEn)
    {
        m_nomType = nom;
        m_nomEn = nomEn;
    }

    public String getNom()
    {
        return m_nomType;
    }

    public static TCGType typeFromEnglishName(String name, String context)
    {
        for(TCGType type : values())
        {
            if(name.equals(type.m_nomEn))
            {
                return type;
            }
        }
        throw new ElementNotFoundException(name, context);
    }
}
