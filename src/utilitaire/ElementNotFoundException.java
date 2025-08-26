package utilitaire;

/**
 * The ElementNotFoundException class is intended to be used when trying to gather information from a long text, mostly
 * Wiki page raw text, but it isn't found.
 *
 * <p>It is currently used when trying to get an Enumeration value from a String, or finding a specific String within text.</p>
 *
 * @author Samuel Chanal
 */
public class ElementNotFoundException extends RuntimeException {
    public ElementNotFoundException(String term, String context) {
        super("No " + context + " found for \"" + term + "\"");
    }
}
