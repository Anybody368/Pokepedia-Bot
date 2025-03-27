package tcgp.category.decorator;

import tcgp.category.CategoryStrategy;

@Deprecated
public class Shiny extends BaseDecorator{
    public Shiny(CategoryStrategy wrapped) {
        super(wrapped);
    }

    @Override
    public String makeAnecdotes() {
        return super.makeAnecdotes() + "| chromatique=oui\n";
    }
}
