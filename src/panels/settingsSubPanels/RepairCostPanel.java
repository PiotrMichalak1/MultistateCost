package panels.settingsSubPanels;

import settings.Parameters;
import tools.GridBagLabel;
import tools.GridBagSpinner;

import javax.swing.*;
import java.awt.*;

public class RepairCostPanel extends JPanel {
    Parameters parameters;
    GridBagLabel bagLabel;

    public RepairCostPanel() {
        parameters = Parameters.getInstance();
        bagLabel = new GridBagLabel();
        initializeRepairCostLabels();
        initializeRepairCostSpinners();
    }

    private void initializeRepairCostLabels() {
        this.setLayout(new GridBagLayout());

        bagLabel.putInGrid(this,"Repair Cost",0,0,true);

        for (int i = 1; i <= parameters.NUM_OF_STATES; i++) {
            if(i==1){
                bagLabel.putInGrid(this,"State",0,1);
            }else {
                bagLabel.putInGrid(this,""+i,i-1,1);
            }

        }

        for (int i = 1; i < parameters.NUM_OF_STATES; i++) {
            bagLabel.putInGrid(this,"to "+i,0,i+1);
        }

    }

    public void initializeRepairCostSpinners() {
        for (int toState = 1; toState < parameters.NUM_OF_STATES; toState++) {
            for (int fromState = toState+1;fromState<=parameters.NUM_OF_STATES;fromState++) {
                GridBagSpinner spinner = new GridBagSpinner(fromState,toState);
                spinner.putInGrid(this,"",fromState-1,toState+1);
            }
        }
    }
}
