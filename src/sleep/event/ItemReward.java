package sleep.event;

public record ItemReward(Item item, int quantity) {
    public enum Item {
        POKE_BISCUIT,
        GREAT_BISCUIT,
        ULTRA_BISCUIT,
    }
}
