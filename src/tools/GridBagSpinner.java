package tools;

import settings.Parameters;
import tools.interfaces.GridBagElement;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class GridBagSpinner implements GridBagElement {
    Parameters parameters = Parameters.getInstance();
    SpinnerModel model;
    JSpinner spinner;

    public GridBagSpinner(int fromState, int toState) {
        spinner = new JSpinner(new SpinnerNumberModel(parameters.getRepairCost(fromState, toState),
                0,
                1000,
                1));

        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                parameters.setRepairCost(fromState, toState, (int) spinner.getValue());
            }
        });
    }

    @Override
    public void putInGrid(JComponent parent, String text, int bagX, int bagY) {
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = bagX;
        c.gridy = bagY;
        parent.add(spinner, c);
    }

}
