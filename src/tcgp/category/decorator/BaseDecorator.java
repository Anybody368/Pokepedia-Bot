package tcgp.category.decorator;

import tcgp.category.CategoryStrategy;

public abstract class BaseDecorator implements CategoryStrategy {
    private final CategoryStrategy m_wrapped;

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
    public String makeFacultes() {
        return m_wrapped.makeFacultes();
    }

    @Override
    public String makeAnecdotes() {
        return m_wrapped.makeAnecdotes();
    }
}
