package Sleep;

public class UtilSleep {
    public static String getNomIle(int numIle)
    {
        return switch (numIle)
        {
            case 1 -> "Île Vertepousse";
            case 2 -> "Plage Cyan";
            case 3 -> "Grotte Sépia";
            case 4 -> "Plaine Perce-neige";
            case 5 -> "Rivage Lapis-lazuli";
            default -> "The promised neverland";
        };
    }

    public static String ligneEtoiles(int nbrEtoiles)
    {
        String r = "|";
        for (int i = 0; i < nbrEtoiles; i++) {
            r += " [[Fichier:Miniature Étoile Sleep.png|20px]]";
        }
        return r;
    }

    public static String getBaie(String type)
    {
        String baie = switch (type)
        {
            case "Normal" -> "Kika";
            case "Feu" -> "Mepo";
            case "Eau" -> "Oran";
            case "Électrik" -> "Résin";
            case "Plante" -> "Durin";
            case "Glace" -> "Fraive";
            case "Combat" -> "Ceriz";
            case "Poison" -> "Maron";
            case "Sol" -> "Figuy";
            case "Vol" -> "Palma";
            case "Psy" -> "Mago";
            case "Insecte" -> "Prine";
            case "Roche" -> "Sitrus";
            case "Spectre" -> "Remu";
            case "Dragon" -> "Nanone";
            case "Ténèbres" -> "Wiki";
            case "Acier" -> "Myrte";
            case "Fée" -> "Pêcha";
            default -> "";
        };
        if(baie.isEmpty()) {
            System.err.println("ERREUR : Type non valide");
            System.exit(1);
        }
        return baie;
    }
}
