package tcgp;

import utilitaire.Page;
import utilitaire.Util;
import utilitaire.Wiki;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CardListMaker {
    public static void main(String[] args)
    {
        final String FILEPATH = "cardPages.txt";

        String pageName;

        if(args.length > 0) {
            pageName = args[0];
        } else {
            Scanner sc = new Scanner(System.in);
            System.out.println("Entrez le nom de l'extension dont vous voulez la liste");
            pageName = sc.nextLine();
        }

        Page page = new Page(pageName + " (TCG Pocket)", Wiki.BULBAPEDIA);
        String content = page.getContent();

        content = Util.searchValueOf(content, "== Card list ==\n", "\n|}", false);
        String[] lines = content.split("\n");

        ArrayList<String> links = new ArrayList<>();
        for(String line : lines) {
            if(line.startsWith("{{") || line.equals("|-")) {
                continue;
            }

            if(line.contains("Star|1")) {
                break;
            }

            String model = Util.searchValueOf(line, "|| ", " ||", false);
            links.add(cardModelToLink(model));
        }

        try {
            File file = new File(FILEPATH);
            if (file.createNewFile()) {
                System.out.println("File created as " + FILEPATH);
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));

            for(String link : links) {
                writer.write(link);
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(FILEPATH + " has been filled with " + links.size() + " entries from " + pageName);
    }

    private static String cardModelToLink(String model) {
        model = model.substring(9, model.length()-2);
        String[] elements = model.split("\\|");

        return elements[1] + " (" + elements[0] + " " + elements[2] + ")";
    }
}
