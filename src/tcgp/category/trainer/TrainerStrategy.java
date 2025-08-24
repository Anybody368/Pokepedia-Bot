package tcgp.category.trainer;

import tcgp.category.CategoryStrategy;

public abstract class TrainerStrategy implements CategoryStrategy {
    @Override
    public String makeInfobox() {
        return "";
    }

    @Override
    public String makeCategorySection() {
        return "\n| catégorie=Dresseur";
    }

    @Override
    public String makeNameSection(String en_name, String fr_name, String jp_name) {
        return("| nom=" + fr_name + "\n| nomen=" + en_name + "\n| nomja=" + jp_name);
    }

    @Override
    public String makeFacultes(String fr_name) {
        return "| faculté-description={{?}}\n";
    }

    @Override
    public String makeAnecdotes() {
        return "| anecdotes=\n* {{?}}\n";
    }

    @Override
    public String getTitleBonus() {
        return "";
    }

    @Override
    public boolean isPokemon() {
        return false;
    }
}
