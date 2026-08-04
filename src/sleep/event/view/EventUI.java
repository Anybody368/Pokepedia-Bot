/*
 * Copyright (c) 2026 - Poképedia's contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package sleep.event.view;

import sleep.event.Event;
import sleep.event.Mission;
import sleep.event.bonus.Bonus;
import sleep.event.bundle.BundlePack;
import sleep.pokemon.SimplifiedPokemon;
import utilitaire.Page;
import utilitaire.PageToPublish;
import utilitaire.Util;
import utilitaire.Wiki;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventUI extends JFrame {
    private List<Bonus> bonuses = new ArrayList<>();
    private List<SimplifiedPokemon> newPokemon = new ArrayList<>();
    private List<SimplifiedPokemon> returningPokemon = new ArrayList<>();
    private List<Mission> missions = new ArrayList<>();
    private List<BundlePack> bundles = new ArrayList<>();

    public EventUI(List<SimplifiedPokemon> pokemonList) {
        super("Création d'un évènement");
        JFrame frame = this;
        setSize(600, 300);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JTextField txtName = new JTextField();

        JSpinner spnStartDate = new JSpinner(new SpinnerDateModel());
        spnStartDate.setEditor(new JSpinner.DateEditor(spnStartDate, "dd/MM/yyyy"));

        JSpinner spnDuration = new JSpinner(new SpinnerNumberModel(1, 1, 3, 1));

        JTextField txtLink =  new JTextField();

        JLabel lblNewPokemon = new JLabel("0 nouveaux Pokémon sélectionné(s)");
        JButton btnAddNewPokemon = new JButton("Ajout");
        JButton btnClearNewPokemon = new JButton("Supprimer dernier");
        btnClearNewPokemon.setEnabled(false);
        btnAddNewPokemon.addActionListener(e -> {
            SimplifiedPokemon newNewPokemon = ObjectSelectionDialog.chooseObject(this, "Nouveau Pokémon", "Sélectionnez le Pokémon à ajouter", pokemonList);
            updateListSingle(newNewPokemon, newPokemon, lblNewPokemon, " Pokémon sélectionné(s)", btnClearNewPokemon);
        });
        btnClearNewPokemon.addActionListener(e -> removeFromList(newPokemon, lblNewPokemon, " Pokémon sélectionné(s)", btnClearNewPokemon));

        JLabel lblOldPokemon = new JLabel("0 anciens Pokémon sélectionné(s)");
        JButton btnAddOldPokemon = new JButton("Ajout");
        JButton btnClearOldPokemon = new JButton("Supprimer dernier");
        btnClearOldPokemon.setEnabled(false);
        btnAddOldPokemon.addActionListener(e -> {
            SimplifiedPokemon newOldPokemon = ObjectSelectionDialog.chooseObject(this, "Pokémon boosté", "Sélectionnez le Pokémon à ajouter", pokemonList);
            updateListSingle(newOldPokemon, returningPokemon, lblOldPokemon, " Pokémon sélectionné(s)", btnClearOldPokemon);
        });
        btnClearOldPokemon.addActionListener(e -> removeFromList(returningPokemon, lblOldPokemon, " Pokémon sélectionné(s)", btnClearOldPokemon));

        JButton btnBonuses = new JButton("Choix des bonus");
        JLabel lblBonuses = new JLabel("0 bonus sélectionné(s)");
        btnBonuses.addActionListener(e -> {
            List<Bonus> newBonuses = BonusDialog.selectBonuses(this);
            updateListMany(newBonuses, bonuses, lblBonuses, " bonus sélectionné(s)");
        });

        JButton btnMissions = new JButton("Choix des missions");
        JLabel lblMissions = new JLabel("0 mission(s) sélectionnée(s)");
        btnMissions.addActionListener(e -> {
            List<Mission> newMissions = MissionsDialog.selectMissions(this);
            updateListMany(newMissions, missions, lblMissions, " missions sélectionnée(s)");
        });

        JLabel lblBundles = new JLabel("0 pack(s) de lots ajouté");
        JButton btnAddBundles = new JButton("Ajout d'un pack");
        JButton btnRemoveBundles = new JButton("Supprimer dernier");
        btnRemoveBundles.setEnabled(false);
        btnAddBundles.addActionListener(e -> {
            BundlePack newBundles = BundlePackDialog.addBundlePack(this);
            updateListSingle(newBundles, bundles, lblBundles, " pack(s) ajouté(s)", btnRemoveBundles);
        });
        btnRemoveBundles.addActionListener(e -> removeFromList(bundles, lblBundles, " pack(s) ajouté(s)", btnRemoveBundles));

        JButton btnConfirm = new JButton("Confirmer");
        btnConfirm.addActionListener(e -> {
            String name = txtName.getText();
            if (name.isBlank()) {
                showInputError("Nom de l'évent manquant");
                return;
            }

            URL link;
            try {
                link = new URI(txtLink.getText()).toURL();
            } catch (MalformedURLException | URISyntaxException | IllegalArgumentException ex) {
                showInputError("Lien invalide");
                System.err.println(ex.getMessage());
                return;
            }

            Event event = new Event(txtName.getText(), (Date) spnStartDate.getValue(), (Integer) spnDuration.getValue(),
                    bonuses, newPokemon, returningPokemon, missions, bundles, link);

            String finalText = event.getWikiCode();

            ArrayList<PageToPublish> edits = new ArrayList<>();
            edits.add(new PageToPublish(new Page(event.name(), Wiki.POKEPEDIA), finalText, "Page d'évènement à relire"));
            edits.add(event.updateEvenPage());
            edits.add(event.updateEventModel());

            Util.publishEditsConfirmed(edits);
            System.out.println(finalText);
        });
        btnConfirm.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel generalPanel = new JPanel();
        generalPanel.setLayout(new GridLayout(2, 3));
        generalPanel.add(new JLabel("Nom :"));
        generalPanel.add(new JLabel("Date de début :"));
        generalPanel.add(new JLabel("Durée :"));
        generalPanel.add(txtName);
        generalPanel.add(spnStartDate);
        generalPanel.add(spnDuration);

        mainPanel.add(generalPanel);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(getSecondaryPanel(new JLabel("Lien officiel :"), txtLink, null));
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(getSecondaryPanel(lblNewPokemon, btnAddNewPokemon, btnClearNewPokemon));
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(getSecondaryPanel(lblOldPokemon, btnAddOldPokemon, btnClearOldPokemon));
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(getSecondaryPanel(lblBundles, btnAddBundles, btnRemoveBundles));
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(getSecondaryPanel(lblBonuses, btnBonuses, null));
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(getSecondaryPanel(lblMissions, btnMissions, null));
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(btnConfirm);

        setContentPane(mainPanel);
        setVisible(true);
    }

    private <T> void updateListSingle(T newObject, List<T> list, JLabel label, String text, JButton clearButton) {
        if (newObject == null) return;

        list.add(newObject);
        label.setText(list.size() + text);
        clearButton.setEnabled(true);
    }

    private <T> void removeFromList(List<T> list, JLabel label, String text, JButton button) {
        if (list.isEmpty()) return;

        list.removeLast();
        label.setText(list.size() + text);

        if (list.isEmpty()) button.setEnabled(false);
    }

    private <T> void updateListMany(List<T> newList, List<T> list, JLabel label, String text) {
        if (newList == null) return;

        list.clear();
        list.addAll(newList);
        label.setText(list.size() + text);
    }

    private void showInputError(String error) {
        JOptionPane.showMessageDialog(this, error, "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
    }

    private JPanel getSecondaryPanel(JComponent firstComponent, JComponent secondComponent, JButton clearButton) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel.add(Box.createHorizontalStrut(2));
        panel.add(firstComponent);
        panel.add(Box.createHorizontalStrut(5));
        panel.add(secondComponent);

        if (clearButton != null) {
            panel.add(Box.createHorizontalStrut(5));
            panel.add(clearButton);
        }
        panel.add(Box.createHorizontalGlue());

        panel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        return panel;
    }
}
