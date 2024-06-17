package Utilitaire;

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
}
