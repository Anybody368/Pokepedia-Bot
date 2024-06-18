package Sleep;

import Utilitaire.Page;
import Utilitaire.Util;

import java.util.*;

public class Pokemon {
    private String m_nom;
    private String m_numDex;
    private String m_type;
    private String m_typeDodo;
    private String m_specialite;
    private ArrayList<Ingredient> m_listeIngredients;
    private ArrayList<Dodo> m_listeDodos;
    private int m_freqHeure;
    private int m_freqMin;
    private int m_freqSec;
    private int m_capacite;
    private String m_competence;
    private int m_ptsAmitie;
    private String m_bonbon;

    public Pokemon(String nom, int numDex, String type, String dodoType, String specialite, int nbIngredients, int nbDodos, String frequence, int capacite, String competence, int ptsAmitie, String bonbon)
    {
        m_nom = nom;
        m_numDex = Util.numDexComplet(numDex);
        m_type = type;
        m_typeDodo = dodoType;
        m_specialite = specialite;
        remplissageFreqence(frequence);
        m_capacite = capacite;
        m_competence = competence;
        m_ptsAmitie = ptsAmitie;
        m_bonbon = bonbon;

        Scanner sc = new Scanner(System.in);
        m_listeDodos = new ArrayList<>();
        for (int i = 1; i <= nbDodos; i++)
        {
            System.out.println("Nom du dodo " + i);
            String nomDodo = sc.nextLine();
            System.out.println("Rareté du dodo " + nomDodo);
            int numDodo = sc.nextInt();
            System.out.println("Points de recherche du dodo " + nomDodo);
            int recherche = sc.nextInt();
            System.out.println("Fragments du dodo " + nomDodo);
            int fragment = sc.nextInt();
            System.out.println("Nombre de bonbons du dodo " + nomDodo);
            int qttBonbon = sc.nextInt();
            m_listeDodos.add(new Dodo(nomDodo, numDodo, recherche, fragment, qttBonbon));
            sc.nextLine();
        }
        m_listeIngredients = new ArrayList<>();
        for (int i = 1; i <= nbIngredients; i++)
        {
            System.out.println("Nom de l'ingrédient " + i);
            String nomIngredient = sc.nextLine();
            System.out.println("Quantité de l'ingrédient " + i);
            int quantite = sc.nextInt();
            m_listeIngredients.add(new Ingredient(nomIngredient, quantite));
            sc.nextLine();
        }
    }

    public String getBaie()
    {
        String baie = switch (m_type)
        {
            case "Normal" -> "Kika";
            case "Feu" -> "Mepo";
            case "Eau" -> "Oran";
            case "Électrik" -> "Résin";
            case "Plante" -> "Durin";
            case "Glace" -> "Fraive";
            case "Combat" -> "Ceriz";
            case "Poison" -> "Maron";
            case "Sol" -> "Figuy";
            case "Vol" -> "Palma";
            case "Psy" -> "Mago";
            case "Insecte" -> "Prine";
            case "Roche" -> "Sitrus";
            case "Spectre" -> "Remu";
            case "Dragon" -> "Nanone";
            case "Ténèbres" -> "Wiki";
            case "Acier" -> "Myrte";
            case "Fée" -> "Pêcha";
            default -> "";
        };
        if(baie.isEmpty()) {
            System.err.println("ERREUR : Type non valide");
            System.exit(1);
        }
        return baie;
    }

    private void remplissageFreqence(String freq)
    {
        String[] details = freq.split(":");
        switch (details.length)
        {
            case 2 :
                m_freqHeure = 0;
                m_freqMin = Integer.parseInt(details[0]);
                m_freqSec = Integer.parseInt(details[1]);
                break;
            case 3 :
                m_freqHeure = Integer.parseInt(details[0]);
                m_freqMin = Integer.parseInt(details[1]);
                m_freqSec = Integer.parseInt(details[2]);
                break;
            default: {
                System.err.println("ERREUR : Format de fréquence invalide");
                System.exit(1);
            }
        }
    }

    public void ajoutPokeWiki()
    {
        //ajoutListePokeSoutien();
        ajoutPokeIles();
    }

    private void ajoutListePokeSoutien()
    {
        final int LIGNES_INFORMATIONS = 11;

        Page listeSoutien = new Page("Liste des Pokémon de soutien de Pokémon Sleep");
        String content = listeSoutien.getContent();
        ArrayList<String> lignes = new ArrayList<>(Arrays.asList(content.split("\n")));

        //On se place au niveau de la première ligne d'un Pokémon de Soutien du premier tableau, normalement "| 0001"
        int l = Util.trouverNumLigne(lignes, "! Points d'amitié requis");
        l += 2;
        String ligneAct = lignes.get(l);

        //On cherche l'endroit où le nouveau Pokémon doit être inséré en comparant les numéros de Pokédex
        while (ligneAct.substring(ligneAct.length()-4).compareTo(m_numDex) < 0)
        {
            l += LIGNES_INFORMATIONS;
            //Si jamais il y a un rowspan avant le numero du dex (coucou Pikachu)
            if(ligneAct.length() > 10)
            {
                int rowspan = Integer.parseInt(ligneAct.substring(11, 12));
                l += (LIGNES_INFORMATIONS-1) * (rowspan-1);
            }
            ligneAct = lignes.get(l);

            //Si jamais le Pokémon doit être inséré à la toute fin du tableau
            if(ligneAct.isEmpty())
            {
                l -= 1;
                break;
            }
        }

        String[] ajout = {"|-",
                "| " + m_numDex,
                "| style=\"text-align:left;\" | {{Miniature|" + m_numDex + "|jeu=Sleep}} [[" + m_nom + "]]",
                "| {{Type|" + m_type + "|Sleep}}",
                "| " + m_specialite,
                "| [[Fichier:Sprite Baie " + getBaie() + " Sleep.png|30px]] [[Baie " + getBaie() + "]]",
                listeIngredientsWiki(),
                frequenceWiki(),
                "| " + m_capacite,
                "| [[" + m_competence + "]]",
                "| " + m_ptsAmitie};
        lignes.addAll(l - 1, List.of(ajout));
        ajout = null;

        //Même procédé pour le deuxième tableau
        l = Util.trouverNumLigne(lignes, "! Récompenses");
        l += 2;
        ligneAct = lignes.get(l);

        while(ligneAct.substring(28, 32).compareTo(m_numDex) < 0)
        {
            l += 2;
            ligneAct = lignes.get(l);

            //Si jamais le Pokémon doit être inséré à la toute fin du tableau
            if(ligneAct.length() < 33)
            {
                break;
            }
        }

        String lignePoke = getLignePoke();
        lignes.add(l, lignePoke);
        lignes.add(l+1, "");

        //Reconstruction du texte de la page afin de publier
        String newContenu = "";
        for(String ligne : lignes)
        {
            newContenu += ligne + "\n";
        }
        System.out.println(newContenu);
        System.out.println("Page " + listeSoutien.getTitle() + " mise à jour");
    }

    private void ajoutPokeIles()
    {
        for (int numIle = 1; numIle <= Dodo.NBR_ILES; numIle++) {
            //Si le pokémon n'est pas dispo sur l'ile, on passe directement à la suivante
            if(!pokeDispoSurIle(numIle))
            {
                continue;
            }

            Page pageIle = new Page(UtilSleep.getNomIle(numIle));
            String content = pageIle.getContent();
            ArrayList<String> lignes = new ArrayList<>(Arrays.asList(content.split("\n")));

            //Recherche de la section Description pour modifier les chiffres
            int l = Util.trouverNumLigne(lignes, "== Description ==");
            l += 3;
            String ligneAct = lignes.get(l);

            //mise à jour de la ligne contenant le nombre de Pokémon et de dodos disponibles
            ligneAct = Util.incrementeValeurDansString(ligneAct, 14, 1);
            ligneAct = Util.incrementeValeurDansString(ligneAct, 22, m_listeDodos.size());
            lignes.set(l, ligneAct);

            //On continue jusqu'au tableau récapitulatif des paliers de Ronflex
            l = Util.trouverNumLigne(lignes, "| [[Fichier:Sprite Rang Basique Sleep.png|30px]] Basique 1");
            ligneAct = lignes.get(l);

            //À partir de la liste des paliers qui gagnent un/des dodo(s), on incrémente les valeurs du tableau en conséquence
            ArrayList<String> paliers = paliersPourIle(numIle);
            int increment = 0;
            for (int p = 0; p < 35; p++) {
                for(String pal : paliers)
                {
                    if(ligneAct.contains(pal))
                    {
                        increment++;
                        lignes.set(l+2, Util.incrementeValeurDansString(lignes.get(l+2), 2, 1));
                    }
                }
                lignes.set(l+3, Util.incrementeValeurDansString(lignes.get(l+3), 1, increment));

                l += 6;
                ligneAct = lignes.get(l);
            }

            //Plus qu'à ajouter le Pokémon dans le tableau global
            l = Util.trouverNumLigne(lignes, "! Rang nécessaire") + 2;
            ligneAct = lignes.get(l);

            int locNumDex = ligneAct.indexOf("{{Miniature") + 12;
            while(ligneAct.substring(locNumDex, locNumDex+4).compareTo(m_numDex) < 0)
            {
                locNumDex = 11;
                while(locNumDex == 11 && !ligneAct.equals("|}"))
                {
                    l++;
                    ligneAct = lignes.get(l);
                    locNumDex = ligneAct.indexOf("{{Miniature") + 12;
                }

                //Si jamais on arrive à la fin du tableau
                if(ligneAct.equals("|}")) l++; break;
            }

            ArrayList<String> ajout = getLignesPokeIle(numIle);
            lignes.addAll(l-1, ajout);

            //Reconstruction du texte de la page afin de publier
            String newContenu = "";
            for(String ligne : lignes)
            {
                newContenu += ligne + "\n";
            }
            System.out.println(newContenu);
            System.out.println("Page " + pageIle.getTitle() + " mise à jour");
        }
    }

    private String getLignePoke() {
        Dodo dodo = m_listeDodos.getFirst();
        String lignePoke = "{{Ligne Pokémon Dododex|dex=" + m_numDex + "|nom=" + m_nom + "|type=" + m_typeDodo +
                "|dodo1=" + dodo.getNom() + "|lieu1=" + dodo.getLieux() + dodo.getRecompenses(1) +
                "|nombonbon=" + m_bonbon + "|bonbon1=" + dodo.getQttBonbons();
        for (int i = 1; i < m_listeDodos.size(); i++) {
            dodo = m_listeDodos.get(i);
            int num = i+1;
            lignePoke += "|dodo" + num + "=" + dodo.getNom() + "|lieu" + num + "=" + dodo.getLieux() +
                    dodo.getRecompenses(num) + "|bonbon" + num + "=" + dodo.getQttBonbons();
        }
        lignePoke += "|dodo=" + m_listeDodos.size() + "}}";
        return lignePoke;
    }

    private ArrayList<String> getLignesPokeIle(int numIle) {
        ArrayList<String> r = new ArrayList<>();
        r.add("|-");
        int nbrDodos = paliersPourIle(numIle).size();
        if (nbrDodos == 1) {
            r.add("| {{Miniature|" + m_numDex + "|jeu=Sleep}} [[" + m_nom + "]]");
            r.add("| class=\"" + m_typeDodo.toLowerCase(Locale.ROOT) + "\" | [[Fichier:Icône Type " +
                    m_typeDodo.toLowerCase(Locale.ROOT) + " Sleep.png|50px]] " + m_typeDodo);
        }
        else {
            r.add("| rowspan=\"" + nbrDodos + "\" | {{Miniature|" + m_numDex + "|jeu=Sleep}} [[" + m_nom + "]]");
            r.add("| rowspan=\"" + nbrDodos + "\" class=\"" + m_typeDodo.toLowerCase(Locale.ROOT) + "\" | [[Fichier:Icône Type " +
                    m_typeDodo.toLowerCase(Locale.ROOT) + " Sleep.png|50px]] " + m_typeDodo);
        }

        //lignes pour chaque dodo
        for(Dodo dodo : m_listeDodos)
        {
            if(!dodo.estDispoSurIle(numIle)) continue;
            if(r.size() != 3) r.add("|-");
            r.add(UtilSleep.ligneEtoiles(dodo.getRarete()));
            r.add("| [[Fichier:Sprite Rang " + dodo.getRangIle(numIle) + " Sleep.png|30px]] " + dodo.getPalierIle(numIle));
        }

        return r;
    }

    private String listeIngredientsWiki()
    {
        String r = "| style=\"text-align:left;\" | ";
        for(Ingredient i : m_listeIngredients)
        {
            if(!r.equals("| style=\"text-align:left;\" | "))
            {
                r += "<br>";
            }
            r += "[[Fichier:Sprite " + i.getNom() + " Sleep.png|30px]] [[" + i.getNom() + "]]";
        }
        return r;
    }

    private String frequenceWiki()
    {
        String r = "| ";
        switch (m_freqHeure)
        {
            case 0:
                break;
            case 1:
                r += m_freqHeure + " heure<br>";
                break;
            default:
                r += m_freqHeure + " heures<br>";
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
        for(Dodo dodo : m_listeDodos)
        {
            if(dodo.estDispoSurIle(numIle)) return true;
        }
        return false;
    }

    private ArrayList<String> paliersPourIle(int numIle)
    {
        ArrayList<String> r = new ArrayList<>();
        for(Dodo dodo : m_listeDodos)
        {
            if(dodo.estDispoSurIle(numIle))
            {
                r.add(dodo.getPalierIle(numIle));
            }
        }
        return r;
    }
}
