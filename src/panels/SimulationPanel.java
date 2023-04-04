package panels;

import panels.simulationSubPanels.PlotPanel;
import panels.simulationSubPanels.RunSimulationPanel;

import javax.swing.*;
import java.awt.*;

public class SimulationPanel extends JPanel {
    public SimulationPanel() {
        addWeightedPanes();
    }
    private void addWeightedPanes(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        PlotPanel plotPanel = new PlotPanel();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 5.0;
        c.gridx = 0;
        c.gridy = 0;
        this.add(plotPanel,c);

        RunSimulationPanel runSimulationPanel = new RunSimulationPanel();
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 1;
        this.add(runSimulationPanel, c);

    }
}
