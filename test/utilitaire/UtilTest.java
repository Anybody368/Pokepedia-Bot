package utilitaire;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void numDexComplet() {
    }

    @Test
    void incrementeValeurDansString() {
    }

    @Test
    void incrementeRowspan() {
    }

    @Test
    void nextIndexOf() {
    }

    @Test
    void decompMilliers() {
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
    void reconstructionCodeSource() {
    }

    @Test
    void getFrenchPokemonDescription() {
        Game game = Game.X;
        String pokemon = "Yanma";

        String description = Util.getFrenchPokemonDescription(pokemon, game);

        assertEquals("Il voit à 360° sans avoir à tourner la tête. Il ne loupe jamais une proie, même dans son dos.", description);
    }
}