package tcgp;

import tcgp.card.Card;
import tcgp.enums.Expansion;
import utilitaire.Login;
import utilitaire.Page;
import utilitaire.Wiki;

import static tcgp.Dictionary.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CardTranslation {

    public static void main(String[] args)
    {
        if(args.length < 1)
        {
            System.err.println("Veuillez indiquer le nom du fichier texte à utiliser.\nVous pouvez falcutativement ajouter votre Login et MDP à la suite.");
            System.exit(1);
        }

        if(args.length == 3) {
            Login.login(args[1], args[2]);
        } else {
            Login.login();
        }

        loadDictionary();

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
                if((content == null) || content.isEmpty())
                {
                    System.err.println("Erreur : La page " + nomPage + " n'a pas pu être lue");
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
                System.out.println(nomPage + " convertie");
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.err.println("Erreur de lecture");
            System.exit(1);
        }

        Scanner confirm = new Scanner(System.in);
        System.out.println("Toutes les pages sont prêtes, appuyez sur \"Entrée\" pour lancer la publication.");
        confirm.nextLine();
        confirm.close();

        pokePages.forEach( (k, v) -> {
            System.out.println(k.getTitle());
            System.out.println(v);
            if ((k != null) && (k.getContent() != null)) {
                if(k.setContent(v, "Création automatique de la page à compléter"))
                {
                    System.out.println(k.getTitle() + " créé avec succès !");
                }
                else
                {
                    System.err.println("Échec de la création de " + k.getTitle());
                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
