import Sleep.Pokemon;
import Utilitaire.*;

public class Main {
    public static void main(String[] args)
    {
        Login.login("Anyboty");

        Pokemon poke = new Pokemon("Nigosier", 845, "Vol", "Grododo", "Ingrédients", 3, 4, "45:00", 19, "Plat Super Bon S", 16, "Nigosier");
        poke.ajoutPokeWiki();
    }
}
