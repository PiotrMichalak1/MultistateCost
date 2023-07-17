package panels.tabs.layeredstateplottab;

import panels.mainpanels.TabsPanel;
import panels.tabs.costplottab.CostPlotTab;
import panels.tabs.costplottab.CostPlotPanel;
import tools.interfaces.IPlotPanel;
import tools.plotting.plotters.LayeredStatePlotter;

public class LayeredStatePlotPanel extends CostPlotPanel implements IPlotPanel {

    public LayeredStatePlotPanel(TabsPanel parentTab) {
        super(parentTab);
    }

    @Override
    public void setPlotter() {
        this.plotter = new LayeredStatePlotter();

    }
}
