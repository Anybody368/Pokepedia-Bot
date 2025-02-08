import sleep.view.AffichageNewPoke;
import utilitaire.Login;

public class Main {
    public static void main(String[] args)
    {
        Login.login("Anyboty");
        new AffichageNewPoke();

        /*ArrayList<Iles> iles = new ArrayList<>();
        ArrayList<Dodo> dodos = new ArrayList<>();
        ArrayList<IngredientPoke> ingr = new ArrayList<>();

        iles.add(Iles.ILE_VERTEPOUSSE);

        dodos.add(new Dodo("en Boule", 1, 27, 75, 3, iles, true));
        dodos.add(new Dodo("Cadeau", 2, 228, 676, 5, iles, true));
        //dodos.add(new Dodo("Queues Enveloppantes", 3, 10059, 14143, 11, iles, true));
        //dodos.add(new Dodo("sur Gros Bidou", 4, 13927, 19580, 12, iles, true));

        ingr.add(new IngredientPoke(ListeIngredients.LAIT_MEUMEU, 1, 2, 4));
        ingr.add(new IngredientPoke(ListeIngredients.CACAO_RELAXANT, 0, 1, 2));
        ingr.add(new IngredientPoke(ListeIngredients.VIANDE_VEGETALE, 0, 0, 3));

        Pokemon goupix = new PokeForme("Évoli", "Fêtes", "", 133, PokeTypes.NORMAL, TypesDodo.BONDODO, Specialites.BAIES, ingr, dodos, iles, "51:40", 20, Competences.AIMANT_FRAGMENT_DE_REVE, 5, "Évoli");
        goupix.ajoutPokeWiki();*/
    }
}
