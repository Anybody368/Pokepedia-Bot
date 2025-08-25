package tcgp.category.decorator;

import tcgp.category.CategoryStrategy;

/**
 * The UltraBeast class is a subclass of BaseDecorator used for cards of UltraBeasts (like Nihilego).
 *
 * @author Samuel Chanal
 */
public class UltraBeast extends BaseDecorator{
    public UltraBeast(CategoryStrategy wrapped) {
        super(wrapped);
    }

    @Override
    public boolean isPokemon() {
        return true;
    }

    @Override
    public String makeCategorySection() {
        String init = super.makeCategorySection();
        if(init.contains("sous-catégorie")) {
            return init + "\n| sous-catégorie2=Ultra-Chimère";
        } else {
            return init + "\n| sous-catégorie=Ultra-Chimère";
        }
    }
}
