package sleep.pokemon;

public enum Competences {
    CHARGE_PUISSANCE_S("Charge de Puissance S", "Charge de Puissance S"),
    STOCKAGE("Stockage (Charge de Puiss. S)", "Charge de Puissance S"),

    CHARGE_PUISSANCE_M("Charge de Puissance M", "Charge de Puissance M"),
    MAUVAIS_REVE("Mauvais Rêve (Ch. de Puiss. M)", "Charge de Puissance M"),

    AIMANT_FRAGMENT_DE_REVE("Aimant à Fragment de Rêve S", "Aimant à Fragment de Rêve S"),

    ENCOURAGEMENT_ENERGIQUE("Encouragement Énergique S", "Encouragement Énergique S"),
    FROTTE_FRIMOUSSE("Frotte-Frimousse (Encouragement Énergique S)", "Encouragement Énergique S"),

    CHARGE_ENERGIE("Charge d'Énergie S", "Charge d'Énergie S"),
    RAYON_LUNE("Rayon Lune (Charge d'Énergie S", "Charge d'Énergie S"),

    ENERGIE_PARTAGEE("Énergie Partagée S", "Énergie Partagée S"),
    PRIERE_LUNAIRE("Prière Lunaire (Én. Partagée S)", "Énergie Partagée S"),
    JUS_DE_BAIE("Jus de Baie (Énergie Partagée S)", "Énergie Partagée S"),

    SOUTIEN_EXTRA("Soutien Extra S", "Soutien Extra S"),

    AIMANT_INGREDIENT("Aimant à Ingrédient S", "Aimant à Ingrédient S"),
    PLUS("Plus (Aimant à Ingrédient S)", "Aimant à Ingrédient S"),

    GARDE_MANGER("Garde-Manger S", "Garde-Manger S"),
    MOINS("Moins (Garde-Manger S)", "Garde-Manger S"),

    PLAT_SUPER_BON("Plat Super Bon S", "Plat Super Bon S"),

    METRONOME("Métronome (Pokémon Sleep)", "Métronome (Pokémon Sleep)"),

    SUPER_SOUTIEN("Super Soutien", "Super Soutien"),

    BAIES_A_GOGO("Baies à gogo", "Baies à gogo"),
    FANTOMASQUE("Fantômasque (Baies à gogo)", "Baies à gogo"),

    PIOCHE_INGREDIENTS("Pioche à Ingrédient S", "Pioche à Ingrédient S"),
    CHANCEUX("Chanceux (Pioche à Ingrédient S)", "Pioche à Ingrédient S"),
    HYPER_CUTTER("Hyper Cutter (Pioche à Ingrédient S)", "Pioche à Ingrédient S");

    private final String m_name;
    private final String m_category;

    Competences(String nom, String category)
    {
        m_name = nom;
        m_category = category;
    }

    public String getName() {
        return m_name;
    }
    public String getCategory() {return m_category;}
}
