package tcgp;

import java.util.ArrayList;

public class CardSpecs {
    private final Expansion m_expansion;
    private final Rarity m_rarity;
    private final int m_number;
    private final String m_illustrator;
    private final ArrayList<Booster> m_boosters;

    public CardSpecs(Expansion exp, Rarity rar, int cardNbr, String illust, ArrayList<Booster> boosters)
    {
        m_expansion = exp;
        m_rarity = rar;
        m_number = cardNbr;
        m_illustrator = illust;
        m_boosters = boosters;
    }

    public String getExtensionName()
    {
        return m_expansion.getNameFr();
    }

    public Expansion getExpansion()
    {
        return m_expansion;
    }

    public String getExtensionSize()
    {
        int nbr = m_expansion.getNbrCards();
        if(nbr == -1)
        {
            return "P-A";
        }
        return numberToWikiFormat(nbr);
    }

    public String getRarityName()
    {
        return m_rarity.getName();
    }

    public int getNbrCard()
    {
        return m_number;
    }

    public String getRelativeNbrCard(int diff)
    {
        return numberToWikiFormat(m_number+diff);
    }

    public String getNbrCardToString()
    {
        return numberToWikiFormat(m_number);
    }

    private String numberToWikiFormat(int nbr)
    {
        if(nbr < 10)
        {
            return "00"+nbr;
        } else if (nbr < 100) {
            return "0"+nbr;
        }
        else {
            return String.valueOf(nbr);
        }
    }

    public String getIllustrator()
    {
        return m_illustrator;
    }

    public String getCodeBoosters()
    {
        if(m_boosters.size() == 1)
        {
            return("le [[booster (JCC)|booster]] [[" + m_boosters.getFirst().getName() + "]]");
        }
        else if (m_boosters.size() == 2)
        {
            return("les [[booster (JCC)|booster]]s [[" + m_boosters.getFirst().getName() + "]] et [[" + m_boosters.getLast().getName()
                + "]]");
        }
        else
        {
            return("les [[booster (JCC)|booster]]s [[" + m_boosters.getFirst().getName() + "]], [[" + m_boosters.get(1).getName()
                + "]] et [[" + m_boosters.getLast().getName() + "]]");
        }
    }

    public boolean isSecret()
    {
        return(m_number > m_expansion.getNbrCards() && m_expansion.getNbrCards() != -1);
    }

    public boolean isLastCard()
    {
        return (m_number == m_expansion.getTrueNbrCards());
    }
}
