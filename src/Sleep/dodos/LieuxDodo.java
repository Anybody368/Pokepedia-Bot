package Sleep.dodos;

public class LieuxDodo {
    private final String m_ile;
    private final String m_rang;
    private final int m_niveau;

    private final static String NA = "n";

    public LieuxDodo(String nomIle, String rangComplet)
    {
        m_ile = nomIle;
        if(rangComplet.length() <= 3)
        {
            m_rang = switch (rangComplet.charAt(0))
            {
                case 'b' -> "Basique";
                case 's' -> "Super";
                case 'h' -> "Hyper";
                case 'm' -> "Master";
                default -> "erreur";
            };
            m_niveau = Integer.parseInt(rangComplet.substring(1));
        }
        else {
            String[] temp = rangComplet.split(" ");
            m_rang = temp[0];
            m_niveau = Integer.parseInt(temp[1]);
        }
    }

    public LieuxDodo(String nomIle)
    {
        m_ile = nomIle;
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

    public int getNiveau() { return m_niveau; }

    public String getPalier() {
        return m_rang + " " + m_niveau;
    }
}
