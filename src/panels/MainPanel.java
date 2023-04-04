package panels;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    public MainPanel(){
        this.setPreferredSize(new Dimension(400,400));
        addWeightedPanes();
    }

    private void addWeightedPanes(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        SettingsPanel settingsPanel = new SettingsPanel();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 3.1;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        this.add(settingsPanel,c);

        SimulationPanel simulationPanel = new SimulationPanel();
        c.weightx = 6.0;
        c.weighty = 1.0;
        c.gridx = 1;
        c.gridy = 0;
        this.add(simulationPanel, c);

    }
}
