package sleep.view;

import sleep.bouffe.IngredientPoke;
import sleep.bouffe.ListeIngredients;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class DialogIngredientPoke extends JDialog {
    // Stocker les JComboBox pour accès ultérieur
    private final ArrayList<JComboBox<ListeIngredients>> comboIngr = new ArrayList<>();
    private final ArrayList<JComboBox<Object>> comboNiv1 = new ArrayList<>();
    private final ArrayList<JComboBox<Object>> comboNiv30 = new ArrayList<>();
    private final ArrayList<JComboBox<Object>> comboNiv60 = new ArrayList<>();

    public DialogIngredientPoke(JFrame owner)
    {
        super(owner, "Ingrédients du Pokémon");
        setSize(700, 200);
        setModal(true);
    }

    public void showDialog(int nbrIngredients, ArrayList<IngredientPoke> ingredientsExistants)
    {
        setSize(700, 50 + 50*nbrIngredients);
        JPanel panel = new JPanel(new GridLayout(0, 4));
        panel.add(new JLabel("Ingredient"));
        panel.add(new JLabel("Qtt Niveau 1"));
        panel.add(new JLabel("Qtt Niveau 30"));
        panel.add(new JLabel("Qtt Niveau 60"));

        for (int i = 0; i < nbrIngredients; i++) {
            JComboBox<ListeIngredients> ingrBox = new JComboBox<>(ListeIngredients.values());
            JComboBox<Object> niv1Box = new JComboBox<>(IntStream.range(0, 10).boxed().toArray());
            JComboBox<Object> niv30Box = new JComboBox<>(IntStream.range(0, 15).boxed().toArray());
            JComboBox<Object> niv60Box = new JComboBox<>(IntStream.range(0, 20).boxed().toArray());
            comboIngr.add(ingrBox);
            comboNiv1.add(niv1Box);
            comboNiv30.add(niv30Box);
            comboNiv60.add(niv60Box);
            panel.add(ingrBox);
            panel.add(niv1Box);
            panel.add(niv30Box);
            panel.add(niv60Box);
        }

        // Pré-remplissage si la taille correspond
        if (ingredientsExistants.size() == nbrIngredients) {
            remplirComboBoxAvecIngredients(ingredientsExistants);
        }

        ingredientsExistants.clear();

        JButton bouton = new JButton("Confirmer");
        bouton.addActionListener(ActionEvent -> {
            for(int i = 0; i < nbrIngredients; i++)
            {
                ingredientsExistants.add(
                    new IngredientPoke(
                        (ListeIngredients) comboIngr.get(i).getSelectedItem(),
                        comboNiv1.get(i).getSelectedIndex(),
                        comboNiv30.get(i).getSelectedIndex(),
                        comboNiv60.get(i).getSelectedIndex()
                    )
                );
            }
            dispose();
            setVisible(false);
        });

        panel.add(bouton);
        setContentPane(panel);
        setVisible(true);
    }

    // Nouvelle fonction pour pré-remplir les JComboBox (utilise les ArrayList membres)
    private void remplirComboBoxAvecIngredients(ArrayList<IngredientPoke> ingredientsExistants) {
        for (int i = 0; i < ingredientsExistants.size(); i++) {
            IngredientPoke ingr = ingredientsExistants.get(i);
            comboIngr.get(i).setSelectedItem(ingr.getIngredient());
            try {
                comboNiv1.get(i).setSelectedIndex(Integer.parseInt(ingr.getQttNv1().equals("—") ? "0" : ingr.getQttNv1()));
            } catch (NumberFormatException e) {
                comboNiv1.get(i).setSelectedIndex(0);
            }
            try {
                comboNiv30.get(i).setSelectedIndex(Integer.parseInt(ingr.getQttNv30().equals("—") ? "0" : ingr.getQttNv30()));
            } catch (NumberFormatException e) {
                comboNiv30.get(i).setSelectedIndex(0);
            }
            try {
                comboNiv60.get(i).setSelectedIndex(Integer.parseInt(ingr.getQttNv60().equals("—") ? "0" : ingr.getQttNv60()));
            } catch (NumberFormatException e) {
                comboNiv60.get(i).setSelectedIndex(0);
            }
        }
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
