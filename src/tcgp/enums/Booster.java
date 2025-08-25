package tcgp.enums;

import utilitaire.ElementNotFoundException;

import java.util.ArrayList;

import static tcgp.enums.Expansion.*;

/**
 * The Booster enumeration is used to tell where a specific card can be obtained.
 *
 * <p>It is only useful for Expansions with multiple boosters, but needs to be kept up to date when new expansions release.</p>
 *
 * <p>For special Expansions like Promo-A, the OTHER value should be used. The NONE value should never be used and
 * indicates that a problem has happened (never happened so far)</p>
 *
 * @author Samuel Chanal
 */
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
    HO_OH("Ho-Oh", SAGESSE_CIEL_MER),
    LUGIA("Lugia", SAGESSE_CIEL_MER),

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

    /**
     * Allows you to get the Booster corresponding to the given English name (case-sensitive).
     * @param name the English name for the wanted Booster
     * @param context the context where this method is called for debugging purposes
     * @return the corresponding Booster
     * @throws ElementNotFoundException if a matching Booster isn't found
     */
    public static Booster getBoosterFromName(String name, String context)
    {
        for (Booster b : values()) {
            if(b.m_name.equals(name))
            {
                return b;
            }
        }
        throw new ElementNotFoundException(name, context);
    }

    /**
     * Allows you to get a list of Booster belonging to an Expansion
     * @param exp the Expansion you want the Boosters of
     * @return an Arraylist containing all the matching Boosters
     */
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
