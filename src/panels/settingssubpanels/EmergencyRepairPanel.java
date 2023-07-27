package panels.settingssubpanels;

import settings.InitialSettings;
import settings.Parameters;
import tools.gridbagelements.GridBagCheckbox;
import tools.gridbagelements.GridBagLabel;
import tools.gridbagelements.GridBagSpinner;

import javax.swing.*;
import java.awt.*;

public class EmergencyRepairPanel extends JPanel implements ISettingPanel {

    public EmergencyRepairPanel() {
        initializeEmergencyRepairLabels();
        initializeEmergencyRepairSpinners();
    }

    private void initializeEmergencyRepairLabels() {
        GridBagLabel bagLabel = new GridBagLabel();
        this.setLayout(new GridBagLayout());

        GridBagCheckbox emergencyRepairCheckBox = new GridBagCheckbox(InitialSettings.EMERGENCY_REPAIR_CHK);
        emergencyRepairCheckBox.putInGrid(this, "Emergency Repair", 1, 0);

        bagLabel.putInGrid(this, "State drops to", 0, 1);
        bagLabel.putInGrid(this, "Next inspection in", 0, 2);
        bagLabel.putInGrid(this, "Emergency cost", 0, 3);
        bagLabel.putInGrid(this, "Delay", 0, 4);

        bagLabel.putInGrid(this, "or below", 2, 1);
    }

    private void initializeEmergencyRepairSpinners() {
        Parameters parameters = Parameters.getInstance();

        GridBagSpinner delaySpinner = new GridBagSpinner(InitialSettings.EMERGENCY_DELAY);
        GridBagSpinner nextInspectionInSpinner = new GridBagSpinner(InitialSettings.NEXT_INSPECTION_IN);

        GridBagSpinner spinner = new GridBagSpinner(InitialSettings.STATE_DROPS_TO);
        spinner.putInGrid(this, "", 1, 1);
        spinner = new GridBagSpinner(InitialSettings.EMERGENCY_COST);
        spinner.putInGrid(this, "", 1, 3);
        delaySpinner.putInGrid(this, "", 1, 4);
        nextInspectionInSpinner.putInGrid(this, "", 1, 2);

        delaySpinner.getSpinner().addChangeListener(e -> {
            parameters.setValueInParameters(InitialSettings.EMERGENCY_DELAY, (int) delaySpinner.getSpinner().getValue());
            nextInspectionInSpinner.setModel(parameters.getEmNextInspectionIn(), parameters.getEmDelay(), 1000, 1);
            nextInspectionInSpinner.setToolTipText("Value Must be an integer value between " + parameters.getEmDelay() + "and 1000");
            System.out.println(delaySpinner.getSpinner().getValue());
        });

        nextInspectionInSpinner.getSpinner().addChangeListener(e -> {
            parameters.setValueInParameters(InitialSettings.NEXT_INSPECTION_IN, (int) nextInspectionInSpinner.getSpinner().getValue());
            delaySpinner.setModel(parameters.getEmDelay(), 0, parameters.getEmNextInspectionIn(), 1);
            delaySpinner.setToolTipText("Value Must be an integer value between 0 and " + parameters.getEmNextInspectionIn());
            System.out.println(nextInspectionInSpinner.getSpinner().getValue());
        });

    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}



