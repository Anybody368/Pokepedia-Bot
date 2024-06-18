import Sleep.Pokemon;
import Utilitaire.*;

public class Main {
    public static void main(String args[])
    {
        //Login.login("Anyboty");

        Pokemon poke = new Pokemon("Pikachu (Fêtes)", 1000, "Électrik", "Bondodo", "Baies", 2, 2, "1:23:45", 10, "Super Soutien", 5, "Pikachu");
        poke.ajoutPokeWiki();
    }
}
