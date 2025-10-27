package tcgp;

import utilitaire.PokeData;
import utilitaire.Region;

public class Utilitaire {
    public static String actualName(String frName) {
        Region region = Region.findRegionalFromFr(frName);
        if (region != null) {
            String pokeName = frName.substring(0, frName.length()-region.getFrAdjective().length()-1);
            if(PokeData.getRegionalFormsOfPokemon(pokeName).contains(region)) {
                return frName;
            } else {
                return pokeName;
            }
        }
        return frName;
    }
}
