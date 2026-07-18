package sleep.pokemon;

import sleep.bouffe.IngredientPoke;
import sleep.dodos.SleepStyle;
import sleep.dodos.Island;
import sleep.dodos.TypesDodo;
import utilitaire.Page;
import utilitaire.PageToPublish;
import utilitaire.PokeTypes;
import utilitaire.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PokeForme extends Pokemon{
    private final String m_nomForme;
    private final String m_precision;

    public PokeForme(String nom, String forme, String precision, int numDex, PokeTypes type, TypesDodo dodoType, Specialites specialite, ArrayList<IngredientPoke> ingredients, ArrayList<SleepStyle> sleepStyles, ArrayList<Island> iles, String frequence, int capacite, Competences competence, int ptsAmitie, String bonbon, Imagery imageryType, String description, boolean isSingle)
    {
        super(nom, numDex, type, dodoType, specialite, ingredients, sleepStyles, iles, frequence, capacite, competence, ptsAmitie, bonbon, imageryType, description, isSingle);
        m_nomForme = forme;
        m_precision = precision;
    }

    @Override
    public ArrayList<PageToPublish> getWikiModifications() {
        ArrayList<PageToPublish> wikiPages = super.getWikiModifications();
        wikiPages.remove(3);

        return wikiPages;
    }

    @Override
    protected ArrayList<PageToPublish> updateZones() {
        return new ArrayList<>();
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
    protected String getNameSection() {
        return (super.getNameSection() + "|forme=" + m_nomForme + addPrecision());
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
        String form = m_nomForme + addPrecision().replace("(", "\\(").replace(")", "\\(");

        return super.getPokemonListName() + " forme(" + form + ")";
    }
}
