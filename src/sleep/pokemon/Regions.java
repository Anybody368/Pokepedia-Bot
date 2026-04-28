package sleep.pokemon;

import utilitaire.ElementNotFoundException;

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

    public static Regions fromName(String name)
    {
        for (Regions r : Regions.values())
        {
            if(name.equals(r.m_nomRegion) || name.equals(r.m_article + r.m_nomRegion)) return r;
        }
        throw new ElementNotFoundException(name, "Pokémon regional form");
    }
}
