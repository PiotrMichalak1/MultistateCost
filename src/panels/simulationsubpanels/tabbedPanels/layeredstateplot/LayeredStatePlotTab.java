package panels.simulationsubpanels.tabbedPanels.layeredstateplot;

import panels.mainpanels.TabbedPlotPanel;
import panels.simulationsubpanels.tabbedPanels.costplot.CostPlotTab;
import panels.simulationsubpanels.tabbedPanels.layeredcostplot.LayeredCostPlotPanel;

import javax.swing.*;
import java.awt.*;

public class LayeredStatePlotTab extends CostPlotTab {
    public LayeredStatePlotTab(TabbedPlotPanel parentTabbedPane) {
        super(parentTabbedPane);
    }
    @Override
    public void setPlotPanel(){
        this.plotPanel = new LayeredStatePlotPanel(this);
    }
}
