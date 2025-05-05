package tcgp.category.decorator;

import tcgp.category.CategoryStrategy;
import utilitaire.Region;
import utilitaire.Util;

public class RegionalForm extends BaseDecorator{
    private final Region m_region;
    public RegionalForm(CategoryStrategy wrapped, Region region) {
        super(wrapped);
        m_region = region;
    }

    @Override
    public String makeNameSection(String en_name, String fr_name, String jp_name) {
        String original = super.makeNameSection(en_name, fr_name, jp_name);
        String name = Util.searchValueOf(original, "| nom=");
        String result;
        int reelPlace;
        if((reelPlace = original.indexOf("| nomréel=")) != -1) {
            int place1 = original.indexOf(m_region.getFrAdjective());
            int place2 = original.indexOf("\n");
            int place3 = original.indexOf("\n", reelPlace);
            result = original.substring(0, place1) + "<small>" + original.substring(place1, place2) + "</small>"
                    + original.substring(place2);//, place3) + " " + m_region.getFrAdjective() + original.substring(place3);
        } else {
            int place1 = original.indexOf(m_region.getFrAdjective());
            int place2 = original.indexOf("\n");
            result = original.substring(0, place1) + "<small>" + original.substring(place1, place2) + "</small>\n| nomréel="
                    + name + original.substring(place2);
        }

        int placeEn = result.indexOf("nomen=");
        String jaAdjective = m_region.getJaAdjective();

        int placeEnStart = result.indexOf("=", placeEn)+1;
        int placeEnEnd = result.indexOf(" ", placeEn);
        int placeJaStart = result.indexOf(jaAdjective);
        int placeJaEnd = placeJaStart + jaAdjective.length();

        result = result.substring(0, placeEnStart) + "<small>" + result.substring(placeEnStart, placeEnEnd) + "</small>"
                + result.substring(placeEnEnd, placeJaStart) + "<small>" + result.substring(placeJaStart, placeJaEnd)
                + "</small> " + result.substring(placeJaEnd);

        return result;
    }

    @Override
    public boolean isPokemon() {
        return true;
    }

    public int getRegionEnSize() {
        return m_region.getEnAdjective().length()+1;
    }

    public String getFrAdjective() {
        return m_region.getFrAdjective();
    }
}
