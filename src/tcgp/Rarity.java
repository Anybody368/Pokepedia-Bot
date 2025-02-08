package tcgp;

public enum Rarity {
    ONE_DIAMOND("1 losange"),
    TWO_DIAMOND("2 losanges"),
    THREE_DIAMOND("3 losanges"),
    FOUR_DIAMOND("4 losanges"),
    ONE_STAR("1 étoile"),
    TWO_STAR("2 étoiles"),
    THREE_STAR("3 étoiles"),
    CROWN("couronne"),
    NONE("aucune");
    private final String m_name;

    Rarity(String name)
    {
        m_name = name;
    }

    public String getName()
    {
        return m_name;
    }
}
