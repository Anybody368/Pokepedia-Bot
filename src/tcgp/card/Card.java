package tcgp.card;

import tcgp.category.*;
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
import utilitaire.PokeData;
import utilitaire.Util;

import java.util.ArrayList;
import java.util.Scanner;

import static utilitaire.Util.searchValueOf;

public class Card {
    private String m_frName;
    private String m_enName;
    private String m_jpName;
    private final CategoryStrategy m_category;
    private boolean m_isReused = false;
    private final ArrayList<CardSpecs> m_specs = new ArrayList<>(5);

    /**
     * Remplis les informations d'une carte depuis la page de Bulbapédia
     *
     * @param en_text : Contenu de la page de Bulbapedia
     */
    public Card(String en_text) {
        m_enName = searchValueOf(en_text, "|en name=");

        String type = searchValueOf(en_text, "TCG Card Infobox/", "/");
        if(type.equals("Pokémon"))
        {
            m_category = getPokeData(en_text);
        } else {
            if (m_enName.equals("Old Amber") || m_enName.endsWith(" Fossil")) {
                int hp = Integer.parseInt(searchValueOf(en_text, "|hp="));
                m_category = new FossilStrategy(hp);
            } else {
                String subType = searchValueOf(en_text, "|subtype=");
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

    private PokemonStrategy getPokeData(String en_text)
    {
        int hp = Integer.parseInt(searchValueOf(en_text, "|hp="));
        TCGType type = TCGType.typeFromEnglishName(searchValueOf(en_text, "|type="));
        TCGType weakness = TCGType.typeFromEnglishName(searchValueOf(en_text, "|weakness="));
        int retreat = Integer.parseInt(searchValueOf(en_text, "|retreat cost="));
        int stage = switch (searchValueOf(en_text, "|evo stage="))
        {
            case "Basic" -> 0;
            case "Stage 1" -> 1;
            case "Stage 2" -> 2;
            default -> -1;
        };
        boolean hasAbility = en_text.contains("{{Cardtext/Ability");

        String prevolution = null;
        if(en_text.contains("|prevo name="))
        {
            prevolution = PokeData.getFrenchName(Util.searchValueOf(en_text, "|prevo name="));
        }

        ArrayList<CardAttack> attacks = fillAttacks(en_text);

        String name = searchValueOf(en_text, "|en name=");
        if(name.contains("{{TCGP Icon|ex}}"))
        {
            return new PokeEXStrategy(type, weakness, hp, stage, retreat, prevolution, hasAbility, attacks);
        }
        return new PokemonStrategy(type, weakness, hp, stage, retreat, prevolution, hasAbility, attacks);
    }

    private ArrayList<CardAttack> fillAttacks(String en_text)
    {
        ArrayList<CardAttack> attacks = new ArrayList<>();
        int currentLine = 0;
        while ((currentLine = en_text.indexOf("{{Cardtext/Attack", currentLine+1)) != -1)
        {
            String damage = searchValueOf(en_text, "|damage=", currentLine);
            String[] energiesString = searchValueOf(en_text, "|cost=", currentLine).split("[{]{2}e[|]");
            ArrayList<TCGType> energies = new ArrayList<>();
            for(int i = 1; i < energiesString.length; i++)
            {
                energies.add(TCGType.typeFromEnglishName(energiesString[i].substring(0, energiesString[i].length()-2)));
            }
            boolean hasEffect = !(searchValueOf(en_text, "|effect=", currentLine).isBlank());
            attacks.add(new CardAttack(damage, energies, hasEffect));
        }

        return attacks;
    }

    private void fillNames(String en_text) {

        if(m_category instanceof PokeEXStrategy)
        {
            m_enName = m_enName.split(" ")[0];
        }

        if(m_category instanceof PokemonStrategy) {
            m_frName = PokeData.getFrenchName(m_enName);
        }
        else
        {
            System.out.println("Indiquez le nom français de la carte " + m_enName);
            Scanner scanner = new Scanner(System.in);
            m_frName = scanner.nextLine();
        }

        m_jpName = searchValueOf(en_text, "|ja name=").split("[{]")[0];
    }

    private void fillRest(String en_text)
    {
        //On vérifie si l'illustration de base est réutilisée (on considèrera que ça ne peut être que la version de base)
        m_isReused = en_text.contains("illustration was first featured");

        ArrayList<String> illustrator = new ArrayList<>(5);
        if(en_text.contains("\n|illustrator=") && !en_text.contains("|image set="))
        {
            illustrator.add(searchValueOf(en_text, "|illustrator="));
        } else {
            int illustLine = 0;
            while((illustLine = en_text.indexOf("{{TCG Card Infobox/Tabbed Image", illustLine+1)) != -1)
            {
                illustrator.add(searchValueOf(en_text, "illustrator=", "|", illustLine));
            }
        }

        int currentLine = en_text.indexOf("{{TCG Card Infobox/Expansion Header");
        Expansion exp = null;
        while(!illustrator.isEmpty())
        {
            if(searchValueOf(en_text, "Expansion ", "/", currentLine).equals("Header"))
            {
                exp = Expansion.ExpansionFromEnglishName(searchValueOf(en_text, "|", "}}", currentLine));
            } else {
                Rarity rar;
                String rarity = searchValueOf(en_text, "rarity=", "|", currentLine);
                rar = switch (rarity) {
                    case "Diamond" -> switch (searchValueOf(en_text, "rarity count=", "|", currentLine)) {
                        case "1" -> Rarity.ONE_DIAMOND;
                        case "2" -> Rarity.TWO_DIAMOND;
                        case "3" -> Rarity.THREE_DIAMOND;
                        case "4" -> Rarity.FOUR_DIAMOND;
                        default -> null;
                    };
                    case "Star" -> switch (searchValueOf(en_text, "rarity count=", "|", currentLine)) {
                        case "1" -> Rarity.ONE_STAR;
                        case "2" -> Rarity.TWO_STAR;
                        case "3" -> Rarity.THREE_STAR;
                        default -> null;
                    };
                    case "Crown" -> Rarity.CROWN;
                    default -> Rarity.NONE;
                };

                int number = Integer.parseInt(searchValueOf(en_text, "number=", "/", currentLine));

                String pack = searchValueOf(en_text, "|pack=", "|", currentLine);
                ArrayList<Booster> boosters;
                if(pack.equals("Any"))
                {
                    boosters = Booster.getBoostersFromExpansion(exp);
                } else if (pack.contains("Vol.")) {
                    boosters = new ArrayList<>();
                    boosters.add(Booster.OTHER);
                } else if (pack.equals("N/A")) {
                    boosters = new ArrayList<>();
                    boosters.add(Booster.NONE);
                } else
                {
                    boosters = new ArrayList<>();
                    boosters.add(Booster.getBoosterFromName(PokeData.getFrenchName(pack)));
                }

                m_specs.add(new CardSpecs(exp, rar, number, illustrator.removeFirst(), boosters));
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
        if(m_category instanceof PokeEXStrategy)
        {
            return(m_frName + "-ex (" + spec.getExtensionName() + " " + spec.getNbrCardToString() + ")");
        }
        else {
            return(m_frName + " (" + spec.getExtensionName() + " " + spec.getNbrCardToString() + ")");
        }
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
                makeFacultes() + "\n" + makeAnecdotes(spec);

        if(m_specs.size() > 1)
        {
            code += makeCartesIdentiques(spec);
        }
        code += "}}\n\n";

        if(!spec.isSecret())
        {
            code += "[[en:" + en_title + "]]\n";
        }

        return code;
    }

    private String makeInfobox(CardSpecs spec)
    {
        StringBuilder code = new StringBuilder("<!-- Infobox -->\n");
        code.append(m_category.makeNameSection(m_enName, m_frName, m_jpName));

        code.append("\n| extension=").append(spec.getExtensionName()).append("\n| jeu=jccp\n| numerocarte=")
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
        String code = "<!-- Anecdotes -->\n" + spec.getCodeBoosters();
        if(spec.isSpecialExtension() && m_specs.size() >= 2)
        {
            String originalName = getPageName(m_specs.getFirst());
            code += "| réédition=" + originalName + "\n| réédition-illustration=différente\n";
        }
        if((!spec.isSecret()) && m_isReused)
        {
            code += "| illustration-réutilisée={{?}}\n";
        }

        code += m_category.makeAnecdotes();
        return code;
    }

    private String makeCartesIdentiques(CardSpecs spec)
    {
        StringBuilder code = new StringBuilder("\n<!-- Cartes identiques -->\n");
        int counter = 1;
        boolean isEmpty = true;
        for(String name : getPagesNames())
        {
            if(name.equals(getPageName(spec)) || !name.contains(spec.getExtensionName())) continue;

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
