package panels.simulationsubpanels.tabbedPanels.costplot;

import panels.mainpanels.TabbedPlotPanel;
import settings.InitialSettings;
import settings.Parameters;
import tools.gridbagelements.GridBagButton;
import tools.gridbagelements.GridBagCheckbox;
import tools.gridbagelements.GridBagLabel;
import tools.gridbagelements.GridBagSpinner;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RunSimulationPanel extends JPanel {

    private Parameters parameters = Parameters.getInstance();

    private final GridBagLabel bagLabel;
    private final GridBagButton bagButton;

    private final ArrayList<GridBagSpinner> GBspinners = new ArrayList<GridBagSpinner>();

    private final GridBagCheckbox holdTheDataCB = new GridBagCheckbox(InitialSettings.HOLD_THE_DATA);

    RunMultipleTimesPanel runMultipleTimesPanel = new RunMultipleTimesPanel();


    public RunSimulationPanel(TabbedPlotPanel parentTabbedPanel) {
        bagLabel = new GridBagLabel();
        bagButton = new GridBagButton(InitialSettings.SIMULATE_BUTTON,parentTabbedPanel);


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
        GridBagSpinner spinner = new GridBagSpinner(InitialSettings.PRODUCTION_CYCLES);
        spinner.putInGrid(this, "", 1, 0);
        GBspinners.add(spinner);
        spinner = new GridBagSpinner(InitialSettings.MIN_INTERVAL);
        spinner.putInGrid(this, "", 1, 1);
        GBspinners.add(spinner);
        spinner = new GridBagSpinner(InitialSettings.MAX_INTERVAL);
        spinner.putInGrid(this, "", 1, 2);
        GBspinners.add(spinner);
        spinner = new GridBagSpinner(InitialSettings.STEP);
        spinner.putInGrid(this, "", 1, 3);
        GBspinners.add(spinner);

        bagButton.putInGrid(this, "Simulate", 3, 2);
        holdTheDataCB.putInGrid(this, "Hold the data", 2, 2);
        runMultipleTimesPanel.putInGrid(this, 2, 3);
    }

    //reads value from Parameters singleton and updates the field.
    public void updateSpinnersAndCheckboxes() {
        for (GridBagSpinner GBspinner: GBspinners) {
            GBspinner.getInstanceOfSpinner().setValue(parameters.getValueFromParameters(GBspinner.getType()));
        }
        holdTheDataCB.getInstanceOfCheckbox().setSelected(parameters.isHoldTheData());
        runMultipleTimesPanel.updateSpinnersAndCheckboxes();
    }
}
