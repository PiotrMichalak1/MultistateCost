package panels.settingsSubPanels;

import settings.Parameters;
import tools.GridBagLabel;
import tools.GridBagSpinner;

import javax.swing.*;
import java.awt.*;

public class InspectionObjectivesPanel extends JPanel {
    Parameters parameters;
    GridBagLabel bagLabel;
    public InspectionObjectivesPanel(){
        parameters = Parameters.getInstance();
        bagLabel = new GridBagLabel();

        initializeInspectionObjectivesLabels();
        initializeInspectionObjectivesSpinners();
    }

    private void initializeInspectionObjectivesLabels() {
        this.setLayout(new GridBagLayout());

        bagLabel.putInGrid(this,"Inspection Objectives",0,0,Parameters.INSPECTION_OBJECTIVES);

        for (int i = 1; i <= Parameters.NUM_OF_STATES; i++) {
            if(i==1){
                bagLabel.putInGrid(this,"Find at",0,1);
            }else {
                bagLabel.putInGrid(this,""+i,i-1,1);
            }
        }

        bagLabel.putInGrid(this,"Leave at",0,2);
    }

    private void initializeInspectionObjectivesSpinners(){
        for (int state = 2; state <= Parameters.NUM_OF_STATES; state++) {
            GridBagSpinner spinner = new GridBagSpinner(Parameters.INSPECTION_OBJECTIVES,state);
            spinner.putInGrid(this,"",state-1,2);
        }
    }
}
