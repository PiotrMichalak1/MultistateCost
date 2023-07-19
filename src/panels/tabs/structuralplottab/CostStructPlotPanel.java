package panels.tabs.structuralplottab;

import panels.mainpanels.TabsPanel;
import panels.tabs.IPlotPanel;
import panels.tabs.costplottab.CostPlotPanel;
import tools.plotting.plottingmodels.CostStructPlotterModel;
import tools.plotting.plottingmodels.StateStructPlotterModel;


public class CostStructPlotPanel extends StateStructPlotPanel implements IPlotPanel {

    public CostStructPlotPanel(TabsPanel parentTab) {
        super(parentTab);
    }

    @Override
    public void setPlotter(){
        this.plotterModel = new CostStructPlotterModel();
    }


}
