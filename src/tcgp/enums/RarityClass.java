package tcgp.enums;

/**
 * The RaritycLASS enumeration is used to categorise the various Rarities of TCGP cards.
 *
 * <p>If it isn't obvious yet, this hasn't been really implemented yet and is only used to detect standard cards (Diamond
 * rarities)</p>
 */
public enum RarityClass {
    STANDARD("standard "),
    PROMO("Promo-A "),
    PLACEHOLDER("");
    private final String m_keyword;

    RarityClass(String keyword) {
        m_keyword = keyword;
    }

    public String getKeyword() {
        return m_keyword;
    }
}
