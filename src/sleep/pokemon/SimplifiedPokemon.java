package sleep.pokemon;

import sleep.dodos.TypesDodo;
import utilitaire.PokeTypes;

public record SimplifiedPokemon(String name, String forme, PokeTypes type, Specialites speciality, TypesDodo sleep) {
}
