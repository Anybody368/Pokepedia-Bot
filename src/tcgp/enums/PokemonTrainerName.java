/*
 * Copyright (c) 2026 - Poképedia's contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package tcgp.enums;

public enum PokemonTrainerName {
    TEAM_ROCKET("Team Rocket's", "de la ", "Team Rocket", "ロケット団の");

    public final String enName;
    private final String frAdj;
    public final String frName;
    public final String jaName;

    PokemonTrainerName(String enName, String frAdj, String frName, String jaName) {
        this.enName = enName;
        this.frAdj = frAdj;
        this.frName = frName;
        this.jaName = jaName;
    }

    public String getNameWithAdjective() {
        return frAdj + frName;
    }

    public static PokemonTrainerName getTrainerFromEnglish(String name) {
        for (PokemonTrainerName trainer : PokemonTrainerName.values()) {
            if (name.startsWith(trainer.enName)) {
                return trainer;
            }
        }
        return null;
    }

    public static PokemonTrainerName getTrainerFromFrench(String name) {
        for (PokemonTrainerName trainer : PokemonTrainerName.values()) {
            if (name.endsWith(trainer.getNameWithAdjective())) {
                return trainer;
            }
        }
        return null;
    }
}
