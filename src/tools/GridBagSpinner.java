package tools;

import settings.Parameters;
import tools.interfaces.GridBagElement;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;


public class GridBagSpinner implements GridBagElement {
    Parameters parameters = Parameters.getInstance();
    JSpinner spinner;

    public GridBagSpinner(int type, int fromState, int toState) {
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
        formatter.setCommitsOnValidEdit(true);


        spinner.addChangeListener(e -> {
            parameters.setValueInSettings(type,fromState, toState, (int) spinner.getValue());
            System.out.println(spinner.getValue());
        });
    }

    public GridBagSpinner(int type, int state) {

        if (type == Parameters.WEIBULL_SHAPE) {
            spinner = new JSpinner(new SpinnerNumberModel(parameters.getWeibullShape(state),
                    1.0,
                    5.0,
                    0.1));
            spinner.setToolTipText("Value Must be a double value between 1 and 5");
        } else if (type == Parameters.INSPECTION_OBJECTIVES) {
            spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromSettings(type,state),
                    1,
                    state,
                    1));
            spinner.setToolTipText("Value Must be a double value between 1 and 5");
        } else {
            spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromSettings(type, state),
                    0,
                    1000,
                    1));
            spinner.setToolTipText("Value Must be an integer between 0 and 1000");
        }


        JSpinner.NumberEditor editor = (JSpinner.NumberEditor) spinner.getEditor();
        JFormattedTextField tf = editor.getTextField();
        NumberFormatter formatter = (NumberFormatter) tf.getFormatter();
        if (type == Parameters.WEIBULL_SHAPE) {
            formatter.setValueClass(Double.class);
        } else {
            formatter.setValueClass(Integer.class);
        }

        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);


        if (type == Parameters.WEIBULL_SHAPE) {
            spinner.addChangeListener(e -> {
                parameters.setWeibullShape(state,(double) spinner.getValue());
                System.out.println(spinner.getValue());
            });
        } else {
            spinner.addChangeListener(e -> {
                parameters.setValueInSettings(type,state, (int) spinner.getValue());
                System.out.println(spinner.getValue());
            });
        }

    }

    @Override
    public void putInGrid(JComponent parent, String text, int bagX, int bagY) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = bagX;
        c.gridy = bagY;
        parent.add(spinner, c);
    }

}
