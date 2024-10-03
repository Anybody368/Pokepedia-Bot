package Sleep;

public enum Competences {
    CHARGE_PUISSANCE_S("Charge de Puissance S"),
    CHARGE_PUISSANCE_M("Charge de Puissance M"),
    AIMANT_FRAGMENT_DE_REVE("Aimant à Fragment de Rêve S"),
    ENCOURAGEMENT_ENERGIQUE("Encouragement Énergique S"),
    CHARGE_ENERGIE("Charge d'Énergie S"),
    ENERGIE_PARTAGEE("Énergie Partagée S"),
    SOUTIEN_EXTRA("Soutien Extra S"),
    AIMANT_INGREDIENT("Aimant à Ingrédient S"),
    GARDE_MANGER("Garde-Manger S"),
    PLAT_SUPER_BON("Plat Super Bon S"),
    METRONOME("Métronome"),
    SUPER_SOUTIEN("Super Soutien");
    private final String m_nom;

    private Competences(String nom)
    {
        m_nom = nom;
    }

    public String getNom() {
        return m_nom;
    }
}
