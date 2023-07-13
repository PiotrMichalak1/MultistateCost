package simulation;

import settings.InitialSettings;

import java.util.ArrayList;

public class LayeredStateValues implements Cloneable{
    private ArrayList<double[]> statePercentages = new ArrayList<>();

    public LayeredStateValues(int domainLength) {
        for (int state = 0; state < InitialSettings.DEFAULT_NUM_OF_STATES+1;state++){
            statePercentages.add(new double[domainLength]);
        }
    }


    public void updateData(int intervalIndex, double[] layeredStateSimulationResults) {
        for (int state =0; state < statePercentages.size();state++){
            statePercentages.get(state)[intervalIndex] = layeredStateSimulationResults[state];
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        LayeredStateValues cloned = (LayeredStateValues) super.clone();
        for (int i = 0; i < statePercentages.size(); i++) {
            cloned.statePercentages.set(i,this.statePercentages.get(i).clone());
        }

        return cloned;
    }

    public ArrayList<double[]> getStatePercentages() {
        return statePercentages;
    }
}
