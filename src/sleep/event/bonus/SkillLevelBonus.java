package sleep.event.bonus;

public record SkillLevelBonus(int extraLevels) implements Bonus {
    @Override
    public String getBonusString() {
        return "le niveau des Compétences Principales des Pokémon de soutien est augmenté de " + extraLevels;
    }
}
