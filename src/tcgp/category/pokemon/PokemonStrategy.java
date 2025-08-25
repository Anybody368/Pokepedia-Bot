package tcgp.category.pokemon;

import org.jetbrains.annotations.Nullable;
import tcgp.card.CardAttack;
import tcgp.enums.TCGType;
import tcgp.category.CategoryStrategy;
import utilitaire.Game;
import utilitaire.Util;

import java.util.ArrayList;

/**
 * The PokemonStrategy class is used for cards of regular Pokémon in order to write the Poképedia page of their card.
 *
 * @author Samuel Chanal
 */
public class PokemonStrategy implements CategoryStrategy {
    private final TCGType m_type;
    private final TCGType m_weakness;
    private final int m_HP;
    private final int m_stage;
    private final int m_retreat;
    private final String m_prevolution;
    private final boolean m_hasAbility;
    private final ArrayList<CardAttack> m_attacks;
    private final Game m_descriptionGame;


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
     * @param desciptionGame the Game this Pokémon's description is from (null if that card doesn't have a description
     *                       or the game is undetermined)
     */
    public PokemonStrategy(TCGType type, @Nullable TCGType weakness, int hp, int stage, int retreat, @Nullable String prevolution, boolean hasAbility, ArrayList<CardAttack> attacks, Game desciptionGame)
    {
        m_type = type;
        m_weakness = weakness;
        m_HP = hp;
        m_stage = stage;
        m_retreat = retreat;
        m_prevolution = prevolution;
        m_hasAbility = hasAbility;
        m_attacks = attacks;
        m_descriptionGame = desciptionGame;
    }

    @Override
    public String makeInfobox() {
        String code = "\n| type=" + m_type.getNom() + "\n| pv=" + m_HP + "\n| stade=" + m_stage;
        if(m_prevolution != null)
        {
            code += "\n| niveau-précédent=" + m_prevolution;
        }
        code += "\n| retraite=" + m_retreat;
        if(m_weakness != null)
        {
            code += "\n| faiblesse=" + m_weakness.getNom();
        }
        return code;
    }

    @Override
    public String makeCategorySection() {
        return "\n| catégorie=Pokémon";
    }

    @Override
    public String makeNameSection(String en_name, String fr_name, String jp_name) {
        return("| nom=" + fr_name + "\n| nomen=" + en_name + "\n| nomja=" + jp_name);
    }

    @Override
    public String makeFaculties(String fr_name) {
        StringBuilder code = new StringBuilder();
        if(m_hasAbility)
        {
            code.append("| talent-nom={{?}}\n| talent-description={{?}}\n");
        }

        for (int i = 0; i < m_attacks.size(); i++) {
            code.append(m_attacks.get(i).getAttackCode(i+1));
        }

        code.append("\n<!-- Description -->\n| description=");
        if(m_descriptionGame != null && m_descriptionGame != Game.SHINING_PEARL && m_descriptionGame != Game.BRILLIANT_DIAMOND) {
            String description = Util.getFrenchPokemonDescription(fr_name, m_descriptionGame);
            code.append(description).append("\n| description-jeu=").append(m_descriptionGame.getFrenchAcronym()).append("\n");
        } else {
            code.append("{{?}}\n| description-jeu={{?}}\n");
        }

        return code.toString();
    }

    @Override
    public String makeAnecdotes() {
        return "";
    }

    @Override
    public String getTitleBonus() {
        return "";
    }

    @Override
    public boolean isPokemon() {
        return true;
    }
}
