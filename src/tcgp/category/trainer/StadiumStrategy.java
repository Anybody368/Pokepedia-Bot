package tcgp.category.trainer;

/**
 * The StadiumStrategy class is a subclass of TrainerStrategy used for cards of regular stadiums in order to write the Poképedia
 * page of their card.
 *
 * @author Matt.
 */
public class StadiumStrategy extends TrainerStrategy {
	@Override
	public String makeCategorySection() {
		return super.makeCategorySection() + "\n| sous-catégorie=Stade";
	}
}
