package panels.simulationsubpanels.tabbedPanels.costplot;

import settings.InitialSettings;
import settings.Parameters;
import tools.gridbagelements.GridBagCheckbox;
import tools.gridbagelements.GridBagLabel;
import tools.gridbagelements.GridBagSpinner;

import javax.swing.*;
import java.awt.*;

public class RunMultipleTimesPanel extends JPanel {
    Parameters parameters;
    GridBagLabel bagLabel;

    GridBagCheckbox runMultipleTimesCB = new GridBagCheckbox(InitialSettings.RUN_MULTIPLE_TIMES);

    GridBagSpinner runMultipleTimesSP = new GridBagSpinner(InitialSettings.RUN_MULTIPLE_TIMES);
    public RunMultipleTimesPanel(){
        parameters = Parameters.getInstance();
        bagLabel = new GridBagLabel();
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
        runMultipleTimesSP.getInstanceOfSpinner().setValue(parameters.getRunMultipleTimesNum());
        runMultipleTimesCB.getInstanceOfCheckbox().setSelected(parameters.isRunMultipleTimes());
    }
}
