package tools;

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
