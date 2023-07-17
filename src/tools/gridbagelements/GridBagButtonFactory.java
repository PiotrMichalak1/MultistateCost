package tools.gridbagelements;

import panels.mainpanels.TabsPanel;
import simulation.Simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import settings.InitialSettings;
public class GridBagButtonFactory implements IGridBagElement {
    JButton button;



    public GridBagButtonFactory(int type, TabsPanel parentTabbedPanel) {
        button = new JButton();

        switch (type) {
            case InitialSettings.SIMULATE_BUTTON ->{
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
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        button.requestFocusInWindow(); // Request focus when the button is pressed
                    }
                });
            }
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
