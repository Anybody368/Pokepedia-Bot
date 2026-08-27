/*
 * Copyright (c) 2026 - Poképedia's contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import utilitaire.*;
import static utilitaire.Util.*;

void main(String[] args) {
    //Login.handleLogin(args);

    for (Pokemon pokemon : PokeData.getAllPokemon()) {
        if (pokemon.getFrenchName().equals("Pikachu")) continue;

        handlePokemon(pokemon.getFrenchName());

        for (Region region : pokemon.getRegionalForms()) {
            handlePokemon(pokemon.getFrenchName() + " " + region.getFrAdjective());
        }
    }
}

private void handlePokemon(String name) {
    System.out.printf("%S%n", name);
    Page mainPage = new Page(name, Wiki.POKEPEDIA);
    Page secondaryPage = new Page(name + "/Jeux secondaires", Wiki.POKEPEDIA);

    String mainContent = mainPage.getContent().replace("|Unite}}", "|UNITE}}").replace("Pokémon Unite", "Pokémon UNITE")
            .replace("''[[Pokkén Tournament]]''", "[[Pokkén Tournament]]")
            .replace("Apparitions dans {{Jeu|UNITE}}", "Apparition dans {{Jeu|UNITE}}")
            .replace("==== Dans {{Jeu|Pokkén}} ====", "=== Dans {{Jeu|PokkénDX}} ===");

    if (mainContent.contains("UNITE")) {
        String sectionName = mainContent.contains("UNITE]]'' ==") ? "== Apparition dans ''[[Pokémon UNITE]]'' ==" : "== Apparition dans {{Jeu|UNITE}} ==";
        String oldSection = extractSection(mainContent, sectionName);
        String newSection = extractUnite(oldSection);
    }

    if (mainContent.contains("Pokkén")) {
        boolean isDX = mainContent.contains("PokkénDX");
        System.out.println(extractPokken(mainContent, isDX, name));
    }

    if (mainContent.contains("Dans {{Jeu|PPk")) System.err.println("Poképark fait son relou");
}

private String extractUnite(String section) {
    String role = searchValueOf(section, "rôle2=", false);

    String newIntro = " apparaît dans {{Jeu|UNITE}} en tant que personnage jouable de type %s.".formatted(role);
    return section.replaceAll(" apparaît comme personnage jouable[^.]*\\.", newIntro).replace(".\n{{", ".\n\n{{");
}

private String extractPokken(String content, boolean isDX, String name) {
    String section;
    String game = isDX ? "PokkénDX" : "Pokkén";
    if (isDX) {
        if (content.contains("''[[Pokkén")) {
            section = extractSection(content, "=== Dans ''[[Pokkén Tournament DX]]'' ===");
        } else {
            section = extractSection(content, "=== Dans {{Jeu|PokkénDX}} ===");
        }
    } else {
        String searchSection = content.contains("Jeu|Pokkén") ? "=== Dans {{Jeu|Pokkén}} ===" : "=== Dans [[Pokkén Tournament]] ===";
        section = extractSection(content, searchSection);
    }

    String newSection = section.replace("Dans ce jeu, ", "").replace("un Pokémon de soutien",
            "un Pokémon de soutien de {{Jeu|%s}},".formatted(game)).replace("Dans ''Pokkén Tournament DX'', ", "")
            .replace("Dans Pokkén Tournament, ", "").replace("Dans {{Jeu|Pokkén|lien=non}}, ", "")
            .replace("\n\n -", "<br>-").replace("\n\n-", "<br>-");

    if (!newSection.contains("Pokémon de soutien")) {
        newSection = "%s est un combattant dans {{Jeu|%s}}.\n\n".formatted(name, game) + newSection;
    }

    return newSection;
}