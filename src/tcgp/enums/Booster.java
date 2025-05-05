package tcgp.enums;

import java.util.ArrayList;

import static tcgp.enums.Expansion.*;

public enum Booster {
    DRACAUFEU("Dracaufeu", PUISSANCE_GENETIQUE),
    MEWTWO("Mewtwo", PUISSANCE_GENETIQUE),
    PIKACHU("Pikachu", PUISSANCE_GENETIQUE),
    MEW("Mew", ILE_FABULEUSE),
    DIALGA("Dialga", SPATIO_TEMPOREL),
    PALKIA("Palkia", SPATIO_TEMPOREL),
    ARCEUS("Arceus", LUMIERE_TRIOMPHALE),
    SOLGALEO("Solgaleo", GARDIENS_ASTRAUX),
    LUNALA("Lunala", GARDIENS_ASTRAUX),

    //Jokers pour Promo-A
    OTHER("{{?}}", null),
    NONE("ERREUR", null);

    private final String m_name;
    private final Expansion m_expansion;

    Booster(String nom, Expansion exp)
    {
        m_name = nom;
        m_expansion = exp;
    }

    public static Booster getBoosterFromName(String name)
    {
        for (Booster b : values()) {
            if(b.m_name.equals(name))
            {
                return b;
            }
        }
        return null;
    }

    public static ArrayList<Booster> getBoostersFromExpansion(Expansion exp)
    {
        ArrayList<Booster> boosters = new ArrayList<>();
        for (Booster b : values()) {
            if(b.m_expansion == exp)
            {
                boosters.add(b);
            }
        }
        return boosters;
    }

    public String getName() {
        return m_name;
    }
}
