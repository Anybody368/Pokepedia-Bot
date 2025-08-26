package utilitaire;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void numberToPokepediaDexFormat() {
    }

    @Test
    void incrementValueInString() {
    }

    @Test
    void incrementRowspan() {
    }

    @Test
    void nextIndexOf() {
    }

    @Test
    void numberDecomposition() {
    }

    @Test
    void searchValueOf() {
    }

    @Test
    void testSearchValueOf() {
    }

    @Test
    void testSearchValueOf1() {
    }

    @Test
    void testSearchValueOf2() {
    }

    @Test
    void wikicodeReconstruction() {
    }

    @Test
    void getFrenchPokemonDescription() {
        Game game = Game.SWORD;
        String pokemon = "Miradar";

        String description = Util.getFrenchPokemonDescription(pokemon, game);

        assertEquals("Il voit à 360° sans avoir à tourner la tête. Il ne loupe jamais une proie, même dans son dos.", description);
    }
}