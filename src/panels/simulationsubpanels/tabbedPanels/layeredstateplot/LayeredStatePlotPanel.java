package panels.simulationsubpanels.tabbedPanels.layeredstateplot;

import panels.simulationsubpanels.tabbedPanels.costplot.CostPlotTab;
import panels.simulationsubpanels.tabbedPanels.costplot.MainPlotPanel;
import tools.interfaces.IPlotPanel;
import tools.plotting.plotters.LayeredStatePlotter;

public class LayeredStatePlotPanel extends MainPlotPanel implements IPlotPanel {

    public LayeredStatePlotPanel(CostPlotTab parentTab) {
        super(parentTab);
    }

    @Override
    public void setPlotter() {
        this.plotter = new LayeredStatePlotter();

    }
}
