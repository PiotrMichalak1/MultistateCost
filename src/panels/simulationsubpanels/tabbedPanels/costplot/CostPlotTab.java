package panels.simulationsubpanels.tabbedPanels.costplot;

import javax.swing.*;
import java.awt.*;

public class CostPlotTab extends JPanel {
    PlotPanel plotPanel;
    RunSimulationPanel runSimulationPanel;
    public CostPlotTab() {
        this.plotPanel = new PlotPanel();
        this.runSimulationPanel = new RunSimulationPanel();
        addWeightedPanes();
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
}
