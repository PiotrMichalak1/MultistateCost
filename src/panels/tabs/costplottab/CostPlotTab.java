package panels.tabs.costplottab;

import panels.mainpanels.TabsPanel;
import panels.tabs.ITab;
import panels.tabs.sharedpanels.RunSimulationPanel;
import settings.Parameters;
import simulation.Simulation;

import javax.swing.*;
import java.awt.*;

public class CostPlotTab extends JPanel implements ITab {
    protected CostPlotPanel plotPanel;

    private RunSimulationPanel runSimulationPanel;
    public final TabsPanel parentTabbedPanel;
    public CostPlotTab(TabsPanel parentTabbedPanel) {
        this.parentTabbedPanel = parentTabbedPanel;
        setPlotPanel();
        addWeightedPanes();
    }

    public void setPlotPanel(){
        this.plotPanel = new CostPlotPanel(parentTabbedPanel);
    }

    private void addWeightedPanes(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 10.0;
        c.gridx = 0;
        c.gridy = 0;
        this.add(plotPanel, c);

        runSimulationPanel = new RunSimulationPanel(parentTabbedPanel);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy =1;
        this.add(runSimulationPanel,c);
    }

    public void updateSpinners() {
        runSimulationPanel.updateSpinnersAndCheckboxes();
    }

    @Override
    public void clearFunctionData() {
        if (!Parameters.getInstance().isHoldTheData()) {
            plotPanel.plotterModel.clearFunctionData();
        }
    }

    @Override
    public void addDataToPlots(Simulation sim) throws CloneNotSupportedException {
        plotPanel.plotterModel.getPlot().addFunctionData(sim.getSimulationDomain(), sim.getOverallCostValues());
    }

    @Override
    public void setPlottersSizes(int width, int height) {
        plotPanel.plotterModel.setDrawingWidth(Math.max(1, width));
        plotPanel.plotterModel.setDrawingHeight(Math.max(1, height));
    }
}
