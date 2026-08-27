package sleep;

import sleep.bouffe.*;
import sleep.dodos.*;
import sleep.pokemon.*;
import sleep.pokemon.Pokemon;
import sleep.view.AffichageNewPoke;
import sleep.zone.Island;
import utilitaire.*;

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
        /*ArrayList<Island> iles = new ArrayList<>();
        ArrayList<SleepStyle> dodos = new ArrayList<>();
        ArrayList<IngredientPoke> ingr = new ArrayList<>();

        iles.add(Island.ILE_VERTEPOUSSE);

        HashMap<Island, SleepRank> ranks1 = new HashMap<>();
        ranks1.put(Island.ILE_VERTEPOUSSE, new SleepRank("b3"));

        //HashMap<Island, SleepRank> ranks2 = new HashMap<>();


        dodos.add(new SleepStyle("sur Queue", 1, 67, 195, 5, ranks1));
        //dodos.add(new SleepStyle("Oreilles Tombantes", 2, 228, 676, 6, iles, true));
        //dodos.add(new Dodo("Vautre-Boue", 3, 2303, 1809, 7, iles, true));
        //dodos.add(new Dodo("sur Gros Bidou", 4, 3076, 2415, 8, iles, true));

        ingr.add(new IngredientPoke(ListeIngredients.POMME_JUTEUSE, 1, 2, 4));
        ingr.add(new IngredientPoke(ListeIngredients.GINGEMBRE_CHALEUREUX, 0, 2, 3));
        ingr.add(new IngredientPoke(ListeIngredients.OEUF_EXQUIS, 0, 0, 3));

        Pokemon newPoke = new PokeForme("Pikachu", "Capitaine", "", 25, PokeTypes.ELECTRIK, TypesDodo.BONDODO, Specialites.BAIES, ingr, dodos, iles, "41:40", 21, Competences.AIMANT_INGREDIENT, 7, "Pikachu", Imagery.AGENDER);
        ArrayList<PageToPublish> wikiPages = newPoke.getWikiModifications();

        Util.publishMultipleEdits(wikiPages);*/

        //Ajout manuel de recettes
        /*ArrayList<IngredientPoke> ingr = new ArrayList<>();
        ingr.add(new IngredientPoke(ListeIngredients.GINGEMBRE_CHALEUREUX, 20));
        ingr.add(new IngredientPoke(ListeIngredients.AROMATE_ARDENT, 20));
        ingr.add(new IngredientPoke(ListeIngredients.SOJA_VERTEPOUSSE, 8));
        ingr.add(new IngredientPoke(ListeIngredients.HUILE_PURE, 15));

        String description = "Un concentré de saveurs de l'Île Vertepousse dans une petit pain au curry. Croustillant à l'extérieur, gourmand à l'intérieur.";

        Recette newRecette = new Recette("Petit Pain au Curry Vertepousse", CategoriesRecettes.CURRYS_RAGOUTS, ingr, 10945, description);
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
