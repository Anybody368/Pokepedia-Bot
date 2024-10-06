import Sleep.bouffe.IngredientPoke;
import Sleep.dodos.Iles;
import Sleep.pokemon.Pokemon;
import Utilitaire.Login;

import static Sleep.pokemon.Competences.*;
import static Sleep.bouffe.ListeIngredients.*;
import static Sleep.pokemon.PokeTypes.*;
import static Sleep.pokemon.Specialites.*;
import static Sleep.dodos.TypesDodo.*;
import static Sleep.dodos.Iles.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args)
    {
        Login.login("Anyboty");

        ArrayList<IngredientPoke> ingredients = new ArrayList<>(3);
        ingredients.add(new IngredientPoke(CAFE_REVEIL, 2, 5, 7));
        ingredients.add(new IngredientPoke(CHAMPI_EXQUIS, 0, 4, 7));
        ingredients.add(new IngredientPoke(MIEL, 0, 0, 11));
        //ingredients.add(new IngredientPoke(TOMATE_ROUPILLON, 10));

        ArrayList<Iles> iles = new ArrayList<>(List.of(new Iles[]{VIEILLE_CENTRALE_DOREE}));

        //Recette recette = new Recette("Salade Coup-Croix", SALADES, ingredients, 8755, "Cette salade a été préparée en coupant finement les ingrédients.");
        //recette.plublieRecette();

        /*Pokemon petit = new Pokemon("Larvibule", 736, INSECTE, PTIDODO, INGREDIENTS, ingredients, 1, iles, "1:16:40", 11, CHARGE_PUISSANCE_S, 5, "Larvibule");
        petit.ajoutPokeWiki();*/
        Pokemon moyen = new Pokemon("Chrysapile", 737, INSECTE, PTIDODO, INGREDIENTS, ingredients, 4, iles, "55:00", 15, CHARGE_PUISSANCE_S, 12, "Larvibule");
        moyen.ajoutPokeWiki();
        Pokemon grand = new Pokemon("Lucanon", 738, INSECTE, PTIDODO, INGREDIENTS, ingredients, 4, iles, "46:40", 19, CHARGE_PUISSANCE_S, 22, "Larvibule");
        grand.ajoutPokeWiki();
    }
}
