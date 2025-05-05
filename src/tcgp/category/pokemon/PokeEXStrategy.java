package tcgp.category.pokemon;

import tcgp.card.CardAttack;
import tcgp.enums.TCGType;

import java.util.ArrayList;

public class PokeEXStrategy extends PokemonStrategy {

    public PokeEXStrategy(TCGType type, TCGType weakness, int hp, int stage, int retreat, String prevolution, boolean hasAbility, ArrayList<CardAttack> attacks)
    {
        super(type, weakness, hp, stage, retreat, prevolution, hasAbility, attacks);
    }

    @Override
    public String makeNameSection(String en_name, String fr_name, String jp_name) {
        String SYMB_EX = "{{Symbole JCC|ex JCCP}}";
        return("| nom=" + fr_name + SYMB_EX + "\n| nomréel=" + fr_name + "\n| nomen=" + en_name + SYMB_EX +
                "\n| nomja=" + jp_name + SYMB_EX);
    }

    @Override
    public String makeCategorySection() {
        return super.makeCategorySection() + "\n| sous-catégorie=ex";
    }

    @Override
    public String makeFacultes() {
        String init = super.makeFacultes();
        return init.substring(0, init.length() - 66);
    }

    @Override
    public String getTitleBonus() {
        return "-ex";
    }
}
