package utilitaire;

public enum Region {
    ALOLA("Alolan", "d'Alola", "アローラ"),
    GALAR("Galarian", "de Galar", "ガラル"),
    HISUI("Hisuian", "de Hisui", "ヒスイ"),
    PALDEA("Paldean", "de Paldea", "パルデア");
    private final String m_enAdjective;
    private final String m_frAdjective;
    private final String m_jaAdjective;

    private Region(String en, String fr, String ja) {
        m_enAdjective = en;
        m_frAdjective = fr;
        m_jaAdjective = ja;
    }

    public static Region findRegionalFromEn(String fullName) {
        for(Region region : values())
        {
            if(fullName.startsWith(region.getEnAdjective())) {
                return region;
            }
        }
        return null;
    }

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
