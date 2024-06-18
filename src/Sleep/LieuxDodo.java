package Sleep;

public class LieuxDodo {
    private String m_ile;
    private String m_rang;
    private int m_niveau;

    private final static String NA = "n";

    public LieuxDodo(int numIle, String rangComplet)
    {
        m_ile = UtilSleep.getNomIle(numIle);
        String[] temp = rangComplet.split(" ");
        m_rang = temp[0];
        m_niveau = Integer.parseInt(temp[1]);
    }

    public LieuxDodo(int numIle)
    {
        m_ile = UtilSleep.getNomIle(numIle);
        m_rang = NA;
        m_niveau = -1;

    }

    public boolean estDisponible()
    {
        return !(m_rang.equals(NA));
    }

    public String getIle() {
        return m_ile;
    }

    public String getRang() {
        return m_rang;
    }

    public String getPalier() {
        return m_rang + " " + m_niveau;
    }
}
