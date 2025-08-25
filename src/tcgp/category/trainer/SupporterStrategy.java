package tcgp.category.trainer;

/**
 * The SupporterStrategy class is a subclass of TrainerStrategy used for cards of Supporters in order to write the Poképedia
 * page of their card.
 *
 * @author Samuel Chanal
 */
public class SupporterStrategy extends TrainerStrategy {
    @Override
    public String makeCategorySection() {
        return super.makeCategorySection() + "\n| sous-catégorie=Supporter";
    }
}
