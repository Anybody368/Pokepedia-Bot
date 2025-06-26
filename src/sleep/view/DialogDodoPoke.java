package sleep.view;

import sleep.dodos.Dodo;
import sleep.dodos.Iles;
import sleep.dodos.LieuxDodo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class DialogDodoPoke extends JDialog {
    private final int m_numDodo;

    public DialogDodoPoke(int numDodo, JFrame owner)
    {
        super(owner, "Dodo numéro " + numDodo);
        m_numDodo = numDodo;
        setSize(600, 350);
        setModal(true);

        JPanel panel = new JPanel(new GridLayout(0,3));
        setContentPane(panel);
    }

    public Dodo showDialog(ArrayList<Iles> iles)
    {
        JPanel panel = (JPanel) getContentPane();

        JTextField nomDodo = new JTextField();
        if(m_numDodo == 4)
        {
            nomDodo.setText("sur Gros Bidou");
        }
        JComboBox<Integer> rareteDodo = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        if(m_numDodo < 5)
        {
            rareteDodo.setSelectedIndex(m_numDodo-1);
        }
        JComboBox<Object> bonbons = new JComboBox<>(IntStream.range(3, 26).boxed().toArray());
        JTextField ptsRech = new JTextField();
        JTextField fragReve = new JTextField();

        JComponent[] composants = {
                new JLabel("Nom du dodo sans \"dodo\""),
                new JLabel("Rareté du dodo"),
                new JLabel("Nombre de bonbons"),
                nomDodo,
                rareteDodo,
                bonbons,
                new JLabel("Points de recherche"),
                new JLabel("Fragments de Rêve"),
                new JLabel(),
                ptsRech,
                fragReve,
                new JLabel()
        };

        for(JComponent comp : composants)
        {
            panel.add(comp);
        }

        for(Iles ile : iles)
        {
            panel.add(new JLabel(ile.getNom()));
            panel.add(new JComboBox<>(new String[]{"n", "Basique 1", "Basique 2", "Basique 3", "Basique 4", "Basique 5",
                    "Super 1", "Super 2", "Super 3", "Super 4", "Super 5", "Hyper 1", "Hyper 2", "Hyper 3", "Hyper 4", "Hyper 5",
                    "Master 1", "Master 2", "Master 3", "Master 4", "Master 5", "Master 6", "Master 7", "Master 8", "Master 9", "Master 10",
                    "Master 11", "Master 12", "Master 13", "Master 14", "Master 15", "Master 16", "Master 17", "Master 18", "Master 19", "Master 20",
            }));
            panel.add(new JLabel());
        }
        panel.add(new JLabel());
        panel.add(new JLabel());

        JButton confirm = new JButton("Confirmer");
        AtomicReference<Dodo> dodo = new AtomicReference<>();

        confirm.addActionListener(ActiveEvent -> {
            int i = 0;
            ArrayList<LieuxDodo> lieux = new ArrayList<>();
            for(Iles ile : iles)
            {
                JComboBox<String> rang = (JComboBox<String>) getContentPane().getComponent(13+i);
                if(rang.getSelectedIndex() == 0)
                {
                    lieux.add(new LieuxDodo(ile));
                }
                else
                {
                    lieux.add(new LieuxDodo(ile, rang.getSelectedItem().toString()));
                }
                i += 3;
            }

            dodo.set(new Dodo(nomDodo.getText(), rareteDodo.getSelectedIndex() + 1, Integer.parseInt(ptsRech.getText()), Integer.parseInt(fragReve.getText()), (int) bonbons.getSelectedItem(), lieux));
            setVisible(false);
            dispose();
        });

        panel.add(confirm);
        setVisible(true);

        return dodo.get();
    }
}
