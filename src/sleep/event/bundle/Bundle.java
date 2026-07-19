package sleep.event.bundle;

import sleep.event.ItemReward;
import utilitaire.Util;

import java.util.List;

public record Bundle(String name, int price, int limit, List<ItemReward> items) {
    String getWikiCode() {
        StringBuilder sb = new StringBuilder("| [[Fichier:Sprite Lot ").append(name).append("Sleep.png|70px|centre]]<br>Lot ")
                .append(name).append("\n| ");

        for (ItemReward item : items) {
            sb.append(item.getWikiCode()).append("<br>");
        }
        sb.delete(sb.length()-4, sb.length()).append("\n| ").append(Util.numberDecomposition(price))
                .append(" [[Fichier:Sprite Diamant Sleep.png|30px]]\n| ").append(limit);

        return sb.toString();
    }
}
