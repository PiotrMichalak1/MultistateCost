package simulation;


import org.apache.commons.math3.distribution.WeibullDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.RandomGeneratorFactory;
import settings.Parameters;
import tools.Mathematics;
import tools.MatrixOperations;

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
        prepareDomainAndValues();
        createRandomData(parameters.isShockDegradation());
        simulateForDifferentTimeIntervals();

    }

    private void prepareDomainAndValues() {
        simulationDomain = new double[(parameters.getMaxInterval() - parameters.getMinInterval()) / parameters.getStep() + 1];
        simulationValues = new double[simulationDomain.length];
    }

    private void createRandomData(boolean isShockDegradation) {

        //randomData =[state,cycle,layer]
        randomData = new double
                [parameters.getNumOfStates()]
                [parameters.getProdCycles()]
                [parameters.getNumOfStates()];

        if (!isShockDegradation) {

            for (int state = 1; state <= parameters.getNumOfStates() - 1; state++) {
                WeibullDistribution distribution = new WeibullDistribution(randomGenerator,
                        parameters.getWeibullShape(state),
                        parameters.getWeibullScale(state));
                for (int cycle = 1; cycle <= parameters.getProdCycles(); cycle++) {
                    //System.out.println("Amount of cycles is " + randomData[0].length);
                    double sample = distribution.sample();
                    for (int layer = 1; layer <= parameters.getNumOfStates(); layer++) {
                        randomData[state - 1][cycle - 1][layer - 1] = sample;
                        //System.out.println(sample);
                    }
                }
            }
        } else {
            for (int layer = 1; layer <= parameters.getNumOfStates(); layer++) {
                for (int state = layer; state <= parameters.getNumOfStates() - 1; state++) {
                    WeibullDistribution distribution = new WeibullDistribution(randomGenerator,
                            parameters.getWeibullShape(state),
                            shockScale(layer, state));
                    for (int cycle = 1; cycle <= parameters.getProdCycles(); cycle++) {
                        randomData[state - 1][cycle - 1][layer - 1] = distribution.sample();
                    }
                }
                for (int row = 2; row <= parameters.getNumOfStates() - 1; row++) {
                    for (int col=1; col <= parameters.getProdCycles(); col++) {
                        randomData[row-1][col-1][layer-1]= Math.max(randomData[row-1][col-1][layer-1],
                                randomData[row-2][col-1][layer-1]);
                    }
                }
                for (int row = 2; row <= parameters.getNumOfStates() - 1; row++) {
                    for (int col=1; col <= parameters.getProdCycles(); col++) {
                        randomData[row-1][col-1][layer-1]= randomData[row-1][col-1][layer-1] -
                                randomData[row-2][col-1][layer-1];
                    }
                }



            }


        }


    }

    private void simulateForDifferentTimeIntervals() {
        int currentInterval = parameters.getMinInterval();
        int intervalIndex = 0;
        while (currentInterval <= parameters.getMaxInterval()) {

            simulationDomain[intervalIndex] = currentInterval;
            simulationValues[intervalIndex] = simulateForGivenInterval(currentInterval);

            intervalIndex++;
            currentInterval += parameters.getStep();
        }
    }

    private double simulateForGivenInterval(int currentInterval) {
        MatrixOperations.fillRow(randomData, currentInterval, parameters.getNumOfStates());
        double[][] lifeSpans = new double[parameters.getNumOfStates() + 1][parameters.getProdCycles()];

        double repairCost = 0;
        int currentState = 1;
        double lastSeenValues = 0;
        double timeShift = 0;
        double missedInspections = 0;
        boolean repairOccured = false;

        for (int cycle = 1; cycle <= parameters.getProdCycles(); cycle++) {
            repairOccured = false;
            lifeSpans[parameters.getNumOfStates()][cycle - 1] = lastSeenValues; //save repair time
            lastSeenValues = lastSeenValues + timeShift; //add emergency time shift

            for (int state = currentState; state <= parameters.getNumOfStates(); state++) {
                if (isRepairAllowed(state)) {
                    if (willRepairOccurInThisState(state, cycle, currentInterval, currentState, lastSeenValues)) {
                        repairCost += parameters.getRepairCost(state, parameters.getInspectionObjectives(state)) +
                                parameters.getStaticCost(parameters.getNumOfStates()) * parameters.getRepairDuration(state, parameters.getInspectionObjectives(state));
                        lifeSpans[state - 1][cycle - 1] = Mathematics.roundUpToTheNearestMultiple(
                                lastSeenValues, currentInterval) - lastSeenValues;
                        currentState = parameters.getInspectionObjectives(state);
                        lastSeenValues = parameters.getRepairDuration(state, parameters.getInspectionObjectives(state));
                        timeShift = 0;
                        repairOccured = true;
                        missedInspections += Math.floor(lastSeenValues / currentInterval);
                        break;
                    } else {
                        lifeSpans[state - 1][cycle - 1] = randomData[state - 1][cycle - 1][currentState - 1];//inspection will occur in further deterioration state
                    }
                } else {
                    lifeSpans[state - 1][cycle - 1] = randomData[state - 1][cycle - 1][currentState - 1];//waiting with repair
                }

                lastSeenValues += randomData[state - 1][cycle - 1][currentState - 1];//summing days from the start of a cycle
            }

            if (!repairOccured) {
                currentState = 4;
                lastSeenValues = 0;
                timeShift = 0;
            }
        }

        double overallTime = MatrixOperations.sum(lifeSpans);
        double inspectionNumber = overallTime/currentInterval - missedInspections;
        double inspectionCost = inspectionNumber*parameters.getInspectionCost();

        MatrixOperations.multiplyMatrixRowsByVector(lifeSpans, parameters.getStaticCostVector());

        double operationalCost = MatrixOperations.sum(lifeSpans);

        double cost = operationalCost + repairCost + inspectionCost;

        cost = cost/overallTime;

        return cost;
    }

    private boolean isRepairAllowed(int state) {
        return state > parameters.getInspectionObjectives(state);
    }

    private boolean willRepairOccurInThisState(int state, int cycle, int currentInterval, int currentState, double lastSeenValues) {
        return randomData[state - 1][cycle - 1][currentState - 1] + (lastSeenValues % currentInterval) >= currentInterval;
    }

    private double shockScale(int layer, int state) {
        double scale = 0.0;
        for (int i = layer; i <= state; i++) {
            scale += parameters.getWeibullScale(i);
        }
        return scale;
    }

    public double[] getSimulationDomain() {
        return simulationDomain;
    }

    public double[] getSimulationValues() {
        return simulationValues;
    }


}
