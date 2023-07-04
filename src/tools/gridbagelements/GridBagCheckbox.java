package tools.gridbagelements;

import settings.InitialSettings;
import settings.Parameters;
import tools.interfaces.GridBagElement;

import javax.swing.*;
import java.awt.*;

public class GridBagCheckbox implements GridBagElement {
    Parameters parameters = Parameters.getInstance();
    JCheckBox checkBox;


    public GridBagCheckbox(int type) {
        checkBox = new JCheckBox();
        checkBox.setFocusable(false);
        switch (type) {
            case InitialSettings.EMERGENCY_REPAIR_CHK -> {
                checkBox.addActionListener(e -> {
                    parameters.setEmergencyRepair(checkBox.isSelected());
                    System.out.println(checkBox.isSelected());
                });
                checkBox.setSelected(InitialSettings.DEFAULT_EMERGENCY_REPAIR_CHK);
            }
            case InitialSettings.HOLD_THE_DATA -> {
                checkBox.addActionListener(e -> {
                    parameters.setHoldTheData(checkBox.isSelected());
                    System.out.println(checkBox.isSelected());
                });
                checkBox.setSelected(InitialSettings.DEFAULT_HOLD_THE_DATA);
            }
            case InitialSettings.RUN_MULTIPLE_TIMES -> {
                checkBox.addActionListener(e -> {
                    parameters.setRunMultipleTimes(checkBox.isSelected());
                    System.out.println(checkBox.isSelected());
                });
                checkBox.setSelected(InitialSettings.DEFAULT_RUN_MULTIPLE_TIMES);
            }
            case InitialSettings.SHOCK_DEGRADATION -> {
                checkBox.setSelected(parameters.isShockDegradation());
                checkBox.addActionListener(e -> {
                    parameters.setShockDegradation(checkBox.isSelected());
                });
            }
            default -> throw new IllegalStateException(
                    "Type of checkbox mismatch");
        }

    }


    @Override
    public void putInGrid(JComponent parent, String text, int bagX, int bagY) {
        checkBox.setText(text);
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = bagX;
        c.gridy = bagY;
        parent.add(checkBox, c);

    }
}
