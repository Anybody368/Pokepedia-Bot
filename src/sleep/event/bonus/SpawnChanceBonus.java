package sleep.event.bonus;

public record SpawnChanceBonus() implements Bonus {
    @Override
    public String getBonusString() {
        return "certains Pokémon ont plus de chance d'apparaître pendant les recherches sur le sommeil";
    }
}
