import Sleep.IngredientPoke;
import Sleep.Recette;
import Utilitaire.Login;

import static Sleep.ListeIngredients.*;
import static Sleep.CategoriesRecettes.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args)
    {
        Login.login("Anyboty");

        ArrayList<IngredientPoke> ingredients = new ArrayList<>(3);
        ingredients.add(new IngredientPoke(OEUF_EXQUIS, 20));
        ingredients.add(new IngredientPoke(VIANDE_VEGETALE, 28));
        ingredients.add(new IngredientPoke(MAIS_VERTEPOUSSE, 11));
        ingredients.add(new IngredientPoke(TOMATE_ROUPILLON, 10));

        Recette recette = new Recette("Salade Coup-Croix", SALADES, ingredients, 8755, "Cette salade a été préparée en coupant finement les ingrédients.");
        recette.plublieRecette();

        //Pokemon petit = new Pokemon("Suicune", 245, EAU, GRODODO, COMPETENCES, eau, 3, "45:00", 17, SUPER_SOUTIEN, 30, "Suicune");
        //Pokemon moyen = new Pokemon("Maraiste", 195, "Eau", "Grododo", "Ingrédients", eau, 4, "56:40", 16, "Charge d'Énergie S", 12, "Axoloto");
        //Pokemon grand = new Pokemon("Palmaval", 914, "Combat", "Grododo", "Ingrédients", eau, 4, "43:20", 19, "Charge de Puissance M", 20, "Coiffeton");

        //petit.ajoutPokeWiki();
        //moyen.ajoutPokeWiki();
        //grand.ajoutPokeWiki();
    }
}
