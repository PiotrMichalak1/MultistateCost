package panels.tabs.sharedpanels;

import panels.mainpanels.TabsPanel;
import settings.InitialSettings;
import settings.Parameters;
import tools.gridbagelements.GridBagButtonFactory;
import tools.gridbagelements.GridBagCheckboxFactory;
import tools.gridbagelements.GridBagLabel;
import tools.gridbagelements.GridBagSpinnerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RunSimulationPanel extends JPanel {

    private Parameters parameters = Parameters.getInstance();

    private final GridBagLabel bagLabel;
    private final GridBagButtonFactory bagButton;

    private final ArrayList<GridBagSpinnerFactory> GBspinners = new ArrayList<GridBagSpinnerFactory>();

    private final GridBagCheckboxFactory holdTheDataCB = new GridBagCheckboxFactory(InitialSettings.HOLD_THE_DATA);

    RunMultipleTimesPanel runMultipleTimesPanel = new RunMultipleTimesPanel();


    public RunSimulationPanel(TabsPanel parentTabbedPanel) {
        bagLabel = new GridBagLabel();
        bagButton = new GridBagButtonFactory(InitialSettings.SIMULATE_BUTTON,parentTabbedPanel);


        initializeRunSimulationLabels();
        initializeRunSimulationSpinnersAndButtons();
    }

    private void initializeRunSimulationLabels() {
        this.setLayout(new GridBagLayout());
        bagLabel.putInGrid(this, "Production Cycles", 0, 0);
        bagLabel.putInGrid(this, "Min Interval", 0, 1);
        bagLabel.putInGrid(this, "Max Interval", 0, 2);
        bagLabel.putInGrid(this, "Step", 0, 3);
    }

    private void initializeRunSimulationSpinnersAndButtons() {
        GridBagSpinnerFactory spinner = new GridBagSpinnerFactory(InitialSettings.PRODUCTION_CYCLES);
        spinner.putInGrid(this, "", 1, 0);
        GBspinners.add(spinner);
        spinner = new GridBagSpinnerFactory(InitialSettings.MIN_INTERVAL);
        spinner.putInGrid(this, "", 1, 1);
        GBspinners.add(spinner);
        spinner = new GridBagSpinnerFactory(InitialSettings.MAX_INTERVAL);
        spinner.putInGrid(this, "", 1, 2);
        GBspinners.add(spinner);
        spinner = new GridBagSpinnerFactory(InitialSettings.STEP);
        spinner.putInGrid(this, "", 1, 3);
        GBspinners.add(spinner);

        bagButton.putInGrid(this, "Simulate", 3, 2);
        holdTheDataCB.putInGrid(this, "Hold the data", 2, 2);
        runMultipleTimesPanel.putInGrid(this, 2, 3);
    }

    //reads value from Parameters singleton and updates the field.
    public void updateSpinnersAndCheckboxes() {
        for (GridBagSpinnerFactory GBspinner: GBspinners) {
            GBspinner.getSpinner().setValue(parameters.getValueFromParameters(GBspinner.getType()));
        }
        holdTheDataCB.getInstanceOfCheckbox().setSelected(parameters.isHoldTheData());
        runMultipleTimesPanel.updateSpinnersAndCheckboxes();
    }
}
