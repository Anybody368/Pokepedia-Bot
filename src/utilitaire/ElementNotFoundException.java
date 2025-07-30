package utilitaire;

public class ElementNotFoundException extends RuntimeException {
    public ElementNotFoundException(String term, String context) {
        super("No " + context + " found for \"" + term + "\"");
    }
}
