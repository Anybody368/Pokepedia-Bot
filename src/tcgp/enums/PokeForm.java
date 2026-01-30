package tcgp.enums;

public enum PokeForm {
    TEAL_MASK("Teal Mask", "Masque Turquoise", "みどりのめん"),
    WELLSPRING_MASK("Wellspring Mask", "Masque du Puits", "いどのめん"),
    HEARTHFLAME_MASK("Hearthflame Mask", "Masque du Fourneau", "かまどのめん"),
    CORNERSTONE_MASK("Cornerstone Mask", "Masque de la Pierre", "いしずえのめん");
    private final String m_enName;
    private final String m_frName;
    private final String m_jaName;

    PokeForm(String en, String fr, String ja) {
        m_enName = en;
        m_frName = fr;
        m_jaName = ja;
    }

    /**
     * Searches if a Pokémon English name is a special form by analysing the start of its name
     * @param fullName the full English name of the Pokémon
     * @return the PokeForm of the special form if the Pokémon is a special variant, null otherwise
     */
    public static PokeForm findFormFromEn(String fullName) {
        for(PokeForm form : values())
        {
            if(fullName.startsWith(form.getEnName())) {
                return form;
            }
        }
        return null;
    }

    /**
     * Searches if a Pokémon French name is a special form by analysing the end of its name
     * @param fullName the full French name of the Pokémon
     * @return the PokeForm of the special form if the Pokémon is a special variant, null otherwise
     */
    public static PokeForm findFormFromFr(String fullName) {
        for(PokeForm form : values())
        {
            if(fullName.endsWith(form.getFrName())) {
                return form;
            }
        }
        return null;
    }

    public String getFrName() {
        return m_frName;
    }

    public String getEnName() {
        return m_enName;
    }

    public String getJaName() {
        return m_jaName;
    }
}
