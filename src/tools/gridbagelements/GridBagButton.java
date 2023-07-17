package tools.gridbagelements;

import panels.mainpanels.TabbedPlotPanel;
import settings.Parameters;
import simulation.Simulation;
import tools.functions.TestingValues;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import settings.InitialSettings;
public class GridBagButton implements IGridBagElement {
    JButton button;

    TestingValues test = new TestingValues();

    Parameters parameters = Parameters.getInstance();


    public GridBagButton(int type, TabbedPlotPanel parentTabbedPanel) {
        button = new JButton();

        switch (type) {
            case InitialSettings.SIMULATE_BUTTON ->{
                button.addActionListener(e -> {
                    Simulation sim = Simulation.getInstance();

                    if (!parameters.isHoldTheData()) {
                        parentTabbedPanel.costPlotTab.plotPanel.plotter.clearFunctionData();
                    }
                    parentTabbedPanel.layeredStatePlotTab.plotPanel.plotter.clearFunctionData();
                    parentTabbedPanel.layeredCostPlotTab.plotPanel.plotter.clearFunctionData();

                    sim.simulate();

                   addSimulationDataToMainPlot(parentTabbedPanel, sim);
                    try {
                        addSimulationDataToLayeredCostPlot(parentTabbedPanel,sim);
                        addSimulationDataToLayeredStatePlot(parentTabbedPanel,sim);
                    } catch (CloneNotSupportedException ex) {
                        throw new RuntimeException(ex);
                    }

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

    private void addSimulationDataToLayeredCostPlot(TabbedPlotPanel parentTabbedPanel, Simulation sim) throws CloneNotSupportedException {
        parentTabbedPanel.layeredCostPlotTab.plotPanel.plotter.plot.addLayeredFunctionData(sim);
        parentTabbedPanel.layeredCostPlotTab.plotPanel.plotter.plot.updateFunctionValuesToStacked();

        parentTabbedPanel.layeredCostPlotTab.plotPanel.repaint();
    }private void addSimulationDataToLayeredStatePlot(TabbedPlotPanel parentTabbedPanel, Simulation sim) throws CloneNotSupportedException {
        parentTabbedPanel.layeredStatePlotTab.plotPanel.plotter.plot.addLayeredFunctionData(sim);
        parentTabbedPanel.layeredStatePlotTab.plotPanel.plotter.plot.updateFunctionValuesToStacked();

        parentTabbedPanel.layeredStatePlotTab.plotPanel.repaint();
    }


    private void addSimulationDataToMainPlot(TabbedPlotPanel parentTabbedPanel, Simulation sim) {
        parentTabbedPanel.costPlotTab.plotPanel.plotter.plot.addFunctionData(sim.getSimulationDomain(), sim.getOverallCostValues());
        parentTabbedPanel.costPlotTab.plotPanel.repaint();
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
