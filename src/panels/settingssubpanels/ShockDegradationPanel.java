package panels.settingssubpanels;

import settings.InitialSettings;
import tools.gridbagelements.GridBagCheckboxFactory;

import javax.swing.*;

public class ShockDegradationPanel extends JPanel implements ISettingPanel {
    public ShockDegradationPanel() {
        initializeCheckBox();
    }

    private void initializeCheckBox() {
        GridBagCheckboxFactory checkbox = new GridBagCheckboxFactory(InitialSettings.SHOCK_DEGRADATION);
        checkbox.putInGrid(this, "Shock Degradation", 0, 0);
    }

    public JPanel getPanel() {
        return this;
    }
}
