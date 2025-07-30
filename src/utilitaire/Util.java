package utilitaire;

import java.util.ArrayList;
import java.util.List;

public class Util {

    private static final int NBR_POKE = 1025;

    /**
     * Permet d'obtenir le numéro de Pokédex comme affiché dans Poképedia
     * @param num : numéro du dex
     * @return le même numéro, avec autant de 0 que nécessaire
     */
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

    /**
     * Fonction permettant d'incrémenter un nombre au sein d'un String, peut causer des problèmes d'affichage si dépasse 999
     * @param ligne : String contenant le nombre à incrémenter
     * @param numMot : Emplacement du nombre à incrémenter dans la phrase (en commençant à 0, +1 par espace)
     * @param increment : De combien le nombre doit être incrémenté
     * @return le même String, avec le nombre mis à jour
     */
    public static String incrementeValeurDansString(String ligne, int numMot, int increment)
    {
        if(increment == 0)
        {
            return ligne;
        }
        String[] mots = ligne.split(" ");
        mots[numMot] = String.valueOf(Integer.parseInt(mots[numMot])+increment);
        StringBuilder newLigne = new StringBuilder();
        for(String mot : mots) newLigne.append(mot).append(" ");
        return newLigne.substring(0, ligne.length());
    }

    public static String incrementeRowspan(String ligne)
    {
        int i;
        String result;
        if((i = ligne.indexOf("rowspan=")) != -1) {
            int previousSpan;
            int previousEnd;
            switch (ligne.charAt(i + 8)) {
                case '"' -> {
                    previousSpan = Integer.parseInt(ligne.substring(i + 9, i + 10));
                    previousEnd = i + 11;
                }
                case ' ' -> {
                    previousSpan = Integer.parseInt(ligne.substring(i + 9, i + 10));
                    previousEnd = i + 10;
                }
                default -> {
                    previousSpan = Integer.parseInt(ligne.substring(i + 8, i + 9));
                    previousEnd = i + 9;
                }
            }

            result = ligne.substring(0, i+8) + "\"" + (previousSpan+1) + "\"" + ligne.substring(previousEnd);
        }
        else
        {
            result = ligne.charAt(0) + " rowspan=\"2\" |" + ligne.substring(1);
        }

        return result;
    }

    /**
     * Permet de chercher le numéro de la première occurrence d'une ligne précise au sein d'un texte, à partir d'une ligne donnée
     * @param lignes : collection de lignes formant le texte
     * @param recherche : contenu exact de la ligne à rechercher
     * @param debut : Index de la ligne à partir de laquelle chercher
     * @return la position de la ligne dans l'ArrayList, sinon -1
     */
    public static int nextIndexOf(ArrayList<String> lignes, String recherche, int debut)
    {
        List<String> sub = lignes.subList(debut, lignes.size());
        int r = sub.indexOf(recherche);

        if(r == -1) return -1;
        return r+debut;
    }

    /**
     * Permet d'ajouter des espaces pour l'affichage de chiffres au-dessus de 999 pour l'affichage dans Poképedia (exemple : 1234567 retournera 1 234 567)
     * @param num : Nombre à adapter
     * @return le nombre sous forme de String avec des espaces
     */
    public static String decompMilliers(int num)
    {
        if(num < 1000) return String.valueOf(num);
        ArrayList<String> temp = new ArrayList<>();
        while(num/1000 >= 1)
        {
            if(num%1000 == 0) {
                temp.addFirst("000");
            } else if (num%1000 < 10) {
                temp.addFirst("00" + num%1000);
            } else if (num%1000 < 100) {
                temp.addFirst("0" + num%1000);
            } else {
                temp.addFirst(String.valueOf(num%1000));
            }
            num /= 1000;
        }

        temp.addFirst(String.valueOf(num));
        StringBuilder r = new StringBuilder();
        for(String t : temp) r.append(t).append(" ");
        return r.substring(0, r.length()-1);
    }

    public static String searchValueOf(String content, String searchRegex, boolean nullIsTolerated)
    {
        return searchValueOf(content, searchRegex, "\n", 0, nullIsTolerated);
    }

    public static String searchValueOf(String content, String searchRegex, int from, boolean nullIsTolerated)
    {
        return searchValueOf(content, searchRegex, "\n", from, nullIsTolerated);
    }

    public static String searchValueOf(String content, String searchRegex, String endRegex, boolean nullIsTolerated)
    {
        return searchValueOf(content, searchRegex, endRegex, 0, nullIsTolerated);
    }

    public static String searchValueOf(String content, String searchRegex, String endRegex, int from, boolean nullIsTolerated)
    {
        int startIndex = content.indexOf(searchRegex, from)+searchRegex.length();
        if(startIndex == -1) {
            if(nullIsTolerated) {
                return null;
            }
            throw new ElementNotFoundException(searchRegex, "result in text");
        }

        int endIndex = content.indexOf(endRegex, startIndex);
        return content.substring(startIndex, endIndex);
    }

    public static String reconstructionCodeSource(ArrayList<String> lignes)
    {
        StringBuilder result = new StringBuilder();
        for(String ligne : lignes)
        {
            result.append(ligne).append("\n");
        }
        return result.toString();
    }
}
