package utilitaire;

/**
 * The Game enumeration represents the different games of the Pokémon Franchise and their basic data.
 *
 * @author Samuel Chanal
 */
public enum Game {
    RED("Rouge", "Red", 1, "R"),
    BLUE("Bleu", "Blue", 1, "B"),
    YELLOW("Jaune", "Yellow", 1, "J"),

    GOLD("Or", "Gold", 2, "O"),
    SILVER("Argent", "Silver", 2, "A"),
    CRYSTAL("Cristal", "Crystal", 2, "C"),

    RUBY("Rubis", "Ruby", 3, "Ru"),
    SAPPHIRE("Saphir", "Sapphire", 3, "Sa"),
    EMERALD("Émeraude", "Emerald", 3, "E"),
    FIRE_RED("Rouge Feu", "FireRed", 3, "RF"),
    LEAF_GREEN("Vert Feuille", "LeafGreen", 3, "VF"),

    DIAMOND("Diamant", "Diamond", 4, "D"),
    PEARL("Perle", "Pearl", 4, "P"),
    PLATINUM("Platine", "Platinum", 4, "Pt"),
    HEART_GOLD("Or HeartGold", "HeartGold", 4, "HG"),
    SOUL_SILVER("Argent SoulSilver", "SoulSilver", 4, "SS"),

    BLACK("Noir", "Black", 5, "No"),
    WHITE("Blanc", "White", 5, "Bl"),
    BLACK_2("Noir 2", "Black 2", 5, "No2"),
    WHITE_2("Blanc 2", "White 2", 5, "Bl2"),

    X("X", "X", 6, "X"),
    Y("Y", "Y", 6, "Y"),
    OMEGA_RUBY("Rubis Oméga", "Omega Ruby", 6, "RuO"),
    ALPHA_SAPPHIRE("Saphir Alpha", "Alpha Sapphire", 6, "SaA"),

    SUN("Soleil", "Sun", 7, "So"),
    MOON("Lune", "Moon", 7, "Lu"),
    ULTRA_SUN("Ultra-Soleil", "Ultra Sun", 7, "US"),
    ULTRA_MOON("Ultra-Lune", "Ultra Moon", 7, "UL"),
    LETS_GO_PIKACHU("Let's Go Pikachu", "Let's Go Pikachu", 7, "LGP"),
    LETS_GO_EEVEE("Let's Go Évoli", "Let's Go Eevee", 7, "LGE"),

    SWORD("Épée", "Sword", 8, "Ep"),
    SHIELD("Bouclier", "Shield", 8, "Bo"),
    BRILLIANT_DIAMOND("Diamant Étincelant", "Brilliant Diamond", 8, "DE"),
    SHINING_PEARL("Perle Scintillante", "Shining Pearl", 8, "PS"),
    LEGENDS_ARCEUS("Légendes Arceus", "Legends Arceus", 8, "LPA"),

    SCARLET("Écarlate", "Scarlet", 9, "Ec"),
    VIOLET("Violet", "Violet", 9,  "Vi");

    private final String m_frenchName;
    private final String m_englishName;
    private final int m_generation;
    private final String m_frenchAcronym;

    Game(String frenchName, String englishName, int generation, String pokepediaAbbreviation) {
        this.m_frenchName = frenchName;
        this.m_englishName = englishName;
        this.m_generation = generation;
        this.m_frenchAcronym = pokepediaAbbreviation;
    }

    public String getFrenchName() {
        return m_frenchName;
    }

    public String getEnglishName() {
        return m_englishName;
    }

    public int getGeneration() {
        return m_generation;
    }

    public String getFrenchAcronym() {
        return m_frenchAcronym;
    }

    /**
     * Allows you to get the Game corresponding to the given English name (not case-sensitive).
     * @param englishName the English name for the wanted Game
     * @param context the context where this method is called for debugging purposes
     * @return the corresponding Game
     * @throws ElementNotFoundException if a matching Game isn't found
     */
    public static Game getGameFromEnglishName(String englishName, String context) {
        for(Game g : values()) {
            if (englishName.equalsIgnoreCase(g.m_englishName)) {
                return g;
            }
        }
        throw new ElementNotFoundException(englishName, context);
    }
}
