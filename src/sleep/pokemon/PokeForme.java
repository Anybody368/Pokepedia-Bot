package sleep.pokemon;

import sleep.bouffe.IngredientPoke;
import sleep.dodos.Dodo;
import sleep.dodos.Iles;
import sleep.dodos.TypesDodo;
import utilitaire.Page;
import utilitaire.PokeTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static utilitaire.Wiki.POKEPEDIA;

public class PokeForme extends Pokemon{
    private final String m_nomForme;
    private final String m_precision;

    public PokeForme(String nom, String forme, String precision, int numDex, PokeTypes type, TypesDodo dodoType, Specialites specialite, ArrayList<IngredientPoke> ingredients, ArrayList<Dodo> dodos, ArrayList<Iles> iles, String frequence, int capacite, Competences competence, int ptsAmitie, String bonbon)
    {
        super(nom, numDex, type, dodoType, specialite, ingredients, dodos, iles, frequence, capacite, competence, ptsAmitie, bonbon);
        m_nomForme = forme;
        m_precision = precision;
    }

    @Override
    public HashMap<Page, String> getWikiModifications() {
        HashMap<Page, String> wikiPages = new HashMap<>();
        Page listeSoutien = new Page(m_pages[0], POKEPEDIA);
        Page listeDodo = new Page(m_pages[1], POKEPEDIA);

        wikiPages.put(listeSoutien, ajoutListePokeSoutien(listeSoutien));
        wikiPages.put(listeDodo, ajouListeDodo(listeDodo));

        return wikiPages;
    }

    @Override
    protected String[] getLignePokeRecap() {
        ArrayList<String> temp = new ArrayList<>(Arrays.asList(super.getLignePokeRecap()));
        temp.remove(1);
        return temp.toArray(new String[0]);
    }

    @Override
     protected String getStringMiniaNomPoke()
     {
         return "{{Miniature|" + super.getNumDex() + "|" + m_nomForme + addPrecision() + "|jeu=Sleep}} [[" + super.getNom() + "]] (" + m_nomForme + ")";
     }

    @Override
    protected String getSectionNom() {
        return ("|forme=" + m_nomForme + addPrecision() + super.getSectionNom() + "{{!}}" + getNom() + " (" + m_nomForme +")");
    }

    private String addPrecision()
    {
        if(m_precision.isEmpty())
        {
            return "";
        }
        else {
            return " (" + m_precision +")";
        }
    }
}
