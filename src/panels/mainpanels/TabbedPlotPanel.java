package panels.mainpanels;

import panels.simulationsubpanels.tabbedPanels.*;
import panels.simulationsubpanels.tabbedPanels.costplot.CostPlotTab;
import panels.simulationsubpanels.tabbedPanels.costplot.PlotPanel;

import javax.swing.*;

public class TabbedPlotPanel extends JTabbedPane {

    public TabbedPlotPanel() {
        CostPlotTab costPlot = new CostPlotTab();
        this.addTab("Cost Plot",costPlot);
        LayeredCostPlotTab layeredCostPlotPanel = new LayeredCostPlotTab();
        this.addTab("Layered Cost Plot",layeredCostPlotPanel);
        LayeredStatePlotTab layeredStatePlot = new LayeredStatePlotTab();
        this.addTab("Layered State Plot",layeredStatePlot);
        StructuralPlotsTab structuralPlots = new StructuralPlotsTab();
        this.addTab("Structural Plots", structuralPlots);
        DistributionsTab distributions = new DistributionsTab();
        this.addTab("Distributions", distributions);

    }


}
