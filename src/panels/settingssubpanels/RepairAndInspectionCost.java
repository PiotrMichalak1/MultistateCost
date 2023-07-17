package panels.settingssubpanels;

import settings.InitialSettings;
import tools.gridbagelements.GridBagLabel;
import tools.gridbagelements.GridBagSpinnerFactory;

import javax.swing.*;
import java.awt.*;

public class RepairAndInspectionCost extends JPanel implements ISettingPanel {


    public RepairAndInspectionCost() {
        initializeRepairCostLabels();
        initializeRepairCostSpinners();
    }

    private void initializeRepairCostLabels() {
        GridBagLabel bagLabel = new GridBagLabel();
        this.setLayout(new GridBagLayout());

        bagLabel.putInGrid(this, "Repair and Inspection Costs", 0, 0, InitialSettings.REPAIR_COST);

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
        bagLabel.putInGrid(this, "Inspection Cost", 0, 5);

    }

    public void initializeRepairCostSpinners() {
        for (int toState = 1; toState < InitialSettings.DEFAULT_NUM_OF_STATES; toState++) {
            for (int fromState = toState + 1; fromState <= InitialSettings.DEFAULT_NUM_OF_STATES; fromState++) {
                GridBagSpinnerFactory spinner = new GridBagSpinnerFactory(InitialSettings.REPAIR_COST, fromState, toState);
                spinner.putInGrid(this, "", fromState - 1, toState + 1);
            }
        }
        GridBagSpinnerFactory spinner = new GridBagSpinnerFactory(InitialSettings.INSPECTION_COST, InitialSettings.INSPECTION_COST);
        spinner.putInGrid(this, "", 1, 5);
    }

    public JPanel getPanel() {
        return this;
    }
}
