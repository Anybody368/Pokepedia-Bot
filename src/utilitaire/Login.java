package utilitaire;

import java.util.Scanner;

public class Login {

	public static void  login(String login, String password) {
		API.login(login, password);
	}

	public static void login(String login) {
		API.login(login,  askPassword(login));
	}

	public static void login() {
		login(askLogin());
	}
	
	private static String askPassword(String username) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Entrez le mot de passe pour " + username);
        return sc.nextLine();
	}

	private static String askLogin() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Entrez votre login");
		return sc.nextLine();
	}
}
