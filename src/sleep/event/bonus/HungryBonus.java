package sleep.event.bonus;

public record HungryBonus() implements Bonus {
    @Override
    public String getBonusString() {
        return "un Pokémon est garanti d'être affamé durant les sessions de recherche";
    }
}
