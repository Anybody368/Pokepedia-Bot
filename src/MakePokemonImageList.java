import utilitaire.API;
import utilitaire.Login;
import utilitaire.Page;
import utilitaire.Wiki;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MakePokemonImageList {
    private static final String[] fileTypes = {
            "Sprite",
            "Miniature",
            "Pokédex"
    };
    private static final ArrayList bannedGames = new ArrayList(List.of(
            "R",
            "B",
            "RB",
            "RV",
            "J",
            "RBJ",
            "A",
            "O",
            "OA",
            "C",
            "E",
            "RS",
            "RFVF",
            "DP",
            "Pt",
            "HGSS",
            "NB",
            "XY",
            "ROSA",
            "SL",
            "USUL",
            "EB",
            "DEPS",
            "EV",
            "EVmt",
            "LGPE",
            "LPA",
            "LPZA",
            "HOME",
            "HOME-v1",
            "PDMRB",
            "PDMTO",
            "PMDM",
            "PDMPI",
            "PDMDX",
            "Ra1",
            "Ra2",
            "Ra3",
            "PNPV1",
            "SMM",

            "Cosplayeur",
            "Rockeur",
            "Lady",
            "Star",
            "Docteur",
            "Catcheur"
    ));

    public static void main(String[] args)
    {
        if(args.length == 2) {
            Login.login(args[0], args[1]);
        } else {
            Login.login();
        }

        HashMap<String, ArrayList<Page>> imagesByGame = new HashMap<>();

        for(String type : fileTypes) {
            ArrayList<Page> pagesList = API.getPagesStartingWith(type + " 0025", API.NS_IMAGES, false, Wiki.POKEPEDIA);
            System.out.println("Found " + pagesList.size() + " pages.");

            for(Page page : pagesList) {
                String words[] = page.getTitle().split(" ");
                String game = words[words.length-1].replaceFirst("\\.[^.]+$", "");

                if(bannedGames.contains(game)) {continue;}

                imagesByGame.computeIfAbsent(game, k -> new ArrayList<>());
                imagesByGame.get(game).add(page);
            }
        }

        ArrayList<String> gameNames = new ArrayList<>();
        imagesByGame.forEach((game, images) -> {
            gameNames.add(game);
        });
        gameNames.sort(String.CASE_INSENSITIVE_ORDER);
        System.out.println(gameNames);
    }
}
