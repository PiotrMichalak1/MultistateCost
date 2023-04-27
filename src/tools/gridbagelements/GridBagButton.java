package tools.gridbagelements;

import panels.simulationsubpanels.tabbedPanels.costplot.PlotPanel;
import tools.TestingValues;
import tools.interfaces.GridBagElement;
import javax.swing.*;
import java.awt.*;

import settings.InitialSettings;
public class GridBagButton implements GridBagElement {
    JButton button;

    TestingValues test = new TestingValues();


    public GridBagButton(int type, PlotPanel parentTab) {
        button = new JButton();

        switch (type) {
            case InitialSettings.SIMULATE_BUTTON ->{
                button.addActionListener(e -> {
                    test.initializeTestFunction();
                    parentTab.plotter.clearFunctionData();
                    parentTab.plotter.addFunctionData(test.testDomain, test.testCodomain);
                    //parentTab.plotter.addFunctionData(test.testDomain2, test.testCodomain2);
                    parentTab.repaint();

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
