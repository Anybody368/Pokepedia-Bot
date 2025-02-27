package tcgp.category.trainer;

public class SupporterStrategy extends TrainerStrategy {
    @Override
    public String makeCategorySection() {
        return super.makeCategorySection() + "\n| sous-catégorie=Supporter";
    }
}
