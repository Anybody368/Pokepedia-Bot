package sleep.dodos;

import utilitaire.Util;

import java.util.Map;

public record SleepStyle(String name, int rarity, int exp, int shards, int candyCount,
                         Map<Island, SleepRank> locations) {
    /**
     * Constructor with all data already filled
     *
     * @param name      : Name of the Sleep Style
     * @param rarity    : Number of stars of the Sleep Style
     * @param exp       : Number of Research EXP given when observed
     * @param shards    : Number of Dream Shards given when observed
     * @param candyCount: Number of candies given when observed
     * @param locations : Islands and rank on which it can be observed
     */
    public SleepStyle {
        locations = Map.copyOf(locations);
    }

    public String getLocationsText() {
        StringBuilder r = new StringBuilder();
        for(Island island : Island.values()) {
            if (!locations.containsKey(island)) continue;

            if (!r.isEmpty()) {
                r.append("<br>");
            }
            SleepRank loc = locations.get(island);
            r.append("[[%s]] ([[Fichier:Sprite Rang %s Sleep.png|Rang %s|25px]]%d)".formatted(
                    island.getName(true), loc.rankBall(), loc.rankBall(), loc.rankLevel()
            ));
        }
        return r.toString();
    }

    public String getRewardsText(int numDodo) {
        return "|recherche" + numDodo + "=" + exp + "|fragment" + numDodo + "=" + shards;
    }

    public String getRewardsOneCell(String candyName) {
        return "| style=\"white-space:nowrap; text-align:left\" | [[Fichier:Sprite Point de recherche Sleep.png|30px]] Point de recherche × %s<br>[[Fichier:Sprite Fragment de Rêve Sleep.png|30px]] [[Fragment de Rêve]] × %s<br>[[Fichier:Sprite Bonbon %s Sleep.png|30px]] [[Bonbon (Pokémon Sleep)|Bonbon %s]] × %d"
                .formatted(Util.numberDecomposition(exp), Util.numberDecomposition(shards), candyName, candyName, candyCount);
    }

    public boolean isAvailableOnIsland(Island island) {
        return locations.containsKey(island);
    }

    public String getRankBallOnIsland(Island island) {
        return locations.get(island).rankBall();
    }

    public String getRankOnIsland(Island island) {
        return locations.get(island).getPalier();
    }
}
