package tcgp.category.trainer;

/**
 * The ItemStrategy class is a subclass of TrainerStrategy used for cards of regular items in order to write the Poképedia
 * page of their card.
 *
 * @author Samuel Chanal
 */
public class ItemStrategy extends TrainerStrategy {
    @Override
    public String makeCategorySection() {
        return super.makeCategorySection() + "\n| sous-catégorie=Objet";
    }
}
