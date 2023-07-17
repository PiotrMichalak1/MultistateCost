package panels.mainpanels;

import panels.settingssubpanels.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SettingsPanel extends JPanel {
    List<ISettingPanel> panels;
    public SettingsPanel() {
        initializePanes();
        addWeightedPanes();
    }

    private void initializePanes(){
        ISettingPanel repairAndInspectionCost = new RepairAndInspectionCost();
        ISettingPanel repairDurationPanel = new RepairDurationPanel();
        ISettingPanel stateRelatedParameters = new StateRelatedParameters();
        ISettingPanel shockDegradationPanel = new ShockDegradationPanel();
        ISettingPanel inspectionObjectivesPanel = new InspectionObjectivesPanel();
        ISettingPanel emergencyRepairPanel = new EmergencyRepairPanel();

        panels = List.of(
                repairAndInspectionCost,
                repairDurationPanel,
                stateRelatedParameters,
                shockDegradationPanel,
                inspectionObjectivesPanel,
                emergencyRepairPanel);
    }
    private void addWeightedPanes(){
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = setGridBagConstraints();

        int gridy =0;
        for (ISettingPanel panel: panels) {
            c.gridy = gridy;
            this.add(panel.getPanel(),c);
            gridy++;
        }
    }

    private static GridBagConstraints setGridBagConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        return c;
    }


}
