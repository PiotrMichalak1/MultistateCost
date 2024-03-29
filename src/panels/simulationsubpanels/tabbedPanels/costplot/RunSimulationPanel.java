package panels.simulationsubpanels.tabbedPanels.costplot;

import settings.InitialSettings;
import settings.Parameters;
import tools.gridbagelements.GridBagButton;
import tools.gridbagelements.GridBagCheckbox;
import tools.gridbagelements.GridBagLabel;
import tools.gridbagelements.GridBagSpinner;

import javax.swing.*;
import java.awt.*;

public class RunSimulationPanel extends JPanel {

    private final GridBagLabel bagLabel;
    private final GridBagButton bagButton;


    public RunSimulationPanel(PlotPanel parentTab) {
        bagLabel = new GridBagLabel();
        bagButton = new GridBagButton(InitialSettings.SIMULATE_BUTTON,parentTab);


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
        spinner = new GridBagSpinner(InitialSettings.MIN_INTERVAL);
        spinner.putInGrid(this, "", 1, 1);
        spinner = new GridBagSpinner(InitialSettings.MAX_INTERVAL);
        spinner.putInGrid(this, "", 1, 2);
        spinner = new GridBagSpinner(InitialSettings.STEP);
        spinner.putInGrid(this, "", 1, 3);

        bagButton.putInGrid(this, "Simulate", 3, 2);

        GridBagCheckbox checkbox = new GridBagCheckbox(InitialSettings.HOLD_THE_DATA);
        checkbox.putInGrid(this, "Hold the data", 2, 2);
        RunMultipleTimesPanel runMultipleTimesPanel = new RunMultipleTimesPanel();
        runMultipleTimesPanel.putInGrid(this, 2, 3);
    }
}
