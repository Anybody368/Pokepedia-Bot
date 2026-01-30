package other;

import java.io.*;
import java.util.Scanner;

public class CapacitesPMD {
    public static void main(String[] args)
    {
        try {
            File fichier = new File("src/init.txt");
            Scanner lecteur = new Scanner(fichier);
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));

            while (lecteur.hasNextLine()) {
                String result = "";
                String ligne = lecteur.nextLine();
                String[] elements = ligne.split("\t");

                if(elements.length < 4) break;
                if(elements[2].equals("Obscur ")) continue;

                result += "|-\n| [[" + elements[1].substring(0, elements[1].length()-1) + "]]\n";
                result += "| {{Type|" + elements[2].substring(0, elements[2].length()-1) + "|PDMDX}}\n";

                result += switch (elements[3]) {
                    case "Capacité physique " -> "| {{type|physique}}\n";
                    case "Capacité spéciale " -> "| {{type|spécial}}\n";
                    case "Capacité de statut " -> "| {{type|statut}}\n";
                    default -> "| erreur\n";
                };

                result += "| \n| \n| \n| \n| \n";
                writer.write(result);
            }
            writer.close();
            lecteur.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
