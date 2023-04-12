package panels.mainpanels;

import panels.settingssubpanels.*;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {
    RepairCostPanel repairCostPanel;
    public SettingsPanel() {
        addWeightedPanes();
    }

    private void addWeightedPanes(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        repairCostPanel = new RepairCostPanel();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        this.add(repairCostPanel,c);

        RepairDurationPanel repairDurationPanel = new RepairDurationPanel();
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 1;
        this.add(repairDurationPanel, c);

        OtherPropertiesPanel otherPropertiesPanel = new OtherPropertiesPanel();
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 2;
        this.add(otherPropertiesPanel, c);

        InspectionObjectivesPanel inspectionObjectivesPanel = new InspectionObjectivesPanel();
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 3;
        this.add(inspectionObjectivesPanel, c);

        EmergencyRepairPanel emergencyRepairPanel = new EmergencyRepairPanel();
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 4;
        this.add(emergencyRepairPanel, c);



    }
}
