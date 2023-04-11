package panels.settingsSubPanels;

import settings.InitialSettings;
import settings.Parameters;
import tools.GridBagCheckbox;
import tools.GridBagLabel;
import tools.GridBagSpinner;

import javax.swing.*;
import java.awt.*;

public class EmergencyRepairPanel extends JPanel {
    Parameters parameters;
    GridBagLabel bagLabel;

    public EmergencyRepairPanel() {
        parameters = Parameters.getInstance();
        bagLabel = new GridBagLabel();
        initializeEmergencyRepairLabels();
        initializeEmergencyRepairSpinners();
    }

    private void initializeEmergencyRepairLabels() {
        this.setLayout(new GridBagLayout());

        GridBagCheckbox emergencyRepairCheckBox = new GridBagCheckbox(InitialSettings.EMERGENCY_REPAIR_CHK);
        emergencyRepairCheckBox.putInGrid(this, "Emergency Repair", 1, 0);

        bagLabel.putInGrid(this, "State drops to", 0, 1);
        bagLabel.putInGrid(this, "Next inspection in", 0, 2);
        bagLabel.putInGrid(this, "Emergency cost", 0, 3);
        bagLabel.putInGrid(this, "Delay", 0, 4);

        bagLabel.putInGrid(this,"or below",2,1);
    }

    private void initializeEmergencyRepairSpinners() {
        GridBagSpinner spinner = new GridBagSpinner(InitialSettings.STATE_DROPS_TO);
        spinner.putInGrid(this, "", 1, 1);
        spinner = new GridBagSpinner(InitialSettings.NEXT_INSPECTION_IN);
        spinner.putInGrid(this, "", 1, 2);
        spinner = new GridBagSpinner(InitialSettings.EMERGENCY_COST);
        spinner.putInGrid(this, "", 1, 3);
        spinner = new GridBagSpinner(InitialSettings.EMERGENCY_DELAY);
        spinner.putInGrid(this, "", 1, 4);

    }
}



