package panels.tabs.sharedpanels;

import settings.InitialSettings;
import settings.Parameters;
import tools.gridbagelements.GridBagCheckboxFactory;
import tools.gridbagelements.GridBagLabel;
import tools.gridbagelements.GridBagSpinnerFactory;

import javax.swing.*;
import java.awt.*;

public class RunMultipleTimesPanel extends JPanel {
    Parameters parameters = Parameters.getInstance();
    GridBagLabel bagLabel = new GridBagLabel();

    GridBagCheckboxFactory runMultipleTimesCB = new GridBagCheckboxFactory(InitialSettings.RUN_MULTIPLE_TIMES);

    GridBagSpinnerFactory runMultipleTimesSP = new GridBagSpinnerFactory(InitialSettings.RUN_MULTIPLE_TIMES);
    public RunMultipleTimesPanel(){
        initializeRunMultipleTimesLabels();
        initializeRunMultipleTimesSpinners();
    }
    private void initializeRunMultipleTimesLabels(){
        this.setLayout(new GridBagLayout());
        bagLabel.putInGrid(this,"Times",2,0);
    }
    private void initializeRunMultipleTimesSpinners(){
        runMultipleTimesCB.putInGrid(this,"Run", 0,0);
        runMultipleTimesSP.putInGrid(this,"",1,0);
    }

    public void putInGrid(JComponent parent, int bagX, int bagY) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = bagX;
        c.gridy = bagY;
        parent.add(this, c);
    }

    public void updateSpinnersAndCheckboxes() {
        runMultipleTimesSP.getSpinner().setValue(parameters.getRunMultipleTimesNum());
        runMultipleTimesCB.getInstanceOfCheckbox().setSelected(parameters.isRunMultipleTimes());
    }
}
