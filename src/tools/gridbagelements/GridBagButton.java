package tools.gridbagelements;

import panels.mainpanels.TabbedPlotPanel;
import settings.Parameters;
import simulation.Simulation;
import tools.Functions.TestingValues;
import tools.interfaces.GridBagElement;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import settings.InitialSettings;
public class GridBagButton implements GridBagElement {
    JButton button;

    TestingValues test = new TestingValues();

    Parameters parameters = Parameters.getInstance();


    public GridBagButton(int type, TabbedPlotPanel parentTabbedPanel) {
        button = new JButton();

        switch (type) {
            case InitialSettings.SIMULATE_BUTTON ->{
                button.addActionListener(e -> {
                    Simulation sim = Simulation.getInstance();

                    test.initializeTestFunction();
                    if (!parameters.isHoldTheData()) {
                        parentTabbedPanel.costPlotTab.plotPanel.plotter.clearFunctionData();
                        parentTabbedPanel.layeredStatePlotTab.plotPanel.plotter.clearFunctionData();
                    }
                    parentTabbedPanel.layeredCostPlotTab.plotPanel.plotter.clearFunctionData();

                    sim.simulate();

                   addSimulationDataToMainPlot(parentTabbedPanel, sim);
                   addSimulationDataToLayeredCostPlot(parentTabbedPanel,sim);
                   addSimulationDataToLayeredStatePlot(parentTabbedPanel,sim);

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

    private void addSimulationDataToLayeredCostPlot(TabbedPlotPanel parentTabbedPanel, Simulation sim) {
        parentTabbedPanel.layeredCostPlotTab.plotPanel.plotter.plot.addLayeredCostFunctionData(Arrays.copyOf(sim.getSimulationDomain(),sim.getSimulationDomain().length),
                Arrays.copyOf(sim.getLayeredCostValues().getOperationalCost(),sim.getLayeredCostValues().getOperationalCost().length),"Operational");

        parentTabbedPanel.layeredCostPlotTab.plotPanel.plotter.plot.addLayeredCostFunctionData(Arrays.copyOf(sim.getSimulationDomain(),sim.getSimulationDomain().length),
                Arrays.copyOf(sim.getLayeredCostValues().getRepairCost(),sim.getLayeredCostValues().getRepairCost().length),"Repair");

        parentTabbedPanel.layeredCostPlotTab.plotPanel.plotter.plot.addLayeredCostFunctionData(Arrays.copyOf(sim.getSimulationDomain(),sim.getSimulationDomain().length),
                Arrays.copyOf(sim.getLayeredCostValues().getInspectionsCost(),sim.getLayeredCostValues().getInspectionsCost().length),"Inspections");

        //parentTabbedPanel.layeredCostPlotTab.plotPanel.plotter.plot.addLayeredCostFunctionData(sim.getSimulationDomain(), sim.getLayeredCostValues().getRepairCost(),"Repair");
        //parentTabbedPanel.layeredCostPlotTab.plotPanel.plotter.plot.addLayeredCostFunctionData(sim.getSimulationDomain(), sim.getLayeredCostValues().getInspectionsCost(),"Inspections");

        parentTabbedPanel.layeredCostPlotTab.plotPanel.plotter.plot.updateFunctionValuesToStacked();

        parentTabbedPanel.layeredCostPlotTab.plotPanel.repaint();
    }private void addSimulationDataToLayeredStatePlot(TabbedPlotPanel parentTabbedPanel, Simulation sim) {
        parentTabbedPanel.layeredStatePlotTab.plotPanel.plotter.plot.addFunctionData(sim.getSimulationDomain(), sim.getLayeredCostValues().getOperationalCost());
        parentTabbedPanel.layeredStatePlotTab.plotPanel.plotter.plot.addFunctionData(sim.getSimulationDomain(), sim.getLayeredCostValues().getRepairCost());
        parentTabbedPanel.layeredStatePlotTab.plotPanel.plotter.plot.addFunctionData(sim.getSimulationDomain(), sim.getLayeredCostValues().getInspectionsCost());

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
