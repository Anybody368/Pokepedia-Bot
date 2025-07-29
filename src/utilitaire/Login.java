package utilitaire;

import java.util.Scanner;

public class Login {

	public static void  login(String login, String password) {
		if(API.login(login, password)) System.out.println("Conexion au compte " + login + " réussie.");
	}

	public static void login(String login) {
		if(API.login(login,  askPassword(login))) System.out.println("Conexion au compte " + login + " réussie.");
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
