package utilitaire;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import static utilitaire.Util.*;

public class GeneratePokeJSON {
    private final static int FIRST = 1;
    private final static int LAST = 1025;

    public static void main(String[] args) {
        ArrayList<Pokemon> pokemonList = new ArrayList<>();

        for(int i = FIRST; i <= LAST; i++) {
            System.out.println("Searching data for Pokémon " + i);
            Page redirectPage = new Page("Pokémon n°" + numberToPokepediaDexFormat(i), Wiki.POKEPEDIA);
            String redirectContent = redirectPage.getContent();

            Page page = new Page(searchValueOf(redirectContent, "[[", "]]", false), Wiki.POKEPEDIA);
            String content = page.getContent();

            String frenchName = searchValueOf(content, "| nom=", false);
            String englishName = searchValueOf(content, "| nom-anglais=", false);
            String japaneseName = searchValueOf(content, "| nom-japonais=", false);

            String firstType = searchValueOf(content, "| type1=", false);
            PokeTypes type1 = PokeTypes.typeFromFrenchName(getFirstType(firstType), "first Pokémon type");
            PokeTypes type2 = null;
            if(content.contains("| type2=")) {
                String secondType = searchValueOf(content, "| type2=", false);
                if(!secondType.contains("-/") && !secondType.isBlank()) {
                    type2 = PokeTypes.typeFromFrenchName(getFirstType(secondType), "second Pokémon type");
                }
            }

            ArrayList<String> forms = new ArrayList<>();

            ArrayList<Region> regionalForms = new ArrayList<>();
            for(Region region : Region.values()) {
                if(content.contains(frenchName + " " + region.getFrAdjective())) {
                    regionalForms.add(region);
                }
            }

            boolean hasMega = content.contains(frenchName + " peut [[Méga-Évolution|méga-évoluer]]");
            boolean hasGigantamax = content.contains(frenchName + " Gigamax.png");

            System.out.println(frenchName + " successfully fetched");
            pokemonList.add(new Pokemon(i, frenchName, englishName, japaneseName, type1, type2, forms, regionalForms, hasMega, hasGigantamax));
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File("ressources/pokémonData.json"), pokemonList);
        } catch (IOException _) {
            System.err.println("Erreur à l'écriture du fichier");
        }
    }

    private static String getFirstType(String line) {
        if(!line.contains("/")) return line;
        return line.split("/")[0];
    }
}
