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

import sleep.event.ItemReward;
import sleep.event.Mission;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Map;
import java.util.List;

public class MissionsDialog {
    public static List<Mission> selectMissions(Component parent) {
        List<Mission> missions = new ArrayList<>();

        Map<Mission.MissionType, JPanel> missionPanels =
                new EnumMap<>(Mission.MissionType.class);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        for (Mission.MissionType missionType : Mission.MissionType.values()) {
            JPanel missionPanel = new JPanel(new GridLayout(2, 2));
            JCheckBox chbMission = new JCheckBox(missionType.description());

            missionPanel.add(chbMission);
            missionPanel.add(new JLabel());
            missionPanel.add(new JComboBox<>(ItemReward.Item.values()));
            missionPanel.add(
                    new JSpinner(new SpinnerNumberModel(1, 1, 100000, 1))
            );

            missionPanels.put(missionType, missionPanel);

            mainPanel.add(missionPanel);
            mainPanel.add(Box.createVerticalStrut(5));
        }

        int result = JOptionPane.showConfirmDialog(
                parent,
                mainPanel,
                "Choix des missions",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            missionPanels.forEach((missionType, panel) -> {
                if (((JCheckBox) panel.getComponent(0)).isSelected()) {
                    ItemReward.Item reward = (ItemReward.Item) ((JComboBox<?>) panel.getComponent(2)).getSelectedItem();

                    int quantity = (Integer) ((JSpinner) panel.getComponent(3)).getValue();

                    missions.add(new Mission(missionType, new ItemReward(reward, quantity)));
                }
            });

            return missions;
        }

        return null;
    }
}
