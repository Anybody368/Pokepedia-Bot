package tcgp.enums;

public enum RarityClass {
    STANDARD("standard "),
    PROMO("Promo-A "),
    PLACEHOLDER("");
    private final String m_keyword;

    private RarityClass(String keyword) {
        m_keyword = keyword;
    }

    public String getKeyword() {
        return m_keyword;
    }
}
