package sleep.event;

import utilitaire.Util;

public record ItemReward(Item item, int quantity) {
    public enum Item {
        POKE_BISCUIT("Poké Biscuit", "Poké Biscuit]]"),
        GREAT_BISCUIT("Super Biscuit", "Super Biscuit]]"),
        ULTRA_BISCUIT("Hyper Biscuit", "Hyper Biscuit]]"),
        ;

        public final String fileName;
        public final String linkName;
        Item(String fileName, String linkName) {
            this.fileName = fileName;
            this.linkName = linkName;
        }
    }

    public String getWikiCode() {
        return "[[Fichier:Sprite %s Sleep.png|30px]] [[%s × %s".formatted(item.fileName, item.linkName, Util.numberDecomposition(quantity));
    }
}
