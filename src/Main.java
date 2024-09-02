import Sleep.Ingredient;
import Sleep.Pokemon;
import Utilitaire.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args)
    {
        Login.login("Anyboty");

        ArrayList<Ingredient> eau = new ArrayList<>(3);
        eau.add(new Ingredient("Pomme Juteuse", 1, 2, 4));
        eau.add(new Ingredient("Huile Pure", 0, 2, 3));
        eau.add(new Ingredient("Maïs Vertepousse", 0, 0, 2));

        Pokemon petit = new Pokemon("Suicune", 245, "Eau", "Grododo", "Compétences", eau, 3, "45:00", 17, "Super Soutien", 30, "Suicune");
        //Pokemon moyen = new Pokemon("Maraiste", 195, "Eau", "Grododo", "Ingrédients", eau, 4, "56:40", 16, "Charge d'Énergie S", 12, "Axoloto");
        //Pokemon grand = new Pokemon("Palmaval", 914, "Combat", "Grododo", "Ingrédients", eau, 4, "43:20", 19, "Charge de Puissance M", 20, "Coiffeton");

        petit.ajoutPokeWiki();
        //moyen.ajoutPokeWiki();
        //grand.ajoutPokeWiki();
    }
}
