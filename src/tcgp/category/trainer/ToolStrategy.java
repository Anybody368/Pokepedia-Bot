package tcgp.category.trainer;

public class ToolStrategy extends TrainerStrategy {
    @Override
    public String makeCategorySection() {
        return super.makeCategorySection() + "\n| sous-catégorie=Outil Pokémon";
    }
}
