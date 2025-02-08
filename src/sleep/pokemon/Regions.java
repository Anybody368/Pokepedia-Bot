package sleep.pokemon;

public enum Regions {
    ALOLA("d'","Alola"),
    GALAR("de ","Galar"),
    HISUI("de ","Hisui"),
    PALDEA("de ","Paldea");

    private final String m_nomRegion;
    private final String m_article;
    Regions(String article, String region)
    {
        m_nomRegion = region;
        m_article = article;
    }

    public String getNom()
    {
        return m_nomRegion;
    }

    public String getComplet()
    {
        return m_article+m_nomRegion;
    }
}
