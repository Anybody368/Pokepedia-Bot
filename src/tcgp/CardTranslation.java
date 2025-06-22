package tcgp;

import tcgp.card.Card;
import tcgp.enums.Expansion;
import utilitaire.Login;
import utilitaire.Page;
import utilitaire.Wiki;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CardTranslation {

    public static void main(String[] args)
    {
        if(args.length != 1)
        {
            System.err.println("Veuillez indiquer le nom du fichier texte à utiliser.");
            System.exit(1);
        }
        Login.login("Anyboty");
        HashMap<Page, String> pokePages = new HashMap<>();

        try {
            File textFile = new File(args[0]);
            Scanner scan = new Scanner(textFile);
            while(scan.hasNextLine())
            {
                String nomPage = scan.nextLine();
                Page page = new Page(nomPage, Wiki.BULBAPEDIA);
                String content = page.getContent();
                //System.out.println(content);
                if(content.isEmpty())
                {
                    System.err.println("Erreur : La page "+nomPage+" n'a pas pu être lue");
                    continue;
                }

                Card carte = new Card(content);
                ArrayList<String> pagesContent = carte.getPokepediaCodes(nomPage);
                ArrayList<String> pagesNames = carte.getPagesNames();
                for (int i = 0; i < pagesNames.size(); i++) {
                    //System.out.println(pagesNames.get(i));
                    //System.out.println(pagesContent.get(i));

                    pokePages.put(new Page(pagesNames.get(i), Wiki.POKEPEDIA), pagesContent.get(i));
                    System.out.println(pagesNames.get(i) + " chargée");
                }
                System.out.println(nomPage + " Convertie");
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.err.println("Erreur de lecture");
            System.exit(1);
        }

        Scanner confirm = new Scanner(System.in);
        System.out.println("Toutes les pages sont prêtes, appuyez sur \"Entrer\" pour lancer la publication.");
        confirm.nextLine();
        confirm.close();

        pokePages.forEach( (k, v) -> {
            if(k.setContent(v, "Création automatique de la page à compléter"))
            {
                System.out.println(k.getTitle() + " créé avec succès !");
            }
            else
            {
                System.err.println("Echec de la création de " + k.getTitle());
            }
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
