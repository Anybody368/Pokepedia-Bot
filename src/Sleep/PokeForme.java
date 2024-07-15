package Sleep;

public class PokeForme extends Pokemon{
    private String m_nomForme;

    public PokeForme(String nom, String forme, int numDex, String type, String dodoType, String specialite, int nbIngredients, int nbDodos, String frequence, int capacite, String competence, int ptsAmitie, String bonbon)
    {
        super(nom, numDex, type, dodoType, specialite, nbIngredients, nbDodos, frequence, capacite, competence, ptsAmitie, bonbon);
        m_nomForme = forme;
    }
}
