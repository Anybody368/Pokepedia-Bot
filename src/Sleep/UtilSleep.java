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
}
