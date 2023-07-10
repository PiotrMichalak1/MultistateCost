package panels.simulationsubpanels.tabbedPanels.layeredplot;

import panels.simulationsubpanels.tabbedPanels.costplot.CostPlotTab;
import panels.simulationsubpanels.tabbedPanels.costplot.MainPlotPanel;
import panels.simulationsubpanels.tabbedPanels.costplot.RunSimulationPanel;

import javax.swing.*;
import java.awt.*;

public class LayeredCostPlotTab extends CostPlotTab {
    @Override
    public void setPlotPanel(){
        this.plotPanel = new LayeredCostPlotPanel();
    }
}
