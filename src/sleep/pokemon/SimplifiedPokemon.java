package sleep.pokemon;

import org.jetbrains.annotations.NotNull;
import sleep.dodos.Island;
import sleep.dodos.SleepRank;
import sleep.dodos.SleepStyle;
import sleep.dodos.TypesDodo;
import utilitaire.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public record SimplifiedPokemon(String name, String forme, PokeTypes type, Specialites speciality, TypesDodo sleep, Competences mainSkill) {
    public String getOldWikiCode() {
        String sleepStr = sleep.getNom();
        return """
                | style="text-align:left;" | {{#invoke:Ressources|pokemon|%s jeu(Sleep)}}
                | {{Type|%s|Sleep}}
                | %s
                | class="%s" | [[Fichier:Icône Type %s Sleep.png|50px]] %s""".formatted(getId(), type.getFrenchName(),
                speciality.getNom(), sleepStr.toLowerCase(), sleepStr.toLowerCase(), sleepStr);
    }

    public String getNewWikiCode() {
        return """
                | style="text-align:left;" | {{#invoke:Ressources|pokemon|%s jeu(Sleep)}}
                | {{Type|%s|Sleep}}
                | %s
                | [[%s]]""".formatted(getId(), type.getFrenchName(),
                speciality.getNom(), mainSkill.getName());
    }

    public String getSleepData() {
        List<SleepStyle> sleepStyles = getSleepStyles();
        String sleepStr = sleep.getNom();

        StringBuilder sleepData = new StringBuilder("""
                | rowspan="%d" | {{#invoke:Ressources|pokemon|%s jeu(Sleep)}}
                | rowspan="%d" class="%s" | [[Fichier:Icône Type %s Sleep.png|50px]] %s""".formatted(sleepStyles.size(),
                getId(), sleepStyles.size(), sleepStr.toLowerCase(), sleepStr.toLowerCase(), sleepStr));

        for (int i = 1; i <= sleepStyles.size(); i++) {
            SleepStyle sleepStyle = sleepStyles.get(i-1);
            sleepData.append("\n| ").repeat("[[Fichier:Miniature Étoile Sleep.png|20px]] ", i);
            sleepData.replace(sleepData.length() - 1, sleepData.length(), "\n");
            sleepData.append("| ").append(sleepStyle.getLocationsText()).append("\n|-");
        }

        return sleepData.toString();
    }

    private String getId() {
        return name + (forme != null ? " forme("  + forme + ")" : "");
    }

    public String getFullName() {
        if (forme == null) return name;

        Region region = getRegion();
        if (region != null) return name + " " + region.getFrAdjective();

        return name + " forme " + forme;
    }

    private Region getRegion() {
        if (forme == null) return null;

        return Region.findRegionalFromFr(forme);
    }

    private List<SleepStyle> getSleepStyles() {
        String usefulName = getFullName().contains(" forme ") ? name : getFullName();
        String content = new Page(usefulName + "/Jeux secondaires", Wiki.POKEPEDIA).getContent();
        List<String> lines = List.of(content.split("\n"));

        int tablePosition = content.indexOf(" | Styles de dodos")-5;
        int sleepCount = Integer.parseInt(Util.searchValueOf(content, "=\"", "\"", tablePosition, false)) -1;
        int initialLine = lines.indexOf("| [[Fichier:Miniature Étoile Sleep.png|25px]] [[Fichier:Miniature Étoile Sleep.png|25px]]");
        lines = lines.subList(initialLine, lines.size());

        ArrayList<SleepStyle> sleepStyles = new ArrayList<>();
        for (int i = 1; i <= sleepCount; i++) {
            String name = Util.searchValueOf(lines.get(lines.indexOf("! Nom") + i), "Dodo ", false);
            int rarity = Math.min(i, 4);
            HashMap<Island, SleepRank> locations = new HashMap<>();

            for (Island island : Island.values()) {
                initialLine = lines.indexOf("! [[%s]]".formatted(island.getName(true)));
                if (initialLine == -1) continue;
                String line = lines.get(initialLine + i);
                if (line.equals("| —")) continue;

                String rank = Util.searchValueOf(line, "30px]] ", false);
                locations.put(island, new SleepRank(rank));
            }

            sleepStyles.add(new SleepStyle(name, rarity, -1, -1, -1, locations));
        }
        return sleepStyles.stream().toList();
    }

    @Override
    public @NotNull String toString() {
        return getFullName();
    }
}
