package tools.gridbagelements;

import panels.simulationsubpanels.tabbedPanels.costplot.MainPlotPanel;
import settings.Parameters;
import simulation.Simulation;
import tools.TestingValues;
import tools.interfaces.GridBagElement;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import settings.InitialSettings;
public class GridBagButton implements GridBagElement {
    JButton button;

    TestingValues test = new TestingValues();

    Parameters parameters = Parameters.getInstance();


    public GridBagButton(int type, MainPlotPanel parentTab) {
        button = new JButton();

        switch (type) {
            case InitialSettings.SIMULATE_BUTTON ->{
                button.addActionListener(e -> {
                    Simulation sim = Simulation.getInstance();

                    test.initializeTestFunction();
                    if (!parameters.isHoldTheData()) {
                        parentTab.plotter.clearFunctionData();
                    }
                    //test.initializeTestFunction();
                    //parentTab.mainPlotter.plot.addFunctionData(test.testDomain, test.testCodomain);
                    //parentTab.mainPlotter.plot.addFunctionData(test.testDomain2, test.testCodomain2);
                    sim.simulate();
                    parentTab.plotter.plot.addFunctionData(sim.getSimulationDomain(), sim.getSimulationValues());
                    parentTab.repaint();
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
