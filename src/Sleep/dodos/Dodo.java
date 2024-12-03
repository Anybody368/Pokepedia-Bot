package Sleep.dodos;

import java.util.ArrayList;
import java.util.Scanner;

public class Dodo {
    private final String m_nom;
    private final int m_rarete;
    private final int m_ptsRecherche;
    private final int m_qttFragment;
    private final int m_qttBonbons;
    private final ArrayList<LieuxDodo> m_localisations;
    public static final int NBR_ILES = 6;

    public Dodo(String nom, int rarete, int recherche, int fragment, int bonbon, ArrayList<Iles> iles, Boolean yes)
    {
        m_nom = nom;
        m_rarete = rarete;
        m_ptsRecherche = recherche;
        m_qttFragment = fragment;
        m_qttBonbons = bonbon;
        Scanner sc = new Scanner(System.in);

        m_localisations = new ArrayList<>();
        for (Iles ile : iles) {
            System.out.println("Quel est le rang pour débloquer le dodo " + m_nom + " sur : " + ile.getNom());
            System.out.println("(\"n\" si absent de cette ile)");
            String rang = sc.nextLine();
            if(rang.equals("n"))
            {
                m_localisations.add(new LieuxDodo(ile));
            }
            else
            {
                m_localisations.add(new LieuxDodo(ile, rang));
            }
        }
    }

    public Dodo(String nom, int rarete, int recherche, int fragment, int bonbon, ArrayList<LieuxDodo> lieux) {
        m_nom = nom;
        m_rarete = rarete;
        m_ptsRecherche = recherche;
        m_qttFragment = fragment;
        m_qttBonbons = bonbon;
        m_localisations = lieux;
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
        StringBuilder r = new StringBuilder();
        for(LieuxDodo ile : m_localisations)
        {
            if(ile.estDisponible())
            {
                if(!r.isEmpty())
                {
                    r.append("<br>");
                }
                r.append("[[").append(ile.getNomIle()).append("]] ([[Fichier:Sprite Rang ").append(ile.getRang()).
                        append(" Sleep.png|Rang ").append(ile.getRang()).append("|25px]]").append(ile.getNiveau()).append(")");
            }
        }
        return r.toString();
    }

    public String getRecompenses(int numDodo)
    {
        return "|recherche" + numDodo + "=" + m_ptsRecherche + "|fragment" + numDodo + "=" + m_qttFragment;
    }

    public int getQttBonbons() {
        return m_qttBonbons;
    }

    public boolean estDispoSurIle(int numile)
    {
        return m_localisations.get(numile).estDisponible();
    }

    public String getRangIle(int numIle)
    {
        return m_localisations.get(numIle).getRang();
    }

    public String getPalierIle(int numIle)
    {
        return m_localisations.get(numIle).getPalier();
    }
}
