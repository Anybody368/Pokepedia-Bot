package tcgp.category.decorator;

import tcgp.category.CategoryStrategy;
import tcgp.enums.PokeForm;
import utilitaire.Util;

/**
 * The SpecialForm class is a subclass of BaseDecorator used for cards of Pokémon with a specific form specified (like Heartflame Mask Ogerpon).
 *
 * <p>On top of stocking the Form, this class has a couple of extra public methods that can only be accessed with a
 * cast with the current implementation. For this reason, it should also always be the last decoration applied to the card.
 * It's not a good design, but for now it works.</p>
 *
 * @author Samuel Chanal
 */
public class SpecialForm extends BaseDecorator {
    private final PokeForm m_pokeForm;
    private final boolean m_isAtTheEnd;

    /**
     * Main constructor with all the relevant data
     *
     * @param wrapped       the main category (or previous decoration) of the card
     * @param form          the form of this Pokémon
     * @param isAtTheEnd    whether the english name has its form at the end or not
     */
    public SpecialForm(CategoryStrategy wrapped, PokeForm form, boolean isAtTheEnd) {
        super(wrapped);
        m_pokeForm = form;
        m_isAtTheEnd = isAtTheEnd;
    }

    @Override
    public String makeNameSection(String en_name, String fr_name, String jp_name) {
        String original = super.makeNameSection(en_name, fr_name, jp_name);
        String name = Util.searchValueOf(original, "| nom=", false).replace(" "  + m_pokeForm.getFrName(), "");
        String result;
        int place1 = original.indexOf(m_pokeForm.getFrName());
        int place2 = original.indexOf("\n");
        if(original.contains("| nomréel=")) {
            result = original.substring(0, place1) + "<small>" + original.substring(place1, place2) + "</small>"
                    + original.substring(place2).replace(" "  + m_pokeForm.getFrName() +"\n", "\n");
        } else {
            result = original.substring(0, place1) + "<small>" + original.substring(place1, place2) + "</small>\n| nomréel="
                    + name + original.substring(place2);
        }

        int enAdjectivePlace = result.indexOf(m_pokeForm.getEnName());
        int enAdjectiveEnd = enAdjectivePlace + m_pokeForm.getEnName().length();
        int jaAdjectivePlace = result.indexOf(m_pokeForm.getJaName());
        int jaAdjectiveEnd = jaAdjectivePlace + m_pokeForm.getJaName().length();

        result = result.substring(0, enAdjectivePlace) + "<small>" + result.substring(enAdjectivePlace, enAdjectiveEnd)
                + "</small>" + result.substring(enAdjectiveEnd, jaAdjectivePlace) + "<small>"
                + result.substring(jaAdjectivePlace,  jaAdjectiveEnd) + "</small>" + result.substring(jaAdjectiveEnd);

        System.out.println(result);
        return result;
    }

    @Override
    public boolean isPokemon() {
        return true;
    }

    public int getFormEnSize() {
        return m_pokeForm.getEnName().length()+1;
    }

    public boolean isAdjectiveAtTheEnd() {
        return m_isAtTheEnd;
    }

    public String getFrAdjective() {
        return m_pokeForm.getFrName();
    }
}
