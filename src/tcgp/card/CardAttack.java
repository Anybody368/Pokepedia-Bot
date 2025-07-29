package tcgp.card;

import tcgp.enums.TCGType;

import java.util.ArrayList;

public class CardAttack {
    private final String m_damage;
    private final ArrayList<TCGType> m_energies;
    private final boolean m_hasEffect;

    public CardAttack(String dam, ArrayList<TCGType> energies, boolean hasEffect)
    {
        m_damage = dam;
        m_energies = energies;
        m_hasEffect = hasEffect;
    }

    public String getDamage() {
        return m_damage;
    }

    public String getEnergyCostCode()
    {
        if(m_energies.isEmpty()) {
            return "{{type|Aucun|jcc}}";
        }

        StringBuilder code = new StringBuilder();
        for(TCGType energy : m_energies)
        {
            code.append("{{type|").append(energy.getNom().toLowerCase()).append("|jcc}}");
        }
        return code.toString();
    }

    public String getAttackCode(int number)
    {
        StringBuilder code = new StringBuilder();
        if(number == 1)
        {
            code.append("| attaque-type=").append(getEnergyCostCode()).append("\n| attaque-nom={{?}}");
            if(!m_damage.isEmpty()) {
                code.append("\n| attaque-dégâts=").append((getDamage()));
            }
            if(m_hasEffect) {
                code.append("\n| attaque-description={{?}}");
            }
        } else {
            code.append("| attaque").append(number).append("-type=").append(getEnergyCostCode()).append("\n| attaque").append(number).append("-nom={{?}}");
            if(!m_damage.isEmpty()) {
                code.append("\n| attaque").append(number).append("-dégâts=").append((getDamage()));
            }
            if(m_hasEffect) {
                code.append("\n| attaque").append(number).append("-description={{?}}");
            }
        }
        code.append("\n");
        return code.toString();
    }
}
