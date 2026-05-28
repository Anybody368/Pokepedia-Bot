package tcgp.category.decorator;

import tcgp.category.CategoryStrategy;

public class PastFutureTense extends BaseDecorator {
    private final boolean isFuture;

    public PastFutureTense(CategoryStrategy wrapped, boolean isFuture) {
        super(wrapped);
        this.isFuture = isFuture;
    }

    @Override
    public String makeCategorySection() {
        return super.makeCategorySection() + "\n| temps=" + (isFuture ? "futur" : "passé");
    }
}
