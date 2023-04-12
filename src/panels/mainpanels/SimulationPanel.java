package panels.mainpanels;

import panels.simulationsubpanels.*;



import javax.swing.*;
import java.awt.*;

public class SimulationPanel extends JPanel {
    public SimulationPanel() {
        addWeightedPanes();
    }
    private void addWeightedPanes(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        TabbedPlotPanel tabbedPlotPanel = new TabbedPlotPanel();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 5.0;
        c.gridx = 0;
        c.gridy = 0;
        this.add(tabbedPlotPanel,c);

        RunSimulationPanel runSimulationPanel = new RunSimulationPanel();
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 1;
        this.add(runSimulationPanel, c);
    }
}
