package other;

import utilitaire.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddGameToSideImagery {
    public static final ArrayList<String> gamesOrder = new ArrayList(List.of(
            "TCG",
            "TCG2",
            "Pin",
            "PinRS",
            "St1",
            "St2",
            "PBR",
            "PuzC",
            "PuzL",
            "Pokéwalker",
            "Colo",
            "XD",
            "Da",
            "L!",
            "LB",
            "Sh",
            "Sh-v1",
            "CM",
            "CM-v1",
            "PDMRB",
            "PDMTO",
            "PDMC",
            "PDMTOC",
            "PDMPI",
            "PMDM",
            "PDMDX",
            "Ra1",
            "Ra2",
            "Ra3",
            "MPR",
            "PR",
            "SPR",
            "PRU",
            "PRW",
            "PRR",
            "PPk1",
            "PPk2",
            "APCC",
            "Cq",
            "RAdar",
            "P3DP",
            "Pic",
            "GO",
            "GO-v1",
            "GO-v2",
            "MJ",
            "Pav",
            "DéPi",
            "DéPi2",
            "Q",
            "PM",
            "Smile",
            "NPSnap",
            "UNITE",
            "Sleep",
            "Friends"
    ));

    private static final String[] priorityImages = {
            "",
            " chromatique",
            " ♂",
            " ♂ chromatique",
            " ♀",
            " ♀ chromatique"
    };

    private static final String[] fileTypes = {
            "Sprite",
            "Miniature",
            "Pokédex",
            "Portrait",
            "Boss",
            "Galerie"
    };

    public static void main(String[] args) {
        Login.handleLogin(args);

        final String GAME = "Sleep";
        final String CATEGORY = "Sprite Pokémon de style de dodo (Sleep)";
        final String CATEGORY2 = "Sprite Pokémon de style de dodo chromatique (Sleep)";

        ArrayList<Page> pagesList = API.getPageFromCategory(CATEGORY, API.NS_IMAGES, Wiki.POKEPEDIA);
        pagesList.addAll(API.getPageFromCategory(CATEGORY2, API.NS_IMAGES, Wiki.POKEPEDIA));

        HashMap<Page, ArrayList<String>> imageryMap = new HashMap<>();
        HashMap<Page, String[]> involvedPokeData = new HashMap<>();
        for (String fileType : fileTypes)
        {
            for (Page page : pagesList) {
                String pageTitle = page.getTitle();
                if (!pageTitle.contains(GAME)) {
                    System.err.println("Fichier louche : " + page.getTitle());
                    continue;
                }

                if(!pageTitle.startsWith("Fichier:" + fileType)) continue;
                //if(page.isRedirect()) continue;

                String numDex = Util.searchValueOf(pageTitle, fileType + " ", " ", false);
                if(numDex.equals("0201")) continue;
                Pokemon currentPoke = PokeData.getPokemonFromNum(Integer.parseInt(numDex));

                Region region = null;
                String form = "";
                StringBuilder imageryTitle = new StringBuilder();

                if(pageTitle.contains(" Méga ")) {
                    form += "Méga";
                    pageTitle = pageTitle.replaceFirst(" Méga", "");
                    imageryTitle.append("Méga-");
                }

                imageryTitle.append(currentPoke.getFrenchName());

                for(Region r : currentPoke.getRegionalForms()) {
                    if(pageTitle.contains(r.getM_frName())) {
                        region = r;
                        pageTitle = pageTitle.replaceFirst(" " + r.getM_frName(), "");
                        imageryTitle.append(" ").append(r.getFrAdjective());
                        break;
                    }
                }

                imageryTitle.append("/Imagerie");

                for(String f : currentPoke.getForms()) {
                    if(pageTitle.contains(" " + f + " ")) {
                        form += f;
                        pageTitle = pageTitle.replaceFirst(" " + f, "");
                        imageryTitle.append("/").append(f);
                        break;
                    }
                }

                String[] test = {currentPoke.getFrenchName(), (region == null ? "" : region.getM_frName()), form};
                Page imageryPage = new Page(imageryTitle.toString(), Wiki.POKEPEDIA);
                imageryMap.computeIfAbsent(imageryPage, k -> new ArrayList<>());
                ArrayList<String> forms = imageryMap.get(imageryPage);

                involvedPokeData.computeIfAbsent(imageryPage, k -> test);

                String detailedForm = Util.searchValueOf(pageTitle, numDex, " " + GAME, false);
                boolean found = false;
                int prioIndex = 0;
                for(String priority : priorityImages){
                    if(!detailedForm.equals(priority)) {
                        prioIndex++;
                        continue;
                    }

                    for(; prioIndex < priorityImages.length; prioIndex++) {
                        int addPlace = forms.indexOf(fileType + priorityImages[prioIndex]);
                        if(addPlace != -1) {
                            forms.add(addPlace, fileType + detailedForm);
                            found = true;
                        }
                    }
                }
                if(!found) forms.add(fileType + detailedForm);
            }
        }

        imageryMap.forEach((page, newImages) -> {
            String[] data = involvedPokeData.get(page);
            System.out.println(data[0] + " " + data[1] + " " + data[2]);

            if(!page.doesPageExists() || page.isRedirect()) {
                System.err.println("L'imagerie " + page.getTitle() + " n'existe pas.");
                return;
            }

            String content = page.getContent();
            if(!content.contains("{{#invoke:Imagerie|secondaire")) {
                content = imageryUpdate(content, PokeData.getPokemonFromName(data[0]), data[1].isEmpty() ? data[2] : data[1] + " " + data[2]);
            }
            ArrayList<String> lines = new ArrayList<>(List.of(content.split("\n")));

            if(!content.contains(GAME + " //")) {
                int gameIndex = gamesOrder.indexOf(GAME);
                int l = lines.indexOf("=== Jeux secondaires ===") + 3;
                String line = lines.get(l);
                while (!line.equals("}}")) {
                    int currentGameIndex = gamesOrder.indexOf(Util.searchValueOf(line, "", " ", false));
                    if (currentGameIndex > gameIndex) {
                        break;
                    }

                    l++;
                    line = lines.get(l);
                }

                lines.add(l, getGameLine(GAME, newImages));
            }
            else {
                int l = -1;
                for (int i = 0; i < lines.size(); i++) {
                    if (lines.get(i).startsWith(GAME + " //")) {
                        l = i;
                        break;
                    }
                }

                String line = lines.get(l);
                //String newLine = updateGameLine(line, newImages);
                String newLine = updateGameLineMiddle(line, newImages, "Miniature");
                if(line.equals(newLine)) return;

                lines.set(l, newLine);
            }

            if(page.setContent(Util.wikicodeReconstruction(lines), "Ajout des images de " + GAME)) {
                System.out.println(page.getTitle() + " mise à jour avec succès");
            } else {
                System.err.println("Erreur durant la publication de " + page.getTitle());
            }
        });
    }

    private static String imageryUpdate(String content, Pokemon poke, String form) {
        content = content.replaceFirst("\n\\[\\[Catégorie:Imagerie Pokémon.*]]", "");
        if(content.contains("Jeux secondaires ==\n")) {
            System.err.println("Erreur de format, == Jeux secondaires ==");
            System.exit(-1);
        }

        String imagery = "{{#invoke:Imagerie|secondaire|" + poke.getPokepediaNumber();
        if(!form.isEmpty()) form += "|forme=" + form;
        form += "|\n}}";

        int delStart = content.indexOf("=== [[Pokémon Ranger (série)|Pokémon Ranger]] ===");
        if(delStart == -1) {
            delStart = content.indexOf("=== [[Pokémon Donjon Mystère (série)|Pokémon Donjon Mystère]] ===");
        }
        if(delStart == -1) {
            delStart = content.indexOf("=== Jeux secondaires ===");
        }

        int delEnd = content.indexOf("\n== Voir aussi ==");
        if(delEnd == -1) {
            delEnd = content.indexOf("\n\n{{Ruban Pokémon");
            imagery = imagery + "== Voir aussi ==\n\n* [[:Catégorie:Image Pokémon représentant " + poke.getFrenchName() + "]]\n";
        }
        if(delEnd == -1) {
            throw new ElementNotFoundException("{{Ruban Pokémon", "old section to delete");
        }
        if(delStart == -1) {
            delStart = delEnd;
            imagery = "\n" + imagery;
        }

        int categoryInsert = content.indexOf("}}", delEnd) +2;
        if(categoryInsert == 1) {
            throw new ElementNotFoundException("}}", "place to insert category");
        }

        content = content.substring(0, delStart) + imagery + content.substring(delEnd + 1, categoryInsert) + "\n\n[[Catégorie:Imagerie Pokémon|" + poke.getFrenchName() + "]]\n" + content.substring(categoryInsert);

        content = content.replaceAll("\n\n\n", "\n\n");
        content = content.replaceAll("\n\n\n", "\n\n");

        return content;
    }

    private static String getGameLine(String game, ArrayList<String> forms) {
        StringBuilder sb = new StringBuilder(game).append(" /");
        for(String form : forms) {
            sb.append("/ ").append(form).append(" ");
        }
        return sb.substring(0, sb.length() - 1);
    }

    private static String updateGameLine(String line, ArrayList<String> forms) {
        StringBuilder sb = new StringBuilder(line);
        for(String form : forms) {
            if(line.contains("/ " + form + " /") || line.endsWith("/ " + form)) continue;

            sb.append(" / ").append(form);
        }

        return sb.toString();
    }

    private static String updateGameLineMiddle(String line, ArrayList<String> forms, String before) {
        int index = line.indexOf("/ " + before);
        if(index == -1) {
            System.out.println(before + " pas trouve dans la ligne, ajout a la fin");
            return updateGameLine(line, forms);
        }

        StringBuilder sb = new StringBuilder(line.substring(0, index));
        for(String form : forms) {
            if(line.contains("/ " + form + " /") || line.endsWith("/ " + form)) continue;

            if(form.contains(" chromatique") && sb.indexOf(form.replace(" chromatique", "")) != -1) {
                sb.insert(sb.indexOf(form.replace(" chromatique", "")) + form.replace(" chromatique", "").length(), " / " + form);
            } else {
                sb.append("/ ").append(form).append(" ");
            }
        }
        sb.append(line.substring(index));
        return sb.toString();
    }
}