package sleep;

import sleep.bouffe.*;
import sleep.dodos.*;
import sleep.pokemon.*;
import sleep.view.AffichageNewPoke;
import utilitaire.Login;
import utilitaire.Page;
import utilitaire.PokeTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SleepApp {
    public static void main(String[] args)
    {
        if(args.length == 2) {
            Login.login(args[0], args[1]);
        } else {
            Login.login();
        }
        new AffichageNewPoke();

        //Ajout manuel de forme spéciale
        /*ArrayList<Iles> iles = new ArrayList<>();
        ArrayList<Dodo> dodos = new ArrayList<>();
        ArrayList<IngredientPoke> ingr = new ArrayList<>();

        iles.add(Iles.ILE_VERTEPOUSSE);
        iles.add(Iles.GROTTE_SEPIA);

        dodos.add(new Dodo("Souriant", 1, 147, 119, 3, iles, true));
        dodos.add(new Dodo("Gros Bâilleur", 2, 493, 390, 5, iles, true));
        dodos.add(new Dodo("Vautre-Boue", 3, 2303, 1809, 7, iles, true));
        dodos.add(new Dodo("sur Gros Bidou", 4, 3076, 2415, 8, iles, true));

        ingr.add(new IngredientPoke(ListeIngredients.CACAO_RELAXANT, 2, 5, 7));
        ingr.add(new IngredientPoke(ListeIngredients.CAFE_REVEIL, 0, 4, 7));
        ingr.add(new IngredientPoke(ListeIngredients.POMME_DE_TERRE, 0, 0, 9));

        Pokemon newPoke = new PokemonRegional("Axoloto", Regions.PALDEA, 194, PokeTypes.POISON, TypesDodo.PTIDODO, Specialites.INGREDIENTS, ingr, dodos, iles, "1:46:40", 9, Competences.CHARGE_ENERGIE, 5, "Axoloto");
        newPoke.ajoutPokeWiki();*/

        //Ajout manuel de recettes
        /*ArrayList<IngredientPoke> ingr = new ArrayList<>();
        ingr.add(new IngredientPoke(ListeIngredients.CITROUILLE_DODUE, 18));
        ingr.add(new IngredientPoke(ListeIngredients.OEUF_EXQUIS, 24));
        ingr.add(new IngredientPoke(ListeIngredients.MIEL, 32));
        ingr.add(new IngredientPoke(ListeIngredients.TOMATE_ROUPILLON, 29));

        Recette newRecette = new Recette("Pancakes Grimace", CategoriesRecettes.BOISSONS_DESSERTS, ingr, 24354, "{{?}}");
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
