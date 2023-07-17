package panels.settingssubpanels;

import settings.InitialSettings;
import tools.gridbagelements.GridBagLabel;
import tools.gridbagelements.GridBagSpinner;

import javax.swing.*;
import java.awt.*;

public class InspectionObjectivesPanel extends JPanel implements ISettingPanel {

    public InspectionObjectivesPanel() {
        initializeInspectionObjectivesLabels();
        initializeInspectionObjectivesSpinners();
    }

    private void initializeInspectionObjectivesLabels() {
        GridBagLabel bagLabel = new GridBagLabel();
        this.setLayout(new GridBagLayout());

        bagLabel.putInGrid(this, "Inspection Objectives", 0, 0, InitialSettings.INSPECTION_OBJECTIVES);

        for (int stateNum = 1; stateNum <= InitialSettings.DEFAULT_NUM_OF_STATES; stateNum++) {
            if (stateNum == 1) {
                bagLabel.putInGrid(this, "Find at", 0, 1);
            } else {
                bagLabel.putInGrid(this, "" + stateNum, stateNum - 1, 1);
            }
        }

        bagLabel.putInGrid(this, "Leave at", 0, 2);
    }

    private void initializeInspectionObjectivesSpinners() {
        for (int stateNum = 2; stateNum <= InitialSettings.DEFAULT_NUM_OF_STATES; stateNum++) {
            GridBagSpinner spinner = new GridBagSpinner(InitialSettings.INSPECTION_OBJECTIVES, stateNum);
            spinner.putInGrid(this, "", stateNum - 1, 2);
        }
    }

    public JPanel getPanel() {
        return this;
    }
}
