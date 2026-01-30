package utilitaire;

import java.util.Scanner;

/**
 * The Login class is used to connect to your Poképedia's account before making edits on the website using the API class.
 *
 * <p>Neither your login nor password will be saved anywhere for safety purposes.</p>
 *
 * <p>Although it's currently limited to Poképedia, the API.login function (and the one they call) could be easily
 * modified to allow for Bulbapedia connection.</p>
 *
 * @author Mewtwo-Ex
 * @author GaletteLithium
 * @author Samuel Chanal
 */
public class Login {

    public static void handleLogin(String[] args) {
        switch (args.length) {
            case 2:
                login(args[0], args[1]);
                break;
            case 1:
                login(args[0]);
                break;
            default:
                login();
        }
    }

    /**
     * Connect to Poképedia with your login and password
     * @param login the username of your Poképedia's account
     * @param password the password of your Poképedia's account
     */
	public static void  login(String login, String password) {
		if(API.login(login, password)) System.out.println("Conexion au compte " + login + " réussie.");
	}

    /**
     * Connect to Poképedia with your login, asking for password during execution
     * @param login the username of your Poképedia's account
     */
	public static void login(String login) {
		if(API.login(login,  askPassword(login))) System.out.println("Conexion au compte " + login + " réussie.");
	}

    /**
     * Connect to Poképedia entering your login and password during execution
     */
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
