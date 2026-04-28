package sleep.pokemon;

import sleep.bouffe.IngredientPoke;
import sleep.dodos.SleepStyle;
import sleep.dodos.Island;
import sleep.dodos.TypesDodo;
import utilitaire.PokeTypes;
import utilitaire.Region;
import utilitaire.Util;

import java.util.ArrayList;
import java.util.Arrays;

public class PokemonRegional extends Pokemon{
    private final Region m_region;

    /**
     *
     * @param nom : Nom du Pokémon
     * @param region : Région d'où provient la forme
     * @param numDex : Numéro du Pokémon dans le Pokédex National
     * @param type : Type du Pokémon dans sleep
     * @param dodoType : Catégorie de dodo du Pokémon (Ptidodo, Bondodo, ou Grododo)
     * @param specialite : Baies, Ingrédients, ou Compétences
     * @param ingredients : Liste des ingrédients du Pokémon à créer au préalable
     * @param sleepStyles : Liste des dodos du Pokémon pré-remplis
     * @param iles : Iles où le Pokémon peut être trouvé
     * @param frequence : Fréquence de base du Pokémon au format "h:min:sec" ou "min:sec"
     * @param capacite : Capacité maximale du Pokémon (combien d'objets peut-il tenir par défaut)
     * @param competence : Compétence Principale du Pokémon
     * @param ptsAmitie : Combien de Pokébiscuits max faut-il pour devenir ami avec ce Pokémon
     * @param bonbon : Nom de Pokémon utilisé pour les bonbons de celui-ci (utile pour les Pokémon évolués)
     */
    public PokemonRegional(String nom, Region region, int numDex, PokeTypes type, TypesDodo dodoType, Specialites specialite, ArrayList<IngredientPoke> ingredients, ArrayList<SleepStyle> sleepStyles, ArrayList<Island> iles, String frequence, int capacite, Competences competence, int ptsAmitie, String bonbon, Imagery imageryType)
    {
        super(nom, numDex, type, dodoType, specialite, ingredients, sleepStyles, iles, frequence, capacite, competence, ptsAmitie, bonbon, imageryType);
        m_region = region;
    }

    @Override
    protected String[] getLignePokeRecap() {
        ArrayList<String> temp = new ArrayList<>(Arrays.asList(super.getLignePokeRecap()));
        temp.remove(1); //On part du principe que le poké original existe déja, donc pas besoin du numdex ici
        return temp.toArray(new String[0]);
    }

    @Override
    protected String getMiniatureString()
    {
        return "{{Miniature|" + getNumDex() + "|" + m_region.getFrName() + "|jeu=Sleep}} [[" + getName() + " " + m_region.getFrAdjective() + "]]";
    }

    @Override
    protected String getNameSection() {
        return ("|forme=" + m_region.getFrName() + super.getNameSection() + "{{!}}" + getName() + " " + m_region.getFrAdjective());
    }

    @Override
    public String getRegionalName() {
        return super.getRegionalName() + " " + m_region.getFrAdjective();
    }

    @Override
    protected String getPokemonListName() {
        return super.getPokemonListName() + " forme(" + m_region.getFrName() + ")";
    }

    @Override
    protected String getImageID() {
        return super.getImageID() + " " + m_region.getFrName();
    }

    @Override
    protected int getInternalID() {
        return 999999999;
    }

    @Override
    protected String getNavigationRibbon() {
        return Util.makeNavigationRibbon(Integer.parseInt(m_numDex), m_region);
    }
}
