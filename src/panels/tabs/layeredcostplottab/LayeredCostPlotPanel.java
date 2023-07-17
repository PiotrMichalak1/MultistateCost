package panels.tabs.layeredcostplottab;

import panels.mainpanels.TabsPanel;
import panels.tabs.costplottab.CostPlotTab;
import panels.tabs.costplottab.CostPlotPanel;
import tools.interfaces.IPlotPanel;
import tools.plotting.plotters.LayeredCostPlotter;


public class LayeredCostPlotPanel extends CostPlotPanel implements IPlotPanel {
    public LayeredCostPlotPanel(TabsPanel parentTab) {
        super(parentTab);
    }

    @Override
    public void setPlotter() {
        this.plotter = new LayeredCostPlotter();

    }

}
