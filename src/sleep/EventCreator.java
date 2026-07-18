package sleep;

import sleep.pokemon.SimplifiedPokemon;

import java.util.List;

public class EventCreator {
    static void main() {
        List<SimplifiedPokemon> pokemonList = UtilSleep.getAllPokemon();

        System.out.println(pokemonList);
    }
}
