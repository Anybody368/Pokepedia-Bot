package sleep.pokemon;

import sleep.dodos.SleepStyle;
import sleep.dodos.Island;
import sleep.dodos.TypesDodo;
import sleep.UtilSleep;
import sleep.bouffe.IngredientPoke;
import utilitaire.Page;
import utilitaire.PokeData;
import utilitaire.PokeTypes;
import utilitaire.Util;

import java.util.*;

import static utilitaire.Wiki.POKEPEDIA;

public class Pokemon {
    private final String m_name;
    protected final String m_numDex;
    private final PokeTypes m_type;
    private final TypesDodo m_sleepType;
    private final Specialites m_speciality;
    private final ArrayList<IngredientPoke> m_ingredientList;
    private final ArrayList<SleepStyle> m_sleepList;
    private final ArrayList<Island> m_zones;
    private int m_freqHour;
    private int m_freqMin;
    private int m_freqSec;
    private final int m_storage;
    protected final Competences m_ability;
    private final int m_recruitPoints;
    private final String m_candy;
    protected final Imagery m_imageryType;

    protected final String[] m_pages = {
            "Liste des Pokémon de soutien de Pokémon Sleep",
            "Liste des styles de dodo de Pokémon Sleep",
            "Ingrédient (Pokémon Sleep)",
            "Bonbon (Pokémon Sleep)"
    };

    /**
     * Constructeur (à peine surchargé) des données d'un Pokémon dans Pokémon sleep sans input supplémentaire
     * @param nom : Nom du Pokémon
     * @param numDex : Numéro du Pokémon dans le Pokédex National
     * @param type : Type du Pokémon dans sleep
     * @param dodoType : Catégorie de sleepStyle du Pokémon (Ptidodo, Bondodo, ou Grododo)
     * @param specialite : Baies, Ingrédients, ou Compétences
     * @param ingredients : Liste des ingrédients du Pokémon à créer au préalable
     * @param sleepStyles : Liste des dodos du Pokémon pré-remplis
     * @param iles : Iles où le Pokémon peut être trouvé
     * @param frequence : Fréquence de base du Pokémon au format "h:min:sec" ou "min:sec"
     * @param capacite : Capacité maximale du Pokémon (combien d'objets peut-il tenir par défaut)
     * @param competence : Compétence Principale du Pokémon
     * @param ptsAmitie : Combien de Pokébiscuits max faut-il pour devenir ami avec ce Pokémon
     * @param bonbon : Nom de Pokémon utilisé pour les bonbons de celui-ci (utile pour les Pokémon évolués)
     */
    public Pokemon(String nom, int numDex, PokeTypes type, TypesDodo dodoType, Specialites specialite, ArrayList<IngredientPoke> ingredients, ArrayList<SleepStyle> sleepStyles, ArrayList<Island> iles, String frequence, int capacite, Competences competence, int ptsAmitie, String bonbon, Imagery imageryType)
    {
        m_name = nom;
        m_numDex = Util.numberToPokepediaDexFormat(numDex);
        m_type = type;
        m_sleepType = dodoType;
        m_speciality = specialite;
        fillFrequency(frequence);
        m_storage = capacite;
        m_ability = competence;
        m_recruitPoints = ptsAmitie;
        m_candy = bonbon;
        m_zones = iles;
        m_sleepList = sleepStyles;
        m_ingredientList = ingredients;
        m_imageryType = imageryType;
    }

    /**
     * Remplit les différentes valeurs de la fréquence à partir du String passé en paramètre
     * @param freq : String au format "min:sec" ou "h:min:sec"
     */
    private void fillFrequency(String freq)
    {
        String[] details = freq.split(":");
        switch (details.length)
        {
            case 2 :
                m_freqHour = 0;
                m_freqMin = Integer.parseInt(details[0]);
                m_freqSec = Integer.parseInt(details[1]);
                break;
            case 3 :
                m_freqHour = Integer.parseInt(details[0]);
                m_freqMin = Integer.parseInt(details[1]);
                m_freqSec = Integer.parseInt(details[2]);
                break;
            default: {
                System.err.println("ERREUR : Format de fréquence invalide");
                System.exit(1);
            }
        }
    }

    /**
     * Fonction à appeler pour ajouter le Pokémon aux pages du Wiki UNIQUEMENT s'il n'y a encore rien pour lui
     *
     * @return all Wiki pages that should be modified for this Pokémon alongside the new text
     */
    public HashMap<Page, String> getWikiModifications()
    {
        HashMap<Page, String> wikiPages = new HashMap<>();
        Page listeSoutien = new Page(m_pages[0], POKEPEDIA);
        Page listeDodo = new Page(m_pages[1], POKEPEDIA);
        Page listeIngredients = new Page(m_pages[2], POKEPEDIA);
        Page listeBonbons = new Page(m_pages[3], POKEPEDIA);
        Page imagery = new Page(getRegionalName() + "/Imagerie", POKEPEDIA);
        Page ability = new Page(m_ability.getCategory(), POKEPEDIA);

        wikiPages.put(listeSoutien, updateListePokeSoutien(listeSoutien));
        wikiPages.put(listeDodo, updateListeDodo(listeDodo));
        wikiPages.put(imagery, updateImageryPage(imagery));
        if(updateAbilityPage(ability) != null) {
            wikiPages.put(ability, updateAbilityPage(ability));
        }
        wikiPages.putAll(updateZones());

        //Si le pokémon n'est pas la forme de base de sa ligne évolutive, on ne l'ajoute pas à certaines pages.
        if(hasUniqueCandy()) {
            wikiPages.put(listeIngredients, updateIngredientsPage(listeIngredients));
            wikiPages.put(listeBonbons, updateCandyPage(listeBonbons));
        }

        return wikiPages;
    }

    /**
     * S'occupe d'ajouter les lignes d'un Pokémon à la page "Liste des Pokémon de soutien de Pokémon sleep"
     *
     * @return
     */
    protected String updateListePokeSoutien(Page listeSoutien)
    {
        final int LIGNES_INFORMATIONS = 11;

        String content = listeSoutien.getContent();
        ArrayList<String> lignes = new ArrayList<>(Arrays.asList(content.split("\n")));

        //On se place au niveau de la première ligne d'un Pokémon de Soutien du premier tableau, normalement "| 0001"
        int l = lignes.indexOf("! Points d'amitié requis") + 2;
        String ligneAct = lignes.get(l);
        //System.out.println(lignes.get(l-2) + "\n" + ligneAct);

        //On cherche l'endroit où le nouveau Pokémon doit être inséré en comparant les numéros de Pokédex
        while (ligneAct.substring(ligneAct.length()-4).compareTo(m_numDex) <= 0)
        {
            //Si jamais le numdex est le même que celui recherché
            if(ligneAct.substring(ligneAct.length()-4).equals(m_numDex)) {
                String temp = Util.incrementRowspan(ligneAct);
                //System.out.println(temp);
                lignes.set(l, temp);
            }

            l += LIGNES_INFORMATIONS;
            ligneAct = lignes.get(l);
            while(!ligneAct.matches("^\\| \\d{4}$") && !ligneAct.isEmpty())
            {
                l++;
                ligneAct = lignes.get(l);
            }

            ligneAct = lignes.get(l);

            //Si jamais le Pokémon doit être inséré à la toute fin du tableau
            if(ligneAct.isEmpty())
            {
                break;
            }
        }

        String[] ajout = getLignePokeRecap();
        lignes.addAll(l - 1, List.of(ajout));

        //Reconstruction du texte de la page afin de publier
        String newContenu = Util.wikicodeReconstruction(lignes);
        //System.out.println(newContenu);
        return newContenu;
    }

    protected String updateListeDodo(Page listeDodos)
    {
        String content = listeDodos.getContent();
        ArrayList<String> lignes = new ArrayList<>(Arrays.asList(content.split("\n")));

        //Même procédé pour le deuxième tableau
        int l = lignes.indexOf("! Récompenses");
        l += 2;
        String ligneAct = lignes.get(l);

        while(ligneAct.substring(28, 32).compareTo(m_numDex) <= 0)
        {
            l += 2;
            ligneAct = lignes.get(l);

            //Si jamais le Pokémon doit être inséré à la toute fin du tableau
            if(ligneAct.length() < 33)
            {
                break;
            }
        }

        String lignePoke = getLignePokeDodos();
        lignes.add(l, lignePoke);
        lignes.add(l+1, "");

        //Reconstruction du texte de la page afin de publier
        String newContenu = Util.wikicodeReconstruction(lignes);
        //System.out.println(newContenu);
        return newContenu;
    }

    /**
     * S'occupe de mettre à jour les pages des îles de Pokémon sleep avec les dodos du nouveau Pokémon
     *
     * @return a map containing the list of pages that need to be modified with their new text
     */
    protected HashMap<Page, String> updateZones()
    {
        HashMap<Page, String> wikiPages = new HashMap<>();

        for (Island ile : m_zones) {
            //Si le pokémon n'est pas dispo sur l'ile, on passe directement à la suivante
            if(!pokeDispoSurIle(ile))
            {
                continue;
            }

            Page pageIle = new Page(ile.getName(false), POKEPEDIA);
            String content = pageIle.getContent();
            ArrayList<String> lignes = new ArrayList<>(Arrays.asList(content.split("\n")));

            //Recherche de la section Description pour modifier les chiffres
            int l = lignes.indexOf("== Description ==");
            l += 3;
            String ligneAct = lignes.get(l);

            //mise à jour de la ligne contenant le nombre de Pokémon et de dodos disponibles
            ligneAct = Util.incrementValueInString(ligneAct, 1, 1);
            ligneAct = Util.incrementValueInString(ligneAct, 2, paliersPourIle(ile).size());
            lignes.set(l, ligneAct);

            //On continue jusqu'au tableau récapitulatif des paliers de Ronflex
            l = lignes.indexOf("| [[Fichier:Sprite Rang Basique Sleep.png|30px]] Basique 1");
            ligneAct = lignes.get(l);

            //À partir de la liste des paliers qui gagnent un/des sleepStyle(s), on incrémente les valeurs du tableau en conséquence
            ArrayList<String> paliers = paliersPourIle(ile);
            int increment = 0;
            for (int p = 0; p < 35; p++) {
                for(String pal : paliers)
                {
                    if(Util.searchValueOf(ligneAct, "]] ", false).equals(pal))
                    {
                        increment++;
                        lignes.set(l+2, Util.incrementValueInString(lignes.get(l+2), 0, 1));
                    }
                }
                lignes.set(l+3, Util.incrementValueInString(lignes.get(l+3), 0, increment));

                l += 6;
                ligneAct = lignes.get(l);
            }

            //Plus qu'à ajouter le Pokémon dans le tableau global
            l = lignes.indexOf("! Rang nécessaire") + 2;
            ligneAct = lignes.get(l);

            String currentNumDex = Util.searchValueOf(ligneAct, "{{Miniature|", "|", false);
            while(currentNumDex.compareTo(m_numDex) <= 0)
            {
                currentNumDex = null;
                while(currentNumDex == null && !ligneAct.equals("|}"))
                {
                    l++;
                    ligneAct = lignes.get(l);
                    currentNumDex = Util.searchValueOf(ligneAct, "{{Miniature|", "|", true);
                }

                //Si jamais on arrive à la fin du tableau
                if(ligneAct.equals("|}"))
                {
                    l++;
                    break;
                }
            }

            ArrayList<String> ajout = getLignesPokeIle(ile);
            lignes.addAll(l-1, ajout);

            //Reconstruction du texte de la page afin de publier
            String newContenu = Util.wikicodeReconstruction(lignes);
            wikiPages.put(pageIle, newContenu);
        }
        return wikiPages;
    }

    /**
     * Mets à jour la page des ingrédients de Pokémon sleep en ajoutant le Pokémon dans les listes de ses ingrédients
     *
     * @return
     */
    protected String updateIngredientsPage(Page listeIngredients)
    {
        String content = listeIngredients.getContent();
        ArrayList<String> lignes = new ArrayList<>(Arrays.asList(content.split("\n")));

        for(IngredientPoke bouffe : m_ingredientList)
        {
            //On cherche le tableau des Pokémon pour l'ingrédient
            int l = lignes.indexOf("| colspan=\"4\" | '''Liste des Pokémon pouvant ramasser le " + bouffe.getNom() + "'''");
            if(l == -1)
            {
                l = lignes.indexOf("| colspan=\"4\" | '''Liste des Pokémon pouvant ramasser la " + bouffe.getNom() + "'''");
            }
            if(l == -1)
            {
                l = lignes.indexOf("| colspan=\"4\" | '''Liste des Pokémon pouvant ramasser l'" + bouffe.getNom() + "'''");
            }
            l += 7;
            String ligneAct = lignes.get(l);

            if(!ligneAct.isEmpty()) {
                while (ligneAct.substring(14, 18).compareTo(m_numDex) <= 0) {
                    l += 5;
                    ligneAct = lignes.get(l);

                    //Si jamais le Pokémon doit être inséré à la toute fin du tableau
                    if (ligneAct.isEmpty()) {
                        break;
                    }
                }
            }
            l--;

            String[] ajout = {"|-",
                    "| " + getMiniatureString(),
                    "| " + bouffe.getQttNv1(),
                    "| " + bouffe.getQttNv30(),
                    "| " + bouffe.getQttNv60()};
            lignes.addAll(l, List.of(ajout));
        }

        //Reconstruction du texte de la page afin de publier
        String newContenu = Util.wikicodeReconstruction(lignes);
        return newContenu;
    }

    private String updateCandyPage(Page sleepCandies)
    {
        String content = sleepCandies.getContent();
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(content.split("\n")));
        int numDex = Integer.parseInt(m_numDex);

        int gen = PokeData.getPokemonGen(numDex);
        String sup = (gen == 1) ? "{{sup|ère}}" : "<sup>e</sup>";
        int l = lines.indexOf("! style=\"background:#C5BDDC; text-align:center\" | " + gen + sup + " génération") + 3;

        String ligneAct = lines.get(l);
        while (!ligneAct.equals("</gallery>")) {
            //System.out.println(ligneAct);
            String currentPokemon = Util.searchValueOf(ligneAct, "Bonbon ", " Sleep", false);
            int currentNumDex = PokeData.getPokemonFromName(currentPokemon).getNumDex();

            if (currentNumDex > numDex)
            {
                break;
            }

            l++;
            ligneAct = lines.get(l);
        }

        lines.add(l, "Sprite Bonbon " + m_name + " Sleep.png|[[" + m_name + "]]");

        return Util.wikicodeReconstruction(lines);
    }

    protected String updateImageryPage(Page imagery)
    {
        String content = imagery.getContent();
        if(!content.contains("{{#invoke:Imagerie|secondaire")) {
            System.err.println("La page d'imagerie suivante ne suit pas le format attendu : " + imagery.getTitle());
            return content;
        }
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(content.split("\n")));
        int l = Util.getInsertionLineForSideImagery(lines, "Sleep");

        StringBuilder sleepingSprites = new StringBuilder();
        for (int i = 1; i <= getSleepCount(); i++) {
            sleepingSprites.append(" / Sprite Dodo ").append(i).append(" / Sprite Dodo ").append(i).append(" chromatique");
        }

        String sleepLine = "Sleep // " + m_imageryType.getSprites() + sleepingSprites + " / " + m_imageryType.getMiniatures();
        lines.add(l, sleepLine);

        return Util.wikicodeReconstruction(lines);
    }

    protected String updateAbilityPage(Page ability)
    {
        String content = ability.getContent();
        if(content == null){
            System.err.println("La page de la compétence n'existe pas encore");
            return null;
        }

        if(m_ability == Competences.CHARGE_PUISSANCE_S || m_ability == Competences.AIMANT_FRAGMENT_DE_REVE) {
            System.err.println("La compétence possède une variante aléatoire et est ignorée");
            return content;
        }

        ArrayList<String> lines = new ArrayList<>(Arrays.asList(content.split("\n")));

        int l = lines.indexOf("== Pokémon ayant cette compétence ==") + 2;

        if(l == 1) {
            l = lines.indexOf("== " + m_ability.getName() + " ==");
            if (l == -1) {
                System.err.println("La section suivante n'existe pas dans sa page de compétence : " + ability.getTitle() + "#" + m_ability.getName());
                return content;
            }

            l = Util.nextIndexOf(lines, "=== Pokémon ayant cette compétence ===", l) + 2;
        }

        String currentLine = lines.get(l);
        if(!currentLine.startsWith("{{#invoke:")) {
            System.err.println("La compétence est actuellement considérée comme exclusive");
            return content;
        }

        l++;
        currentLine = lines.get(l);
        int currentNumDex = PokeData.getPokemonFromName(currentLine.split(" f")[0]).getNumDex();
        while(currentNumDex <= Integer.parseInt(m_numDex)) {
            l++;
            currentLine = lines.get(l);
            if (currentLine.equals("}}")) break;
            currentNumDex = PokeData.getPokemonFromName(currentLine.split(" f")[0]).getNumDex();
        }

        lines.add(l, getPokemonListName());
        return Util.wikicodeReconstruction(lines);
    }

    protected String[] getLignePokeRecap()
    {
        return new String[]{"|-",
                "| " + m_numDex,
                "| style=\"text-align:left;\" | " + getMiniatureString(),
                "| {{Type|" + m_type.getFrenchName() + "|Sleep}}",
                "| " + m_speciality.getNom(),
                "| [[Fichier:Sprite Baie " + m_type.getBerry() + " Sleep.png|30px]] [[Baie " + m_type.getBerry() + "]]",
                listeIngredientsWiki(),
                frequenceWiki(),
                "| " + m_storage,
                "| [[" + m_ability.getName() + "]]",
                "| " + m_recruitPoints
        };
    }

    /**
     * Retourne la ligne du second tableau de la page "liste des Pokémon de soutien" pour le Pokémon
     * @return voir ci-dessus
     */
    private String getLignePokeDodos() {
        SleepStyle sleepStyle = m_sleepList.getFirst();
        StringBuilder lignePoke = new StringBuilder("{{Ligne Pokémon Dododex|dex=" + m_numDex + getNameSection() + "|type=" + m_sleepType.getNom().toLowerCase() +
                "|dodo1=" + sleepStyle.name() + "|lieu1=" + sleepStyle.getLocationsText() + sleepStyle.getRewardsText(1) +
                "|nombonbon=" + m_candy + "|bonbon1=" + sleepStyle.candyCount());
        for (int i = 1; i < m_sleepList.size(); i++) {
            sleepStyle = m_sleepList.get(i);
            int num = i+1;
            lignePoke.append("|dodo").append(num).append("=").append(sleepStyle.name()).append("|lieu").append(num).append("=")
                    .append(sleepStyle.getLocationsText()).append(sleepStyle.getRewardsText(num)).append("|bonbon").append(num).append("=").append(sleepStyle.candyCount());
        }
        lignePoke.append("|dodo=").append(m_sleepList.size()).append("}}");
        return lignePoke.toString();
    }

    protected String getNameSection()
    {
        return "|nom=" + m_name;
    }

    /**
     * Permets d'obtenir les lignes à ajouter pour le Pokémon dans le tableau des îles
     * @param island : numéro de l'île en considération (1 pour Vertepousse, etc)
     * @return un String à ajouter dans le tableau des Pokémon présents sur l'île
     */
    private ArrayList<String> getLignesPokeIle(Island island) {
        ArrayList<String> r = new ArrayList<>();
        r.add("|-");
        int nbrDodos = paliersPourIle(island).size();
        if (nbrDodos == 1) {
            r.add("| " + getMiniatureString());
            r.add("| class=\"" + m_sleepType.getNom().toLowerCase() + "\" | [[Fichier:Icône Type " +
                    m_sleepType.getNom().toLowerCase() + " Sleep.png|50px]] " + m_sleepType.getNom());
        }
        else {
            r.add("| rowspan=\"" + nbrDodos + "\" | " + getMiniatureString());
            r.add("| rowspan=\"" + nbrDodos + "\" class=\"" + m_sleepType.getNom().toLowerCase() + "\" | [[Fichier:Icône Type " +
                    m_sleepType.getNom().toLowerCase() + " Sleep.png|50px]] " + m_sleepType.getNom());
        }

        //lignes pour chaque sleepStyle
        for(SleepStyle sleepStyle : m_sleepList)
        {
            if(!sleepStyle.isAvailableOnIsland(island)) continue;
            if(r.size() != 3) r.add("|-");
            r.add(UtilSleep.ligneEtoiles(sleepStyle.rarity()));
            r.add("| [[Fichier:Sprite Rang " + sleepStyle.getRankBallOnIsland(island) + " Sleep.png|30px]] " + sleepStyle.getRankOnIsland(island));
        }

        return r;
    }

    protected String getMiniatureString()
    {
        return "{{Miniature|" + m_numDex + "|jeu=Sleep}} [[" + m_name + "]]";
    }

    /**
     * Mise en forme des ingrédients d'un Pokémon pour son récap dans la liste des Pokémon de soutien
     * @return voir ci-dessus
     */
    private String listeIngredientsWiki()
    {
        StringBuilder r = new StringBuilder("| style=\"text-align:left;\" | ");
        for(IngredientPoke i : m_ingredientList)
        {
            r.append("[[Fichier:Sprite ").append(i.getNom()).append(" Sleep.png|30px]] [[").append(i.getNom()).append("]]<br>");
        }
        return r.substring(0, r.length()-4);
    }

    /**
     * Mise en forme de la fréquence de base d'un Pokémon pour le récap dans la liste des Pokémon de soutien
     * @return idem
     */
    private String frequenceWiki()
    {
        StringBuilder sb = new StringBuilder();

        boolean hasHours = m_freqHour > 0;
        boolean hasMinutes = m_freqMin > 0;
        boolean hasSeconds = m_freqSec > 0;

        String hText = m_freqHour + " heure" + (m_freqHour > 1 ? "s" : "");
        String mText = m_freqMin + " minute" + (m_freqMin > 1 ? "s" : "");
        String sText = m_freqSec + " seconde" + (m_freqSec > 1 ? "s" : "");

        if (hasHours) sb.append(hText);
        if (hasMinutes) {
            if (hasHours && hasSeconds) sb.append(", ");
            else if (hasHours) sb.append(" et ");
            sb.append(mText);
        }
        if (hasSeconds) {
            if ((hasHours || hasMinutes)) sb.append(" et ");
            sb.append(sText);
        }

        return sb.toString();
    }

    /**
     * Fonction qui permet de déterminer si un Pokémon est discponible sur une ile donnée en regardant si chacun de ses dodos existent sur l'ile
     * @param island : numéro de l'Ile que l'on souhaite vérifer (1 pour Vertepousse, etc)
     * @return true si disponible, false sinon
     */
    private boolean pokeDispoSurIle(Island island)
    {
        for(SleepStyle sleepStyle : m_sleepList)
        {
            if(sleepStyle.isAvailableOnIsland(island)) return true;
        }
        return false;
    }

    /**
     * Permets d'obtenir les paliers nécessaires pour chaque sleepStyle pouvant être obtenus sur une ile donnée (peut aussi être utilisée pour savoir combien de dodos sont sur l'île)
     * @param island : Numéro de l'ile pour laquelle on cherche les paliers (1 pour Vertepousse, etc)
     * @return une liste de String contenant les paliers
     */
    private ArrayList<String> paliersPourIle(Island island)
    {
        ArrayList<String> r = new ArrayList<>();
        for(SleepStyle sleepStyle : m_sleepList)
        {
            if(sleepStyle.isAvailableOnIsland(island))
            {
                r.add(sleepStyle.getRankOnIsland(island));
            }
        }
        return r;
    }

    public String makePokemonPage() {
        StringBuilder result = new StringBuilder(5000);
        String type = m_type.getFrenchName();
        String description;
        {
            Page pokePage = new Page(getRegionalName() + "/Jeux secondaires", POKEPEDIA);
            if (false && pokePage.doesPageExists()) {
                System.out.println("Warning: Page already exists, might not behave as expected");
                result.append(pokePage.getContent()).append("\n");
            } else {
                result.append(getNavigationRibbon()).append("\n\n");
            }
            //TODO temporaire a supprimer
            description = Util.searchValueOf(pokePage.getContent(), "[[Dododex]] ===\n\n", false);
        }


        Page basePage = new Page(getRegionalName(), POKEPEDIA);

        int berryAmount = (m_speciality == Specialites.BAIES || m_speciality == Specialites.TOUTES) ? 2 : 1;

        result.append("== Pokémon Sleep ==\n{{Édité par robot}}\n[[Fichier:Sprite ").append(getImageID());

        if (m_imageryType.equals(Imagery.SEXUAL_DIMORPHISM)) result.append(" ♂");

        result.append(" Sleep.png|200px|right|thumb|Sprite de ")
            .append(getRegionalName()).append(" dans {{Jeu|Sleep}}.]]\n\n").append("""
                '''%s''' est présent dans {{Jeu|Sleep}} depuis {{?}}. Il possède %d styles de dodo et apparaît lors de sessions de recherche du type %s.
                
                En tant que Pokémon de soutien, %s arbore le type %s et possède la spécialité « %s ».
                """.formatted(getRegionalName(), m_sleepList.size(), m_sleepType.getNom().toLowerCase(),
                    getRegionalName(), type, m_speciality.getNom())).append("\n<div class=\"liste-tableaux\">\n")
            .append("""
                {| class="tableaustandard %s ficheinfocentrée"
                ! style="text-align:center" colspan="2" | Recherches sur le sommeil
                |-
                ! Nombre de styles de dodo
                | %d
                |-
                ! Points d'amitié
                | %d
                |-
                ! Récompenses d'amitié
                | [[Fichier:Sprite Point de recherche Sleep.png|30px]] Point de recherche × %s<br>[[Fichier:Sprite Fragment de Rêve Sleep.png|30px]] [[Fragment de Rêve]] × %s
                |-
                ! Médailles Amitié
                | N. 10 [[Fichier:Sprite Médaille Amitié Bronze Sleep.png|30px]], N. %d [[Fichier:Sprite Médaille Amitié Argent Sleep.png|30px]], N. %d [[Fichier:Sprite Médaille Amitié Or Sleep.png|30px]]
                |}""".formatted(type.toLowerCase(), m_sleepList.size(), m_recruitPoints,
                    Util.numberDecomposition(m_sleepList.getFirst().exp()), Util.numberDecomposition(m_sleepList.getFirst().shards()),
                    getMedalUnlocks()[1], getMedalUnlocks()[2])).append("\n\n").append("""
                {| class="tableaustandard %s ficheinfocentrée"
                ! style="text-align:center" colspan="2" | Stats de soutien
                |-
                ! [[Type]]
                | {{Type|%s|Sleep}} [[%s (type)|%s]]
                |-
                ! Spécialité
                | %s
                |-
                ! Baie
                | [[Fichier:Sprite Baie %s Sleep.png|30px]] [[Baie %s]] × %d
                |-
                ! Fréquence de base
                | %s
                |-
                ! Capacité de stockage
                | %d
                |-
                ! [[Liste des compétences de Pokémon Sleep#Compétences Principales|Compétence principale]]
                | %s [[%s]]
                |-
                ! Envoi à [[Professeur Néroli|Néroli]]
                | [[Fichier:Sprite Bonbon %s Sleep.png|30px]] [[Bonbon (Pokémon Sleep)|Bonbon %s]] × %d
                |}""".formatted(type.toLowerCase(), type, type, type, m_speciality.getNom(), m_type.getBerry(),
                    m_type.getBerry(), berryAmount, frequenceWiki(), m_storage, getAbilityIcon(), m_ability.getName(), m_candy, m_candy, getSendCandyCount()))
            .append("\n</div>\n\n").append("""
                === [[Ingrédient (Pokémon Sleep)|Ingrédients]] possibles ===
                
                Le tableau ci-dessous indique les différents ingrédients que %s peut récolter. Pour chaque palier de niveau, un seul de ces ingrédients est sélectionné aléatoirement pour un Pokémon de soutien donné."""
                    .formatted(getRegionalName())).append("\n\n").append(getPokemonIngredientsData()).append("\n\n").append("""
                %s=== Description du [[Dododex]] ===
                
                %s
                
                === Styles de dodo ===
                
                """.formatted(getEvolutionData(basePage), description)).append(getPokemonSleepData()).append("""
                
                [[Catégorie:Page de %s]]
                [[Catégorie:Page de jeux secondaires]]
                [[Catégorie:Pokémon apparaissant dans Pokémon Sleep]]""".formatted(getRegionalName()));

        return result.toString();
    }

    protected String getNavigationRibbon() {
        return Util.makeNavigationRibbon(Integer.parseInt(m_numDex));
    }

    private int[] getMedalUnlocks() {
        return switch (m_recruitPoints) {
            case 5, 7 -> new int[] {10, 40, 100};
            case 12 -> new int[] {10, 30, 60};
            case 15, 16 -> new int[] {10, 25, 50};
            case 20, 25, 30 -> new int[] {10, 20, 40};
            default -> new int[] {-1, -1, -1};
        };
    }

    private String getPokemonIngredientsData() {
        StringBuilder data = new StringBuilder("""
                {| class="tableaustandard %s centre"
                ! rowspan="2" | Ingrédient
                ! colspan="3" | Niveau
                |-
                ! width="45px" | N. 1
                ! width="45px" | N. 30
                ! width="45px" | N. 60
                """.formatted(m_type.getFrenchName().toLowerCase()));

        for (IngredientPoke ingredient : m_ingredientList) {
            data.append(ingredient.getCondensedData());
        }
        data.append("|}");
        return data.toString();
    }

    private String getPokemonSleepData() {
        StringBuilder data = new StringBuilder("""
                <div class="center">
                {| class="tableaustandard centre %s tableau-overflow" style="max-width:100%c"
                ! colspan="%d" | Styles de dodos de %s
                |-
                ! Rareté
                """.formatted(m_type.getFrenchName().toLowerCase(), '%', m_sleepList.size() + 1 ,getRegionalName()));
        for (SleepStyle sleepStyle : m_sleepList) {
            data.append("|").append(" [[Fichier:Miniature Étoile Sleep.png|20px]]".repeat(sleepStyle.rarity())).append("\n");
        }

        data.append("|-\n! Image\n");
        for (int i = 1; i <= m_sleepList.size(); i++) {
            data.append("| [[Fichier:Sprite %s Dodo %d Sleep.png|150px]]\n".formatted(getImageID(), i));
        }

        data.append("|-\n! Nom\n");
        for (SleepStyle sleepStyle : m_sleepList) {
            data.append("| Dodo ").append(sleepStyle.name()).append("\n");
        }

        data.append("""
                |-
                ! colspan="%d" | Mode normal
                |-
                ! Récompenses
                """.formatted(m_sleepList.size() + 1));
        for (SleepStyle sleepStyle : m_sleepList) {
            data.append(sleepStyle.getRewardsOneCell(m_candy)).append("\n");
        }

        ArrayList<Island> expertAreas = new ArrayList<>();
        for (Island island : m_zones) {
            if (island.isExpert()) {
                expertAreas.add(island);
                continue;
            }

            data.append(getLocationsOnIsland(island));
        }

        if (!expertAreas.isEmpty()) {
            data.append("""
                |-
                ! colspan="%d" | Mode expert
                |-
                ! Récompenses
                """.formatted(m_sleepList.size() + 1));

            data.append(("| style=\"white-space:nowrap; text-align:left\" | [[Fichier:Sprite Point de recherche Sleep.png|30px]] Point de recherche × {{?}}<br>[[Fichier:Sprite Fragment de Rêve Sleep.png|30px]] [[Fragment de Rêve]] × {{?}}<br>[[Fichier:Sprite Bonbon %s Sleep.png|30px]] [[Bonbon (Pokémon Sleep)|Bonbon %s]] × {{?}}\n"
                    .formatted(m_candy, m_candy).repeat(m_sleepList.size())));

            for (Island island : expertAreas) {
                data.append(getLocationsOnIsland(island));
            }
        }

        data.append("|}\n</div>\n");
        return data.toString();
    }

    private String getEvolutionData(Page basePage) {
        String evolutionData = Util.searchValueOf(basePage.getContent(), "=== [[Évolution]] ===\n", "\n==", true);
        if(evolutionData == null || evolutionData.contains("n'a pas d'évolution")) return "";

        if(evolutionData.endsWith("\n")) evolutionData = evolutionData.substring(0, evolutionData.length() - 1);

        evolutionData = evolutionData.replaceAll("\\[\\[niveau]] [0-9]+", "niveau {{?}}").replaceAll("niveau [0-9]+", "niveau {{?}}")
                .replaceAll("\\[*Niveau]* [0-9]+", "Niveau {{?}} + [[Fichier:Sprite Bonbon %s Sleep.png|25px|Bonbon %s|lien=Bonbon (Pokémon Sleep)]] × {{?}}"
                        .formatted(getRegionalName(), getRegionalName()))
                .replace("Tableau d'évolution", "Tableau d'évolution Sleep")
                .replace("] qui lui-", "], qui lui-");

        if(evolutionData.contains("#lst")) {
            String pokeName = Util.searchValueOf(evolutionData, "#lst:", "|", false);
            evolutionData = evolutionData.replaceAll("\\{\\{#lst:.+", "{{#lst:%s/Jeux secondaires|Tableau d'évolution Sleep}}".formatted(pokeName))
                    .replace("[[%s]]".formatted(pokeName), "[[%s/Jeux secondaires|%s]]".formatted(pokeName, pokeName));
        }
        else {
            for (String line : evolutionData.split("\n")) {
                if (!line.contains("TableauEvolution")) continue;

                String pokeName = line.split("\\|")[line.split("\\|").length - 1].replace("}}", "");
                String thisNumDex = pokeName.equals(getRegionalName()) ? m_numDex : "{{?}}";
                evolutionData = evolutionData.replace("|" + pokeName, "|%s|lien=%s/Pokémon Sleep|image=Sprite %s Sleep.png".formatted(pokeName, pokeName, thisNumDex))
                        .replace("[[" + pokeName + "]]", "[[%s/Pokémon Sleep|%s]]".formatted(pokeName, pokeName));
            }
        }



        String data = """
        === [[Évolution]] ===
        
        %s
        
        """.formatted(evolutionData);

        return data;
    }

    private String getLocationsOnIsland(Island island) {
        StringBuilder data =  new StringBuilder("|-\n");
        data.append("! [[%s]]\n".formatted(island.getName(true)));
        for (SleepStyle sleepStyle : m_sleepList) {
            if (sleepStyle.isAvailableOnIsland(island)) {
                data.append("| [[Fichier:Sprite Rang %s Sleep.png|30px]] %s\n".formatted(sleepStyle.getRankBallOnIsland(island),
                        sleepStyle.getRankOnIsland(island)));
            } else {
                data.append("| —\n");
            }
        }
        return data.toString();
    }

    protected String getImageID() {
        return m_numDex;
    }

    protected int getInternalID() {
        return Integer.parseInt(m_numDex);
    }

    private String getAbilityIcon() {
        if (m_ability.equals(Competences.SUPER_SOUTIEN)) {
            return "{{Type|%s|Sleep}}".formatted(m_type.getFrenchName());
        } else {
            return "[[Fichier:Icône Compétence %s Sleep.png|30px]]".formatted(m_ability.getIcon());
        }
    }

    private int getSendCandyCount() {
        if (m_name.equals("Branette")) return 7;
        if (m_name.equals("Grodoudou")) return 10;

        return switch (m_recruitPoints) {
            case 5 -> 5;
            case 7 -> 6;
            case 12 -> 7;
            case 15, 20 -> 10;
            case 16 -> 8;
            case 18, 22 -> 11;
            case 25 -> 12;
            case 30 -> 25;
            default -> -1;
        };
    }

    protected String getPokemonListName()
    {
        return m_name;
    }

    public String getRegionalName()
    {
        return m_name;
    }

    public String getName()
    {
        return m_name;
    }

    public String getNumDex()
    {
        return m_numDex;
    }

    public Imagery getImageryType() {
        return m_imageryType;
    }

    public int getSleepCount() {
        return m_sleepList.size();
    }

    public boolean hasUniqueCandy() {
        return m_name.equals(m_candy);
    }
}
