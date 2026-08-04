package sleep;

import sleep.event.view.EventUI;
import sleep.pokemon.SimplifiedPokemon;
import utilitaire.Login;

import java.util.List;

public class EventCreator {
    static void main(String[] args) {
        Login.handleLogin(args);
        List<SimplifiedPokemon> pokemonList = UtilSleep.getAllPokemon();
        new EventUI(pokemonList);
    }
}
