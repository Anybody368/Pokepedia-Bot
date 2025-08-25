package tcgp.category.decorator;

import tcgp.category.CategoryStrategy;

/**
 * NOT FUNCTIONAL IN THE SLIGHTEST
 *
 * <p>First attempt at making a decoration, before realising this would not work because Shiny cards are specific variants
 * of existing cards, and therefore depends on the CardSpecs.</p>
 */
@Deprecated
public class Shiny extends BaseDecorator{
    public Shiny(CategoryStrategy wrapped) {
        super(wrapped);
    }

    @Override
    public String makeAnecdotes() {
        return super.makeAnecdotes() + "| chromatique=oui\n";
    }

    @Override
    public boolean isPokemon() {
        return false;
    }
}
