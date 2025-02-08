package sleep.pokemon;

import sleep.bouffe.IngredientPoke;
import sleep.dodos.Dodo;
import sleep.dodos.Iles;
import sleep.dodos.TypesDodo;
import utilitaire.PokeTypes;

import java.util.ArrayList;
import java.util.Arrays;

public class PokemonRegional extends Pokemon{
    private final Regions m_region;

    /**
     *
     * @param nom : Nom du Pokémon
     * @param region : Région d'où provient la forme
     * @param numDex : Numéro du Pokémon dans le Pokédex National
     * @param type : Type du Pokémon dans sleep
     * @param dodoType : Catégorie de dodo du Pokémon (Ptidodo, Bondodo, ou Grododo)
     * @param specialite : Baies, Ingrédients, ou Compétences
     * @param ingredients : Liste des ingrédients du Pokémon à créer au préalable
     * @param dodos : Liste des dodos du Pokémon pré-remplis
     * @param iles : Iles où le Pokémon peut être trouvé
     * @param frequence : Fréquence de base du Pokémon au format "h:min:sec" ou "min:sec"
     * @param capacite : Capacité maximale du Pokémon (combien d'objets peut-il tenir par défaut)
     * @param competence : Compétence Principale du Pokémon
     * @param ptsAmitie : Combien de Pokébiscuits max faut-il pour devenir ami avec ce Pokémon
     * @param bonbon : Nom de Pokémon utilisé pour les bonbons de celui-ci (utile pour les Pokémon évolués)
     */
    public PokemonRegional(String nom, Regions region, int numDex, PokeTypes type, TypesDodo dodoType, Specialites specialite, ArrayList<IngredientPoke> ingredients, ArrayList<Dodo> dodos, ArrayList<Iles> iles, String frequence, int capacite, Competences competence, int ptsAmitie, String bonbon)
    {
        super(nom, numDex, type, dodoType, specialite, ingredients, dodos, iles, frequence, capacite, competence, ptsAmitie, bonbon);
        m_region = region;
    }

    @Override
    protected String[] getLignePokeRecap() {
        ArrayList<String> temp = new ArrayList<>(Arrays.asList(super.getLignePokeRecap()));
        temp.remove(1); //On part du principe que le poké original existe déja, donc pas besoin du numdex ici
        return temp.toArray(new String[0]);
    }

    @Override
    protected String getStringMiniaNomPoke()
    {
        return "{{Miniature|" + getNumDex() + "|" + m_region.getNom() + "|jeu=sleep}} [[" + getNom() + " " + m_region.getComplet() + "]]";
    }

    @Override
    protected String getSectionNom() {
        return ("|forme=" + m_region.getNom() + super.getSectionNom() + "{{!}}" + getNom() + " " + m_region.getComplet());
    }
}
