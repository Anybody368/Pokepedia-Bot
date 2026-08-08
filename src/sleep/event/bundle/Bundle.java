package sleep.event.bundle;

import sleep.event.ItemReward;
import utilitaire.*;

import java.io.File;
import java.util.List;

public record Bundle(String name, int price, int limit, List<ItemReward> items, File icon) {
    public Bundle {
        name = (name.startsWith("Lot ") ? name : "Lot " + name);
    }

    String getWikiCode() {
        StringBuilder sb = new StringBuilder("| [[Fichier:Sprite ").append(name).append(" Sleep.png|70px|centre]]<br>")
                .append(name).append("\n| ");

        for (ItemReward item : items) {
            sb.append(item.getWikiCode()).append("<br>");
        }
        sb.delete(sb.length()-4, sb.length()).append("\n| ").append(Util.numberDecomposition(price))
                .append(" [[Fichier:Sprite Diamant Sleep.png|30px]]\n| ").append(limit);

        return sb.toString();
    }

    FileToUpload getIconPage() {
        if (icon == null) return null;

        String description = """
                == Description ==
                Sprite d'un lot temporaire d'événement dans {{Jeu|Sleep}}.
                
                {{Informations Fichier
                | Source = [https://pks.raenonx.cc/en Pokémon Sleep Info Wiki]
                | Auteur = [[The Pokémon Company]]
                }}
                
                [[Catégorie:Image d'évènement de Pokémon Sleep]]""";
        return new FileToUpload(icon, "Fichier:Sprite %s Sleep.png".formatted(name), description, "Sprite de lot d'évènement");
    }
}
