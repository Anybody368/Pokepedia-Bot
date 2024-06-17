package Sleep;

import Utilitaire.Util;

import java.util.ArrayList;
import java.util.Scanner;

public class Pokemon {
    private String m_nom;
    private String m_numDex;
    private String m_specialite;
    private ArrayList<Ingredient> m_listeIngredients;
    private ArrayList<Dodo> m_listeDodos;
    private int m_freqHeure;
    private int m_freqMin;
    private int m_freqSec;
    private int m_capacite;
    private String m_competence;
    private int m_ptsAmitie;

    public Pokemon(String nom, int numDex, String specialite, int nbIngredients, int nbDodos, String frequence, int capacite, String competence, int ptsAmitie)
    {
        m_nom = nom;
        m_numDex = Util.numDexComplet(numDex);
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
}
