package panels.settingsSubPanels;

import settings.Parameters;
import tools.GridBagLabel;

import javax.swing.*;
import java.awt.*;

public class RepairCostPanel extends JPanel {
    Parameters parameters;
    GridBagLabel bagLabel;

    public RepairCostPanel() {
        parameters = Parameters.getInstance();
        bagLabel = new GridBagLabel();
        initializeRepairCostLabels();
    }

    private void initializeRepairCostLabels() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        bagLabel.put(this,"Repair Cost",0,0);

        for (int i = 1; i <= parameters.NUM_OF_STATES; i++) {
            if(i==1){
                bagLabel.put(this,"State",0,1);
            }else {
                bagLabel.put(this,""+i,i-1,1);
            }

        }

        for (int i = 1; i < parameters.NUM_OF_STATES; i++) {
            bagLabel.put(this,"to "+i,0,i+1);
        }

    }

    public void initializeRepairCostSpinners() {

    }
}
