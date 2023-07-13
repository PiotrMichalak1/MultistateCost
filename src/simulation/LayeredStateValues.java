package simulation;

import settings.InitialSettings;

import java.util.ArrayList;

public class LayeredStateValues {
    private ArrayList<double[]> statePercentages = new ArrayList<>();

    public LayeredStateValues(int domainLength) {
        for (int state = 0; state < InitialSettings.DEFAULT_NUM_OF_STATES;state++){
            statePercentages.add(new double[domainLength]);
        }
    }


    public void updateData(int intervalIndex, double[] layeredStateSimulationResults) {
        //for (int state)
    }
}
