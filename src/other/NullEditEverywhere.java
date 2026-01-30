package other;

import utilitaire.Login;
import utilitaire.Page;
import utilitaire.Wiki;

public class NullEditEverywhere {

    public static void main(String[] args) {
        if (args.length == 2) {
            Login.login(args[0], args[1]);
        } else {
            Login.login();
        }

        /*ArrayList<Page> pages = API.getPagesStartingWith("", API.NS_IMAGES, false, Wiki.POKEPEDIA);
        for (Page page : pages) {
            String content =  page.getContent();
            if(!page.setContent(content, "null edit")) System.err.println("You just got rate limited!");
            else System.out.println("Done");
        }*/

        Page test = new Page("Utilisateur:Sara368/Bonbon (Pokémon Sleep)", Wiki.POKEPEDIA);
        String content = test.getContent();
        test.setContent(null, "Test");
    }
}
