package panels.tabs.structuralplottab;

import panels.mainpanels.TabsPanel;
import panels.tabs.IPlotPanel;
import panels.tabs.costplottab.CostPlotPanel;
import tools.plotting.plottingmodels.StateStructPlotterModel;


public class StateStructPlotPanel extends CostPlotPanel implements IPlotPanel {
    public StateStructPlotPanel(TabsPanel parentTab) {
        super(parentTab);
    }
    @Override
    public void setPlotter(){
        this.plotterModel = new StateStructPlotterModel();
    }


}
