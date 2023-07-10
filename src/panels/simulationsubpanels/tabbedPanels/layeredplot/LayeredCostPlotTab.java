package panels.simulationsubpanels.tabbedPanels.layeredplot;

import panels.mainpanels.TabbedPlotPanel;
import panels.simulationsubpanels.tabbedPanels.costplot.CostPlotTab;
import panels.simulationsubpanels.tabbedPanels.costplot.MainPlotPanel;
import panels.simulationsubpanels.tabbedPanels.costplot.RunSimulationPanel;

import javax.swing.*;
import java.awt.*;

public class LayeredCostPlotTab extends CostPlotTab {
    public LayeredCostPlotTab(TabbedPlotPanel parentTabbedPane) {
        super(parentTabbedPane);
    }

    @Override
    public void setPlotPanel(){
        this.plotPanel = new LayeredCostPlotPanel();
    }
}
