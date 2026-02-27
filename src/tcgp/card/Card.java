package tcgp.card;

import tcgp.Dictionary;
import tcgp.category.*;
import tcgp.category.decorator.RegionalForm;
import tcgp.category.decorator.SpecialForm;
import tcgp.category.decorator.UltraBeast;
import tcgp.category.pokemon.PokeEXStrategy;
import tcgp.category.pokemon.PokeMegaEXStrategy;
import tcgp.category.pokemon.PokemonStrategy;
import tcgp.category.trainer.*;
import tcgp.enums.*;
import utilitaire.*;

import java.util.ArrayList;

import static utilitaire.Util.searchValueOf;

/**
 * The Card class represent a card in TCGP as defined by Bulbapedia.
 *
 * <p>A given card can have specific versions from different Expansions and/or different authors and rarity.</p>
 *
 * <p>Its intended use is to be filled using the Bulbapedia page of a card, then use the gathered data to generate the
 * equivalent pages for Poképedia, with some empty spots for data that can't be translated from English or that is missing.</p>
 *
 * <p>The program may throw an ElementNotFoundException during the first phase, this usually means that there is an error
 * in the Bulbapedia page structure. The page will need to be corrected before attempting to make the card again</p>
 *
 * @author Samuel Chanal
 */

public class Card {
    private String m_frName;
    private String m_enName;
    private String m_jpName;
    private final CategoryStrategy m_category;
    private final ArrayList<CardSpecs> m_specs = new ArrayList<>(5);

    /**
     * Main Constructor used to fill the data of a card with the information contained in Bulbapedia's page
     * @param en_text Bulbapedia's page raw text
     */
    public Card(String en_text) {
        m_enName = searchValueOf(en_text, "|en name=", false);

        String type = searchValueOf(en_text, "TCG Card Infobox/", "/", false);
        if(type.equals("Pokémon"))
        {
            m_category = getPokeData(en_text);
        } else {
            if (m_enName.equals("Old Amber") || m_enName.endsWith(" Fossil")) {
                int hp = Integer.parseInt(searchValueOf(en_text, "|hp=", false));
                m_category = new FossilStrategy(hp);
            } else {
                String subType = searchValueOf(en_text, "|subtype=", true);
                m_category = switch (subType) {
                    case "Item" -> new ItemStrategy();
                    case "Supporter" -> new SupporterStrategy();
                    case "Pokémon Tool" -> new ToolStrategy();
                    case "Stadium" -> new StadiumStrategy();
                    default -> null;
                };
            }
        }

        fillNames(en_text);
        fillRest(en_text);
    }

    /**
     * Finds the specific category and data for the card of a Pokémon
     * @param en_text Bulbapedia's page raw text
     * @return the category of the card containing its specific data
     */
    private CategoryStrategy getPokeData(String en_text)
    {
        CategoryStrategy category;
        int hp = Integer.parseInt(searchValueOf(en_text, "|hp=", false));
        TCGType type = TCGType.typeFromEnglishName(searchValueOf(en_text, "|type=", false), "Pokémon type");
        TCGType weakness = null;
        if(en_text.contains("|weakness=") && !searchValueOf(en_text, "|weakness=", false).isBlank()) {
            weakness = TCGType.typeFromEnglishName(searchValueOf(en_text, "|weakness=", false), "weakness type");
        }
        int retreat = Integer.parseInt(searchValueOf(en_text, "|retreat cost=", false));
        int stage = switch (searchValueOf(en_text, "|evo stage=", false))
        {
            case "Basic" -> 0;
            case "Stage 1" -> 1;
            case "Stage 2" -> 2;
            default -> -1;
        };
        boolean hasAbility = en_text.contains("{{Cardtext/Ability");

        String prevolution = null;
        if(en_text.contains("|prevo name=") && stage != 0)
        {
            String enPrevo = Util.searchValueOf(en_text, "|prevo name=", false);
            Region prevoRegion = Region.findRegionalFromEn(enPrevo);
            if(prevoRegion != null)
            {
                enPrevo = enPrevo.substring(prevoRegion.getEnAdjective().length() +1);
                prevolution = PokeData.getFrenchNameFromEnglish(enPrevo, "pre-evolution regional Pokémon") + " " + prevoRegion.getFrAdjective();
            } else if (enPrevo.contains(" Fossil") || enPrevo.contains("Old ")) {
                prevolution = Dictionary.getTranslation(enPrevo);
            } else {
                prevolution = PokeData.getFrenchNameFromEnglish(enPrevo, "pre-evolution Pokémon");
            }
        }

        ArrayList<CardAttack> attacks = fillAttacks(en_text);

        String name = searchValueOf(en_text, "|en name=", false);
        if(name.contains("{{TCGP Icon|ex}}"))
        {
            category = new PokeEXStrategy(type, weakness, hp, stage, retreat, prevolution, hasAbility, attacks);
        } else if (name.contains("{{TCGP Icon|Mega ex}}")) {
            category = new PokeMegaEXStrategy(type, weakness, hp, stage, retreat, prevolution, hasAbility, attacks);
        } else {
            String englishDescriptionGame = searchValueOf(en_text, "[[Pokédex]] entry comes from {{g|", "}}", true);
            if (englishDescriptionGame == null) {
                System.err.println("No game description found.");
                category = new PokemonStrategy(type, weakness, hp, stage, retreat, prevolution, hasAbility, attacks, null);
            } else {
                Game descriptionGame = Game.getGameFromEnglishName(englishDescriptionGame,
                        "game the description is from");
                category = new PokemonStrategy(type, weakness, hp, stage, retreat, prevolution, hasAbility, attacks, descriptionGame);
            }
        }

        Region region = Region.findRegionalFromEn(m_enName);
        if(region != null) {
            category = new RegionalForm(category, region);
        }

        PokeForm pokeForm = PokeForm.findFormFromEn(m_enName);
        if(pokeForm != null) {
            category = new SpecialForm(category, pokeForm);
        }

        if(en_text.contains("{{Cardtext/UltraBeast")) {
            category = new UltraBeast(category);
        }

        return category;
    }

    /**
     * Fills the data about the Pokémon attacks
     * @param en_text Bulbapedia's page raw text
     * @return a list of CardAttack
     */
    private ArrayList<CardAttack> fillAttacks(String en_text)
    {
        ArrayList<CardAttack> attacks = new ArrayList<>();
        int currentLine = 0;
        while ((currentLine = en_text.indexOf("{{Cardtext/Attack", currentLine+1)) != -1)
        {
            String damage = searchValueOf(en_text, "|damage=", currentLine, true);
            if(damage == null) damage = "";

            String energiesLine = searchValueOf(en_text, "|cost=", currentLine, true);
            ArrayList<TCGType> energies = new ArrayList<>();
            if(energiesLine != null && !energiesLine.isEmpty() && !energiesLine.equalsIgnoreCase("{{e|None}}")) {
                if (energiesLine.contains("repeat")) {
                    TCGType type = TCGType.typeFromEnglishName(searchValueOf(energiesLine, "{{e|", "}}", false), "attack cost type");
                    for (int i = 0; i < Integer.parseInt(searchValueOf(energiesLine, "}}|", "}}", false)); i++) {
                        energies.add(type);
                    }
                } else {
                    String[] energiesString = energiesLine.split("[{]{2}e[|]");
                    for (int i = 1; i < energiesString.length; i++) {
                        energies.add(TCGType.typeFromEnglishName(energiesString[i].substring(0, energiesString[i].length() - 2), "attack cost type"));
                    }
                }
            }
            String effectValue = searchValueOf(en_text, "|effect=", currentLine, true);
            boolean hasEffect = (effectValue != null) && (!effectValue.isBlank());
            attacks.add(new CardAttack(damage, energies, hasEffect));
        }

        return attacks;
    }

    /**
     * Fills the name of a card in English, Japanese, and French (asking for user's input if needed)
     * @param en_text Bulbapedia's page raw text
     */
    private void fillNames(String en_text) {

        if(m_enName.contains("{{TCGP Icon|ex}}"))
        {
            m_enName = m_enName.substring(0, m_enName.length()-17);
        } else if (m_enName.contains("{{TCGP Icon|Mega ex}}")) {
            m_enName = m_enName.substring(0, m_enName.length()-22);
        }

        if(m_category.isPokemon()) {
            if(m_category instanceof RegionalForm) {
                String name = m_enName.substring(((RegionalForm) m_category).getRegionEnSize());
                m_frName = PokeData.getFrenchNameFromEnglish(name, "regional Pokémon name") + " " + ((RegionalForm) m_category).getFrAdjective();
            } else if(m_category instanceof SpecialForm) {
                String name = m_enName.substring(((SpecialForm) m_category).getFormEnSize());
                m_frName = PokeData.getFrenchNameFromEnglish(name, "regional Pokémon name") + " " + ((SpecialForm) m_category).getFrAdjective();
            } else {
                m_frName = PokeData.getFrenchNameFromEnglish(m_enName, "Pokémon name");
            }
        }
        else
        {
            m_frName = Dictionary.getTranslation(m_enName);
        }

        m_jpName = searchValueOf(en_text, "|ja name=", false).split("[{]")[0];
    }

    /**
     * Fills the remaining data about the card, mostly the data specific to each version of a given card
     * @param en_text Bulbapedia's page raw text
     */
    private void fillRest(String en_text)
    {
        ArrayList<String> illustrator = new ArrayList<>(5);
        if(en_text.contains("\n|illustrator=") && !en_text.contains("|image set="))
        {
            illustrator.add(searchValueOf(en_text, "|illustrator=", false));
        } else {
            int illustLine = 0;
            while((illustLine = en_text.indexOf("{{TCG Card Infobox/Tabbed Image", illustLine+1)) != -1)
            {
                illustrator.add(searchValueOf(en_text, "illustrator=", "|", illustLine, false));
            }
        }

        int currentLine = en_text.indexOf("{{TCG Card Infobox/Expansion Header");
        Expansion exp = Expansion.PROMO_A;
        while(!illustrator.isEmpty())
        {
            if(searchValueOf(en_text, "Expansion ", "/", currentLine, false).equals("Header"))
            {
                exp = Expansion.ExpansionFromEnglishName(searchValueOf(en_text, "|", "}}", currentLine, false), "card expansion");
            } else {
                Rarity rar = Rarity.NONE;
                String rarity = searchValueOf(en_text, "rarity=", "|", currentLine, true);
                if(rarity != null) {
                    String count = "1";

                    // Fallback if rarity is Crown}}
                    if (rarity.contains("}}")) {
                        rarity = rarity.substring(0, rarity.indexOf("}}"));
                    }
                    System.out.println(rarity);
                    if (!rarity.equals("Crown")) {
                        count = searchValueOf(en_text, "rarity count=", "}}", currentLine, false);

                        if(count.length() > 1) {
                            count = searchValueOf(en_text, "rarity count=", "|", currentLine, false);
                        }
                    }

                    rar = switch (rarity) {
                        case "Diamond" -> switch (count) {
                            case "1" -> Rarity.ONE_DIAMOND;
                            case "2" -> Rarity.TWO_DIAMOND;
                            case "3" -> Rarity.THREE_DIAMOND;
                            case "4" -> Rarity.FOUR_DIAMOND;
                            default -> null;
                        };
                        case "Star" -> switch (count) {
                            case "1" -> Rarity.ONE_STAR;
                            case "2" -> Rarity.TWO_STAR;
                            case "3" -> Rarity.THREE_STAR;
                            default -> null;
                        };
                        case "Shiny" -> switch (count) {
                            case "1" -> Rarity.SHINY_ONE;
                            case "2" -> Rarity.SHINY_TWO;
                            default -> null;
                        };
                        case "Crown" -> Rarity.CROWN;

                        default -> null;
                    };
                }

                if(rar == null) {
                    throw new RuntimeException("Rarity count is not valid, check Bulbapedia code");
                }

                int number = Integer.parseInt(searchValueOf(en_text, "number=", "/", currentLine, false));

                String pack = searchValueOf(en_text, "|pack=", "|", currentLine, true);
                ArrayList<Booster> boosters;
                if (pack == null || pack.equals("N/A") || !exp.hasMultipleBoosters() || pack.equals("TBD") || pack.equals("—")) {
                    boosters = new ArrayList<>();
                    boosters.add(Booster.NONE);
                } else if (pack.contains("Vol.")) {
                    boosters = new ArrayList<>();
                    boosters.add(Booster.OTHER);
                } else if(pack.equals("Any")) {
                    boosters = Booster.getBoostersFromExpansion(exp);
                } else
                {
                    boosters = new ArrayList<>();
                    boosters.add(Booster.getBoosterFromName(PokeData.getFrenchNameFromEnglish(pack, "booster name"), "card booster"));
                }

                boolean isReused = rar.getIllustrationKeyword().equals("standard ") && en_text.contains("illustration was first featured");
                //TODO : Trouver comment faire pour vérifier si chaque version est réutilisée ou pas
                /*System.out.println("This card's " + rar.getIllustrationKeyword() + "illustration was first featured");
                boolean isReused = en_text.contains("This card's " + rar.getIllustrationKeyword() + " illustration was first featured");*/
                //System.out.println(isReused);

                m_specs.add(new CardSpecs(exp, rar, number, illustrator.removeFirst(), boosters, isReused));
            }

            currentLine = en_text.indexOf("{{TCG Card Infobox", currentLine+1);
        }
    }

    /**
     * Used to get the Poképedia title of all versions of the card
     * @return a list of Strings containing the titles used to create/update Poképedia articles for the cards
     */
    public ArrayList<String> getPagesNames()
    {
        ArrayList<String> names = new ArrayList<>();
        for(CardSpecs spec : m_specs)
        {
            names.add(getPageName(spec));
        }
        return names;
    }

    /**
     * Used to get the Poképedia title of a specific versions of the card
     * @param spec version of the card
     * @return the title used to create/update the Poképedia article for this specific card
     */
    private String getPageName(CardSpecs spec)
    {
        return(m_frName + m_category.getTitleBonus() + " (" + spec.getExpansionFrName() + " " + spec.getNbrCardToString() + ")");
    }

    /**
     * Used to get the Poképedia title of all versions of the card from a specific Expansion (useless now that you can make sure
     * the program won't update existing cards)
     * @param exp Expansion you want the titles for
     * @return a list of Strings containing the titles used to create/update Poképedia articles for the cards in this expansion (might be empty)
     */
    @Deprecated
    public ArrayList<String> getPagesNames(Expansion exp)
    {
        ArrayList<String> names = new ArrayList<>();
        for(CardSpecs spec : m_specs)
        {
            if(exp == spec.getExpansion()) {
                names.add(getPageName(spec));
            }
        }
        return names;
    }

    /**
     * Used to generate the raw text of the Poképedia articles for all versions of the card
     * @param en_title name of Bulbapedia's article for the interwiki link
     * @return a list of Strings of the raw text that can be used to create/update the different articles
     */
    public ArrayList<String> getPokepediaCodes(String en_title)
    {
        ArrayList<String> codes = new ArrayList<>();
        for(CardSpecs spec : m_specs)
        {
            codes.add(getPokepediaCode(spec, en_title));
        }
        return codes;
    }

    /**
     * Used to generate the raw text of the Poképedia articles for the versions of the card that belong to exp
     * @param en_title name of Bulbapedia's article for the interwiki link
     * @param exp Expansion you want the raw text for
     * @return a list of Strings of the raw text that can be used to create/update the different articles
     */
    @Deprecated
    public ArrayList<String> getPokepediaCodes(String en_title, Expansion exp)
    {
        ArrayList<String> codes = new ArrayList<>();
        for(CardSpecs spec : m_specs)
        {
            if(exp == spec.getExpansion()) {
                codes.add(getPokepediaCode(spec, en_title));
            }
        }
        return codes;
    }

    /**
     * Used to generate the raw text of the Poképedia article for a specific version of the card
     * @param spec version of the card
     * @param en_title name of Bulbapedia's article for the interwiki link
     * @return a String of the raw text of the specific card that can be used to create/update its article
     */
    private String getPokepediaCode(CardSpecs spec, String en_title)
    {

        String code = "{{Édité par robot}}\n\n{{Article carte\n" + makeInfobox(spec) + "\n" +
                makeFacultes() + makeAnecdotes(spec);

        if(m_specs.size() > 1)
        {
            code += makeCartesIdentiques(spec);
        }
        code += "}}\n\n";

        int nameEnd = en_title.indexOf(" (");
        code += "[[en:" + en_title.substring(0, nameEnd) + " (" + spec.getExpansionEnName() + " " + spec.getNbrCard() + ")]]\n";

        return code;
    }

    /**
     * Generates the "Infobox" section of a Poképedia article for a specific version of the card
     * @param spec version of the card
     * @return a String containing the "Infobox" section
     */
    private String makeInfobox(CardSpecs spec)
    {
        StringBuilder code = new StringBuilder("<!-- Infobox -->\n");
        code.append(m_category.makeNameSection(m_enName, m_frName, m_jpName));

        code.append("\n| extension=").append(spec.getExpansionFrName()).append("\n| jeu=jccp\n| numerocarte=")
                .append(spec.getNbrCardToString()).append("\n| maxsetcarte=").append(spec.getExpansionSize())
                .append("\n| rareté=").append(spec.getRarityName());

        if(spec.isSecret())
        {
            code.append("\n| secrète=oui");
        }

        code.append(m_category.makeInfobox());

        if(spec.isSecret())
        {
            code.append("\n| full-art=oui");
        }

        code.append(m_category.makeCategorySection());

        code.append("\n| illus=").append(spec.getIllustrator()).append("\n");

        return code.toString();
    }

    /**
     * Generates the "Facultés" section of a Poképedia article for any version of the card
     * @return a String containing the "Facultés" section
     */
    private String makeFacultes()
    {
        return "<!-- Facultés -->\n" + m_category.makeFaculties(m_frName);
    }

    /**
     * Generates the "Anecdotes" section of a Poképedia article for a specific version of the card
     * @param spec version of the card
     * @return a String containing the "Anecdotes" section, or an empty String if there are no anecdotes
     */
    private String makeAnecdotes(CardSpecs spec)
    {
        String code = "\n<!-- Anecdotes -->\n" + spec.getCodeBoosters();
        if(spec.isFromSpecialExpansion() && m_specs.size() >= 2)
        {
            String originalName = getPageName(m_specs.getFirst());
            code += "| réédition=" + originalName + "\n| réédition-illustration=différente\n";
        }
        if(spec.illustrationIsReused())
        {
            code += "| illustration-réutilisée={{?}}\n";
        }

        code += m_category.makeAnecdotes();

        if(spec.isShiny())
        {
            code += "| chromatique=oui\n";
        }

        if(code.equals("\n<!-- Anecdotes -->\n"))
        {
            return "";
        }
        return code;
    }

    /**
     * Generates the "Cartes identiques" section of a Poképedia article for a specific version of the card
     * @param spec version of the card
     * @return a String containing the "Cartes identiques" section, or an empty String if there are no identical card within the Expansion
     */
    private String makeCartesIdentiques(CardSpecs spec)
    {
        StringBuilder code = new StringBuilder("\n<!-- Cartes identiques -->\n");
        int counter = 1;
        boolean isEmpty = true;
        for(String name : getPagesNames())
        {
            if(name.equals(getPageName(spec)) || !name.contains(spec.getExpansionFrName())) continue;

            code.append("| carte-identique");
            if(counter != 1)
            {
                code.append(counter);
            }
            code.append("=").append(name).append("\n");
            isEmpty = false;
            counter++;
        }
        if(isEmpty)
        {
            return "";
        }
        return code.toString();
    }
}
