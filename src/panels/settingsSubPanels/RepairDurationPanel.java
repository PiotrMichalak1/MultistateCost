package panels.settingsSubPanels;

import settings.Parameters;
import tools.GridBagLabel;

import javax.swing.*;
import java.awt.*;

public class RepairDurationPanel extends JPanel {
    Parameters parameters;
    GridBagLabel bagLabel;

    public RepairDurationPanel() {
        parameters = Parameters.getInstance();
        bagLabel = new GridBagLabel();
        initializeRepairDurationLabels();
    }

    private void initializeRepairDurationLabels() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        bagLabel.putInGrid(this,"Repair Duration",0,0,true);

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
}
