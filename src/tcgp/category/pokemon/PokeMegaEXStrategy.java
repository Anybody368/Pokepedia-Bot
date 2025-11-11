package tcgp.category.pokemon;

import tcgp.Utilitaire;
import tcgp.card.CardAttack;
import tcgp.enums.TCGType;

import java.util.ArrayList;

public class PokeMegaEXStrategy extends PokeEXStrategy {
    /**
     * Main constructor with all the relevant data
     *
     * @param type        the TCGType of the Pokémon
     * @param weakness    the TCGType of the Pokémon's weakness (might be null)
     * @param hp          the Health Points of the Pokémon
     * @param stage       the evolution stage of the Pokémon (0 for basic, 1 for first evolution, etc.)
     * @param retreat     the cost of retreat for that pokémon, usually between 0 and 4
     * @param prevolution French name of the Pokémon this Pokémon evolves from (should be null if stage = 0)
     * @param hasAbility  whether that Pokémon has an ability or not
     * @param attacks     the list of CardAttacks this Pokémon has
     */
    public PokeMegaEXStrategy(TCGType type, TCGType weakness, int hp, int stage, int retreat, String prevolution, boolean hasAbility, ArrayList<CardAttack> attacks) {
        super(type, weakness, hp, stage, retreat, prevolution, hasAbility, attacks);
    }

    @Override
    public String makeNameSection(String en_name, String fr_name, String jp_name) {
        String SYMB_MEX = "{{Symbole JCC|ex Méga}}";
        return("| nom=" + fr_name + SYMB_MEX + "\n| nomréel=" + Utilitaire.actualName(fr_name) + "\n| nomen=" + en_name + SYMB_MEX +
                "\n| nomja=" + jp_name + SYMB_MEX);
    }

    @Override
    public String makeCategorySection() {
        return super.makeCategorySection() + "\n| sous-catégorie2=Méga-Évolution";
    }

}
