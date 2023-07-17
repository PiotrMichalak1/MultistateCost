package panels.settingssubpanels;

import settings.InitialSettings;
import settings.Parameters;
import tools.gridbagelements.GridBagLabel;
import tools.gridbagelements.GridBagSpinner;

import javax.swing.*;
import java.awt.*;

public class StateRelatedParameters extends JPanel implements ISettingPanel {


    public StateRelatedParameters() {
        initializeOtherPropertiesLabels();
        initializeStaticCostSpinners();
        initializeWeibullScaleSpinners();
        initializeWeibullShapeSpinners();
    }

    private void initializeOtherPropertiesLabels() {
        GridBagLabel bagLabel= new GridBagLabel();
        this.setLayout(new GridBagLayout());

        bagLabel.putInGrid(this, "State-Related Parameters ", 0, 0, InitialSettings.STATE_RELATED_PROPERTIES);

        for (int i = 1; i <= InitialSettings.DEFAULT_NUM_OF_STATES; i++) {
            if (i == 1) {
                bagLabel.putInGrid(this, "State", 0, 1);
                bagLabel.putInGrid(this, "" + i, i, 1);
            } else {
                bagLabel.putInGrid(this, "" + i, i, 1);
            }

        }

        bagLabel.putInGrid(this, "Static Cost", 0, 2);
        bagLabel.putInGrid(this, "Weibull Scale", 0, 3);
        bagLabel.putInGrid(this, "Weibull Shape", 0, 4);


    }

    private void initializeStaticCostSpinners() {
        for (int state = 1; state <= InitialSettings.DEFAULT_NUM_OF_STATES; state++) {
            GridBagSpinner spinner = new GridBagSpinner(InitialSettings.STATIC_COST, state);
            spinner.putInGrid(this, "", state, 2);
        }
    }

    private void initializeWeibullScaleSpinners() {
        for (int state = 1; state < InitialSettings.DEFAULT_NUM_OF_STATES; state++) {
            GridBagSpinner spinner = new GridBagSpinner(InitialSettings.WEIBULL_SCALE, state);
            spinner.putInGrid(this, "", state, 3);
        }
    }

    private void initializeWeibullShapeSpinners() {
        for (int state = 1; state < InitialSettings.DEFAULT_NUM_OF_STATES; state++) {
            GridBagSpinner spinner = new GridBagSpinner(InitialSettings.WEIBULL_SHAPE, state);
            spinner.putInGrid(this, "", state, 4);
        }
    }

    public JPanel getPanel() {
        return this;
    }

}
