package other;

import sleep.bouffe.IngredientPoke;
import sleep.bouffe.ListeIngredients;
import sleep.dodos.SleepStyle;
import sleep.dodos.Island;
import sleep.dodos.SleepRank;
import sleep.dodos.TypesDodo;
import sleep.pokemon.*;
import sleep.pokemon.Pokemon;
import utilitaire.*;

import java.util.ArrayList;
import java.util.HashMap;

public class MakeSleepPokemonPage {
    private static final String ListeSoutienText = new Page("Liste des Pokémon de soutien de Pokémon Sleep", Wiki.POKEPEDIA).getContent();
    private static final String ListeDodosText = new Page("Liste des styles de dodo de Pokémon Sleep", Wiki.POKEPEDIA).getContent();
    private static final String IngredientsText = new Page("Ingrédient (Pokémon Sleep)", Wiki.POKEPEDIA).getContent();
    private static final String EvolutionsText = new Page("Liste des Pokémon par famille d'évolution dans Pokémon Sleep", Wiki.POKEPEDIA).getContent();

    static void main(String[] args){
        if(args.length == 2) {
            Login.login(args[0], args[1]);
        } else {
            Login.login();
        }

        String[] AllPokemon = ListeDodosText.substring(ListeDodosText.indexOf("{{Ligne"), ListeDodosText.indexOf("\n|}")).split("}}\n\n\\{\\{");
        ArrayList<Pokemon> pokemonList = new ArrayList<>();
        System.out.println("found " + AllPokemon.length + " Pokémon");
        for (String sleepData : AllPokemon) {
            String name = Util.searchValueOf(sleepData, "nom=", "|", false);
            if (name.contains("(")) continue;

            boolean isRegionalForm = (name.contains("{{!}}"));
            Region region;
            String generalData;
            if (isRegionalForm) {
                region = Region.findRegionalFromFr(name.split(" ")[name.split(" ").length - 1]);
                name = name.substring(0, name.indexOf("{{!}}"));
                generalData = Util.searchValueOf(ListeSoutienText, name + " " + region.getFrAdjective() + "]]", "|-", false);
                //System.out.println(name + " " + region.getFrAdjective());
            } else {
                region = null;
                generalData = Util.searchValueOf(ListeSoutienText, name + "]]", "|-", false);
                //System.out.println(name);
            }
            String[] specificData = generalData.replace("| rowspan=\"4\" ", "").replace("\n| ", "\n").split("\n");

            int numDex = Integer.parseInt(Util.searchValueOf(sleepData, "dex=", "|", false));

            PokeTypes type = PokeTypes.typeFromFrenchName(Util.searchValueOf(generalData, "{{Type|", "|", false), "Finding Pokémon type");
            TypesDodo sleepStyle = switch (Util.searchValueOf(sleepData, "type=", "|", false)) {
                case "ptidodo" -> TypesDodo.PTIDODO;
                case "bondodo" -> TypesDodo.BONDODO;
                case "grododo" -> TypesDodo.GRODODO;
                default -> null;
            };

            Specialites speciality;
            if (generalData.contains("| Baies\n")) { speciality = Specialites.BAIES; }
            else if (generalData.contains("| Compétences\n")) { speciality = Specialites.COMPETENCES; }
            else if (generalData.contains("| Ingrédients\n")) { speciality = Specialites.INGREDIENTS; }

            else speciality = Specialites.TOUTES;
            String frequency = formatDuration(specificData[5]);
            int storage = Integer.parseInt(specificData[6]);
            Competences ability = Competences.getFromName(Util.searchValueOf(specificData[7], "[[", "]]", false).replace("|Métronome", ""));
            int recruitPoints = Integer.parseInt(specificData[8]);

            String candy = Util.searchValueOf(sleepData, "nombonbon=", "|", false);
            ArrayList<Island> islands = getPokemonIslands(sleepData);
            ArrayList<SleepStyle> sleeps = getPokemonSleps(sleepData, islands);
            String nameForIng = isRegionalForm ? candy + " " + region.getFrAdjective() : candy;
            if (name.equals("Terraiste")) nameForIng = "Axoloto de Paldea";
            ArrayList<IngredientPoke> ingredients = getPokemonIngredients(nameForIng, specificData[4]);

            Imagery gendering = EvolutionsText.contains("Sprite " + Util.numberToPokepediaDexFormat(numDex) + " ♂") ? Imagery.SEXUAL_DIMORPHISM : Imagery.AGENDER;

            if (isRegionalForm) {
                pokemonList.add(new PokemonRegional(name, region, numDex, type, sleepStyle, speciality, ingredients, sleeps, islands, frequency, storage, ability, recruitPoints, candy, gendering));

            } else {
                pokemonList.add(new Pokemon(name, numDex, type, sleepStyle, speciality, ingredients, sleeps, islands, frequency, storage, ability, recruitPoints, candy, gendering));
                //System.out.println(pokemonList.getLast().makePokemonPage());
            }
        }

        System.out.println("found " + pokemonList.size() + " Pokemon");
        System.out.println();
        HashMap<Page, String> wikiPages = new HashMap<>();
        for (Pokemon pokemon : pokemonList) {
            System.out.println("Loading " + pokemon.getRegionalName());
            Page page = new Page(pokemon.getRegionalName() + "/Jeux secondaires", Wiki.POKEPEDIA);
            String newContent = page.getContent().replace("style=\"text-align:center\"", "class=\"entêtesection\"")
                    .replace("! [[Type]]", "! width=\"30%\" | [[Type]]")
                    .replace("! Nombre de st", "! width=\"30%\" | Nombre de st");
            wikiPages.put(page, newContent);
        }

        //System.out.println(pokemonList.get(3).makePokemonPage());
        Util.publishMultipleEdits(wikiPages, "Mise en forme", true);
    }

    private static String formatDuration(String input) {
        int hours = 0;
        int minutes = 0;
        int seconds = 0;

        String[] parts = input.split("<br>");

        for (String part : parts) {
            part = part.trim().toLowerCase();

            if (part.contains("heure")) {
                hours = extractNumber(part);
            } else if (part.contains("minute")) {
                minutes = extractNumber(part);
            } else if (part.contains("seconde")) {
                seconds = extractNumber(part);
            }
        }

        return String.format("%d:%02d:%02d", hours, minutes, seconds);
    }

    private static int extractNumber(String text) {
        return Integer.parseInt(text.replaceAll("[^0-9]", ""));
    }

    private static ArrayList<Island> getPokemonIslands(String sleepData) {
        ArrayList<Island> islands = new ArrayList<>();
        for (Island i : Island.values()) {
            if (sleepData.contains(i.getName(true) + "]]")) islands.add(i);
        }
        return islands;
    }

    private static ArrayList<SleepStyle> getPokemonSleps(String sleepData, ArrayList<Island> islands) {
        int nbrSleeps = (Util.searchValueOf(sleepData, "dodo=", "}}", true) != null ? Integer.parseInt(Util.searchValueOf(sleepData, "dodo=", "}}", false)) : 4);
        ArrayList<SleepStyle> sleeps = new ArrayList<>(nbrSleeps);

        for (int i = 1; i <= nbrSleeps; i++) {
            HashMap<Island, SleepRank> sleepLocations = new HashMap<>(islands.size());
            String[] locations = Util.searchValueOf(sleepData, "lieu" + i + "=", "|re", false).split("<br>");
            for (Island island : islands) {
                int l = -1;
                boolean exists = false;
                for(int j = 0; j < locations.length; j++) {
                    if (locations[j].contains(island.getName(true) + "]")) {
                        exists = true;
                        l = j;
                        break;
                    }
                }
                if (!exists) {
                    continue;
                }
                String rank = Util.searchValueOf(locations[l], "Rang ", " ", false) + " " + Integer.parseInt(Util.searchValueOf(locations[l], "px]]", ")", false));
                //System.out.print(island + " " + rank + "; ");
                sleepLocations.put(island, new SleepRank(rank));
            }
            //System.out.println();

            String sleepName = Util.searchValueOf(sleepData, "dodo" + i + "=", "|", false);
            int exp = Integer.parseInt(Util.searchValueOf(sleepData, "recherche" + i + "=", "|", false));
            int shards = Integer.parseInt(Util.searchValueOf(sleepData, "fragment" + i + "=", "|", false));
            int candies = extractNumber(Util.searchValueOf(sleepData, "bonbon" + i + "=", "|", false));
            int rarity = Math.min(i, 4);

            sleeps.add(new SleepStyle(sleepName, rarity, exp, shards, candies, sleepLocations));
        }
        return sleeps;
    }

    private static ArrayList<IngredientPoke> getPokemonIngredients(String pokeName, String ingredientList) {
        ArrayList<IngredientPoke> ingredients = new ArrayList<>();
        String[] ingredientsNames = ingredientList.split("<br>");

        for (String ingredientName : ingredientsNames) {
            ListeIngredients ingredient = ListeIngredients.getFromName(Util.searchValueOf(ingredientName, "Sprite ", " Sleep", false));
            String ingSection = Util.searchValueOf(MakeSleepPokemonPage.IngredientsText, ingredient.getNom() + "'''\n", "\n|}", false);
            ingSection = Util.searchValueOf(ingSection, pokeName + "]]\n", "\n|-", false);
            String[] lines = ingSection.split("\n");

            int lvl0 = lines[0].equals("| —") ? 0 : extractNumber(lines[0]);
            int lvl30 = lines[1].equals("| —") ? 0 : extractNumber(lines[1]);
            int lvl60 = extractNumber(lines[2]);

            ingredients.add(new IngredientPoke(ingredient, lvl0, lvl30, lvl60));
        }
        return ingredients;
    }
}
