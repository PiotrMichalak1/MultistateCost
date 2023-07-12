package panels.simulationsubpanels.tabbedPanels.layeredcostplot;

import panels.simulationsubpanels.tabbedPanels.costplot.CostPlotTab;
import panels.simulationsubpanels.tabbedPanels.costplot.MainPlotPanel;
import tools.plotting.plotters.LayeredCostPlotter;


public class LayeredCostPlotPanel extends MainPlotPanel {
    public LayeredCostPlotPanel(CostPlotTab parentTab) {
        super(parentTab);
    }

    @Override
    public void setPlotter() {
        this.plotter = new LayeredCostPlotter();

    }

}
