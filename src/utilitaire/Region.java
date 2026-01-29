package utilitaire;

/**
 * The Region enumeration is currently used to detect regional variants of Pokémon such as Alolan Meowth.
 *
 * <p>Although it isn't used as such, it could be fairly easily extended to be used as a general enumeration for
 * Pokémon regions.</p>
 *
 * @author Samuel Chanal
 */
public enum Region {
    ALOLA("Alolan", "d'Alola", "アローラ"),
    GALAR("Galarian", "de Galar", "ガラル"),
    HISUI("Hisuian", "de Hisui", "ヒスイ"),
    PALDEA("Paldean", "de Paldea", "パルデア"),

    TEAL_MASK("Teal Mask", "Masque Turquoise", "みどりのめん"),
    WELLSPRING_MASK("Wellspring Mask", "Masque du Puits", "いどのめん"),
    HEARTHFLAME_MASK("Hearthflame Mask", "Masque du Fourneau", "かまどのめん"),
    CORNERSTONE_MASK("Cornerstone Mask", "Masque de la Pierre", "いしずえのめん");
    private final String m_enAdjective;
    private final String m_frAdjective;
    private final String m_jaAdjective;

    private Region(String en, String fr, String ja) {
        m_enAdjective = en;
        m_frAdjective = fr;
        m_jaAdjective = ja;
    }

    /**
     * Searches if a Pokémon English name is a regional form by analysing the start of its name
     * @param fullName the English name of the Pokémon
     * @return the Region of the regional form if the Pokémon is a regional variant, null otherwise
     */
    public static Region findRegionalFromEn(String fullName) {
        for(Region region : values())
        {
            if(fullName.startsWith(region.getEnAdjective())) {
                return region;
            }
        }
        return null;
    }

    /**
     * Searches if a Pokémon French name is a regional form by analysing the end of its name
     * @param fullName the French name of the Pokémon
     * @return the Region of the regional form if the Pokémon is a regional variant, null otherwise
     */
    public static Region findRegionalFromFr(String fullName) {
        for(Region region : values())
        {
            if(fullName.endsWith(region.getFrAdjective())) {
                return region;
            }
        }
        return null;
    }

    public String getFrAdjective() {
        return m_frAdjective;
    }

    public String getEnAdjective() {
        return m_enAdjective;
    }

    public String getJaAdjective() {
        return m_jaAdjective;
    }
}
