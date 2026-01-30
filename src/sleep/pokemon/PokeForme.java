package sleep.pokemon;

import sleep.bouffe.IngredientPoke;
import sleep.dodos.Dodo;
import sleep.dodos.Iles;
import sleep.dodos.TypesDodo;
import utilitaire.Page;
import utilitaire.PokeTypes;
import utilitaire.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static utilitaire.Wiki.POKEPEDIA;

public class PokeForme extends Pokemon{
    private final String m_nomForme;
    private final String m_precision;

    public PokeForme(String nom, String forme, String precision, int numDex, PokeTypes type, TypesDodo dodoType, Specialites specialite, ArrayList<IngredientPoke> ingredients, ArrayList<Dodo> dodos, ArrayList<Iles> iles, String frequence, int capacite, Competences competence, int ptsAmitie, String bonbon, Imagery imageryType)
    {
        super(nom, numDex, type, dodoType, specialite, ingredients, dodos, iles, frequence, capacite, competence, ptsAmitie, bonbon, imageryType);
        m_nomForme = forme;
        m_precision = precision;
    }

    @Override
    public HashMap<Page, String> getWikiModifications() {
        HashMap<Page, String> wikiPages = super.getWikiModifications();
        wikiPages.entrySet().removeIf(e -> e.getKey().getTitle().equals(m_pages[3]));

        return wikiPages;
    }

    @Override
    protected HashMap<Page, String> updateZones() {
        return new HashMap<>();
    }

    @Override
    protected String updateImageryPage(Page imagery) {
        String content = imagery.getContent();
        if(!content.contains("{{#invoke:Imagerie|secondaire")) {
            System.err.println("La page d'imagerie suivante ne suit pas le format attendu : " + imagery.getTitle());
            return content;
        }
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(content.split("\n")));
        int l = Util.getInsertionLineForSideImagery(lines, "Sleep");

        String currentLine = lines.get(l);
        int miniaIndex = currentLine.indexOf("Miniature");
        currentLine = currentLine.substring(0, miniaIndex) + m_imageryType.getSprites() + " / "
                + currentLine.substring(miniaIndex) + " / " + m_imageryType.getMiniatures();
        lines.set(l, currentLine);

        return Util.wikicodeReconstruction(lines);
    }

    @Override
    protected String[] getLignePokeRecap() {
        ArrayList<String> temp = new ArrayList<>(Arrays.asList(super.getLignePokeRecap()));
        temp.remove(1);
        return temp.toArray(new String[0]);
    }

    @Override
     protected String getMiniatureString()
     {
         return "{{Miniature|" + super.getNumDex() + "|" + m_nomForme + addPrecision() + "|jeu=Sleep}} [[" + super.getName() + "]] (" + m_nomForme + ")";
     }

    @Override
    protected String getNameSection() {
        return ("|forme=" + m_nomForme + addPrecision() + super.getNameSection() + "{{!}}" + getName() + " (" + m_nomForme +")");
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

    @Override
    protected String getPokemonListName() {
        String form = m_nomForme.replace("(", "\\(").replace(")", "\\(");

        return super.getPokemonListName() + " forme(" + form + ")";
    }
}
