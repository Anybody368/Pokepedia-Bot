package sleep.event.bonus;

import static sleep.UtilSleep.getMultiplierString;

public record ExpBonus(int extraPercentile) implements Bonus {
    @Override
    public String getBonusString() {
        return "l'expérience gagnée par les Pokémon de soutien après une session de sommeil est multipliée par " + getMultiplierString(extraPercentile);
    }
}
