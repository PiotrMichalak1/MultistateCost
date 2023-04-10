package tools;

import settings.Parameters;
import tools.interfaces.GridBagElement;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;


public class GridBagSpinner implements GridBagElement {
    Parameters parameters = Parameters.getInstance();
    JSpinner spinner;

    public GridBagSpinner(String type, int fromState, int toState) {
        spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromSettings(type, fromState, toState),
                0,
                1000,
                1));
        spinner.setToolTipText("Value Must be an integer between 0 and 1000");

        JSpinner.NumberEditor editor = (JSpinner.NumberEditor) spinner.getEditor();
        JFormattedTextField tf = editor.getTextField();
        NumberFormatter formatter = (NumberFormatter) tf.getFormatter();
        formatter.setValueClass(Integer.class);
        formatter.setAllowsInvalid(false);
        formatter.setMinimum(0);
        formatter.setMaximum(1000);
        formatter.setCommitsOnValidEdit(true);


        spinner.addChangeListener(e -> {
            parameters.setValueInSettings(type,fromState, toState, (int) spinner.getValue());
            System.out.println(spinner.getValue());
        });
    }

    public GridBagSpinner(int type, int state) {
        spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromSettings(type, state),
                0,
                1000,
                1));
        spinner.setToolTipText("Value Must be an integer between 0 and 1000");

        JSpinner.NumberEditor editor = (JSpinner.NumberEditor) spinner.getEditor();
        JFormattedTextField tf = editor.getTextField();
        NumberFormatter formatter = (NumberFormatter) tf.getFormatter();
        formatter.setValueClass(Integer.class);
        formatter.setAllowsInvalid(false);
        formatter.setMinimum(0);
        formatter.setMaximum(1000);
        formatter.setCommitsOnValidEdit(true);


        spinner.addChangeListener(e -> {
            parameters.setValueInSettings(type,state, (int) spinner.getValue());
            System.out.println(spinner.getValue());
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
