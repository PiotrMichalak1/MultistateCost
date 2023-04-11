package panels.settingsSubPanels;

import settings.Parameters;
import tools.GridBagLabel;
import tools.GridBagSpinner;

import javax.swing.*;
import java.awt.*;

public class OtherPropertiesPanel extends JPanel {
    Parameters parameters;
    GridBagLabel bagLabel;
    public OtherPropertiesPanel() {
        parameters = Parameters.getInstance();
        bagLabel = new GridBagLabel();
        initializeOtherPropertiesLabels();
        initializeStaticCostSpinners();
        initializeWeibullScaleSpinners();
        initializeWeibullShapeSpinners();
        initializeInspectionCostSpinner();
    }
    private void initializeOtherPropertiesLabels() {
        this.setLayout(new GridBagLayout());

        bagLabel.putInGrid(this,"Other Properties",0,0,Parameters.OTHER_PROPERTIES);

        for (int i = 1; i <= parameters.NUM_OF_STATES; i++) {
            if(i==1){
                bagLabel.putInGrid(this,"State",0,1);
                bagLabel.putInGrid(this,""+i,i,1);
            }else {
                bagLabel.putInGrid(this,""+i,i,1);
            }

        }

        bagLabel.putInGrid(this,"Static Cost",0,2);
        bagLabel.putInGrid(this,"Weibull Scale",0,3);
        bagLabel.putInGrid(this,"Weibull Shape",0,4);
        bagLabel.putInGrid(this,"Inspection Cost",0,5);

    }
    private void initializeStaticCostSpinners(){
        for (int state = 1; state <= parameters.NUM_OF_STATES; state++) {
            GridBagSpinner spinner = new GridBagSpinner(parameters.STATIC_COST,state);
            spinner.putInGrid(this,"",state,2);
        }
    }

    private void initializeWeibullScaleSpinners() {
        for (int state = 1; state < parameters.NUM_OF_STATES; state++) {
            GridBagSpinner spinner = new GridBagSpinner(parameters.WEIBULL_SCALE,state);
            spinner.putInGrid(this,"",state,3);
        }
    }
    private void initializeWeibullShapeSpinners(){
        for (int state = 1; state < parameters.NUM_OF_STATES; state++) {
            GridBagSpinner spinner = new GridBagSpinner(parameters.WEIBULL_SHAPE,state);
            spinner.putInGrid(this,"",state,4);
        }
    }

    private void initializeInspectionCostSpinner(){
        GridBagSpinner spinner = new GridBagSpinner(parameters.INSPECTION_COST,parameters.INSPECTION_COST);
        spinner.putInGrid(this,"",1,5);
    }

}
