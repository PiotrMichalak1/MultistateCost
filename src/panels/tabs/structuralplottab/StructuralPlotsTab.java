package panels.tabs.structuralplottab;

import panels.mainpanels.TabsPanel;
import panels.tabs.ITab;
import panels.tabs.sharedpanels.RunSimulationPanel;
import simulation.Simulation;

import javax.swing.*;
import java.awt.*;

public class StructuralPlotsTab extends JPanel implements ITab {
    TabsPanel parentTabbedPanel;
    private StateStructPlotPanel stateStructurePanel;
    private CostStructPlotPanel costStructurePanel;


    private RunSimulationPanel runSimulationPanel;
    public StructuralPlotsTab(TabsPanel parentTabbedPanel) {
        this.parentTabbedPanel = parentTabbedPanel;
        initializeStructuralPanels(parentTabbedPanel);
        runSimulationPanel = new RunSimulationPanel(parentTabbedPanel);
        addWeightedPanes();
    }

    private void initializeStructuralPanels(TabsPanel parentTabbedPanel) {
        stateStructurePanel = new StateStructPlotPanel(parentTabbedPanel);
        costStructurePanel = new CostStructPlotPanel(parentTabbedPanel);
    }

    public void addWeightedPanes(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth=1;
        c.weightx = 1.0;
        c.weighty = 10.0;
        c.gridx = 0;
        c.gridy = 0;
        this.add(stateStructurePanel, c);


        c.fill = GridBagConstraints.BOTH;
        c.gridwidth=1;
        c.weightx = 1.0;
        c.weighty = 10.0;
        c.gridx = 1;
        c.gridy = 0;
        this.add(costStructurePanel,c);

        runSimulationPanel = new RunSimulationPanel(parentTabbedPanel);
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth=2;
        c.weightx = 2.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy =1;
        this.add(runSimulationPanel,c);
    }

    @Override
    public void updateSpinners() {

    }

    @Override
    public void clearFunctionData() {

    }

    @Override
    public void addDataToPlots(Simulation sim) throws CloneNotSupportedException {
        stateStructurePanel.plotterModel.getPlot().addData(sim);
        costStructurePanel.plotterModel.getPlot().addData(sim);
    }

    @Override
    public void setPlottersSizes(int width, int height) {
        stateStructurePanel.plotterModel.setDrawingWidth(Math.max(1, width));
        stateStructurePanel.plotterModel.setDrawingHeight(Math.max(1, height));
        costStructurePanel.plotterModel.setDrawingWidth(Math.max(1, width));
        costStructurePanel.plotterModel.setDrawingHeight(Math.max(1, height));
    }

}
