package panels.tabs.layeredstateplottab;

import panels.mainpanels.TabsPanel;
import panels.tabs.ITab;
import panels.tabs.costplottab.CostPlotTab;
import simulation.Simulation;

public class LayeredStatePlotTab extends CostPlotTab implements ITab {
    public LayeredStatePlotTab(TabsPanel parentTabbedPane) {
        super(parentTabbedPane);
    }
    @Override
    public void setPlotPanel(){
        this.plotPanel = new LayeredStatePlotPanel(parentTabbedPanel);
    }

    @Override
    public void clearFunctionData() {
        plotPanel.plotterModel.clearFunctionData();
    }

    @Override
    public void addDataToPlots(Simulation sim) throws CloneNotSupportedException {
        plotPanel.plotterModel.getPlot().addData(sim);
    }
}
