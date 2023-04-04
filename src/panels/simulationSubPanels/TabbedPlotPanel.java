package panels.simulationSubPanels;

import panels.simulationSubPanels.tabbedPanels.*;

import javax.swing.*;

public class TabbedPlotPanel extends JTabbedPane {

    public TabbedPlotPanel() {
        PlotPanel plotPanel = new PlotPanel();
        this.addTab("Cost Plot",plotPanel);
        LayeredCostPlotPanel layeredCostPlotPanel = new LayeredCostPlotPanel();
        this.addTab("Layered Cost Plot",layeredCostPlotPanel);
        LayeredStatePlot layeredStatePlot = new LayeredStatePlot();
        this.addTab("Layered State Plot",layeredStatePlot);
        StructuralPlots structuralPlots = new StructuralPlots();
        this.addTab("Structural Plots", structuralPlots);
        Distributions distributions = new Distributions();
        this.addTab("Distributions", distributions);

    }


}
