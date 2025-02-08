package tcgp;

public enum Category {
    POKEMON("Pokémon"),
    DRESSEUR("Dresseur"),
    SUPPORTER("Supporter"),
    ITEM("Objet"),
    FOSSIL("Fossile"),
    TOOL("Outil Pokémon"),
    EX("ex");

    private final String m_name;
    Category(String name)
    {
        m_name = name;
    }

    public String getName()
    {
        return m_name;
    }
}
