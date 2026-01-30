package sleep.pokemon;

import sleep.dodos.Dodo;
import sleep.dodos.Iles;
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
    private final String m_numDex;
    private final PokeTypes m_type;
    private final TypesDodo m_sleepType;
    private final Specialites m_speciality;
    private final ArrayList<IngredientPoke> m_ingredientsList;
    private final ArrayList<Dodo> m_sleepList;
    private final ArrayList<Iles> m_zones;
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
     * @param dodoType : Catégorie de dodo du Pokémon (Ptidodo, Bondodo, ou Grododo)
     * @param specialite : Baies, Ingrédients, ou Compétences
     * @param ingredients : Liste des ingrédients du Pokémon à créer au préalable
     * @param dodos : Liste des dodos du Pokémon pré-remplis
     * @param iles : Iles où le Pokémon peut être trouvé
     * @param frequence : Fréquence de base du Pokémon au format "h:min:sec" ou "min:sec"
     * @param capacite : Capacité maximale du Pokémon (combien d'objets peut-il tenir par défaut)
     * @param competence : Compétence Principale du Pokémon
     * @param ptsAmitie : Combien de Pokébiscuits max faut-il pour devenir ami avec ce Pokémon
     * @param bonbon : Nom de Pokémon utilisé pour les bonbons de celui-ci (utile pour les Pokémon évolués)
     */
    public Pokemon(String nom, int numDex, PokeTypes type, TypesDodo dodoType, Specialites specialite, ArrayList<IngredientPoke> ingredients, ArrayList<Dodo> dodos, ArrayList<Iles> iles, String frequence, int capacite, Competences competence, int ptsAmitie, String bonbon, Imagery imageryType)
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
        m_sleepList = dodos;
        m_ingredientsList = ingredients;
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
        wikiPages.put(ability, updateAbilityPage(ability));
        wikiPages.putAll(updateZones());

        //Si le pokémon n'est pas la forme de base de sa ligne évolutive, on ne l'ajoute pas à certaines pages.
        if(m_name.equals(m_candy)) {
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
        System.out.println(lignes.get(l-2) + "\n" + ligneAct);

        //On cherche l'endroit où le nouveau Pokémon doit être inséré en comparant les numéros de Pokédex
        while (ligneAct.substring(ligneAct.length()-4).compareTo(m_numDex) <= 0)
        {
            //Si jamais le numdex est le même que celui recherché
            if(ligneAct.substring(ligneAct.length()-4).equals(m_numDex)) {
                String temp = Util.incrementRowspan(ligneAct);
                System.out.println(temp);
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

        int numIle = 0;
        for (Iles ile : m_zones) {
            //Si le pokémon n'est pas dispo sur l'ile, on passe directement à la suivante
            if(!pokeDispoSurIle(numIle))
            {
                continue;
            }

            Page pageIle = new Page(ile.getNom(false), POKEPEDIA);
            String content = pageIle.getContent();
            ArrayList<String> lignes = new ArrayList<>(Arrays.asList(content.split("\n")));

            //Recherche de la section Description pour modifier les chiffres
            int l = lignes.indexOf("== Description ==");
            l += 3;
            String ligneAct = lignes.get(l);

            //mise à jour de la ligne contenant le nombre de Pokémon et de dodos disponibles
            ligneAct = Util.incrementValueInString(ligneAct, 1, 1);
            ligneAct = Util.incrementValueInString(ligneAct, 2, paliersPourIle(numIle).size());
            lignes.set(l, ligneAct);

            //On continue jusqu'au tableau récapitulatif des paliers de Ronflex
            l = lignes.indexOf("| [[Fichier:Sprite Rang Basique Sleep.png|30px]] Basique 1");
            ligneAct = lignes.get(l);

            //À partir de la liste des paliers qui gagnent un/des dodo(s), on incrémente les valeurs du tableau en conséquence
            ArrayList<String> paliers = paliersPourIle(numIle);
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

            ArrayList<String> ajout = getLignesPokeIle(numIle);
            lignes.addAll(l-1, ajout);

            //Reconstruction du texte de la page afin de publier
            String newContenu = Util.wikicodeReconstruction(lignes);
            wikiPages.put(pageIle, newContenu);

            numIle++;
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

        for(IngredientPoke bouffe : m_ingredientsList)
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
            System.out.println(ligneAct);
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

        String sleepLine = "Sleep // " + m_imageryType.getSprites() + " / " + m_imageryType.getMiniatures();
        lines.add(l, sleepLine);

        return Util.wikicodeReconstruction(lines);
    }

    protected String updateAbilityPage(Page ability)
    {
        String content = ability.getContent();
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
        Dodo dodo = m_sleepList.getFirst();
        StringBuilder lignePoke = new StringBuilder("{{Ligne Pokémon Dododex|dex=" + m_numDex + getNameSection() + "|type=" + m_sleepType.getNom().toLowerCase() +
                "|dodo1=" + dodo.getNom() + "|lieu1=" + dodo.getLieux() + dodo.getRecompenses(1) +
                "|nombonbon=" + m_candy + "|bonbon1=" + dodo.getQttBonbons());
        for (int i = 1; i < m_sleepList.size(); i++) {
            dodo = m_sleepList.get(i);
            int num = i+1;
            lignePoke.append("|dodo").append(num).append("=").append(dodo.getNom()).append("|lieu").append(num).append("=")
                    .append(dodo.getLieux()).append(dodo.getRecompenses(num)).append("|bonbon").append(num).append("=").append(dodo.getQttBonbons());
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
     * @param numIle : numéro de l'île en considération (1 pour Vertepousse, etc)
     * @return un String à ajouter dans le tableau des Pokémon présents sur l'île
     */
    private ArrayList<String> getLignesPokeIle(int numIle) {
        ArrayList<String> r = new ArrayList<>();
        r.add("|-");
        int nbrDodos = paliersPourIle(numIle).size();
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

        //lignes pour chaque dodo
        for(Dodo dodo : m_sleepList)
        {
            if(!dodo.estDispoSurIle(numIle)) continue;
            if(r.size() != 3) r.add("|-");
            r.add(UtilSleep.ligneEtoiles(dodo.getRarete()));
            r.add("| [[Fichier:Sprite Rang " + dodo.getRangIle(numIle) + " Sleep.png|30px]] " + dodo.getPalierIle(numIle));
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
        for(IngredientPoke i : m_ingredientsList)
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
        String r = "| ";
        switch (m_freqHour)
        {
            case 0:
                break;
            case 1:
                r += m_freqHour + " heure<br>";
                break;
            default:
                r += m_freqHour + " heures<br>";
                break;
        }
        switch (m_freqMin)
        {
            case 0:
                break;
            case 1:
                r += m_freqMin + " minute<br>";
                break;
            default:
                r += m_freqMin + " minutes<br>";
                break;
        }
        switch (m_freqSec)
        {
            case 0:
                r = r.substring(0, r.length()-4);
                break;
            case 1:
                r += m_freqSec + " seconde";
                break;
            default:
                r += m_freqSec + " secondes";
                break;
        }
        return r;
    }

    /**
     * Fonction qui permet de déterminer si un Pokémon est discponible sur une ile donnée en regardant si chacun de ses dodos existent sur l'ile
     * @param numIle : numéro de l'Ile que l'on souhaite vérifer (1 pour Vertepousse, etc)
     * @return true si disponible, false sinon
     */
    private boolean pokeDispoSurIle(int numIle)
    {
        for(Dodo dodo : m_sleepList)
        {
            if(dodo.estDispoSurIle(numIle)) return true;
        }
        return false;
    }

    /**
     * Permets d'obtenir les paliers nécessaires pour chaque dodo pouvant être obtenus sur une ile donnée (peut aussi être utilisée pour savoir combien de dodos sont sur l'île)
     * @param numIle : Numéro de l'ile pour laquelle on cherche les paliers (1 pour Vertepousse, etc)
     * @return une liste de String contenant les paliers
     */
    private ArrayList<String> paliersPourIle(int numIle)
    {
        ArrayList<String> r = new ArrayList<>();
        for(Dodo dodo : m_sleepList)
        {
            if(dodo.estDispoSurIle(numIle))
            {
                r.add(dodo.getPalierIle(numIle));
            }
        }
        return r;
    }

    protected String getPokemonListName()
    {
        return m_name;
    }

    protected String getRegionalName()
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
}
