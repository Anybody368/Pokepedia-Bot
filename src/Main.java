import Sleep.Pokemon;
import Utilitaire.*;

public class Main {
    public static void main(String args[])
    {
        //Login.login("Anyboty");

        Pokemon poke = new Pokemon("Abra", 1000, "Feu", "Ingrédients", 2, 2, "1:23:45", 10, "Super Soutien", 5);
        poke.ajoutPokeWiki();
    }
}
