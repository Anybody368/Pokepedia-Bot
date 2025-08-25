package tcgp.category.trainer;

/**
 * The ToolStrategy class is a subclass of TrainerStrategy used for cards of Pokémon tools in order to write the Poképedia
 * page of their card.
 *
 * @author Samuel Chanal
 */
public class ToolStrategy extends TrainerStrategy {
    @Override
    public String makeCategorySection() {
        return super.makeCategorySection() + "\n| sous-catégorie=Outil Pokémon";
    }
}
