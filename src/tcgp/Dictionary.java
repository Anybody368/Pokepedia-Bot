package tcgp;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public class Dictionary {
    private static final String FILE_PATH = "ressources"+File.separator+"tcgpDico.properties";
    private static final Properties props = new Properties();

    public static void loadDictionary() {
        try (FileInputStream in = new FileInputStream(FILE_PATH)) {
            props.load(in);
            System.out.println("Dictionnaire chargé avec " + props.size() + " entrées.");
        } catch (FileNotFoundException e) {
            System.err.println("Fichier dictionnaire non trouvé, un nouveau sera créé.");
        } catch (IOException e) {
            System.err.println("Erreur dans le chargement du dictionaire :");
            e.printStackTrace();
        }
    }

    public static String getTranslation(String english) {
        if(props.containsKey(english)) {
            return props.getProperty(english);
        }

        // Si jamais le mot n'existe pas encore dans le dictionnaire
        return (addTranslation(english));
    }

    private static String addTranslation(String english) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Entrez la traduction française de " + english + " : ");
        String french = sc.nextLine();

        props.setProperty(english, french);
        try (FileOutputStream out = new FileOutputStream(FILE_PATH)) {
            props.store(out, "Dictionnaire EN-FR mis à jour");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return french;
    }
}
