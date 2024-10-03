import Sleep.IngredientPoke;
import Sleep.Pokemon;
import Utilitaire.*;

import static Sleep.ListeIngredients.*;
import static Sleep.PokeTypes.*;
import static Sleep.Competences.*;
import static Sleep.TypesDodo.*;
import static Sleep.Specialites.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args)
    {
        Login.login("Anyboty");

        ArrayList<IngredientPoke> eau = new ArrayList<>(3);
        eau.add(new IngredientPoke(POMME_JUTEUSE, 1, 2, 4));
        eau.add(new IngredientPoke(HUILE_PURE, 0, 2, 3));
        eau.add(new IngredientPoke(MAIS_VERTEPOUSSE, 0, 0, 2));

        Pokemon petit = new Pokemon("Suicune", 245, EAU, GRODODO, COMPETENCES, eau, 3, "45:00", 17, SUPER_SOUTIEN, 30, "Suicune");
        //Pokemon moyen = new Pokemon("Maraiste", 195, "Eau", "Grododo", "Ingrédients", eau, 4, "56:40", 16, "Charge d'Énergie S", 12, "Axoloto");
        //Pokemon grand = new Pokemon("Palmaval", 914, "Combat", "Grododo", "Ingrédients", eau, 4, "43:20", 19, "Charge de Puissance M", 20, "Coiffeton");

        petit.ajoutPokeWiki();
        //moyen.ajoutPokeWiki();
        //grand.ajoutPokeWiki();
    }
}
