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
        iles.add(Iles.EX_ILE_VERTEPOUSSE);

        dodos.add(new Dodo("en Boule", 1, 27, 75, 4, iles, true));
        dodos.add(new Dodo("Oreilles Tombantes", 2, 228, 676, 6, iles, true));
        //dodos.add(new Dodo("Vautre-Boue", 3, 2303, 1809, 7, iles, true));
        //dodos.add(new Dodo("sur Gros Bidou", 4, 3076, 2415, 8, iles, true));

        ingr.add(new IngredientPoke(ListeIngredients.CITROUILLE_DODUE, 1, 2, 4));
        ingr.add(new IngredientPoke(ListeIngredients.CACAO_RELAXANT, 0, 4, 6));
        ingr.add(new IngredientPoke(ListeIngredients.LAIT_MEUMEU, 0, 0, 9));

        Pokemon newPoke = new PokeForme("Évoli", "Halloween", "", 133, PokeTypes.NORMAL, TypesDodo.BONDODO, Specialites.COMPETENCES, ingr, dodos, iles, "53:20", 18, Competences.AIMANT_INGREDIENT, 5, "Évoli");
        HashMap<Page, String> wikiPages = newPoke.getWikiModifications();

        Scanner confirm = new Scanner(System.in);
        System.out.println("Toutes les pages sont prêtes, appuyez sur \"Entrer\" pour lancer la publication.");
        confirm.nextLine();
        confirm.close();

        wikiPages.forEach( (k, v) -> {
            if(k.setContent(v, "Ajout automatique de la forme de " + newPoke.getNom()))
            {
                System.out.println(k.getTitle() + " modifiée avec succès !");
            }
            else
            {
                System.err.println("Echec de la modification de " + k.getTitle());
            }
        });*/

        //Ajout manuel de recettes
        /*ArrayList<IngredientPoke> ingr = new ArrayList<>();
        ingr.add(new IngredientPoke(ListeIngredients.AVOCAT_APPETISSANT, 18));
        ingr.add(new IngredientPoke(ListeIngredients.TOMATE_ROUPILLON, 16));
        ingr.add(new IngredientPoke(ListeIngredients.LAIT_MEUMEU, 14));
        //ingr.add(new IngredientPoke(ListeIngredients.SOJA_VERTEPOUSSE, 22));

        Recette newRecette = new Recette("Smoothie Phytomixeur", CategoriesRecettes.BOISSONS_DESSERTS, ingr, 8165, "{{?}}");
        HashMap<Page, String> pages = newRecette.getModifiedPages();

        Scanner confirm = new Scanner(System.in);
        System.out.println("Toutes les pages sont prêtes, appuyez sur \"Entrer\" pour lancer la publication.");
        confirm.nextLine();
        confirm.close();

        pages.forEach( (k, v) -> {
            if(k.setContent(v, "Ajout automatique de " + newRecette.getName()))
            {
                System.out.println(k.getTitle() + " modifiée avec succès !");
            }
            else
            {
                System.err.println("Echec de la modification de " + k.getTitle());
            }
        });*/
    }
}
