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

    private double[][] randomData;

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
        createRandomData();

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

    private void createRandomData() {

        randomData = new double[InitialSettings.DEFAULT_NUM_OF_STATES + 1][parameters.getProdCycles()];
        //creating random numbers for states 1,2,...,n-1
        for (int state = 1; state <= randomData.length-2; state++) {
            WeibullDistribution distribution = new WeibullDistribution(randomGenerator,
                    parameters.getWeibullShape(state),
                    parameters.getWeibullScale(state));
            for (int cycle = 1; cycle <= randomData[0].length; cycle++) {
                randomData[state - 1][cycle - 1] = distribution.sample();
                System.out.println(randomData[state - 1][cycle - 1]);
            }
        }


    }

    public double[] getSimulationDomain() {
        return simulationDomain;
    }

    public double[] getSimulationValues() {
        return simulationValues;
    }
}
