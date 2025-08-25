package tcgp.category.decorator;

import tcgp.category.CategoryStrategy;

/**
 * The BaseDecorator abstract class is the root of a Decorator design pattern used to add extra information on cards
 * that may apply to different categories, such as Pokémon being an UltraBeast regardless if their card is regular or an
 * ex variant.
 *
 * <p>The "main" category of the card needs to be confirmed before adding decorator elements to it</p>
 *
 * @author Samuel Chanal
 */
public abstract class BaseDecorator implements CategoryStrategy {
    private final CategoryStrategy m_wrapped;

    /**
     * Main constructor used by the other Decorator classes extending it
     * @param wrapped the main category of the card (or another Decorator if the card has multiple things going on)
     */
    protected BaseDecorator(CategoryStrategy wrapped)
    {
        m_wrapped = wrapped;
    }

    @Override
    public String makeInfobox() {
        return m_wrapped.makeInfobox();
    }

    @Override
    public String makeCategorySection() {
        return m_wrapped.makeCategorySection();
    }

    @Override
    public String makeNameSection(String en_name, String fr_name, String jp_name) {
        return m_wrapped.makeNameSection(en_name, fr_name, jp_name);
    }

    @Override
    public String makeFaculties(String fr_name) {
        return m_wrapped.makeFaculties(fr_name);
    }

    @Override
    public String makeAnecdotes() {
        return m_wrapped.makeAnecdotes();
    }

    @Override
    public String getTitleBonus() {
        return m_wrapped.getTitleBonus();
    }
}
