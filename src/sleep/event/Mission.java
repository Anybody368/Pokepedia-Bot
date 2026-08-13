package sleep.event;

public record Mission(MissionType mission, ItemReward reward) {
    public enum MissionType {
        BASIC_2("Faire monter Ronflex au rang Basique 2"),
        HYPER_1("Faire monter Ronflex au rang Hyper 1"),
        MASTER_1("Faire monter Ronflex au rang Master 1"),
        GIVE_BISCUITS("Donner 15 biscuits"),
        CANDY_USE("Utiliser 100 bonbons"),
        USE_ENCENS("Utiliser 7 encens"),
        RESPECT_SLEEP("Respecter 3 fois l'heure du coucher fixée"),
        COOK("Cuisiner 12 fois")
        ;
        private final String description;
        MissionType(String description) {
            this.description = description;
        }

        public String description() {return this.description;}

        public static MissionType fromString(String description) {
            for (MissionType mission : MissionType.values()) {
                if (mission.description().equals(description)) {
                    return mission;
                }
            }
            throw new IllegalArgumentException("Mission not found: " + description);
        }
    }

    public String getWikiCode() {
        return """
                | %s
                | %s""".formatted(mission.description, reward.getWikiCode());
    }
}
