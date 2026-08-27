/*
 * Copyright (c) 2026 - Poképedia's contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package tcgp.category.decorator;

import tcgp.category.CategoryStrategy;
import tcgp.enums.PokemonTrainerName;
import utilitaire.Util;

import java.util.regex.Pattern;

public class PokemonTrainer extends BaseDecorator {
    private final PokemonTrainerName trainer;

    /**
     * Main constructor used by the other Decorator classes extending it
     *
     * @param wrapped the main category of the card (or another Decorator if the card has multiple things going on)
     */
    public PokemonTrainer(CategoryStrategy wrapped, PokemonTrainerName trainer) {
        super(wrapped);
        this.trainer = trainer;
    }

    @Override
    public String makeNameSection(String en_name, String fr_name, String jp_name) {
        String original = super.makeNameSection(en_name, fr_name, jp_name);
        String result;
        int place1 = original.indexOf(trainer.getNameWithAdjective());
        int place2 = original.indexOf("\n");
        if(original.contains("| nomréel=")) {
            result = original.substring(0, place1) + "<small>" + original.substring(place1, place2) + "</small>"
                    + original.substring(place2).replace(" "  + trainer.getNameWithAdjective() +"\n", "\n");
        } else {
            String name = Util.searchValueOf(original, "| nom=", false).replace(" "  + trainer.getNameWithAdjective(), "");
            result = original.substring(0, place1) + "<small>" + original.substring(place1, place2) + "</small>\n| nomréel="
                    + name + original.substring(place2);
        }

        if (original.contains("{{Symbole JCC")) {
            String symbol = "{{Symbole JCC|" + Util.searchValueOf(original, "{{Symbole JCC|", "}}", false) + "}}";
            result = Util.insertIntoString(result.replaceFirst(Pattern.quote(symbol), ""), symbol, place1-1);
        }

        int enAdjectivePlace = result.indexOf(trainer.enName);
        int enAdjectiveEnd = enAdjectivePlace + trainer.enName.length();
        int jaAdjectivePlace = result.indexOf(trainer.jaName);
        int jaAdjectiveEnd = jaAdjectivePlace + trainer.jaName.length();

        result = result.substring(0, enAdjectivePlace) + "<small>" + result.substring(enAdjectivePlace, enAdjectiveEnd)
                + "</small>" + result.substring(enAdjectiveEnd, jaAdjectivePlace) + "<small>"
                + result.substring(jaAdjectivePlace,  jaAdjectiveEnd) + "</small>" + result.substring(jaAdjectiveEnd);

        return result;
    }

    @Override
    public String makeCategorySection() {
        String original = super.makeCategorySection();
        if(original.contains("sous-catégorie")) {
            return original + "\n| sous-catégorie2=Dresseur\n| dresseur=" + trainer.frName;
        } else {
            return original + "\n| sous-catégorie=Dresseur\n| dresseur=" + trainer.frName;
        }
    }

    @Override
    public boolean isPokemon() {
        return true;
    }

    public int getTrainerEnSize() {
        return trainer.enName.length()+1;
    }

    public String getFrFullName() {
        return trainer.getNameWithAdjective();
    }
}
