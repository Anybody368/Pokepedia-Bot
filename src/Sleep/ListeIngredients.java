package Sleep;

public enum ListeIngredients {
    GROS_POIREAU("Gros Poireau", 185, 7),
    CHAMPI_EXQUIS("Champi Exquis", 167, 7),
    OEUF_EXQUIS("Œuf Exquis", 115, 5),
    POMME_DE_TERRE("Pomme de Terre Fondante", 124, 5),
    POMME_JUTEUSE("Pomme Juteuse", 90, 4),
    AROMATE_ARDENT("Aromate Ardent", 130, 5),
    VIANDE_VEGETALE("Viande Végétale", 103, 4),
    LAIT_MEUMEU("Lait Meumeu", 98, 4),
    MIEL("Miel", 101, 4),
    HUILE_PURE("Hule Pure", 121, 5),
    GINGEMBRE_CHALEUREUX("Gingembre Chaleureux", 109, 4),
    TOMATE_ROUPILLON("Tomate Roupillon", 110, 4),
    CACAO_RELAXANT("Cacao Relaxant", 151, 6),
    QUEUE_RAMOLOSS("Queue Ramoloss", 342, 14),
    SOJA_VERTEPOUSSE("Soja Vertepousse", 100, 4),
    MAIS_VERTEPOUSSE("Maïs Vertepousse", 140, 6),
    CAFE_REVEIL("Café Réveil", 153, 6);
    private final String m_nom;
    private final int m_puissance;
    private final int m_prix;

    private ListeIngredients(String nom, int puissance, int prix)
    {
        m_nom = nom;
        m_prix = prix;
        m_puissance = puissance;
    }

    public String getNom() {
        return m_nom;
    }

    public int getPrix() {
        return m_prix;
    }

    public int getPuissance() {
        return m_puissance;
    }
}
