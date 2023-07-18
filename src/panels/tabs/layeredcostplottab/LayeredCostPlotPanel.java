package panels.tabs.layeredcostplottab;

import panels.mainpanels.TabsPanel;
import panels.tabs.IPlotPanel;
import panels.tabs.costplottab.CostPlotPanel;
import tools.plotting.plottingmodels.LayeredCostPlotterModel;


public class LayeredCostPlotPanel extends CostPlotPanel implements IPlotPanel {
    public LayeredCostPlotPanel(TabsPanel parentTab) {
        super(parentTab);
    }

    @Override
    public void setPlotter() {
        this.plotterModel = new LayeredCostPlotterModel();

    }

}
