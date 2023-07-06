package panels;

import panels.mainpanels.SettingsPanel;
import panels.mainpanels.TabbedPlotPanel;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    SettingsPanel settingsPanel;
    TabbedPlotPanel tabbedPlotPanel;

    public MainPanel() {
        this.setPreferredSize(new Dimension(1200, 675));
        addWeightedPanes();


    }

    private void addWeightedPanes(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        settingsPanel = new SettingsPanel();
        c.fill = GridBagConstraints.VERTICAL;
        c.weightx = 0.1;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        this.add(settingsPanel, c);

        c.fill = GridBagConstraints.BOTH;
        tabbedPlotPanel = new TabbedPlotPanel();
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 1;
        c.gridy = 0;
        this.add(tabbedPlotPanel, c);

    }


}
