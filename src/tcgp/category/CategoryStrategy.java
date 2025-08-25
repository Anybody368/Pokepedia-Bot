package tcgp.category;

/**
 * the CategoryStrategy interface represents the category of a card.
 *
 * <p>There are two types of categories: The main category (a Pokémon or a Trainer card), with more specific categories
 * as subclasses, then modifiers that can apply to different categories as a Decorator design pattern (such as a Pokémon
 * being an Ultra Beast or a Regional variant</p>
 *
 * <p>Depending on the category, different information will be needed (like a Pokémon HP and type)</p>
 *
 * @author Samuel Chanal
 */
public interface CategoryStrategy {

    /**
     * Generates the raw text for the "Infobox" section specific to the category for the Poképedia's page
     * @return a String containing the category part of the "Infobox" section
     */
    String makeInfobox();

    /**
     * Generates the line of raw text giving the category of the card for Poképedia
     * @return the following String: {@code \n| catégorie=[category]}
     */
    String makeCategorySection();

    /**
     * Generates the lines of raw text giving the names of the card for Poképedia
     * @param en_name the English name of the card before adding symbols or styles
     * @param fr_name the French name of the card before adding symbols or styles
     * @param jp_name the Japanese name of the card before adding symbols or styles
     * @return a String containing three lines (one for each language)
     */
    String makeNameSection(String en_name, String fr_name, String jp_name);

    /**
     * Generates the raw text for the "Facultés" section specific to the category for the Poképedia's page
     * @param fr_name the French name of the card
     * @return a String containing the category part of the "Facultés" section
     */
    String makeFaculties(String fr_name);

    /**
     * Generates the raw text for the "Anecdotes" section specific to the category for the Poképedia's page
     * @return a String containing the category part of the "Facultés" section (might be empty)
     */
    String makeAnecdotes();

    /**
     * Getter of a fixed element added at the end of the Pokémon name depending on the category (such as -ex)
     * @return a String to be put directly after the Pokémon name
     */
    String getTitleBonus();

    boolean isPokemon();
}
