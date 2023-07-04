package tools.gridbagelements;

import settings.InitialSettings;
import settings.Parameters;
import tools.interfaces.GridBagElement;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;


public class GridBagSpinner implements GridBagElement {
    Parameters parameters = Parameters.getInstance();
    JSpinner spinner;

    public GridBagSpinner(int type, int fromState, int toState) {
        spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromParameters(type, fromState, toState),
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
            parameters.setValueInParameters(type,fromState, toState, (int) spinner.getValue());
            System.out.println(spinner.getValue());
        });
    }

    public GridBagSpinner(int type, int state) {

        if (type == InitialSettings.WEIBULL_SHAPE) {
            spinner = new JSpinner(new SpinnerNumberModel(parameters.getWeibullShape(state),
                    1.0,
                    5.0,
                    0.1));
            spinner.setToolTipText("Value Must be a double value between 1 and 5");
        } else if (type == InitialSettings.INSPECTION_OBJECTIVES) {
            spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromParameters(type,state),
                    1,
                    state,
                    1));
            spinner.setToolTipText("Value Must be a double value between 1 and 5");
        } else {
            spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromParameters(type, state),
                    0,
                    1000,
                    1));
            spinner.setToolTipText("Value Must be an integer between 0 and 1000");
        }


        JSpinner.NumberEditor editor = (JSpinner.NumberEditor) spinner.getEditor();
        JFormattedTextField tf = editor.getTextField();
        NumberFormatter formatter = (NumberFormatter) tf.getFormatter();
        if (type == InitialSettings.WEIBULL_SHAPE) {
            formatter.setValueClass(Double.class);
        } else {
            formatter.setValueClass(Integer.class);
        }

        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);


        if (type == InitialSettings.WEIBULL_SHAPE) {
            spinner.addChangeListener(e -> {
                parameters.setWeibullShape(state,(double) spinner.getValue());
                System.out.println(spinner.getValue());
            });
        } else {
            spinner.addChangeListener(e -> {
                parameters.setValueInParameters(type,state, (int) spinner.getValue());
                System.out.println(spinner.getValue());
            });
        }

    }

    public GridBagSpinner(int type){
        switch (type){
            case InitialSettings.STATE_DROPS_TO -> {
                    spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromParameters(type),
                            1,
                            InitialSettings.DEFAULT_NUM_OF_STATES,
                            1));
                    spinner.setToolTipText("Value Must be an integer value between 1 and "+InitialSettings.DEFAULT_NUM_OF_STATES);
                    spinner.addChangeListener(e -> {
                        parameters.setValueInParameters(type,(int) spinner.getValue());
                        System.out.println(spinner.getValue());
                    });
            }
            case InitialSettings.NEXT_INSPECTION_IN -> {
                spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromParameters(type),
                        parameters.getValueFromParameters(InitialSettings.EMERGENCY_DELAY),
                        1000,
                        1));
                spinner.setToolTipText("Value Must be an integer value between "+ parameters.getEmDelay() +"and 1000");
            }
            case InitialSettings.EMERGENCY_COST -> {
                spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromParameters(type),
                        0,
                        1000,
                        1));
                spinner.setToolTipText("Value Must be an integer value between 0 and 1000");
                spinner.addChangeListener(e -> {
                    parameters.setValueInParameters(type,(int) spinner.getValue());
                    System.out.println(spinner.getValue());
                });

            }
            case InitialSettings.EMERGENCY_DELAY -> {
                spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromParameters(type),
                        0,
                        parameters.getValueFromParameters(InitialSettings.NEXT_INSPECTION_IN),
                        1));
                spinner.setToolTipText("Value Must be an integer value between 0 and "+ parameters.getEmNextInspectionIn());
            }
            case InitialSettings.PRODUCTION_CYCLES -> {
                spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromParameters(type),
                        10,
                        10000,
                        1));
                spinner.setToolTipText("Value Must be an integer value between 0 and 10000");
                spinner.addChangeListener(e -> {
                    parameters.setValueInParameters(type,(int) spinner.getValue());
                    System.out.println(spinner.getValue());
                });
            }
            case InitialSettings.MIN_INTERVAL -> {
                spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromParameters(type),
                        1,
                        parameters.getMaxInterval(),
                        1));
                spinner.setToolTipText("Value Must be an integer value between 1 and 40");
                spinner.addChangeListener(e -> {
                    parameters.setValueInParameters(type,(int) spinner.getValue());
                    System.out.println(spinner.getValue());
                });
            }
            case InitialSettings.MAX_INTERVAL -> {
                spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromParameters(type),
                        60,
                        500,
                        1));
                spinner.setToolTipText("Value Must be an integer value between 60 and 500");
                spinner.addChangeListener(e -> {
                    parameters.setValueInParameters(type,(int) spinner.getValue());
                    System.out.println(spinner.getValue());
                });
            }
            case InitialSettings.STEP -> {
                spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromParameters(type),
                        1,
                        20,
                        1));
                spinner.setToolTipText("Value Must be an integer value between 1 and 20");
                spinner.addChangeListener(e -> {
                    parameters.setValueInParameters(type,(int) spinner.getValue());
                    System.out.println(spinner.getValue());
                });
            }
            case InitialSettings.RUN_MULTIPLE_TIMES-> {
                spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromParameters(type),
                        1,
                        100,
                        1));
                spinner.setToolTipText("Value Must be an integer value between 1 and 100");
                spinner.addChangeListener(e -> {
                    parameters.setValueInParameters(type,(int) spinner.getValue());
                    System.out.println(spinner.getValue());
                });
            }
            default -> throw new IllegalStateException(
                    "GridBagSpinner type mismatch");
        }

        JSpinner.NumberEditor editor = (JSpinner.NumberEditor) spinner.getEditor();
        JFormattedTextField tf = editor.getTextField();
        NumberFormatter formatter = (NumberFormatter) tf.getFormatter();
        formatter.setValueClass(Integer.class);

        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);

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

    public JSpinner getInstanceOfSpinner(){
        return spinner;
    }

    public void setModel(int start, int min, int max, int step){
        spinner.setModel(new SpinnerNumberModel(start,min,max,step));
    }
    public void setToolTipText(String text){
        spinner.setToolTipText(text);
    }

}
