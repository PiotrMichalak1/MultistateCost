package panels.settingssubpanels;

import settings.InitialSettings;
import settings.Parameters;
import tools.gridbagelements.GridBagLabel;
import tools.gridbagelements.GridBagSpinner;

import javax.swing.*;
import java.awt.*;

public class RepairDurationPanel extends JPanel implements ISettingPanel {


    public RepairDurationPanel() {
        initializeRepairDurationLabels();
        initializeRepairDurationSpinners();
    }

    private void initializeRepairDurationLabels() {
        GridBagLabel bagLabel= new GridBagLabel();

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        bagLabel.putInGrid(this, "Repair Duration", 0, 0, InitialSettings.REPAIR_DURATION);

        for (int i = 1; i <= InitialSettings.DEFAULT_NUM_OF_STATES; i++) {
            if (i == 1) {
                bagLabel.putInGrid(this, "State", 0, 1);
            } else {
                bagLabel.putInGrid(this, "" + i, i - 1, 1);
            }

        }

        for (int i = 1; i < InitialSettings.DEFAULT_NUM_OF_STATES; i++) {
            bagLabel.putInGrid(this, "to " + i, 0, i + 1);
        }

    }

    private void initializeRepairDurationSpinners() {
        for (int toState = 1; toState < InitialSettings.DEFAULT_NUM_OF_STATES; toState++) {
            for (int fromState = toState + 1; fromState <= InitialSettings.DEFAULT_NUM_OF_STATES; fromState++) {
                GridBagSpinner spinner = new GridBagSpinner(InitialSettings.REPAIR_DURATION, fromState, toState);
                spinner.putInGrid(this, "", fromState - 1, toState + 1);
            }
        }
    }

    public JPanel getPanel() {
        return this;
    }
}
