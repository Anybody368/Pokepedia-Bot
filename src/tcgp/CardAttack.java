package tcgp;

import java.util.ArrayList;

public class CardAttack {
    private final String m_damage;
    private final ArrayList<TCGType> m_energies;

    public CardAttack(String dam, ArrayList<TCGType> energies)
    {
        m_damage = dam;
        m_energies = energies;
    }

    public String getDamage() {
        return m_damage;
    }

    public String getEnergyCostCode()
    {
        StringBuilder code = new StringBuilder("| type=");
        for(TCGType energy : m_energies)
        {
            code.append("{{type|").append(energy.getNom().toLowerCase()).append("|jcc}}");
        }
        return code.toString();
    }
}
