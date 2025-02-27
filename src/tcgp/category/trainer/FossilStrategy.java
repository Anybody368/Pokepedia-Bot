package tcgp.category.trainer;

public class FossilStrategy extends ItemStrategy {
    private final int m_HP;

    public FossilStrategy(int hp)
    {
        m_HP = hp;
    }

    @Override
    public String makeNameSection(String en_name, String fr_name, String jp_name) {
        return("| nom=" + fr_name + "\n| sujet=" + fr_name + "\n| nomen=" + en_name + "\n| nomja=" + jp_name);
    }

    @Override
    public String makeInfobox() {
        return "\n| pv=" + m_HP;
    }

    @Override
    public String makeCategorySection() {
        return super.makeCategorySection() + "\n| sous-catégorie2=Fossile";
    }

    @Override
    public String makeFacultes() {
        return "Jouez cette carte comme si c'était un Pokémon {{type|incolore|jcci}} de base avec " + m_HP + " [[PV]]. " +
                "N'importe quand pendant votre tour, vous pouvez défausser cette carte du jeu. Cette carte ne peut pas battre en [[retraite]].\n";
    }
}
