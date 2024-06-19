package Sleep;

import Utilitaire.Util;

import java.util.ArrayList;
import java.util.Scanner;

public class Dodo {
    private String m_nom;
    private int m_rarete;
    private int m_ptsRecherche;
    private int m_qttFragment;
    private int m_qttBonbons;
    private ArrayList<LieuxDodo> m_localisations;
    public static final int NBR_ILES = 5;

    public Dodo(String nom, int rarete, int recherche, int fragment, int bonbon)
    {
        m_nom = nom;
        m_rarete = rarete;
        m_ptsRecherche = recherche;
        m_qttFragment = fragment;
        m_qttBonbons = bonbon;
        Scanner sc = new Scanner(System.in);

        m_localisations = new ArrayList<>();
        for (int i = 1; i <= NBR_ILES; i++) {
            System.out.println("Quel est le rang pour débloquer le dodo " + m_nom + " sur : " + UtilSleep.getNomIle(i));
            System.out.println("(\"n\" si absent de cette ile)");
            String rang = sc.nextLine();
            if(rang.equals("n"))
            {
                m_localisations.add(new LieuxDodo(i));
            }
            else
            {
                m_localisations.add(new LieuxDodo(i, rang));
            }
        }
    }

    public String getNom() {
        return m_nom;
    }

    public int getRarete()
    {
        return m_rarete;
    }

    public String getLieux()
    {
        String r = "";
        for(LieuxDodo ile : m_localisations)
        {
            if(ile.estDisponible())
            {
                if(!r.isEmpty())
                {
                    r += "<br>";
                }
                r += "[[" + ile.getIle() + "]] (" + ile.getPalier() + ")";
            }
        }
        return r;
    }

    public String getRecompenses(int numDodo)
    {
        return "|recherche" + numDodo + "=" + Util.decompMilliers(m_ptsRecherche) + "|fragment" + numDodo + "=" + Util.decompMilliers(m_qttFragment);
    }

    public int getQttBonbons() {
        return m_qttBonbons;
    }

    public boolean estDispoSurIle(int numile)
    {
        return m_localisations.get(numile - 1).estDisponible();
    }

    public String getRangIle(int numIle)
    {
        return m_localisations.get(numIle - 1).getRang();
    }

    public String getPalierIle(int numIle)
    {
        return m_localisations.get(numIle - 1).getPalier();
    }
}
