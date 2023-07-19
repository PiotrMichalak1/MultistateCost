package tools.gridbagelements;

import settings.GraphicSettings;
import settings.InitialSettings;
import settings.Parameters;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;


public class GridBagSpinnerFactory implements IGridBagElement {
    Parameters parameters = Parameters.getInstance();
    JSpinner spinner;

    private final int type;

    Number lastValid;

    public GridBagSpinnerFactory(int type, int fromState, int toState) {
        this.type = type;
        spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromParameters(type, fromState, toState),
                0,
                1000,
                1));
        spinner.setToolTipText("Must be an integer in range [ 0 , 1000 ]");

        JSpinner.NumberEditor editor = (JSpinner.NumberEditor) spinner.getEditor();
        JFormattedTextField tf = editor.getTextField();
        NumberFormatter formatter = (NumberFormatter) tf.getFormatter();
        formatter.setValueClass(Integer.class);
        formatter.setCommitsOnValidEdit(true);

        addPropertyValidation(spinner);


        spinner.addChangeListener(e -> {
            parameters.setValueInParameters(type, fromState, toState, (int) spinner.getValue());
            System.out.println(spinner.getValue());
        });
    }

    public GridBagSpinnerFactory(int type, int state) {

        this.type = type;

        switch (type) {
            case InitialSettings.WEIBULL_SHAPE -> {
                spinner = new JSpinner(new SpinnerNumberModel(parameters.getWeibullShape(state),
                        1.0,
                        5.0,
                        0.1));
                spinner.setToolTipText("Must be a double in range [ 1.0 , 5.0 ]");
            }
            case InitialSettings.INSPECTION_OBJECTIVES -> {
                spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromParameters(type, state),
                        1,
                        state,
                        1));
                spinner.setToolTipText("Must be an integer in range [ 1 , "+ state +" ]");
            }
            case InitialSettings.INSPECTION_COST, InitialSettings.WEIBULL_SCALE -> {
                spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromParameters(type, state),
                        0,
                        1000,
                        1));
                spinner.setToolTipText("Must be an integer in range [ 0 , 1000 ]");
                lastValid = parameters.getValueFromParameters(type, state);
            }
            case InitialSettings.STATIC_COST -> {
                spinner = new JSpinner(new SpinnerNumberModel(parameters.getDoubleValueFromParameters(type, state),
                        0.0,
                        1000.0,
                        0.1));
                spinner.setToolTipText("Must be a double in range [ 0.0 , 1000.0 ]");
            }
            default -> {
                spinner = new JSpinner(new SpinnerNumberModel(parameters.getDoubleValueFromParameters(type, state),
                        0,
                        1000,
                        1));
                spinner.setToolTipText("Must be a double in range [ 0 , 1000 ]");
            }
        }


        JSpinner.NumberEditor editor = (JSpinner.NumberEditor) spinner.getEditor();
        JFormattedTextField tf = editor.getTextField();
        NumberFormatter formatter = (NumberFormatter) tf.getFormatter();
        formatter.setCommitsOnValidEdit(true);

        //setting formatter class
        switch (type) {
            case InitialSettings.WEIBULL_SHAPE, InitialSettings.STATIC_COST -> formatter.setValueClass(Double.class);
            default -> formatter.setValueClass(Integer.class);
        }


        addPropertyValidation(spinner);



        switch (type) {
            case InitialSettings.WEIBULL_SHAPE, InitialSettings.STATIC_COST -> spinner.addChangeListener(e -> {
                parameters.setDoubleValueInParameters(type, state, (double) spinner.getValue());
                System.out.println(parameters.getDoubleValueFromParameters(type,state));
            });

            default -> spinner.addChangeListener(e -> {
                parameters.setValueInParameters(type, state, (int) spinner.getValue());
                System.out.println(parameters.getValueFromParameters(type, state));
            });


        }




    }

    public GridBagSpinnerFactory(int type) {

        this.type = type;

        switch (type) {
            case InitialSettings.STATE_DROPS_TO -> {
                spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromParameters(type),
                        1,
                        InitialSettings.DEFAULT_NUM_OF_STATES,
                        1));
                spinner.setToolTipText("Must be an integer in range [ 1 ," + InitialSettings.DEFAULT_NUM_OF_STATES+ " ]");
                spinner.addChangeListener(e -> {
                    parameters.setValueInParameters(type, (int) spinner.getValue());
                    System.out.println(spinner.getValue());
                });
            }
            case InitialSettings.NEXT_INSPECTION_IN -> {
                spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromParameters(type),
                        parameters.getValueFromParameters(InitialSettings.EMERGENCY_DELAY),
                        1000,
                        1));
                spinner.setToolTipText("Must be an integer value in range [ " + parameters.getEmDelay() +" , "+ 1000 + " ]");
            }
            case InitialSettings.EMERGENCY_COST -> {
                spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromParameters(type),
                        0,
                        1000,
                        1));
                spinner.setToolTipText("Must be an integer in range [ 0 , 1000 ]");
                spinner.addChangeListener(e -> {
                    parameters.setValueInParameters(type, (int) spinner.getValue());
                    System.out.println(spinner.getValue());
                });

            }
            case InitialSettings.EMERGENCY_DELAY -> {
                spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromParameters(type),
                        0,
                        parameters.getValueFromParameters(InitialSettings.NEXT_INSPECTION_IN),
                        1));
                spinner.setToolTipText("Must be an integer in range [ 0 , " + parameters.getEmNextInspectionIn()+" ]");
            }
            case InitialSettings.PRODUCTION_CYCLES -> {
                spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromParameters(type),
                        10,
                        1000000,
                        1));
                spinner.setToolTipText("Must be an integer in range [ 0 , 1 000 000 ]");
                spinner.addChangeListener(e -> {
                    parameters.setValueInParameters(type, (int) spinner.getValue());
                    System.out.println(spinner.getValue());
                });
            }
            case InitialSettings.MIN_INTERVAL -> {
                spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromParameters(type),
                        1,
                        50,
                        1));
                spinner.setToolTipText("Must be an integer in range [ 1 , 50 ]");
                spinner.addChangeListener(e -> {
                    parameters.setValueInParameters(type, (int) spinner.getValue());
                    System.out.println(spinner.getValue());
                });
            }
            case InitialSettings.MAX_INTERVAL -> {
                spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromParameters(type),
                        60,
                        1000,
                        1));
                spinner.setToolTipText("Must be an integer in range [ 60 , 1000 ]");
                spinner.addChangeListener(e -> {
                    parameters.setValueInParameters(type, (int) spinner.getValue());
                    System.out.println(spinner.getValue());
                });
            }
            case InitialSettings.STEP -> {
                spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromParameters(type),
                        1,
                        20,
                        1));
                spinner.setToolTipText("Must be an integer in range [ 1 , 20 ]");
                spinner.addChangeListener(e -> {
                    parameters.setValueInParameters(type, (int) spinner.getValue());
                    System.out.println(spinner.getValue());
                });
            }
            case InitialSettings.RUN_MULTIPLE_TIMES -> {
                spinner = new JSpinner(new SpinnerNumberModel(parameters.getValueFromParameters(type),
                        1,
                        100,
                        1));
                spinner.setToolTipText("Must be an integer in range [ 1 , 100 ]");
                spinner.addChangeListener(e -> {
                    parameters.setValueInParameters(type, (int) spinner.getValue());
                    System.out.println(spinner.getValue());
                });
            }
            default -> throw new IllegalStateException(
                    "GridBagSpinnerFactory type mismatch");


        }

        JSpinner.NumberEditor editor = (JSpinner.NumberEditor) spinner.getEditor();
        JFormattedTextField tf = editor.getTextField();
        NumberFormatter formatter = (NumberFormatter) tf.getFormatter();
        formatter.setValueClass(Integer.class);

        formatter.setCommitsOnValidEdit(true);

        addPropertyValidation(spinner);

    }

    @Override
    public void putInGrid(JComponent parent, String text, int bagX, int bagY) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = bagX;
        c.gridy = bagY;
        parent.add(spinner, c);
    }

    //adds PropertyChangeListener to the spinner that causes text field color to turn red on invalid input and resets
    // the value to the last valid value when the focus is lost
    private void addPropertyValidation(JSpinner spinner) {
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().addPropertyChangeListener(evt -> {
            //LOG.info("" + evt);
            if ("editValid".equals(evt.getPropertyName())) {
                if (Boolean.FALSE.equals(evt.getNewValue())) {
                    SpinnerNumberModel model = (SpinnerNumberModel) spinner.getModel();

                    ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setBackground(GraphicSettings.INVALID_VALUE_COLOR);
                    ((JSpinner.DefaultEditor)spinner.getEditor()).getTextField().setToolTipText(
                            "Must be in range: [ " + model.getMinimum() + " , " + model.getMaximum() + " ]");
                } else {
                    SpinnerNumberModel model = (SpinnerNumberModel) spinner.getModel();
                    ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setBackground(Color.WHITE);
                    ((JSpinner.DefaultEditor)spinner.getEditor()).getTextField().setToolTipText(
                            "Must be in range: [ " + model.getMinimum() + " , " + model.getMaximum() + " ]");
                }
            }

        });
    }

    public int getType() {
        return type;
    }

    public JSpinner getSpinner() {
        return spinner;
    }

    public void setModel(int start, int min, int max, int step) {
        spinner.setModel(new SpinnerNumberModel(start, min, max, step));
    }

    public void setToolTipText(String text) {
        spinner.setToolTipText(text);
    }

}
