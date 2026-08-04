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

import sleep.event.bundle.Bundle;
import sleep.event.bundle.BundlePack;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BundlePackDialog {
    public static BundlePack addBundlePack(Component parent) {
        ArrayList<Bundle> bundles = new ArrayList<>(3);

        MaskFormatter dateFormatter = null;
        try {
            dateFormatter = new MaskFormatter("##/##/####");
            dateFormatter.setPlaceholderCharacter('_');
        } catch (java.text.ParseException _) {}

        JPanel panel = new JPanel(new GridLayout(0, 2));

        JSpinner spnStartDate = new JSpinner(new SpinnerDateModel());
        spnStartDate.setEditor(new JSpinner.DateEditor(spnStartDate, "dd/MM/yyyy"));
        JSpinner spnEndDate = new JSpinner(new SpinnerDateModel());
        spnEndDate.setEditor(new JSpinner.DateEditor(spnEndDate, "dd/MM/yyyy"));
        JLabel lblBundles = new JLabel("0 Bundles ajoutés");
        JButton btnAddBundle = new JButton("Nouveau Bundle");
        JButton btnRemoveBundle = new JButton("Supprimer le dernier bundle");

        btnAddBundle.addActionListener(e -> {
            Bundle newBundle = BundleDialog.addBundle(panel);
            if (newBundle != null) {
                bundles.add(newBundle);
                lblBundles.setText("%d Bundles ajoutés".formatted(bundles.size()));
            }
        });

        btnRemoveBundle.addActionListener(e -> {
            if (!bundles.isEmpty()) {
                bundles.removeLast();
                lblBundles.setText("%d Bundles ajoutés".formatted(bundles.size()));
            }
        });

        panel.add(new JLabel("Dates de début et de fin de disponibilité"));
        panel.add(new JLabel());
        panel.add(spnStartDate);
        panel.add(spnEndDate);
        panel.add(lblBundles);
        panel.add(btnAddBundle);
        panel.add(btnRemoveBundle);

        int result = JOptionPane.showConfirmDialog(parent, panel, "Nouveau pack de bundles", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            Date startDate = (Date) spnStartDate.getValue();
            Date endDate = (Date) spnEndDate.getValue();

            return new BundlePack(startDate, endDate, bundles);
        }
        return null;
    }
}
