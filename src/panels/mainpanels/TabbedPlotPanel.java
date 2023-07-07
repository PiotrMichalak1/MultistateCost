package panels.mainpanels;

import panels.simulationsubpanels.tabbedPanels.*;
import panels.simulationsubpanels.tabbedPanels.costplot.CostPlotTab;
import panels.simulationsubpanels.tabbedPanels.layeredplot.LayeredCostPlotTab;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class TabbedPlotPanel extends JTabbedPane {

    public TabbedPlotPanel() {
        //index 0
        CostPlotTab costPlot = new CostPlotTab();
        this.addTab("Cost Plot",costPlot);
        //index 1
        LayeredCostPlotTab layeredCostPlotPanel = new LayeredCostPlotTab();
        this.addTab("Layered Cost Plot",layeredCostPlotPanel);
        LayeredStatePlotTab layeredStatePlot = new LayeredStatePlotTab();
        this.addTab("Layered State Plot",layeredStatePlot);
        StructuralPlotsTab structuralPlots = new StructuralPlotsTab();
        this.addTab("Structural Plots", structuralPlots);
        DistributionsTab distributions = new DistributionsTab();
        this.addTab("Distributions", distributions);

        this.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = getSelectedIndex();

                if (selectedIndex == 0) {
                    costPlot.updateSpinners();
                } else if (selectedIndex == 1) {
                    layeredCostPlotPanel.updateSpinners();
                }
            }
        });

    }


}
