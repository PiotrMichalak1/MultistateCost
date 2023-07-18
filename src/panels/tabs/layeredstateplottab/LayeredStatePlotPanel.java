package panels.tabs.layeredstateplottab;

import panels.mainpanels.TabsPanel;
import panels.tabs.IPlotPanel;
import panels.tabs.costplottab.CostPlotPanel;
import tools.plotting.plottingmodels.LayeredStatePlotterModel;

public class LayeredStatePlotPanel extends CostPlotPanel implements IPlotPanel {

    public LayeredStatePlotPanel(TabsPanel parentTab) {
        super(parentTab);
    }

    @Override
    public void setPlotter() {
        this.plotterModel = new LayeredStatePlotterModel();

    }
}
