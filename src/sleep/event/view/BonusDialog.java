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

import sleep.event.bonus.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BonusDialog {
    public static List<Bonus> selectBonuses(Component parent) {
        List<Bonus> bonuses = new ArrayList<>();

        JPanel mainPanel = new JPanel(new GridLayout(0, 2));

        JCheckBox chbCB = new JCheckBox("Boost bonbon");
        JCheckBox chbCBM = new JCheckBox("Mini boost bonbon");

        JCheckBox chbCS = new JCheckBox("Multiplicateur cuisine");
        JSpinner spnCS = new JSpinner(new SpinnerNumberModel(150,105,200,5));

        JCheckBox chbEXP = new JCheckBox("Multiplicateur EXP");
        JSpinner spnEXP = new JSpinner(new SpinnerNumberModel(150,125,300,25));

        JCheckBox chbH = new JCheckBox("Pokémon affamé");

        JCheckBox chbSL = new JCheckBox("Niveau compétence");
        JSpinner spnSL = new JSpinner(new SpinnerNumberModel(1,1,5,1));

        JCheckBox chbST = new JCheckBox("Activation compétence");
        JSpinner spnST = new JSpinner(new SpinnerNumberModel(150,105,200,5));

        JCheckBox chbRC = new JCheckBox("Multiplicateur bonbons");
        JSpinner spnRC = new JSpinner(new SpinnerNumberModel(150,125,300,25));

        JCheckBox chbRR = new JCheckBox("Multiplicateur Pts. de Rech.");
        JSpinner spnRR = new JSpinner(new SpinnerNumberModel(150,125,300,25));

        JCheckBox chbRS = new JCheckBox("Multiplicateur Frag. de Rêve");
        JSpinner spnRS = new JSpinner(new SpinnerNumberModel(150,125,300,25));

        JCheckBox chbSTQ = new JCheckBox("Quelques Pokémon...");
        JCheckBox chbSTP = new JCheckBox("Plusieurs Pokémon...");

        mainPanel.add(chbCS);
        mainPanel.add(spnCS);
        mainPanel.add(chbSL);
        mainPanel.add(spnSL);
        mainPanel.add(chbST);
        mainPanel.add(spnST);
        mainPanel.add(chbEXP);
        mainPanel.add(spnEXP);
        mainPanel.add(chbRC);
        mainPanel.add(spnRC);
        mainPanel.add(chbRR);
        mainPanel.add(spnRR);
        mainPanel.add(chbRS);
        mainPanel.add(spnRS);
        mainPanel.add(chbH);
        mainPanel.add(new JLabel());
        mainPanel.add(chbSTQ);
        mainPanel.add(chbSTP);
        mainPanel.add(chbCB);
        mainPanel.add(chbCBM);

        int result = JOptionPane.showConfirmDialog(parent, mainPanel, "Choix des bonus", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            if (chbCS.isSelected()) bonuses.add(new CookingStrengthBonus((Integer) spnCS.getValue()));
            if (chbSL.isSelected()) bonuses.add(new SkillLevelBonus((Integer) spnSL.getValue()));
            if (chbST.isSelected()) bonuses.add(new SkillTrigerBonus((Integer) spnST.getValue()));
            if (chbEXP.isSelected()) bonuses.add(new ExpBonus((Integer) spnEXP.getValue()));
            if (chbRC.isSelected()) bonuses.add(new SleepRewardBonus(SleepRewardBonus.Reward.CANDIES, (Integer) spnRC.getValue()));
            if (chbRS.isSelected()) bonuses.add(new SleepRewardBonus(SleepRewardBonus.Reward.SHARDS, (Integer) spnRS.getValue()));
            if (chbRR.isSelected()) bonuses.add(new SleepRewardBonus(SleepRewardBonus.Reward.RESEARCH_EXP, (Integer) spnRR.getValue()));
            if (chbH.isSelected()) bonuses.add(new HungryBonus());
            if (chbSTQ.isSelected()) bonuses.add(new SleepTypeBonus(SleepTypeBonus.Quantity.QUELQUES));
            if (chbSTP.isSelected()) bonuses.add(new SleepTypeBonus(SleepTypeBonus.Quantity.PLUSIEURS));
            if (chbCB.isSelected()) bonuses.add(new CandyBoostBonus(false));
            if (chbCBM.isSelected()) bonuses.add(new CandyBoostBonus(true));

            return bonuses;
        }
        return null;
    }
}
