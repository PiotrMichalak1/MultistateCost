package panels.settingssubpanels;

import settings.InitialSettings;
import tools.gridbagelements.GridBagCheckbox;

import javax.swing.*;

public class ShockDegradation extends JPanel {
    public ShockDegradation() {
        initializeCheckBox();
    }

    private void initializeCheckBox(){
        GridBagCheckbox checkbox = new GridBagCheckbox(InitialSettings.SHOCK_DEGRADATION);
        checkbox.putInGrid(this, "Shock Degradation", 0, 0);
    }
}
