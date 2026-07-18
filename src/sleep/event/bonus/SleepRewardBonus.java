package sleep.event.bonus;

import static sleep.UtilSleep.getMultiplierString;

public record SleepRewardBonus(Reward rewardType, int extraPercentile) implements Bonus {
    public enum Reward {
        CANDIES("[[Bonbon (Pokémon Sleep)#Bonbon Pokémon|bonbons]]"),
        SHARDS("[[Fragment de Rêve|Fragments de Rêve]]"),
        RESEARCH_EXP("points de recherche");

        private final String label;
        Reward(String label) {
            this.label = label;
        }
    }

    @Override
    public String getBonusString() {
        return "les " + rewardType.label + " obtenus pendant les recherches sur le sommeil sont multipliés par " + getMultiplierString(extraPercentile);
    }
}
