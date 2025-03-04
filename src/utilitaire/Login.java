package utilitaire;

import java.util.Scanner;

public class Login {
	public static void login(String username) {
		API.login(username,  askPassword(username));
	}
	
	private static String askPassword(String username) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Entrez le mot de passe pour " + username);
        return sc.nextLine();
	}
}
