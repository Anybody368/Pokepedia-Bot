package other;

import utilitaire.*;

import java.util.ArrayList;

public class SideGameReplacements {
    static void main(String[] args) {
        Login.handleLogin(args);

        ArrayList<Page> pages = API.getPageFromCategory("Page de jeux secondaires", API.NS_MAIN, Wiki.POKEPEDIA);
        ArrayList<PageToPublish> editedPages = new ArrayList<>();

        boolean temp = false;
        for (Page page : pages) {
            if(!temp) {
                temp = page.getTitle().contains("Farfuret");
                continue;
            }

            System.out.println("Handling " + page.getTitle());
            String content = page.getContent();

            if(!content.contains("== Pokémon Sleep ==")) continue;

            content = content.replace("25px", "30px").replace("20px", "25px")
                    .replace("/Jeux secondaires|", "/Pokémon Sleep|").replace("/Pokémon Sleep|Tableau d'", "/Jeux secondaires|Tableau d'");

            editedPages.add(new PageToPublish(page, content, "Forme et liens", true));
        }

        Util.publishMultipleEdits(editedPages);
    }
}
