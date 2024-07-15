import Sleep.Ingredient;
import Sleep.Pokemon;
import Utilitaire.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args)
    {
        //Login.login("Anyboty");

        ArrayList<Ingredient> plante = new ArrayList<>(3);
        plante.add(new Ingredient("Pomme de Terre Fondante", 2, 5, 7));
        plante.add(new Ingredient("Lait Meumeu", 0, 6, 9));
        plante.add(new Ingredient("Gingembre Chaleureux", 0, 0, 8));

        Pokemon poke = new Pokemon("Poussacha", 906, "Plante", "Ptidodo", "Ingrédients", plante, 1, "1:16:00", 10, "Garde-Manger S", 5, "Poussacha");
        poke.ajoutPokeWiki();
    }
}
