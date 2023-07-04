package simulation;


import org.apache.commons.math3.distribution.WeibullDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.RandomGeneratorFactory;
import settings.InitialSettings;
import settings.Parameters;

import java.util.Random;


public class Simulation {
    private static Simulation instance = null;

    private final Parameters parameters = Parameters.getInstance();

    private double[][][] randomData;

    private double[] simulationDomain;

    private double[] simulationValues;

    private static RandomGenerator randomGenerator = RandomGeneratorFactory.createRandomGenerator(new Random());

    public Simulation() {
    }

    public static Simulation getInstance() {
        if (instance == null) {
            instance = new Simulation();
        }
        return instance;
    }

    public void simulate() {
        simulationDomain = new double[(parameters.getMaxInterval() - parameters.getMinInterval()) / parameters.getStep() + 1];
        simulationValues = new double[simulationDomain.length];
        createRandomData(parameters.isShockDegradation());

        int currentInterval = parameters.getMinInterval();
        int intervalIndex = 0;
        while (currentInterval <= parameters.getMaxInterval()) {

            simulationDomain[intervalIndex] = currentInterval;
            simulationValues[intervalIndex] = simulateForGivenInterval();

            intervalIndex++;
            currentInterval += parameters.getStep();
        }
    }

    private double simulateForGivenInterval() {

        return 0;
    }

    private void createRandomData(boolean isShockDegradation) {

        //randomData =[state,cycle,layer]
        randomData = new double
                [parameters.getNumOfStates()]
                [parameters.getProdCycles()]
                [parameters.getNumOfStates()];

        if (!isShockDegradation) {

            for (int state = 1; state <= parameters.getNumOfStates()-1; state++) {
                WeibullDistribution distribution = new WeibullDistribution(randomGenerator,
                        parameters.getWeibullShape(state),
                        parameters.getWeibullScale(state));
                for (int cycle = 1; cycle <= parameters.getProdCycles(); cycle++) {
                    System.out.println("Amount of cycles is "+randomData[0].length);
                    double sample = distribution.sample();
                    for (int layer = 1; layer <= parameters.getNumOfStates(); layer++) {
                        randomData[state - 1][cycle - 1][layer - 1] = sample;
                        System.out.println(sample);
                    }
                }
            }
        } else {
            for (int layer = 1; layer <= randomData[0][0].length; layer++) {
                for (int state = layer; state<=parameters.getNumOfStates()-1;state++){
                    WeibullDistribution distribution = new WeibullDistribution(randomGenerator,
                            parameters.getWeibullShape(state),
                            shockScale(layer,state));
                    for (int cycle = 1; cycle <= parameters.getProdCycles(); cycle++) {
                        randomData[state - 1][cycle - 1][layer - 1] = distribution.sample();
                       // System.out.println(randomData[state - 1][cycle - 1][layer - 1]);
                    }
                }
            }

        }
        //creating random numbers for states 1,2,...,n-1


    }

    private double shockScale(int layer, int state) {
        double scale = 0.0;
        for (int i=layer; i <= state; i++) {
            scale += parameters.getWeibullScale(i);
        }
        System.out.println("scale for layer: "+ layer+ "state: "+ state + " is equal to: " + scale);
        return scale;
    }

    public double[] getSimulationDomain() {
        return simulationDomain;
    }

    public double[] getSimulationValues() {
        return simulationValues;
    }


}
