package sleep.event.bonus;

public record CandyBoostBonus(boolean isMini) implements Bonus {
    @Override
    public String getBonusString() {
        return "le [[Bonbon (Pokémon Sleep)#Gain d'expérience|%sBoost Bonbon]] est disponible".formatted(isMini ? "mini " : "");
    }
}
