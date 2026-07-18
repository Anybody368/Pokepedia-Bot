package sleep.event.bonus;

import static sleep.UtilSleep.getMultiplierString;

public record CookingStrengthBonus(int extraPercentile) implements Bonus {
    @Override
    public String getBonusString() {
        return "la puissance des [[Cuisine (Pokémon Sleep)|plats cuisinés]] est multipliée par " + getMultiplierString(extraPercentile);
    }
}
