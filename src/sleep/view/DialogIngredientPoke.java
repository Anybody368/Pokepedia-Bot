package sleep.view;

import sleep.bouffe.IngredientPoke;
import sleep.bouffe.ListeIngredients;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class DialogIngredientPoke extends JDialog {
    public DialogIngredientPoke(JFrame owner)
    {
        super(owner, "Ingrédients du Pokémon");
        setSize(700, 200);
        setModal(true);
    }

    public ArrayList<IngredientPoke> showDialog(int nbrIngredients)
    {
        setSize(700, 50 + 50*nbrIngredients);
        JPanel panel = new JPanel(new GridLayout(0, 4));
        panel.add(new JLabel("Ingredient"));
        panel.add(new JLabel("Qtt Niveau 1"));
        panel.add(new JLabel("Qtt Niveau 30"));
        panel.add(new JLabel("Qtt Niveau 60"));

        for (int i = 0; i < nbrIngredients; i++) {
            panel.add(new JComboBox<>(ListeIngredients.values()));
            panel.add(new JComboBox<>(IntStream.range(0, 15).boxed().toArray()));
            panel.add(new JComboBox<>(IntStream.range(0, 15).boxed().toArray()));
            panel.add(new JComboBox<>(IntStream.range(0, 15).boxed().toArray()));
        }

        ArrayList<IngredientPoke> listeIngr = new ArrayList<>();

        JButton bouton = new JButton("Confirmer");
        AtomicReference<String> test = new AtomicReference<>("");
        bouton.addActionListener(ActionEvent -> {
            for(int i = 1; i <= nbrIngredients; i++)
            {
                listeIngr.add(newIngr(i));
                dispose();
            }
            setVisible(false);
        });

        panel.add(bouton);
        setContentPane(panel);
        setVisible(true);
        return listeIngr;
    }

    private IngredientPoke newIngr(int num)
    {
        JComboBox<ListeIngredients> ingr = (JComboBox<ListeIngredients>) getContentPane().getComponent(4*num);
        JComboBox<Object> niv1 = (JComboBox<Object>) getContentPane().getComponent(4*num+1);
        JComboBox<Object> niv30 = (JComboBox<Object>) getContentPane().getComponent(4*num+2);
        JComboBox<Object> niv60 = (JComboBox<Object>) getContentPane().getComponent(4*num+3);
        return new IngredientPoke((ListeIngredients) ingr.getSelectedItem(), niv1.getSelectedIndex(), niv30.getSelectedIndex(), niv60.getSelectedIndex());
    }
}
