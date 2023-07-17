package panels.tabs.layeredcostplottab;

import panels.tabs.costplottab.CostPlotTab;
import panels.tabs.costplottab.CostPlotPanel;
import tools.interfaces.IPlotPanel;
import tools.plotting.plotters.LayeredCostPlotter;


public class LayeredCostPlotPanel extends CostPlotPanel implements IPlotPanel {
    public LayeredCostPlotPanel(CostPlotTab parentTab) {
        super(parentTab);
    }

    @Override
    public void setPlotter() {
        this.plotter = new LayeredCostPlotter();

    }

}
