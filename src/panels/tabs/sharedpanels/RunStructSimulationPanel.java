package panels.tabs.sharedpanels;

import panels.mainpanels.TabsPanel;
import settings.InitialSettings;
import settings.Parameters;
import tools.gridbagelements.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RunStructSimulationPanel extends JPanel {

    private final Parameters parameters = Parameters.getInstance();

    private final TabsPanel parentTabbedPanel;

    private final ArrayList<GridBagSpinner> gridBagSpinners = new ArrayList<>();

    private final GridBagTextField costPerTimeUnit = new GridBagTextField();



    public RunStructSimulationPanel(TabsPanel parentTabbedPanel) {
        this.parentTabbedPanel = parentTabbedPanel;
        initializeRunSimulationLabels();
        initializeRunSimulationSpinnersAndButtons();
        putElementsInLeftColumns(3);
    }

    private void initializeRunSimulationLabels() {
        this.setLayout(new GridBagLayout());
        GridBagLabel bagLabel = new GridBagLabel();
        bagLabel.putInGrid(this, "Production Cycles", 0, 1);
        bagLabel.putInGrid(this, "Inspection Interval", 0, 2);
        bagLabel.putInGrid(this, "Cost per time unit", 0, 3);
    }

    private void initializeRunSimulationSpinnersAndButtons() {
        GridBagSpinner spinner = new GridBagSpinner(InitialSettings.PRODUCTION_CYCLES);
        spinner.putInGrid(this, "", 1, 1);
        gridBagSpinners.add(spinner);
        spinner = new GridBagSpinner(InitialSettings.STRUCT_INSPECTION_INTERVAL);
        spinner.putInGrid(this, "", 1, 2);
        gridBagSpinners.add(spinner);

        costPerTimeUnit.putInGrid(this,"",1,3);


        GridBagButton bagButton = new GridBagButton(InitialSettings.SIMULATE_STRUCT_BUTTON,parentTabbedPanel);

        bagButton.putInGrid(this, "Simulate", 1, 0);
    }

    //All elements take up 2 columns, to bring them to the left side additional columns with empty labels ar added
    private void putElementsInLeftColumns(int additionalColumnsToTheRight){
        GridBagLabel emptyLabel = new GridBagLabel();
        for (int i =0; i<additionalColumnsToTheRight;i++){
            emptyLabel.putInGrid(this,"",2+i,0);
        }

    }
    //reads value from Parameters singleton and updates the field.
    public void updateSpinnersAndCheckboxes() {
        for (GridBagSpinner GBspinner: gridBagSpinners) {
            GBspinner.getSpinner().setValue(parameters.getValueFromParameters(GBspinner.getType()));
        }

    }
}
