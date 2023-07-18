package panels.tabs.structuralplottab;

import panels.mainpanels.TabsPanel;
import panels.tabs.IPlotPanel;
import panels.tabs.costplottab.CostPlotPanel;
import tools.plotting.plottingmodels.StructuralPlotterModel;


public class StructuralPlotsPanel extends CostPlotPanel implements IPlotPanel {
    public StructuralPlotsPanel(TabsPanel parentTab) {
        super(parentTab);
    }
    @Override
    public void setPlotter(){
        this.plotterModel = new StructuralPlotterModel();
    }


}
