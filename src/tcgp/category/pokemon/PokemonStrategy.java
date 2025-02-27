package tcgp.category.pokemon;

import org.jetbrains.annotations.Nullable;
import tcgp.card.CardAttack;
import tcgp.enums.TCGType;
import tcgp.category.CategoryStrategy;

import java.util.ArrayList;

public class PokemonStrategy implements CategoryStrategy {
    private final TCGType m_type;
    private final TCGType m_weakness;
    private final int m_HP;
    private final int m_stage;
    private final int m_retreat;
    private final String m_prevolution;
    private final boolean m_hasAbility;
    private final ArrayList<CardAttack> m_attacks;

    public PokemonStrategy(TCGType type, @Nullable TCGType weakness, int hp, int stage, int retreat, @Nullable String prevolution, boolean hasAbility, ArrayList<CardAttack> attacks)
    {
        m_type = type;
        m_weakness = weakness;
        m_HP = hp;
        m_stage = stage;
        m_retreat = retreat;
        m_prevolution = prevolution;
        m_hasAbility = hasAbility;
        m_attacks = attacks;
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
    public String makeFacultes() {
        StringBuilder code = new StringBuilder();
        if(m_hasAbility)
        {
            code.append("| talent-nom={{?}}\n| talent-description={{?}}\n");
        }

        for (int i = 0; i < m_attacks.size(); i++) {
            code.append(m_attacks.get(i).getAttackCode(i+1));
        }
        code.append("\n<!-- Description -->\n| description={{?}}\n| description-jeu={{?}}\n");

        return code.toString();
    }

    @Override
    public String makeAnecdotes() {
        return "";
    }
}
