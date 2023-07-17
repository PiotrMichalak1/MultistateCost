package panels.tabs.layeredcostplottab;

import panels.mainpanels.TabsPanel;
import panels.tabs.ITab;
import panels.tabs.costplottab.CostPlotTab;
import settings.Parameters;
import simulation.Simulation;

public class LayeredCostPlotTab extends CostPlotTab implements ITab {
    public LayeredCostPlotTab(TabsPanel parentTabbedPane) {
        super(parentTabbedPane);
    }

    @Override
    public void setPlotPanel(){
        this.plotPanel = new LayeredCostPlotPanel(parentTabbedPanel);
    }

    @Override
    public void clearFunctionData() {
            plotPanel.plotter.clearFunctionData();
    }
    @Override
    public void addDataToPlots(Simulation sim) throws CloneNotSupportedException {
        plotPanel.plotter.plot.addLayeredFunctionData(sim);
        plotPanel.plotter.plot.updateFunctionValuesToStacked();
    }
}
