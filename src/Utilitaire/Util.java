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
                return -1;
            }
        }
        return l;
    }

    public static String decompMilliers(int num)
    {
        if(num < 1000) return String.valueOf(num);
        ArrayList<String> temp = new ArrayList<>();
        while(num/1000 >= 1)
        {
            temp.addFirst(String.valueOf(num%1000));
            num /= 1000;
        }
        temp.addFirst(String.valueOf(num));
        String r = "";
        for(String t : temp) r += t + " ";
        return r.substring(0, r.length()-1);
    }
}
