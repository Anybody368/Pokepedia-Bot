package tcgp.card;

import tcgp.enums.Booster;
import tcgp.enums.Expansion;
import tcgp.enums.Rarity;

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
        if(m_boosters.getFirst() == Booster.NONE)
        {
            return "";
        }

        StringBuilder code = new StringBuilder("| booster=");
        code.append(m_boosters.getFirst().getName()).append("\n");

        for (int i = 1; i < m_boosters.size(); i++) {
            code.append("| booster").append(i+1).append("=").append(m_boosters.get(i).getName()).append("\n");
        }

        return code.toString();
    }

    public boolean isSecret()
    {
        return(m_number > m_expansion.getNbrCards() && m_expansion.getNbrCards() != -1);
    }

    public boolean isSpecialExtension()
    {
        return (m_expansion.isSpecial());
    }
}
