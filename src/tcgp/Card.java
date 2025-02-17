package tcgp;

import utilitaire.PokeData;
import utilitaire.Util;

import java.util.ArrayList;
import java.util.Scanner;

import static utilitaire.Util.searchValueOf;

public class Card {
    private String m_frName;
    private String m_enName;
    private String m_jpName;
    private TCGType m_type;
    private TCGType m_weakness = null;
    private int m_HP;
    private int m_stage;
    private int m_retreat;
    private Category m_category;
    private Category m_subCategory = null;
    private Category m_subSubCategory = null;
    private String m_prevolution = null;
    private boolean m_hasAbility;
    private final ArrayList<CardAttack> m_attacks = new ArrayList<>();
    private final ArrayList<CardSpecs> m_specs = new ArrayList<>(5);

    /**
     * Remplis les informations d'une carte depuis la page de Bulbapédia
     *
     * @param en_text : Contenu de la page de Bulbapedia
     */
    public Card(String en_text) {
        fillCategories(en_text);
        fillNames(en_text);
        fillRest(en_text);
        if(m_category == Category.POKEMON)
        {
            fillCapacities(en_text);
        }
    }

    private void fillNames(String en_text) {

        m_enName = searchValueOf(en_text, "|en name=");
        if(m_subCategory == Category.EX)
        {
            m_enName = m_enName.split(" ")[0];
        }

        if(m_category == Category.POKEMON) {
            m_frName = PokeData.getFrenchName(m_enName);
        }
        else
        {
            System.out.println("Indiquez le nom français de la carte " + m_enName);
            Scanner scanner = new Scanner(System.in);
            m_frName = scanner.nextLine();

            if(m_subCategory == Category.ITEM)
            {
                String[] split = m_enName.split(" ");
                if(m_enName.equals("Old Amber") || (split.length == 2 && split[1].equals("Fossil")))
                {
                    m_subSubCategory = Category.FOSSIL;
                }
            }
        }
        m_jpName = searchValueOf(en_text, "|ja name=").split("[{]")[0];
    }

    private void fillCategories(String en_text)
    {
        int indexStart = en_text.indexOf("{{TCG Card Infobox")+19;
        int indexEnd = en_text.indexOf("/", indexStart);
        String infoboxType = en_text.substring(indexStart, indexEnd);

        if(infoboxType.equals("Pokémon"))
        {
            m_category = Category.POKEMON;
            String name = searchValueOf(en_text, "|en name=");
            if(name.contains("{{TCGP Icon|ex}}"))
            {
                m_subCategory = Category.EX;
            }
        }
        else
        {
            m_category = Category.DRESSEUR;
            String subCategory = searchValueOf(en_text, "|subtype=");
            switch (subCategory) {
                case "Item" -> m_subCategory = Category.ITEM;
                case "Supporter" -> m_subCategory = Category.SUPPORTER;
                case "Pokémon Tool" -> m_subCategory = Category.TOOL;
            }
        }
    }

    private void fillRest(String en_text)
    {
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
                }
                else
                {
                    boosters = new ArrayList<>();
                    boosters.add(Booster.getBoosterFromName(PokeData.getFrenchName(pack)));
                }

                m_specs.add(new CardSpecs(exp, rar, number, illustrator.removeFirst(), boosters));
            }

            currentLine = en_text.indexOf("{{TCG Card Infobox", currentLine+1);
        }

        //Pour les fossiles, on récupère les PV
        if(m_subSubCategory == Category.FOSSIL)
        {
            m_HP = Integer.parseInt(searchValueOf(en_text, "|hp="));
        }

        //Et enfin tous les trucs pour les Pokémon
        if(m_category == Category.POKEMON)
        {
            m_HP = Integer.parseInt(searchValueOf(en_text, "|hp="));
            m_type = TCGType.typeFromEnglishName(searchValueOf(en_text, "|type="));
            m_weakness = TCGType.typeFromEnglishName(searchValueOf(en_text, "|weakness="));
            m_retreat = Integer.parseInt(searchValueOf(en_text, "|retreat cost="));
            m_stage = switch (searchValueOf(en_text, "|evo stage="))
            {
                case "Basic" -> 0;
                case "Stage 1" -> 1;
                case "Stage 2" -> 2;
                default -> -1;
            };
        }

        //Si c'est un Pokémon Évolué
        if(en_text.contains("|prevo name="))
        {
            String name =
            m_prevolution = PokeData.getFrenchName(Util.searchValueOf(en_text, "|prevo name="));
        }
    }

    private void fillCapacities(String en_text)
    {
        m_hasAbility = en_text.contains("{{Cardtext/Ability");
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
            m_attacks.add(new CardAttack(damage, energies));
        }
    }

    public ArrayList<String> getPagesNames()
    {
        ArrayList<String> names = new ArrayList<>();
        for(CardSpecs spec : m_specs)
        {
            if(m_subCategory == Category.EX)
            {
                names.add(m_frName + "-ex (" + spec.getExtensionName() + " " + spec.getNbrCardToString() + ")");
            }
            else {
                names.add(m_frName + " (" + spec.getExtensionName() + " " + spec.getNbrCardToString() + ")");
            }
        }
        return names;
    }

    public ArrayList<String> getPagesNames(Expansion exp)
    {
        ArrayList<String> names = new ArrayList<>();
        for(CardSpecs spec : m_specs)
        {
            if(exp == spec.getExpansion()) {
                if (m_subCategory == Category.EX) {
                    names.add(m_frName + "-ex (" + spec.getExtensionName() + " " + spec.getNbrCardToString() + ")");
                } else {
                    names.add(m_frName + " (" + spec.getExtensionName() + " " + spec.getNbrCardToString() + ")");
                }
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
        StringBuilder code = new StringBuilder("{{Édité par robot}}\n\n");
        code.append("{{Ruban Carte JCC\n| extension=").append(spec.getExtensionName()).append("\n| jeu=jccp\n");
        if(spec.getNbrCard() != 1)
        {
            code.append("| carteprécédente={{?}}\n| pageprécédente={{?}} (").append(spec.getExtensionName()).append(" ")
                    .append(spec.getRelativeNbrCard(-1)).append(")\n");
        }
        if(!spec.isLastCard())
        {
            code.append("| cartesuivante={{?}}\n| pagesuivante={{?}} (").append(spec.getExtensionName()).append(" ")
                    .append(spec.getRelativeNbrCard(1)).append(")\n");
        }
        code.append("}}\n");

        code.append(makeInfobox(spec)).append("\n");
        code.append(makeBody(spec)).append("\n");
        code.append("[[en:").append(en_title).append("]]\n");

        return code.toString();
    }

    private String makeInfobox(CardSpecs spec)
    {
        StringBuilder code = new StringBuilder("{{Infobox Carte\n");
        code.append(makeNameSection());

        code.append("\n| extension=").append(spec.getExtensionName()).append("\n| jeu=jccp\n| numerocarte=")
                .append(spec.getNbrCardToString()).append("\n| maxsetcarte=").append(spec.getExtensionSize())
                .append("\n| rareté=").append(spec.getRarityName());

        if(spec.isSecret())
        {
            code.append("\n| secrète=oui");
        }

        if(m_category == Category.POKEMON)
        {
            code.append("\n| type=").append(m_type.getNom()).append("\n| pv=").append(m_HP).append("\n| stade=").append(m_stage)
                    .append("\n| retraite=").append(m_retreat);
            if(m_weakness != null)
            {
                code.append("\n| faiblesse=").append(m_weakness.getNom());
            }
        }
        if(m_subSubCategory == Category.FOSSIL)
        {
            code.append("\n| pv=").append(m_HP);
        }

        code.append("\n| catégorie=").append(m_category.getName());
        if(m_subCategory != null)
        {
            code.append("\n| sous-catégorie=").append(m_subCategory.getName());
            if(m_subSubCategory != null)
            {
                code.append("\n| sous-catégorie2=").append(m_subSubCategory.getName());
            }
        }
        code.append("\n| illus=").append(spec.getIllustrator()).append("\n}}");

        return code.toString();
    }

    private String makeNameSection()
    {
        if(m_subCategory == Category.EX)
        {
            final String symbEx = "{{Symbole JCC|ex JCCP}}";
            return("| nom=" + m_frName + symbEx + "\n| nomréel=" + m_frName + "\n| nomen=" + m_enName + symbEx +
                    "\n| nomja=" + m_jpName + symbEx);
        }

        //Tant qu'il n'y a pas de fossiles spéciaux
        if(m_subSubCategory == Category.FOSSIL)
        {
            return("| nom=" + m_frName + "\n| sujet=" + m_frName + "\n| nomen=" + m_enName + "\n| nomja=" + m_jpName);
        }

        return("| nom=" + m_frName + "\n| nomen=" + m_enName + "\n| nomja=" + m_jpName);
    }

    private String makeBody(CardSpecs spec)
    {
        StringBuilder code = new StringBuilder(makeBodyIntro(spec));

        code.append("\n\n__TOC__\n== Facultés ==\n\n");
        if(m_category == Category.POKEMON) {
            code.append(makeCodeAttacks());
            if (m_subCategory == Category.EX) {
                code.append("=== Règle pour les [[Pokémon-ex]] ===\n\nQuand votre Pokémon-ex est mis [[K.O. (JCC)|K.O.]], votre adversaire gagne 2 points.\n\n");
            }
        } else if (m_subSubCategory == Category.FOSSIL) {
            code.append("{{Infobox Faculté (JCC)\n| description=Jouez cette carte comme si c'était un Pokémon {{type|incolore|jcci}} de base avec ")
                    .append(m_HP).append("""
                             [[PV]]. N'importe quand pendant votre tour, vous pouvez défausser cette carte du jeu. Cette carte ne peut pas battre en [[retraite]].
                            }}
                            
                            === Règle supplémentaire ===
                            {{Infobox Faculté (JCC)
                            | description=Vous pouvez jouer autant de [[carte Objet|cartes Objet]] que vous le voulez pendant votre tour.
                            }}""");
        } else if (m_subCategory == Category.ITEM) {
            code.append("{{?}}\n\n=== Règle supplémentaire ===\n{{Infobox Faculté (JCC)\n| description=Vous pouvez jouer autant")
                    .append(" de [[carte Objet|cartes Objet]] que vous le voulez pendant votre tour.\n}}\n\n");
        } else if (m_subCategory == Category.SUPPORTER) {
            code.append("{{?}}\n\n=== Règle supplémentaire ===\n{{Infobox Faculté (JCC)\n| description=Vous ne pouvez jouer qu'une")
                    .append(" seule [[carte Supporter]] pendant votre tour.\n}}\n\n");
        } else if (m_subCategory == Category.TOOL) {
            code.append("{{?}}\n\n=== Règle supplémentaire ===\n{{Infobox Faculté (JCC)\n| description=Les Outils Pokémon")
                    .append(" se jouent en les attachant à vos Pokémon. Vous ne pouvez en attacher qu'un à un Pokémon. ")
                    .append("L'Outil Pokémon lui reste attaché.\n}}\n\n");
        }

        if(m_category == Category.POKEMON && m_subCategory != Category.EX)
        {
            code.append("== Description du Pokémon ==\n\n:''{{?}}''\nCette description est identique à celle de {{Jeu|{{?}}}}.\n\n");
        }

        code.append("== Anecdote ==\n\n* Cette carte peut être obtenue dans ").append(spec.getCodeBoosters())
                .append(" de l'[[extension]] [[").append(spec.getExtensionName()).append("]].\n\n").append(makeVoirAussi(spec));

        return code.toString();
    }

    private String makeBodyIntro(CardSpecs spec)
    {
        if(m_subCategory == Category.EX)
        {
            String res = ("'''"+m_frName+"{{Symbole JCC|ex JCCP}}''' est une [[carte Pokémon|carte]] [[Pokémon-ex]] de l'[[extension]] [["
                    +spec.getExtensionName()+"]], à l'effigie du Pokémon [["+m_frName+"]].");

            if(m_prevolution != null)
            {
                res += " Elle doit être posée sur un {{Requête JCC|[[Nom de carte formaté::"+ m_prevolution +"]]|texte="+ m_prevolution +"|jeu=jccp}} pour pouvoir être jouée.";
            }
            return res;
        }
        if(m_category == Category.POKEMON)
        {
            String res = ("'''"+m_frName+"''' est une [[carte Pokémon]] de l'[[extension]] [["
                +spec.getExtensionName()+"]], à l'effigie du Pokémon [["+m_frName+"]].");
            if(m_prevolution != null)
            {
                res += " Elle doit être posée sur un {{Requête JCC|[[Nom de carte formaté::"+ m_prevolution +"]]|texte="+ m_prevolution +"|jeu=jccp}} pour pouvoir être jouée.";
            }
            return res;
        }
        if(m_subCategory == Category.ITEM)
        {
            return("'''"+m_frName+"''' est une [[carte Dresseur (JCC)|carte]] [[Carte Objet|Objet]] de l'[[extension]] [["+spec.getExtensionName()+"]].");
        }
        if(m_subCategory == Category.SUPPORTER)
        {
            return("'''"+m_frName+"''' est une [[carte Dresseur (JCC)|carte]] [[Carte Supporter|Supporter]] de l'[[extension]] [["+spec.getExtensionName()+"]].");
        }
        if(m_subCategory == Category.TOOL)
        {
            return("'''"+m_frName+"''' est une [[carte Dresseur (JCC)|carte]] [[Carte Outil Pokémon|Outil Pokémon]] de l'[[extension]] [["+spec.getExtensionName()+"]].");
        }
        System.err.println("Attention, cas non pris en charge dans makeBodyIntro");
        return "T'as oublié un cas frère";
    }

    private String makeCodeAttacks() {
        StringBuilder code = new StringBuilder();
        if(m_hasAbility)
        {
            code.append("{{Infobox Faculté (JCC)\n| faculté=Talent\n| nom={{?}}\n| description={{?}}\n}}\n\n");
        }
        if(m_attacks.size() == 1) {
            code.append("=== [[Attaque (JCC)|Attaque]] ===\n\n");
        } else {
            code.append("=== [[Attaque (JCC)|Attaques]] ===\n\n");
        }

        for(CardAttack attack : m_attacks)
        {
            code.append("{{Infobox Faculté (JCC)\n").append(attack.getEnergyCostCode()).append("\n| nom={{?}}\n| dégâts=")
                    .append(attack.getDamage()).append("\n}}\n\n");
        }

        return code.toString();
    }

    private String makeVoirAussi(CardSpecs spec)
    {
        String code = "== Voir aussi ==\n\n";
        if(m_category == Category.POKEMON)
        {
            code += "* Pour plus d'informations sur le Pokémon : [["+ m_frName +"]].\n";
        }
        code += "* Pour plus d'informations sur l'extension : [["+ spec.getExtensionName() +"]].\n";
        if(m_subCategory == Category.EX)
        {
            code += "* [[Pokémon-ex]].\n";
        }
        code += "\n{{Bandeau JCC}}\n";

        if(spec.isSecret())
        {
            code += "\n[[Catégorie:Carte Full Art]]\n";
        }

        return code;
    }
}