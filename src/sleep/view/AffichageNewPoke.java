package sleep.view;

import sleep.bouffe.IngredientPoke;
import sleep.dodos.Dodo;
import sleep.dodos.Iles;
import sleep.dodos.TypesDodo;
import sleep.pokemon.*;
import utilitaire.Page;
import utilitaire.PokeTypes;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;

public class AffichageNewPoke extends  JFrame {
    public AffichageNewPoke() {
        super("Ajout de Pokémon");
        JFrame frame = this;
        setSize(1000, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 4));

        JTextField nomPoke = new JTextField();
        JTextField numPoke = new JTextField();
        JComboBox<PokeTypes> typePoke = new JComboBox<>(PokeTypes.values());
        JComboBox<Specialites> spePoke = new JComboBox<>(Specialites.values());
        JComboBox<TypesDodo> dodoPoke = new JComboBox<>(TypesDodo.values());
        JComboBox<Competences> compPoke = new JComboBox<>(Competences.values());
        JComboBox<Object> stockPoke = new JComboBox<>(IntStream.range(5, 31).boxed().toArray());
        JComboBox<Integer> recPoke = new JComboBox<>(new Integer[] {5, 7, 10, 12, 16, 20, 22, 25, 30});
        JComboBox<Integer> heures = new JComboBox<>(new Integer[] {0, 1});
        JComboBox<Object> minutes = new JComboBox<>(IntStream.range(0, 60).boxed().toArray());
        JComboBox<Object> secondes = new JComboBox<>(IntStream.range(0, 60).boxed().toArray());
        JTextField nomBonbon = new JTextField();
        JComboBox<Integer> nbrDodos = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        JComboBox<Integer> nbrIngr = new JComboBox<>(new Integer[]{1, 2, 3});
        JComboBox<Imagery> imgType = new JComboBox<>(Imagery.values());

        JComponent[] composants = {
                new JLabel("Nom du Pokémon"),
                new JLabel("Numéro du Pokémon"),
                new JLabel("Type du Pokémon"),
                new JLabel("Spécialité du Pokémon"),
                nomPoke,
                numPoke,
                typePoke,
                spePoke,
                new JLabel("Catégorie de dodo"),
                new JLabel("Compétence du Pokémon"),
                new JLabel("Stockage du Pokémon"),
                new JLabel("Points de recrutement"),
                dodoPoke,
                compPoke,
                stockPoke,
                recPoke,
                new JLabel("Fréquence Heures"),
                new JLabel("Minutes"),
                new JLabel("Secondes"),
                new JLabel("Nom Bonbon"),
                heures,
                minutes,
                secondes,
                nomBonbon,
                new JLabel("Nombre de dodos"),
                new JLabel("Nombre d'ingrédients"),
                new JLabel("Type d'imagerie"),
                new JLabel(),
                nbrDodos,
                nbrIngr,
                imgType
        };

        for(JComponent composant : composants)
        {
            panel.add(composant);
        }

        for(Iles ile : Iles.values())
        {
            panel.add(new JCheckBox(ile.getNom(true)));
        }

        panel.add(new JLabel());
        for(int i = 0; i < (panel.getComponentCount()+1)%4; i++)
        {
            panel.add(new JLabel());
        }

        final ArrayList<IngredientPoke> ingredients = new ArrayList<>();

        JButton confirm = new JButton("Confirmer");
        confirm.addActionListener(ActionEvent -> {
            int numDex = Integer.parseInt(numPoke.getText());

            DialogIngredientPoke getIngredients = new DialogIngredientPoke(this);
            getIngredients.showDialog(nbrIngr.getSelectedIndex()+1, ingredients);

            ArrayList<Iles> iles = new ArrayList<>();
            int j = 0;
            for(Iles ile : Iles.values())
            {
                JCheckBox box = (JCheckBox) panel.getComponent(31+j);
                if(box.isSelected())
                {
                    iles.add(ile);
                }
                j++;
            }

            ArrayList<Dodo> dodos = new ArrayList<>();
            int nbDodo = nbrDodos.getSelectedIndex()+1;
            for(int i = 1; i <= nbDodo; i++)
            {
                DialogDodoPoke getDodo = new DialogDodoPoke(i, this);
                dodos.add(getDodo.showDialog(iles));
            }

            String nom = nomPoke.getText();
            PokeTypes type = (PokeTypes) typePoke.getSelectedItem();
            TypesDodo typeDodo = (TypesDodo) dodoPoke.getSelectedItem();
            Specialites spec = (Specialites) spePoke.getSelectedItem();
            String freq = heures.getSelectedIndex() + ":" + minutes.getSelectedIndex() + ":" + secondes.getSelectedIndex();
            int capacite = stockPoke.getSelectedIndex()+5;
            Competences comp = (Competences) compPoke.getSelectedItem();
            int ptsAmitie = (int) recPoke.getSelectedItem();
            String bonbon = nomBonbon.getText();
            Imagery img = (Imagery) imgType.getSelectedItem();

            frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    Pokemon Poke = new Pokemon(nom, numDex, type, typeDodo, spec, ingredients, dodos, iles, freq, capacite, comp, ptsAmitie, bonbon, img);
                    HashMap<Page, String> wikiPages = Poke.getWikiModifications();

                    JLabel label = (JLabel) getContentPane().getComponent(getContentPane().getComponentCount() -2);
                    label.setOpaque(false);

                    wikiPages.forEach( (k, v) -> {
                        label.setText(k.getTitle() + "...");
                        String summary = k.getTitle().contains("/Imagerie") ? "Ajout Pokemon Sleep" :
                                "Ajout automatique de " + Poke.getName();
                        if(k.setContent(v, summary))
                        {
                            System.out.println(k.getTitle() + " modifiée avec succès !");
                        }
                        else
                        {
                            System.err.println("Echec de la modification de " + k.getTitle());
                        }
                    });

                    new SpriteHandler(Poke).uploadAll();

                    return null;
                }

                @Override
                protected void done() {
                    frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    confirmation(nom);
                    try {
                        get();
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Erreur : " + e.getMessage());
                    }
                }
            }.execute();
        });
        panel.add(confirm);

        setContentPane(panel);
        setVisible(true);
    }

    private void confirmation(String nomPoke) {
        System.out.println("Ajout de " + nomPoke + " terminé !");
        JLabel label = (JLabel) getContentPane().getComponent(getContentPane().getComponentCount() -2);
        label.setText(nomPoke + " bien ajouté !");
        label.setOpaque(true);
        label.setBackground(Color.green);
    }
}
