package tcgp.card;

import tcgp.enums.Booster;
import tcgp.enums.Expansion;
import tcgp.enums.Rarity;

import java.util.ArrayList;

/**
 * The CardSpecs class holds all the data of a specific version of a Card.
 *
 * <p>Each Card may have one or more CardSpecs, and each CardSpecs will generate one Poképedia article.</p>
 *
 * @author Samuel Chanal
 */

public class CardSpecs {
    private final Expansion m_expansion;
    private final Rarity m_rarity;
    private final int m_number;
    private final String m_illustrator;
    private final ArrayList<Booster> m_boosters;
    private final boolean m_isReused;

    /**
     * Main constructor of a CardSpecs with all the relevant data
     * @param exp the Expansion of the card
     * @param rar the rarity of the card
     * @param cardNbr the number of the card within its expansion
     * @param illust the name of the illustrator
     * @param boosters the list of Boosters this card is available in withing its expansion
     * @param isReused whether the illustration of this card is taken from a previous card or not, usually outside TCGP
     */
    public CardSpecs(Expansion exp, Rarity rar, int cardNbr, String illust, ArrayList<Booster> boosters, boolean isReused)
    {
        m_expansion = exp;
        m_rarity = rar;
        m_number = cardNbr;
        m_illustrator = illust;
        m_boosters = boosters;
        m_isReused = isReused;
    }

    public String getExpansionFrName()
    {
        return m_expansion.getNameFr();
    }

    public String getExpansionEnName()
    {
        return m_expansion.getNameEn();
    }

    public Expansion getExpansion()
    {
        return m_expansion;
    }

    public String getExpansionSize()
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

    /**
     * Getter for the card's number using Poképedia's card number format
     * @return a String containing the formatted card number
     */
    public String getNbrCardToString()
    {
        return numberToWikiFormat(m_number);
    }

    /**
     * Turns the number of the card in Poképedia's format, with three numbers
     * @param nbr number to be converted
     * @return a string containing the number, preceded by 0s if necessary
     */
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

    /**
     * Generates the raw text of this specific card's Booster for the Poképedia article
     * @return a String containing the list of boosters this card is available in for Poképedia (might be empty)
     */
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

    /**
     * Returns a boolean whether the card is considered as secret (meaning Full-art in regular expansions)
     * @return true if the card's number exceed the expansion size and said expansion is regular, false otherwise
     */
    public boolean isSecret()
    {
        return(m_number > m_expansion.getNbrCards() && !isFromSpecialExpansion());
    }

    public boolean isFromSpecialExpansion()
    {
        return (m_expansion.isSpecial());
    }

    public boolean isShiny() { return (m_rarity == Rarity.SHINY_ONE || m_rarity == Rarity.SHINY_TWO); }

    public boolean illustrationIsReused() {
        return m_isReused;
    }
}
