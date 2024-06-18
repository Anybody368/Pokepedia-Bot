package Utilitaire;

import java.util.ArrayList;

public class Util {

    private static final int NBR_POKE = 1025;

    public static String numDexComplet(int num)
    {
        if(num <= 0 || num > NBR_POKE)
        {
            System.err.println("ERREUR : Numéro ne correspond à aucun Pokémon du Pokédex");
            System.exit(1);
        }

        if(num < 10)
        {
            return "000" + num;
        } else if (num < 100) {
            return "00" + num;
        } else if (num < 1000) {
            return "0" + num;
        }
        return String.valueOf(num);
    }

    public static String incrementeValeurDansString(String ligne, int numMot, int increment)
    {
        if(increment == 0)
        {
            return ligne;
        }
        String[] mots = ligne.split(" ");
        mots[numMot] = String.valueOf(Integer.parseInt(mots[numMot])+increment);
        ligne = "";
        for(String mot : mots) ligne += mot + " ";
        return ligne.substring(0, ligne.length()-1);
    }

    public static int trouverNumLigne(ArrayList<String> lignes, String recherche)
    {
        int l = 0;
        while (!lignes.get(l).equals(recherche))
        {
            l++;
            if(l == lignes.size())
            {
                System.err.println("ERREUR : Ligne non trouvée dans le texte");
                System.exit(2);
            }
        }
        return l;
    }
}
