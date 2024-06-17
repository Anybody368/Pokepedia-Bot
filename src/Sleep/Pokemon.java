package Sleep;

import Utilitaire.Page;
import Utilitaire.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Pokemon {
    private String m_nom;
    private String m_numDex;
    private String m_type;
    private String m_specialite;
    private ArrayList<Ingredient> m_listeIngredients;
    private ArrayList<Dodo> m_listeDodos;
    private int m_freqHeure;
    private int m_freqMin;
    private int m_freqSec;
    private int m_capacite;
    private String m_competence;
    private int m_ptsAmitie;

    public Pokemon(String nom, int numDex, String type, String specialite, int nbIngredients, int nbDodos, String frequence, int capacite, String competence, int ptsAmitie)
    {
        m_nom = nom;
        m_numDex = Util.numDexComplet(numDex);
        m_type = type;
        m_specialite = specialite;
        remplissageFreqence(frequence);
        m_capacite = capacite;
        m_competence = competence;
        m_ptsAmitie = ptsAmitie;

        Scanner sc = new Scanner(System.in);
        m_listeDodos = new ArrayList<>();
        for (int i = 1; i <= nbDodos; i++)
        {
            System.out.println("Nom du dodo " + i);
            String nomDodo = sc.nextLine();
            System.out.println("Rareté du dodo " + i);
            int numDodo = sc.nextInt();
            m_listeDodos.add(new Dodo(nomDodo, numDodo));
            sc.nextLine();
        }
        m_listeIngredients = new ArrayList<>();
        for (int i = 1; i <= nbIngredients; i++)
        {
            System.out.println("Nom de l'ingrédient " + i);
            String nomIngredient = sc.nextLine();
            System.out.println("Quantité de l'ingrédient " + i);
            int quantite = sc.nextInt();
            m_listeIngredients.add(new Ingredient(nomIngredient, quantite));
            sc.nextLine();
        }
    }

    public String getBaie()
    {
        String baie = switch (m_type)
        {
            case "Normal" -> "Kika";
            case "Feu" -> "Mepo";
            case "Eau" -> "Oran";
            case "Électrik" -> "Résin";
            case "Plante" -> "Durin";
            case "Glace" -> "Fraive";
            case "Combat" -> "Ceriz";
            case "Poison" -> "Maron";
            case "Sol" -> "Figuy";
            case "Vol" -> "Palma";
            case "Psy" -> "Mago";
            case "Insecte" -> "Prine";
            case "Roche" -> "Sitrus";
            case "Spectre" -> "Remu";
            case "Dragon" -> "Nanone";
            case "Ténèbres" -> "Wiki";
            case "Acier" -> "Myrte";
            case "Fée" -> "Pêcha";
            default -> "";
        };
        if(baie.isEmpty()) {
            System.err.println("ERREUR : Type non valide");
            System.exit(1);
        }
        return baie;
    }

    private void remplissageFreqence(String freq)
    {
        String[] details = freq.split(":");
        switch (details.length)
        {
            case 2 :
                m_freqHeure = 0;
                m_freqMin = Integer.parseInt(details[0]);
                m_freqSec = Integer.parseInt(details[1]);
                break;
            case 3 :
                m_freqHeure = Integer.parseInt(details[0]);
                m_freqMin = Integer.parseInt(details[1]);
                m_freqSec = Integer.parseInt(details[2]);
                break;
            default: {
                System.err.println("ERREUR : Format de fréquence invalide");
                System.exit(1);
            }
        }
    }

    public void ajoutPokeWiki()
    {
        ajoutListePokeSoutien();
    }

    private void ajoutListePokeSoutien()
    {
        final int LIGNES_INFORMATIONS = 11;

        Page listeSoutien = new Page("Liste des Pokémon de soutien de Pokémon Sleep");
        String content = listeSoutien.getContent();
        ArrayList<String> lignes = new ArrayList<>(Arrays.asList(content.split("\n")));

        //On se place au niveau de la première ligne d'un Pokémon de Soutien du premier tableau, normalement "| 0001"
        int l = 0;
        String ligneAct = lignes.get(l);
        while(!ligneAct.equals("! Points d'amitié requis"))
        {
            l++;
            ligneAct = lignes.get(l);
        }
        l += 2;
        ligneAct = lignes.get(l);

        //On cherche l'endroit où le nouveau Pokémon doit être inséré en comparant les numéros de Pokédex
        while (ligneAct.substring(ligneAct.length()-4).compareTo(m_numDex) < 0)
        {
            l += LIGNES_INFORMATIONS;
            //Si jamais il y a un rowspan avant le numero du dex (coucou Pikachu)
            if(ligneAct.length() > 10)
            {
                int rowspan = Integer.parseInt(ligneAct.substring(11, 12));
                l += (LIGNES_INFORMATIONS-1) * (rowspan-1);
            }
            ligneAct = lignes.get(l);

            //Si jamais le Pokémon doit être inséré à la toute fin du tableau
            if(ligneAct.isEmpty())
            {
                l -= 1;
                break;
            }
        }

        String[] ajout = {"|-",
                "| " + m_numDex,
                "| style=\"text-align:left;\" | {{Miniature|" + m_numDex + "|jeu=Sleep}} [[" + m_nom + "]]",
                "| {{Type|" + m_type + "|Sleep}}",
                "| " + m_specialite,
                "| [[Fichier:Sprite Baie " + getBaie() + " Sleep.png|30px]] [[Baie " + getBaie() + "]]",
                listeIngredientsWiki(),
                frequenceWiki(),
                "| " + m_capacite,
                "| [[" + m_competence + "]]",
                "| " + m_ptsAmitie};
        lignes.addAll(l - 1, List.of(ajout));
        ajout = null;

        //Même procédé pour le deuxième tableau
        while(!ligneAct.equals("! Récompenses"))
        {
            l++;
            ligneAct = lignes.get(l);
        }
        l += 2;
        ligneAct = lignes.get(l);

        while(ligneAct.substring(28, 32).compareTo(m_numDex) < 0)
        {
            l += 2;
            ligneAct = lignes.get(l);

            //Si jamais le Pokémon doit être inséré à la toute fin du tableau
            if(ligneAct.length() < 33)
            {
                break;
            }
        }
    }

    private String listeIngredientsWiki()
    {
        String r = "| style=\"text-align:left;\" | ";
        for(Ingredient i : m_listeIngredients)
        {
            if(!r.equals("| style=\"text-align:left;\" | "))
            {
                r += "<br>";
            }
            r += "[[Fichier:Sprite " + i.getNom() + " Sleep.png|30px]] [[" + i.getNom() + "]]";
        }
        return r;
    }

    private String frequenceWiki()
    {
        String r = "| ";
        switch (m_freqHeure)
        {
            case 0:
                break;
            case 1:
                r += m_freqHeure + " heure<br>";
                break;
            default:
                r += m_freqHeure + " heures<br>";
                break;
        }
        switch (m_freqMin)
        {
            case 0:
                break;
            case 1:
                r += m_freqMin + " minute<br>";
                break;
            default:
                r += m_freqMin + " minutes<br>";
                break;
        }
        switch (m_freqSec)
        {
            case 0:
                r = r.substring(0, r.length()-4);
                break;
            case 1:
                r += m_freqSec + " seconde";
                break;
            default:
                r += m_freqSec + " secondes";
                break;
        }
        return r;
    }
}
