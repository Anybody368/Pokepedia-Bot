package tcgp.card;

import tcgp.enums.TCGType;

import java.util.ArrayList;

/**
 * The CardAttack class represent an attack of the card of a Pokémon in TCGP.
 *
 * <p>This does not cover Pokémon abilities, or
 * effects from any other type of card. The specific effect of an attack cannot be directly translated, and therefore
 * we can only know whether an effect exists for this attack.</p>
 *
 * @author Samuel Chanal
 */

public class CardAttack {
    private final String m_damage;
    private final ArrayList<TCGType> m_energies;
    private final boolean m_hasEffect;

    /**
     * Main constructor of an attack with every relevant data
     * @param damage damage dealt by the attack
     * @param energies list of energies needed to use the attack (might be empty)
     * @param hasEffect whether this attack has an additional effect or not
     */
    public CardAttack(String damage, ArrayList<TCGType> energies, boolean hasEffect)
    {
        m_damage = damage;
        m_energies = energies;
        m_hasEffect = hasEffect;
    }

    private String getDamage() {
        return m_damage;
    }

    /**
     * Generates the raw text of this attack for the Poképedia article of a card
     * @param number Order of the attack on the card, starting at 1
     * @return a String containing the raw text of this attack for Poképedia
     */
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

    /**
     * Generates the raw text for a Poképedia article of the energy cost
     * @return a String containing the attack's energy cost for Poképedia
     */
    private String getEnergyCostCode()
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
}
