package tcgp.enums;

import static tcgp.enums.RarityClass.*;

public enum Rarity {
    ONE_DIAMOND("1 losange", STANDARD),
    TWO_DIAMOND("2 losanges", STANDARD),
    THREE_DIAMOND("3 losanges", STANDARD),
    FOUR_DIAMOND("4 losanges", STANDARD),
    ONE_STAR("1 étoile", PLACEHOLDER),
    TWO_STAR("2 étoiles", PLACEHOLDER),
    THREE_STAR("3 étoiles", PLACEHOLDER),
    CROWN("couronne", PLACEHOLDER),
    SHINY_ONE("chromatique 1", PLACEHOLDER),
    SHINY_TWO("chromatique 2", PLACEHOLDER),
    NONE("aucune", PROMO);
    private final String m_name;
    private final RarityClass m_class;

    Rarity(String name, RarityClass rClass)
    {
        m_name = name;
        m_class = rClass;
    }

    public String getName()
    {
        return m_name;
    }

    public String getIllustrationKeyword() {
        return m_class.getKeyword();
    }
}
