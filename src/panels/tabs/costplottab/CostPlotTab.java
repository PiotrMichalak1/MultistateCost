package panels.tabs.costplottab;

import panels.mainpanels.TabsPanel;
import panels.tabs.ITab;
import panels.tabs.sharedpanels.RunSimulationPanel;
import settings.Parameters;
import simulation.Simulation;

import javax.swing.*;
import java.awt.*;

public class CostPlotTab extends JPanel implements ITab {
    public CostPlotPanel plotPanel;
    private final RunSimulationPanel runSimulationPanel;

    public final TabsPanel parentTabbedPanel;
    public CostPlotTab(TabsPanel parentTabbedPanel) {
        this.parentTabbedPanel = parentTabbedPanel;
        setPlotPanel();
        this.runSimulationPanel = new RunSimulationPanel(parentTabbedPanel);
        addWeightedPanes();
    }

    public void setPlotPanel(){
        this.plotPanel = new CostPlotPanel(this);
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
            plotPanel.plotter.clearFunctionData();
        }
    }

    @Override
    public void addDataToPlots(Simulation sim) throws CloneNotSupportedException {
        plotPanel.plotter.plot.addFunctionData(sim.getSimulationDomain(), sim.getOverallCostValues());
    }

    @Override
    public void setPlottersSizes(int width, int height) {
        plotPanel.plotter.setWidth(Math.max(1, width));
        plotPanel.plotter.setHeight(Math.max(1, height));
    }
}
