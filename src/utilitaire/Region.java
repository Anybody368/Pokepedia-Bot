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
    ALOLA("Alolan", "d'Alola", "アローラ", "Alola"),
    GALAR("Galarian", "de Galar", "ガラル", "Galar"),
    HISUI("Hisuian", "de Hisui", "ヒスイ", "Hisui"),
    PALDEA("Paldean", "de Paldea", "パルデア", "Paldea"),;
    private final String m_enAdjective;
    private final String m_frAdjective;
    private final String m_jaAdjective;

    private final String m_frName;

    private Region(String en, String fr, String ja, String name) {
        m_enAdjective = en;
        m_frAdjective = fr;
        m_jaAdjective = ja;
        m_frName = name;
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

    public String getM_frName() {
        return m_frName;
    }
}
