package tools.gridbagelements;

import panels.mainpanels.TabsPanel;
import settings.InitialSettings;
import simulation.Simulation;

import javax.swing.*;
import java.awt.*;

public class GridBagButton implements IGridBagElement {
    JButton button;


    public GridBagButton(int type, TabsPanel parentTabbedPanel) {
        button = new JButton();

        if (type == InitialSettings.SIMULATE_BUTTON) {
            button.addActionListener(e -> {
                Simulation sim = Simulation.getInstance();
                sim.simulate();

                parentTabbedPanel.clearFunctionData();
                try {
                    parentTabbedPanel.addDataToPlots(sim);
                } catch (CloneNotSupportedException ex) {
                    throw new RuntimeException(ex);
                }

                parentTabbedPanel.repaintAllPlots();

            });
        } else if (type == InitialSettings.SIMULATE_STRUCT_BUTTON) {

            button.addActionListener(e -> {
                Simulation sim = Simulation.getInstance();
                sim.createRandomData();
                try {
                    parentTabbedPanel.addDataToStructuralPlots(sim);
                } catch (CloneNotSupportedException ex) {
                    throw new RuntimeException(ex);
                }



                parentTabbedPanel.repaintAllPlots();
            });


        }
    }


    @Override
    public void putInGrid(JComponent parent, String text, int bagX, int bagY) {
        GridBagConstraints c = new GridBagConstraints();
        button.setFocusable(false);
        button.setText(text);


        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = bagX;
        c.gridy = bagY;
        parent.add(button, c);
    }
}
