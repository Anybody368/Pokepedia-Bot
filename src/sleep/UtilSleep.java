package sleep;

import sleep.dodos.SleepStyle;
import sleep.dodos.TypesDodo;
import sleep.pokemon.Competences;
import sleep.pokemon.SimplifiedPokemon;
import sleep.pokemon.Specialites;
import utilitaire.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UtilSleep {
    public static String ligneEtoiles(int nbrEtoiles)
    {
        return "|" + " [[Fichier:Miniature Étoile Sleep.png|20px]]".repeat(nbrEtoiles);
    }

    public static String getLastMonday() {
        LocalDate lastMonday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        String day = lastMonday.getDayOfMonth() == 1 ? "1er" : String.valueOf(lastMonday.getDayOfMonth());
        return "%s %s %d".formatted(day, lastMonday.getMonth().getDisplayName(TextStyle.FULL, Locale.FRENCH), lastMonday.getYear());
    }

    public static utilitaire.Pokemon getPokemonFromLine(String line)
    {
        String name;
        if (line.contains("forme(")) {
            name = Util.searchValueOf(line, "pokemon|", " forme(", false);
        } else {
            name = Util.searchValueOf(line, "pokemon|", " jeu(", false);
        }

        return PokeData.getPokemonFromName(name);
    }

    public static int getInsertionLine(ArrayList<String> lines, int start, int numDex) {
        String currentLine = lines.get(start);
        int l = start;
        int currentNumDex = currentLine.contains("{{#invoke:Ressources|p") ? getPokemonFromLine(currentLine).getNumDex() : 0;


        while (numDex >= currentNumDex) {
            l++;
            currentLine = lines.get(l);

            if (currentLine.contains("{{#invoke:Ressources|p")) {
                currentNumDex = getPokemonFromLine(currentLine).getNumDex();
            }

            if (currentLine.equals("|}")) return l;
        }
        return l-1;
    }

    public static String getMultiplierString(int percentile)
    {
        int base = 1 + percentile/100;
        int decimal = percentile%100;

        return base + "," + decimal;
    }

    public static List<SimplifiedPokemon> getAllPokemon() {
        List<SimplifiedPokemon> pokemonList = new ArrayList<>();
        String mainContent = new Page("Liste des Pokémon de soutien de Pokémon Sleep", Wiki.POKEPEDIA).getContent();
        String sleepContent = new Page("Liste des styles de dodo de Pokémon Sleep", Wiki.POKEPEDIA).getContent();

        int i = mainContent.indexOf("{{#invoke:Ressources|p");
        while (i >= 0) {
            String currentMon = Util.searchValueOf(mainContent, "Ressources|pokemon|", " jeu(", i, false);
            String name, form;
            if (currentMon.contains("forme(")) {
                name = Util.searchValueOf(currentMon, "", " forme(", false);
                form = Util.searchValueOf(currentMon, " forme(", ")", false);
            }
            else {
                name = currentMon;
                form = null;
            }

            PokeTypes type = PokeTypes.typeFromFrenchName(Util.searchValueOf(mainContent, "{{Type|", "|", i, false), "Pokemon type");

            String specialityString = Util.searchValueOf(mainContent, "|Sleep}}\n| ", i, false);
            Specialites speciality = Specialites.SpecialityFromName(specialityString);

            String skillString = Util.searchValueOf(mainContent, "| [[", "]]", i, false);
            Competences mainSkill = Competences.getFromName(skillString);

            String searchString;
            if (form != null) {
                searchString = "nom=%s|forme=%s|type=".formatted(name, form);
            } else {
                searchString = "nom=%s|type=".formatted(name);
            }
            String sleepString = Util.searchValueOf(sleepContent, searchString, "|", false);
            TypesDodo style = TypesDodo.searchByName(sleepString);

            pokemonList.add(new SimplifiedPokemon(name, form, type, speciality, style, mainSkill));

            i = mainContent.indexOf("{{#invoke:Ressources|p", i+30);
        }

        return pokemonList.stream().toList();
    }
}
