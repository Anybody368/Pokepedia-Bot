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

import org.jetbrains.annotations.NotNull;
import sleep.event.ItemReward;
import sleep.event.bundle.Bundle;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BundleDialog {
    public static Bundle addBundle(Component parent) {
        ArrayList<ItemReward> items = new ArrayList<>(2);

        JTextField txtName = new JTextField();
        JSpinner spnPrice = new JSpinner(new SpinnerNumberModel(250,250,5000,50));
        JSpinner spnLimit = new JSpinner(new SpinnerNumberModel(1,1,3,1));
        JLabel lblItems = new JLabel("0 objets ajoutés");
        JButton btnRemove = new JButton("Supprimer dernier objet");

        btnRemove.addActionListener(e -> {
            if (!items.isEmpty()) {
                items.removeLast();
                lblItems.setText("%d objets ajoutés au bundle".formatted(items.size()));
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel itemPanel = getItemPanel(items, lblItems);

        mainPanel.add(new JLabel("Nom du Bundle"));
        mainPanel.add(txtName);
        mainPanel.add(new JLabel("Prix"));
        mainPanel.add(spnPrice);
        mainPanel.add(new JLabel("Limite"));
        mainPanel.add(spnLimit);
        mainPanel.add(new JLabel("Objets"));
        mainPanel.add(itemPanel);
        mainPanel.add(lblItems);
        mainPanel.add(btnRemove);

        int result = JOptionPane.showConfirmDialog(parent, mainPanel, "Ajout d'un Bundle", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            return new Bundle(txtName.getText(), (Integer) spnPrice.getValue(), (Integer) spnLimit.getValue(), items);
        }

        return null;
    }

    private static @NotNull JPanel getItemPanel(ArrayList<ItemReward> items, JLabel lblItems) {
        JComboBox<ItemReward.Item> cmbItem = new JComboBox<>(ItemReward.Item.values());
        JSpinner spnQuantity = new JSpinner(new SpinnerNumberModel(1,1,300,1));
        JButton btnAdd = new JButton("+");

        btnAdd.addActionListener(e -> {
            ItemReward.Item item = (ItemReward.Item) cmbItem.getSelectedItem();
            ItemReward newReward = new ItemReward(item, (Integer) spnQuantity.getValue());
            if (items.contains(newReward)) return;

            items.add(newReward);
            lblItems.setText("%d objets ajoutés".formatted(items.size()));
        });

        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.X_AXIS));

        itemPanel.add(cmbItem);
        itemPanel.add(spnQuantity);
        itemPanel.add(btnAdd);
        return itemPanel;
    }
}
