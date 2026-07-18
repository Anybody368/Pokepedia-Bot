package sleep.event.bonus;

public record SleepTypeBonus(Quantity quantity) implements Bonus {
    public enum Quantity {
        QUELQUES("quelques"),
        PLUSIEURS("plusieurs");

        private final String label;
        Quantity(String label) {
            this.label = label;
        }
    }

    @Override
    public String getBonusString() {
        return quantity.label + " Pokémon de n'importe quel type de dodo peuvent apparaître pendant les recherches sur le sommeil, quel que soit le type de dodo associé à la mesure du sommeil";
    }
}