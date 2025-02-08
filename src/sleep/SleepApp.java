package sleep;

import sleep.bouffe.*;
import sleep.view.AffichageNewPoke;
import utilitaire.Login;
import utilitaire.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SleepApp {
    public static void main(String[] args)
    {
        Login.login("Anyboty");
        new AffichageNewPoke();

        //Ajout manuel de forme spéciale
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

        //Ajout manuel de recettes
        /*ArrayList<IngredientPoke> ingr = new ArrayList<>();
        ingr.add(new IngredientPoke(ListeIngredients.CACAO_RELAXANT, 30));
        ingr.add(new IngredientPoke(ListeIngredients.LAIT_MEUMEU, 26));
        ingr.add(new IngredientPoke(ListeIngredients.CAFE_REVEIL, 24));
        ingr.add(new IngredientPoke(ListeIngredients.MIEL, 22));

        Recette newRecette = new Recette("Éclair Terraiste", CategoriesRecettes.BOISSONS_DESSERTS, ingr, 20885, "Un éclair bien garni au goût amer à l'effigie d'un joyeux Terraiste.");
        HashMap<Page, String> pages = newRecette.getModifiedPages();

        Scanner confirm = new Scanner(System.in);
        System.out.println("Toutes les pages sont prêtes, appuyez sur \"Entrer\" pour lancer la publication.");
        confirm.nextLine();
        confirm.close();

        pages.forEach( (k, v) -> {
            if(k.setContent(v, "Ajout automatique de " + newRecette.getName()))
            {
                System.out.println(k.getTitle() + " créé avec succès !");
            }
            else
            {
                System.err.println("Echec de la création de " + k.getTitle());
            }
        });*/
    }
}
