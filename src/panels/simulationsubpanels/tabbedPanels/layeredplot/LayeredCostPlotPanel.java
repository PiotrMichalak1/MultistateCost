package panels.simulationsubpanels.tabbedPanels.layeredplot;

import panels.simulationsubpanels.tabbedPanels.costplot.MainPlotPanel;
import tools.plotting.plotters.LayeredPlotter;


public class LayeredCostPlotPanel extends MainPlotPanel {
    @Override
    public void setPlotter() {
        this.plotter = new LayeredPlotter();
    }
}
