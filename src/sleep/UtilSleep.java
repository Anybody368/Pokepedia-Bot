package sleep;

import utilitaire.PokeData;
import utilitaire.Util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
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
}
