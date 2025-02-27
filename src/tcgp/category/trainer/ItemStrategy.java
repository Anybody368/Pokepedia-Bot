package tcgp.category.trainer;

public class ItemStrategy extends TrainerStrategy {
    @Override
    public String makeCategorySection() {
        return super.makeCategorySection() + "\n| sous-catégorie=Objet";
    }
}
