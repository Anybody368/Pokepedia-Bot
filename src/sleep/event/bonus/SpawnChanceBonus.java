package sleep.event.bonus;

public record SpawnChanceBonus(boolean shinyIsBoosted) implements Bonus {
    @Override
    public String getBonusString() {
        if (shinyIsBoosted) {
            return "certains Pokémon ont plus de chance d'apparaître pendant les recherches sur le sommeil, y compris sous leur forme [[chromatique]]";
        } else {
            return "certains Pokémon ont plus de chance d'apparaître pendant les recherches sur le sommeil";
        }
    }
}
