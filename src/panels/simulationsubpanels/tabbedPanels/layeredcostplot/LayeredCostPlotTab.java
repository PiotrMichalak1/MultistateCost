package panels.simulationsubpanels.tabbedPanels.layeredcostplot;

import panels.mainpanels.TabbedPlotPanel;
import panels.simulationsubpanels.tabbedPanels.costplot.CostPlotTab;

public class LayeredCostPlotTab extends CostPlotTab {
    public LayeredCostPlotTab(TabbedPlotPanel parentTabbedPane) {
        super(parentTabbedPane);
    }

    @Override
    public void setPlotPanel(){
        this.plotPanel = new LayeredCostPlotPanel(this);
    }
}
