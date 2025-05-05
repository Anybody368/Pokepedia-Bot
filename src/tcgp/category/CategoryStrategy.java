package tcgp.category;

public interface CategoryStrategy {

    String makeInfobox();
    String makeCategorySection();
    String makeNameSection(String en_name, String fr_name, String jp_name);
    String makeFacultes();
    String makeAnecdotes();
    String getTitleBonus();
    boolean isPokemon();
}
