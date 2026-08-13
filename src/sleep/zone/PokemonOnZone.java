/*
 * Copyright (c) 2026 - Poképedia's contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package sleep.zone;

import sleep.Rank;
import sleep.dodos.SleepRank;
import sleep.pokemon.SimplifiedPokemon;

import java.util.List;

public record PokemonOnZone(SimplifiedPokemon pokemon, List<Rank> sleepRanks) {
    public String getZoneCode() {
        StringBuilder code = new StringBuilder("|-\n").append(pokemon.getSleepFirstLines(sleepRanks.size()));

        for (int i = 1; i <= sleepRanks.size(); i++) {
            Rank sleepRank = sleepRanks.get(i - 1);

            if (i > 1) code.append("\n|-");

            code.append("\n|").repeat(" [[Fichier:Miniature Étoile Sleep.png|20px]]", i);
            code.append("\n| ").append(sleepRank.getRankLine());
        }

        return code.toString();
    }
}
