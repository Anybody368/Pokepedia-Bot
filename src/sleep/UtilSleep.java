package sleep;

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
            case 6 -> "Vieille Centrale Dorée";
            default -> "The promised neverland";
        };
    }

    public static String ligneEtoiles(int nbrEtoiles)
    {
        return "|" + " [[Fichier:Miniature Étoile sleep.png|20px]]".repeat(nbrEtoiles);
    }
}
