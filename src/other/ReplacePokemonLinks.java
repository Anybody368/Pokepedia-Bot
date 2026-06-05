package other;

import utilitaire.*;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplacePokemonLinks {
    private static final ArrayList<String> availableMons = new ArrayList<>();
    private static final String[] CATEGORIES = {"Pokémon Sleep"/*, "Compétence de Pokémon Sleep"*/};


    static void main(String[] args) {
        Login.handleLogin(args);

        {
            Page page = new Page("Liste des Pokémon de soutien de Pokémon Sleep", Wiki.POKEPEDIA);
            String content = page.getContent();

            for (Pokemon pokemon : PokeData.getAllPokemon()) {
                if(pokemon.getFrenchName().equals("Ronflex")) continue;

                String name = pokemon.getFrenchName();
                if (content.contains(name)) availableMons.add(name);

                for (Region region : pokemon.getRegionalForms()) {
                    String regionalName = name + " " + region.getFrAdjective();
                    if (content.contains(regionalName)) availableMons.add(regionalName);
                }
            }
        }

        System.out.println(availableMons);

        for (String category : CATEGORIES) {
            System.out.println("Handling category " + category);
            replaceLinks(API.getPageFromCategory(category, API.NS_MAIN, Wiki.POKEPEDIA));
        }
    }

    private static void replaceLinks(ArrayList<Page> pages) {
        ArrayList<PageToPublish> changedPages = new ArrayList<>();

        for (Page page : pages) {
            String content = page.getContent();
            String newContent = content;

            for (String name : availableMons) {
                if (!content.contains(name)) continue;

                newContent = newContent.replaceAll("\\{\\{Miniature.+" + Pattern.quote(name) + "]]\n", "{{#invoke:Ressources|pokemon|%s jeu(Sleep)}}\n".formatted(name));
            }

            if (content.equals(newContent)) {
                System.out.printf("Nothing changed on %s%n", page.getTitle());
            } else {
                System.out.printf("Change on %s%n", page.getTitle());
                changedPages.add(new PageToPublish(page, newContent, "Utilisation du module ressources", true));
            }
        }

        if(changedPages.isEmpty()) return;
        Util.publishMultipleEdits(changedPages);
    }
}
