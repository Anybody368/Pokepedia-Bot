package tcgp.category.pokemon;

import tcgp.card.CardAttack;
import tcgp.enums.TCGType;

import java.util.ArrayList;

/**
 * The PokeEXStrategy class is a subclass of PokemonStrategy used for cards of ex Pokémon in order to write the Poképedia
 * page of their card.
 *
 * <p>This subclass doesn't store any additional data, but overrides most methods</p>
 *
 * @author Samuel Chanal
 */
public class PokeEXStrategy extends PokemonStrategy {

    /**
     * Main constructor with all the relevant data
     * @param type the TCGType of the Pokémon
     * @param weakness the TCGType of the Pokémon's weakness (might be null)
     * @param hp the Health Points of the Pokémon
     * @param stage the evolution stage of the Pokémon (0 for basic, 1 for first evolution, etc.)
     * @param retreat the cost of retreat for that pokémon, usually between 0 and 4
     * @param prevolution French name of the Pokémon this Pokémon evolves from (should be null if stage = 0)
     * @param hasAbility whether that Pokémon has an ability or not
     * @param attacks the list of CardAttacks this Pokémon has
     */
    public PokeEXStrategy(TCGType type, TCGType weakness, int hp, int stage, int retreat, String prevolution, boolean hasAbility, ArrayList<CardAttack> attacks)
    {
        super(type, weakness, hp, stage, retreat, prevolution, hasAbility, attacks, null);
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
    public String makeFaculties(String fr_name) {
        String init = super.makeFaculties(fr_name);
        return init.substring(0, init.length() - 66);
    }

    @Override
    public String getTitleBonus() {
        return "-ex";
    }
}
