package utilitaire;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static utilitaire.Util.*;

class UtilTest {

    @Test
    void numberToPokepediaDexFormat() {
    }

    @Test
    void testIncrementValueInString() {
        String[] test = {"voilà 1 test", "vo1là 1 000 tests", "voilà 999 tests"};

        test[0] = incrementValueInString(test[0], 0, 3);
        test[1] = incrementValueInString(test[1], 0, 10);
        test[1] = incrementValueInString(test[1], 1, 50);
        test[2] = incrementValueInString(test[2], 0, 51);

        assertEquals("voilà 4 test", test[0]);
        assertEquals("vo11là 1 050 tests", test[1]);
        assertEquals("voilà 1 050 tests", test[2]);
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
        Game game = Game.OMEGA_RUBY;
        String pokemon = "Crabicoque";

        String description = Util.getFrenchPokemonDescription(pokemon, game);

        assertEquals("Il voit à 360° sans avoir à tourner la tête. Il ne loupe jamais une proie, même dans son dos.", description);
    }
}