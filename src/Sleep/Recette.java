package Sleep;

import Utilitaire.Page;
import Utilitaire.Util;

import java.util.ArrayList;
import java.util.Arrays;

public class Recette {
    private final String m_nom;
    private final String m_categorie;
    private final ArrayList<IngredientPoke> m_ingredients;
    private final int m_puissance;
    private final String m_description;

    public Recette(String nom, String categorie, ArrayList<IngredientPoke> liste, int puissance, String description)
    {
        m_nom = nom;
        m_categorie = categorie;
        m_ingredients = liste;
        m_puissance = puissance;
        m_description = description;
    }

    public void plublieRecette()
    {
        ajoutCuisine();
        ajoutIngredients();
    }

    private void ajoutCuisine()
    {
        Page cuisineSleep = new Page("Liste des Pokémon de soutien de Pokémon Sleep");
        String content = cuisineSleep.getContent();
        ArrayList<String> lignes = new ArrayList<>(Arrays.asList(content.split("\n")));

        int l = lignes.indexOf("==== " + m_categorie + " ====");
        if(l == -1)
        {
            System.err.println("Erreur : catégorie incorrecte");
            System.exit(-1);
        }


    }

    private void ajoutIngredients()
    {

    }
}
