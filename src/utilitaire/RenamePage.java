package utilitaire;

import java.util.Scanner;

public class RenamePage {
    private static final boolean KEEP_REDIRECT = false; //Aucun impact si l'utilisateur n'a pas de droits de suppression

    public static void main(String[] args)
    {
        if(args.length == 2) {
            Login.login(args[0], args[1]);
        } else {
            Login.login();
        }
        String from;

        while(true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Entrez le nom de la page à renommer, 'q' pour quitter");
            from = sc.nextLine();
            if(from.isBlank() || from.equals("q")) break;

            System.out.println("Entrez le nouveau nom de la page");
            String to = sc.nextLine();

            System.out.println("Entrez la raison du renommage");
            String reason = sc.nextLine();

            if(API.rename(from, to, KEEP_REDIRECT, reason)) {
                System.out.println("Renommage effectué avec succès");
            } else {
                System.err.println("Échec du renommage");
            }
        }
    }
}

