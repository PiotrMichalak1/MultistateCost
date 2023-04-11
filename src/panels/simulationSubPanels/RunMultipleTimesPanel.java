package panels.simulationSubPanels;

import settings.InitialSettings;
import settings.Parameters;
import tools.GridBagCheckbox;
import tools.GridBagLabel;
import tools.GridBagSpinner;

import javax.swing.*;
import java.awt.*;

public class RunMultipleTimesPanel extends JPanel {
    Parameters parameters;
    GridBagLabel bagLabel;
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
        GridBagCheckbox checkbox = new GridBagCheckbox(InitialSettings.RUN_MULTIPLE_TIMES);
        checkbox.putInGrid(this,"Run", 0,0);

        GridBagSpinner spinner = new GridBagSpinner(InitialSettings.RUN_MULTIPLE_TIMES);
        spinner.putInGrid(this,"",1,0);
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
}
