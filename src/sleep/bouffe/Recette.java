package sleep.bouffe;

import utilitaire.Page;
import utilitaire.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static utilitaire.Wiki.POKEPEDIA;

public class Recette {
    private final String m_nom;
    private final CategoriesRecettes m_categorie;
    private final ArrayList<IngredientPoke> m_ingredients;
    private final int m_puissance;
    private final String m_description;

    public Recette(String nom, CategoriesRecettes categorie, ArrayList<IngredientPoke> liste, int puissance, String description)
    {
        m_nom = nom;
        m_categorie = categorie;
        m_ingredients = liste;
        m_puissance = puissance;
        m_description = description;
    }

    public HashMap<Page, String> getModifiedPages()
    {
        HashMap<Page, String> pages = new HashMap<>(2);
        Page pageCuisine = new Page("Cuisine (Pokémon Sleep)", POKEPEDIA);
        pages.put(pageCuisine, ajoutCuisine(pageCuisine.getContent()));
        Page pageIngredients = new Page("Ingrédient (Pokémon Sleep)", POKEPEDIA);
        pages.put(pageIngredients, ajoutIngredients(pageIngredients.getContent()));

        return pages;
    }

    private String ajoutCuisine(String content)
    {
        if(content.isEmpty())
        {
            System.out.println("Erreur dans la récupération de la page de Cuisine");
            System.exit(1);
        }
        ArrayList<String> lignes = new ArrayList<>(Arrays.asList(content.split("\n")));

        int l = lignes.indexOf("==== " + m_categorie.getNom() + " ====");
        if(l == -1)
        {
            System.err.println("Erreur : Problème dans le nom de catégorie");
            System.exit(-1);
        }

        l = Util.nextIndexOf(lignes, "|}", l);
        if(l == -1)
        {
            System.err.println("Erreur : Huuuuuuh... Ouais, c'est pas normal");
            System.exit(-1);
        }

        lignes.add(l, "|-");
        lignes.add(l+1, "| [[Fichier:Sprite " + m_nom + " Sleep.png|50px]]");
        lignes.add(l+2, "| " + m_nom);
        lignes.add(l+3, "| " + m_description);
        lignes.add(l+4, ligneIngredients());
        lignes.add(l+5, "| " + Util.decompMilliers(m_puissance));

        return Util.reconstructionCodeSource(lignes);
    }

    private String ajoutIngredients(String content)
    {
        if(content.isEmpty())
        {
            System.out.println("Erreur dans la récupération de la page d'ingrédients");
            System.exit(1);
        }
        ArrayList<String> lignes = new ArrayList<>(Arrays.asList(content.split("\n")));

        for(IngredientPoke ingredient : m_ingredients)
        {
            int l = lignes.indexOf("=== " + ingredient.getNom() + " ===");
            if(l == -1)
            {
                System.err.println("Erreur : Problème dans le nom d'un ingrédient'");
                System.exit(-1);
            }
            l = Util.nextIndexOf(lignes, "|}", l);
            if(l == -1)
            {
                System.err.println("Erreur : Huuuuuuh... Ouais, c'est pas normal");
                System.exit(-1);
            }

            if((!lignes.get(l-3).equals("|-")) || lignes.get(l-1).equals("|-"))
            {
                lignes.add(l, "|-");
                l++;
            }

            lignes.add(l, "| colspan=\"2\" | [[Fichier:Sprite " + m_nom + " Sleep.png|60px]] " + m_nom);
            lignes.add(l+1, "| ×" + ingredient.getQttNv1());
        }

        return Util.reconstructionCodeSource(lignes);
    }

    private String ligneIngredients()
    {
        StringBuilder r = new StringBuilder("| ");
        for(IngredientPoke ingredient : m_ingredients)
        {
            r.append("[[Fichier:Sprite ").append(ingredient.getNom()).append(" Sleep.png|30px]] [[")
                    .append(ingredient.getNom()).append("]] ×").append(ingredient.getQttNv1()).append("<br>");
        }
        return r.substring(0, r.length()-4);
    }

    public String getName()
    {
        return m_nom;
    }
}
