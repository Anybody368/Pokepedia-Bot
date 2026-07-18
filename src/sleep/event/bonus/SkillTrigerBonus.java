package sleep.event.bonus;

import static sleep.UtilSleep.getMultiplierString;

public record SkillTrigerBonus(int extraPercentile) implements Bonus {
    @Override
    public String getBonusString() {
        return "la probabilité qu'un Pokémon de soutien active sa [[Liste des compétences de Pokémon Sleep|Compétence Principale]] est multipliée par " + getMultiplierString(extraPercentile);
    }
}
