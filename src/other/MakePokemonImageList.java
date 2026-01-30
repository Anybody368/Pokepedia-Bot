package other;

import utilitaire.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MakePokemonImageList {
    private static final String[] fileTypes = {
            "Galerie",
            "Boss",
            "Portrait",
            "Pokédex",
            "Miniature",
            "Sprite"
    };

    private static final ArrayList<String> gamesOrder = new ArrayList(List.of(
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

    private static boolean isXDMerged;
    private static boolean isPRWMerged;
    private static boolean isRa2Merged;
    private static boolean isRa3Merged;

    public static void main(String[] args)
    {
        if(args.length == 2) {
            Login.login(args[0], args[1]);
        } else {
            Login.login();
        }

        specialFomsUpdate(421, 421);
        //RegularUpdate(711,711);
    }

    private static void RegularUpdate(int firstNumDex, int lastNumDex) {
        for(int num = firstNumDex; num <= lastNumDex; num++) {

            isXDMerged = false;
            isPRWMerged = false;
            isRa2Merged = false;
            isRa3Merged = false;

            Pokemon currentPoke = PokeData.getPokemonFromNum(num);
            ArrayList<Region> regionalForms = currentPoke.getRegionalForms();
            HashMap<Region, HashMap<String, ArrayList<Page>>> imagesByPage = new HashMap<>();
            for(Region region : regionalForms) {
                imagesByPage.put(region, new HashMap<>());
            }

            HashMap<String, ArrayList<Page>> imagesByGame = new HashMap<>();

            for(String type : fileTypes) {
                ArrayList<Page> pagesList = API.getPagesStartingWith(type + " " + currentPoke.getPokepediaNumber(), API.NS_IMAGES, true, Wiki.POKEPEDIA);
                System.out.println("Found " + pagesList.size() + " pages.");

                ArrayList<Page> priorityPages = new ArrayList<>();

                for(String prio :  priorityImages) {
                    String regex = "Fichier:" + type + " " + currentPoke.getPokepediaNumber() + prio + " " + "[^ ]*\\..*";
                    //System.out.println(pagesList.getFirst().getTitle());
                    //System.out.println(regex);
                    priorityPages.addAll(pagesList.stream()
                            .filter(p -> p.getTitle().matches(regex))
                            .toList()
                    );
                }

                pagesList.removeAll(priorityPages);

                for(Page page : pagesList) {
                    if (page.getTitle().contains("v2") && !page.getTitle().endsWith("HOME-v2.png") && !page.getTitle().endsWith("GO-v2.png")) {
                        System.err.println("Found " + page.getTitle());
                        System.err.println("V2 pas prévue on stoppe tout !");
                        System.exit(1);
                    }
                    boolean regionFound = false;
                    for(Region region : regionalForms) {
                        if(page.getTitle().contains(region.getM_frName())) {
                            addImage(imagesByPage.get(region), page);
                            regionFound = true;
                        }
                    }
                    if(!regionFound) {
                        addImage(imagesByGame, page);
                    }
                }

                for(int i = priorityPages.size() - 1; i >= 0; i--) {
                    //System.out.println("Found priority page " + page.getTitle());
                    addImage(imagesByGame, priorityPages.get(i));
                }
            }

            ArrayList<Page> tcgCards = API.getPagesStartingWith("TCG1 ", API.NS_IMAGES, true, Wiki.POKEPEDIA);
            tcgCards.removeIf(page -> !page.getTitle().contains(currentPoke.getFrenchName()));
            if(!tcgCards.isEmpty()) {
                System.out.println("Found " + tcgCards.size() + " tcg cards.");
                imagesByGame.put("TCG", tcgCards);
            }
            ArrayList<Page> tcg2Cards = API.getPagesStartingWith("TCG2 ", API.NS_IMAGES, true, Wiki.POKEPEDIA);
            tcg2Cards.removeIf(page -> !page.getTitle().contains(currentPoke.getFrenchName()));
            if(!tcg2Cards.isEmpty()) {
                System.out.println("Found " + tcg2Cards.size() + " tcg2 cards.");
                imagesByGame.put("TCG2", tcg2Cards);
            }

            System.out.println(getImageryCode(imagesByGame, currentPoke, " "));
            //publishImagery(new Page(currentPoke.getFrenchName() + "/Imagerie", Wiki.POKEPEDIA), getImageryCode(imagesByGame, currentPoke, null), currentPoke);

            for(Region region : regionalForms) {
                if(imagesByPage.containsKey(region)) {
                    publishImagery(new Page(currentPoke.getFrenchName() + " " + region.getFrAdjective() + "/Imagerie", Wiki.POKEPEDIA), getImageryCode(imagesByPage.get(region), currentPoke, region.getM_frName()), currentPoke);
                }
            }
        }
    }

    private static void specialFomsUpdate(int FirstNumDex, int LastNumDex) {
        for(int num = FirstNumDex; num <= LastNumDex; num++) {

            isXDMerged = false;
            isPRWMerged = false;
            isRa2Merged = false;
            isRa3Merged = false;

            Pokemon currentPoke = PokeData.getPokemonFromNum(num);
            ArrayList<String> forms = currentPoke.getForms();
            if(forms.isEmpty()) {continue;}

            System.out.println("Currently: " + currentPoke.getFrenchName());
            HashMap<String, HashMap<String, ArrayList<Page>>> imagesByPage = new HashMap<>();
            for(String form : forms) {
                imagesByPage.put(form, new HashMap<>());
            }

            HashMap<String, ArrayList<Page>> extraPages = new HashMap<>();

            for(String type : fileTypes) {
                ArrayList<Page> pagesList = API.getPagesStartingWith(type + " " + currentPoke.getPokepediaNumber(), API.NS_IMAGES, true, Wiki.POKEPEDIA);
                //System.out.println("Found " + pagesList.size() + " pages.");

                HashMap<String, ArrayList<Page>> priorityPages = new HashMap<>();

                for(String form : forms) {
                    ArrayList<Page> pP = new ArrayList<>();
                    for(String prio : priorityImages) {
                        String regex = "Fichier:" + type + " " + currentPoke.getPokepediaNumber() + " " + form + prio + " " + "[^ ]*\\..*";
                        //System.out.println(pagesList.getFirst().getTitle());
                        //System.out.println(regex);
                        pP.addAll(pagesList.stream()
                                .filter(p -> p.getTitle().matches(regex))
                                .toList()
                        );
                    }
                    priorityPages.put(form, pP);
                    pagesList.removeAll(pP);
                }


                for(Page page : pagesList) {
                    if (page.getTitle().contains("v2") && !page.getTitle().endsWith("HOME-v2.png") && !page.getTitle().endsWith("GO-v2.png")) {
                        System.err.println("Found " + page.getTitle());
                        System.err.println("V2 pas prévue on stoppe tout !");
                        System.exit(1);
                    }
                    boolean formFound = false;
                    for(String form : forms) {
                        if(page.getTitle().contains(form)) {
                            addImage(imagesByPage.get(form), page);
                            formFound = true;
                        }
                    }
                    if(!formFound && !page.isRedirect()) {
                        addImage(extraPages, page);
                    }
                }

                for(String form : forms) {
                    for(int i = priorityPages.get(form).size() - 1; i >= 0; i--) {
                        //System.out.println("Found priority page " + page.getTitle());
                        addImage(imagesByPage.get(form), priorityPages.get(form).get(i));
                    }
                }
            }

            System.err.println(currentPoke.getFrenchName() + " : " + extraPages);
            for(String form : forms) {
                if(imagesByPage.containsKey(form)) {
                    //System.out.println(getImageryCode(imagesByPage.get(form), currentPoke, form));
                    publishImagery(new Page(currentPoke.getFrenchName() + "/Imagerie/" + form, Wiki.POKEPEDIA), getImageryCode(imagesByPage.get(form), currentPoke, form), currentPoke);
                }
            }
        }
    }

    private static void addImage(HashMap<String, ArrayList<Page>> imageMap, Page page)
    {
        String[] words = page.getTitle().split(" ");
        String game = words[words.length-1].replaceFirst("\\.[^.]+$", "");

        if(!gamesOrder.contains(game)) {return;}

        if(game.equals("XD") && page.isRedirect()) {
            isXDMerged = true;
            return;
        }
        if(game.equals("PRW") && page.isRedirect()) {
            isPRWMerged = true;
            return;
        }
        if(game.equals("Ra3") && page.isRedirect()) {
            isRa3Merged = true;
            return;
        }
        if(game.equals("Ra2") && page.isRedirect()) {
            isRa2Merged = true;
            return;
        }
        if(game.equals("PMDM") && page.isRedirect()) {
            return;
        }
        if(game.equals("PM") && page.isRedirect()) {
            return;
        }
        if(game.equals("Sh") && page.isRedirect()) {
            return;
        }
        if(game.equals("PDMTO") && page.isRedirect()) {
            return;
        }
        if(game.equals("Ra2") && page.isRedirect()) System.err.println("Redirection pas prévue");
        if(game.equals("PDMC") && page.isRedirect()) System.err.println("Redirection pas prévue");
        if(game.equals("PPk2") && page.isRedirect()) System.err.println("Redirection pas prévue");

        imageMap.computeIfAbsent(game, k -> new ArrayList<>());
        imageMap.get(game).add(page);
    }

    private static String getImageryCode(HashMap<String, ArrayList<Page>> imageMap, Pokemon poke, String pokeForm) {
        if(imageMap.isEmpty()) return "";

        StringBuilder imagery = new StringBuilder("=== Jeux secondaires ===");

        if(poke.isHasMega() || poke.HasGigantamax()) {
            imagery.append("\n{{Édité par robot}}");
        }

        imagery.append("\n\n{{#invoke:Imagerie|secondaire|").append(poke.getPokepediaNumber());

        if(!pokeForm.isEmpty()) {
            imagery.append("|forme=").append(pokeForm);
        }
        imagery.append("|\n");

        for(String game : gamesOrder) {
            if(!imageMap.containsKey(game)) {continue;}

            imagery.append(game);

            if(game.equals("Colo") && isXDMerged) {
                imagery.append(" XD");
            }
            if(game.equals("SPR") && isPRWMerged) {
                imagery.append(" PRW");
            }
            if(game.equals("Ra1") && isRa2Merged) {
                imagery.append(" Ra2");
            }
            if(imageMap.containsKey("Ra1") && game.equals("Ra1") && isRa3Merged) {
                imagery.append(" Ra3");
            } else if (imageMap.containsKey("Ra2") && game.equals("Ra2") && isRa3Merged) {
                imagery.append(" Ra3");
            }

            imagery.append(" /");

            if(game.equals("TCG") || game.equals("TCG2")) {
                for(Page page : imageMap.get(game)) {
                    String card = Util.searchValueOf(page.getTitle(), "TCG", " " + poke.getFrenchName(), false).substring(2);
                    String form = Util.searchValueOf(page.getTitle(), poke.getFrenchName() + " ", ".", true);

                    if(form == null) {form = "";} else {form += " ";}

                    imagery.append("/ ").append(card).append(" ").append(form);
                }
                imagery.setLength(imagery.length() - 1);
                imagery.append("\n");
                continue;
            }

            boolean invertedOrder = false;
            for(int i = imageMap.get(game).size() - 1; i >= 0; i--) {
                Page page = imageMap.get(game).get(i);
                if(page.getTitle().contains(poke.getPokepediaNumber() + "M") || page.getTitle().contains(poke.getPokepediaNumber() + "a")) {continue;}

                if(page.getTitle().contains("chromatique") && i > 0) {
                    if(page.getTitle().replaceFirst("chromatique ", "").equals(imageMap.get(game).get(i - 1).getTitle())) {
                        page = imageMap.get(game).get(i - 1);
                        invertedOrder = true;
                    }
                } else if (invertedOrder) {
                    page = imageMap.get(game).get(i + 1);
                    invertedOrder = false;
                }

                String type = Util.searchValueOf(page.getTitle(), ":", " ", false);
                //System.out.println(page.getTitle());
                String form = Util.searchValueOf(page.getTitle(), poke.getPokepediaNumber() + " ", game, false);
                String extension = page.getTitle().endsWith("gif") ? "gif " : "";

                imagery.append("/ ").append(type).append(" ");
                if(!form.equals(" ")) {
                    if(!form.isEmpty()) {
                        form = form.replaceFirst(pokeForm + " ", "");
                    }
                    imagery.append(form);
                }
                imagery.append(extension);
            }
            imagery.setLength(imagery.length() - 1);
            imagery.append("\n");
        }
        //System.out.println(imagery.toString());
        return imagery.append("}}\n\n").toString();
    }

    private static void publishImagery(Page page, String imagery,Pokemon poke)
    {
        if(!page.doesPageExists()) {
            System.err.println("Page " + page.getTitle() + " doesn't exist");
            return;
        }

        String content = page.getContent();
        content = content.replaceFirst("\n\\[\\[Catégorie:Imagerie Pokémon.*]]", "");

        if(content.isEmpty() || content.contains("{{#invoke:Imagerie|secondaire")) return;
        if(content.contains("Jeux secondaires ==\n")) {
            System.err.println("Erreur de format, == Jeux secondaires ==");
            System.exit(-1);
        }

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

        //System.out.println(content);
        if(page.setContent(content, "Mise à jour de l'imagerie pour utiliser le module autres jeux")) {
            System.out.println(page.getTitle() + " mise à jour avec succès");
        } else {
            System.err.println("Erreur durant la publication de " + page.getTitle());
        }
    }
}
