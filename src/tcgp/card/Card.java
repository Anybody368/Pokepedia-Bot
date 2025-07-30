package tcgp.card;

import tcgp.Dictionary;
import tcgp.category.*;
import tcgp.category.decorator.RegionalForm;
import tcgp.category.decorator.UltraBeast;
import tcgp.category.pokemon.PokeEXStrategy;
import tcgp.category.pokemon.PokemonStrategy;
import tcgp.category.trainer.FossilStrategy;
import tcgp.category.trainer.ItemStrategy;
import tcgp.category.trainer.SupporterStrategy;
import tcgp.category.trainer.ToolStrategy;
import tcgp.enums.Booster;
import tcgp.enums.Expansion;
import tcgp.enums.Rarity;
import tcgp.enums.TCGType;
import utilitaire.ElementNotFoundException;
import utilitaire.PokeData;
import utilitaire.Region;
import utilitaire.Util;

import java.util.ArrayList;

import static utilitaire.Util.searchValueOf;

public class Card {
    private String m_frName;
    private String m_enName;
    private String m_jpName;
    private final CategoryStrategy m_category;
    private final ArrayList<CardSpecs> m_specs = new ArrayList<>(5);

    /**
     * Remplis les informations d'une carte depuis la page de Bulbapédia
     *
     * @param en_text : Contenu de la page de Bulbapedia
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
                    default -> null;
                };
            }
        }

        fillNames(en_text);
        fillRest(en_text);
    }

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
                prevolution = PokeData.getFrenchName(enPrevo, "pre-evolution regional Pokémon") + " " + prevoRegion.getFrAdjective();
            } else {
                prevolution = PokeData.getFrenchName(enPrevo, "pre-evolution Pokémon");
            }
        }

        ArrayList<CardAttack> attacks = fillAttacks(en_text);

        String name = searchValueOf(en_text, "|en name=", false);
        if(name.contains("{{TCGP Icon|ex}}"))
        {
            category = new PokeEXStrategy(type, weakness, hp, stage, retreat, prevolution, hasAbility, attacks);
        } else {
            category = new PokemonStrategy(type, weakness, hp, stage, retreat, prevolution, hasAbility, attacks);
        }

        Region region = Region.findRegionalFromEn(m_enName);
        if(region != null) {
            category = new RegionalForm(category, region);
        }

        if(en_text.contains("{{Cardtext/UltraBeast")) {
            category = new UltraBeast(category);
        }

        return category;
    }

    private ArrayList<CardAttack> fillAttacks(String en_text)
    {
        ArrayList<CardAttack> attacks = new ArrayList<>();
        int currentLine = 0;
        while ((currentLine = en_text.indexOf("{{Cardtext/Attack", currentLine+1)) != -1)
        {
            String damage = searchValueOf(en_text, "|damage=", currentLine, false);
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
            boolean hasEffect = !(searchValueOf(en_text, "|effect=", currentLine, false).isBlank());
            attacks.add(new CardAttack(damage, energies, hasEffect));
        }

        return attacks;
    }

    private void fillNames(String en_text) {

        if(m_enName.contains("{{TCGP Icon|ex}}"))
        {
            m_enName = m_enName.substring(0, m_enName.length()-17);
        }

        if(m_category.isPokemon()) {
            if(m_category instanceof RegionalForm) {
                String name = m_enName.substring(((RegionalForm) m_category).getRegionEnSize());
                m_frName = PokeData.getFrenchName(name, "regional Pokémon name") + " " + ((RegionalForm) m_category).getFrAdjective();
            } else {
                m_frName = PokeData.getFrenchName(m_enName, "Pokémon name");
            }
        }
        else
        {
            m_frName = Dictionary.getTranslation(m_enName);
        }

        m_jpName = searchValueOf(en_text, "|ja name=", false).split("[{]")[0];
    }

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
                Rarity rar;
                String rarity = searchValueOf(en_text, "rarity=", "|", currentLine, false);
                rar = switch (rarity) {
                    case "Diamond" -> switch (searchValueOf(en_text, "rarity count=", "|", currentLine, false)) {
                        case "1" -> Rarity.ONE_DIAMOND;
                        case "2" -> Rarity.TWO_DIAMOND;
                        case "3" -> Rarity.THREE_DIAMOND;
                        case "4" -> Rarity.FOUR_DIAMOND;
                        default -> null;
                    };
                    case "Star" -> switch (searchValueOf(en_text, "rarity count=", "|", currentLine, false)) {
                        case "1" -> Rarity.ONE_STAR;
                        case "2" -> Rarity.TWO_STAR;
                        case "3" -> Rarity.THREE_STAR;
                        default -> null;
                    };
                    case "Shiny" -> switch (searchValueOf(en_text, "rarity count=", "|", currentLine, false)) {
                        case "1" -> Rarity.SHINY_ONE;
                        case "2" -> Rarity.SHINY_TWO;
                        default -> null;
                    };
                    case "Crown" -> Rarity.CROWN;

                    default -> Rarity.NONE;
                };

                if(rar == null) {
                    throw new RuntimeException("Rarity count is not valid, check Bulbapedia code");
                }

                int number = Integer.parseInt(searchValueOf(en_text, "number=", "/", currentLine, false));

                String pack = searchValueOf(en_text, "|pack=", "|", currentLine, false);
                ArrayList<Booster> boosters;
                if (pack.equals("N/A") || !exp.hasMultipleBoosters() || pack.equals("TBD")) {
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
                    boosters.add(Booster.getBoosterFromName(PokeData.getFrenchName(pack, "booster name"), "card booster"));
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

    public ArrayList<String> getPagesNames()
    {
        ArrayList<String> names = new ArrayList<>();
        for(CardSpecs spec : m_specs)
        {
            names.add(getPageName(spec));
        }
        return names;
    }

    private String getPageName(CardSpecs spec)
    {
        return(m_frName + m_category.getTitleBonus() + " (" + spec.getExtensionFrName() + " " + spec.getNbrCardToString() + ")");
    }

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

    public ArrayList<String> getPokepediaCodes(String en_title)
    {
        ArrayList<String> codes = new ArrayList<>();
        for(CardSpecs spec : m_specs)
        {
            codes.add(getPokepediaCode(spec, en_title));
        }
        return codes;
    }

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
        code += "[[en:" + en_title.substring(0, nameEnd) + " (" + spec.getExtensionEnName() + " " + spec.getNbrCard() + ")]]\n";

        return code;
    }

    private String makeInfobox(CardSpecs spec)
    {
        StringBuilder code = new StringBuilder("<!-- Infobox -->\n");
        code.append(m_category.makeNameSection(m_enName, m_frName, m_jpName));

        code.append("\n| extension=").append(spec.getExtensionFrName()).append("\n| jeu=jccp\n| numerocarte=")
                .append(spec.getNbrCardToString()).append("\n| maxsetcarte=").append(spec.getExtensionSize())
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

    private String makeFacultes()
    {
        return "<!-- Facultés -->\n" + m_category.makeFacultes();
    }

    private String makeAnecdotes(CardSpecs spec)
    {
        String code = "\n<!-- Anecdotes -->\n" + spec.getCodeBoosters();
        if(spec.isSpecialExtension() && m_specs.size() >= 2)
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

    private String makeCartesIdentiques(CardSpecs spec)
    {
        StringBuilder code = new StringBuilder("\n<!-- Cartes identiques -->\n");
        int counter = 1;
        boolean isEmpty = true;
        for(String name : getPagesNames())
        {
            if(name.equals(getPageName(spec)) || !name.contains(spec.getExtensionFrName())) continue;

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
