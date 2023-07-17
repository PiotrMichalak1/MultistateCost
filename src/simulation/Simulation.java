package simulation;



import org.apache.commons.math3.distribution.WeibullDistribution;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.RandomGeneratorFactory;
import settings.Parameters;
import tools.functions.Mathematics;
import tools.functions.MatrixOperations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;


public class Simulation {
    private static Simulation instance = null;

    private final Parameters parameters = Parameters.getInstance();

    private double[][][] randomData;

    private double[] simulationDomain;

    private double[] overallCostValues;

    private LayeredCostValues layeredCostValues;

    private LayeredStateValues layeredStateValues;

    private static final RandomGenerator randomGenerator = RandomGeneratorFactory.createRandomGenerator(new Random());

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
        overallCostValues = new double[simulationDomain.length];
        layeredCostValues = new LayeredCostValues(simulationDomain.length);
        layeredStateValues = new LayeredStateValues(simulationDomain.length);
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
                    for (int col = 1; col <= parameters.getProdCycles(); col++) {
                        randomData[row - 1][col - 1][layer - 1] = Math.max(randomData[row - 1][col - 1][layer - 1],
                                randomData[row - 2][col - 1][layer - 1]);
                    }
                }
                for (int row = 2; row <= parameters.getNumOfStates() - 1; row++) {
                    for (int col = 1; col <= parameters.getProdCycles(); col++) {
                        randomData[row - 1][col - 1][layer - 1] = randomData[row - 1][col - 1][layer - 1] -
                                randomData[row - 2][col - 1][layer - 1];
                    }
                }


            }


        }


    }

    private void simulateForDifferentTimeIntervals() {
        int currentInterval = parameters.getMinInterval();
        int intervalIndex = 0;
        while (currentInterval <= parameters.getMaxInterval()) {

            HashMap<String,double[]> simulationResults = simulateForGivenInterval(currentInterval);

            updateMainSimulationData(currentInterval, intervalIndex, simulationResults.get("cost"));
            updateLayeredCostSimulationData(intervalIndex, simulationResults.get("cost"));
            updateLayeredStateSimulationData(intervalIndex,simulationResults.get("state"));

            intervalIndex++;
            currentInterval += parameters.getStep();
        }
    }

    //Returns HashMap containing {overallCost}, {Operational, Repair, Inspections}, {State Percentage Shares []}

    private HashMap<String,double[]> simulateForGivenInterval(int currentInterval) {
        MatrixOperations.fillRow(randomData, currentInterval, parameters.getNumOfStates());
        double[][] lifeSpans = new double[parameters.getNumOfStates() + 1][parameters.getProdCycles()];

        double repairCost = 0;
        int startingState = 1;
        double lastSeenValues = 0;
        double timeShift = 0;
        double missedInspections = 0;
        boolean repairOccured;


        for (int cycle = 1; cycle <= parameters.getProdCycles(); cycle++) {
            repairOccured = false;
            lifeSpans[parameters.getNumOfStates()][cycle - 1] = lastSeenValues; //save repair time
            lastSeenValues = lastSeenValues + timeShift; //add emergency time shift
            lifeSpans[startingState-1][cycle-1] = randomData[startingState-1][cycle-1][startingState-1];


            for (int state = startingState; state <= parameters.getNumOfStates(); state++) {

                if (parameters.isEmergencyRepairEnabled()) {
                    if (isEmergencyCalled(state, startingState, currentInterval, lastSeenValues)) {

                        double timeToEm = parameters.getEmDelay();

                        for (int i = state; i <= parameters.getNumOfStates(); i++) {

                            lifeSpans[i-1][cycle-1] = Math.min(timeToEm,randomData[i-1][cycle-1][startingState-1]);
                            timeToEm-=lifeSpans[i-1][cycle-1];
                            if (timeToEm <= 0.0) {
                                repairCost += parameters.getRepairCost(state, parameters.getInspectionObjectives(state)) +
                                        parameters.getStaticCost(parameters.getNumOfStates()) * parameters.getRepairDuration(state, parameters.getInspectionObjectives(state))+
                                        parameters.getEmEmergencyCost();
                                timeShift = lastSeenValues + parameters.getEmDelay();
                                lastSeenValues = parameters.getRepairDuration(state, parameters.getInspectionObjectives(state));
                                startingState = parameters.getInspectionObjectives(state);
                                missedInspections += Math.floor(lastSeenValues / currentInterval) - Math.floor(timeShift/ currentInterval+lastSeenValues/ currentInterval);
                                repairOccured = true;
                                break;
                            }
                        }
                        break;
                    }
                }


                if (isRepairAllowed(state)) {
                    if (willRepairOccurInThisState(state, cycle, currentInterval, startingState, lastSeenValues)) {
                        repairCost += parameters.getRepairCost(state, parameters.getInspectionObjectives(state)) +
                                parameters.getStaticCost(parameters.getNumOfStates()) * parameters.getRepairDuration(state, parameters.getInspectionObjectives(state));
                        lifeSpans[state - 1][cycle - 1] = Mathematics.roundUpToTheNearestMultiple(
                                lastSeenValues, currentInterval) - lastSeenValues;
                        startingState = parameters.getInspectionObjectives(state);
                        lastSeenValues = parameters.getRepairDuration(state, parameters.getInspectionObjectives(state));
                        timeShift = 0;
                        repairOccured = true;
                        missedInspections += Math.floor(lastSeenValues / currentInterval);
                        break;
                    } else {
                        lifeSpans[state - 1][cycle - 1] = randomData[state - 1][cycle - 1][startingState - 1];//inspection will occur in further deterioration state
                    }
                } else {
                    lifeSpans[state - 1][cycle - 1] = randomData[state - 1][cycle - 1][startingState - 1];//waiting with repair
                }

                lastSeenValues += randomData[state - 1][cycle - 1][startingState - 1];//summing days from the start of a cycle
            }

            if (!repairOccured) {
                startingState = 4;
                lastSeenValues = 0;
                timeShift = 0;
            }
        }

        double overallTime = MatrixOperations.sum(lifeSpans);
        double inspectionNumber = overallTime / currentInterval - missedInspections;
        double inspectionCost = inspectionNumber * parameters.getInspectionCost();


        Double[] v = Arrays.stream(parameters.getStaticCostVector()).boxed().toArray(Double[]::new);
        double[] concatenated = MatrixOperations.concatenateVectors(v, new Integer[]{0});
        double[][] lifeSpansMultiplied = MatrixOperations.multiplyMatrixRowsByVector(lifeSpans, concatenated);


        double operationalCost = MatrixOperations.sum(lifeSpansMultiplied);

        //normalization of costs in time
        operationalCost = operationalCost/overallTime;
        repairCost = repairCost/overallTime;
        inspectionCost =inspectionCost/overallTime;

        HashMap<String,double[]> result = new HashMap<>();
        double[] layeredCostSimulationResult = {operationalCost,repairCost,inspectionCost};
        double[] layeredStateSimulationResult = getStatePercentage(lifeSpans,overallTime);
        result.put("cost",layeredCostSimulationResult);
        result.put("state", layeredStateSimulationResult);

        return result;
    }
    //takes operational, repair and inspection cost for a given interval and inserts this data into layeredCostValues

    private void updateLayeredCostSimulationData(int intervalIndex, double[] layeredCostSimulationResults) {
        layeredCostValues.setOperationalCost(intervalIndex, layeredCostSimulationResults[0]);
        layeredCostValues.setRepairCost(intervalIndex, layeredCostSimulationResults[1]);
        layeredCostValues.setInspectionsCost(intervalIndex, layeredCostSimulationResults[2]);
    }

    private void updateLayeredStateSimulationData(int intervalIndex, double[] layeredStateSimulationResults){
        layeredStateValues.updateData(intervalIndex,layeredStateSimulationResults);
    }

    private void updateMainSimulationData(int currentInterval, int intervalIndex, double[] layeredCostSimulationResults) {
        simulationDomain[intervalIndex] = currentInterval;
        overallCostValues[intervalIndex] = MatrixOperations.sum(layeredCostSimulationResults);
    }



    //Given matrix of times being in certain state in certain cycle (lifeSpans), and overallTime of simulation
    //Returns double[] vector of percentage share of sums of rows in a whole sum.
    private double[] getStatePercentage(double[][] lifeSpans, double overallTime) {
        if (overallTime <= 0) throw new IllegalArgumentException("Overall time must be positive.");
        if (lifeSpans == null) System.out.println("Life Spans matrix cannot be null");

        assert lifeSpans != null;
        double[] statePercentages = new double[lifeSpans[0].length]; // 5 values in array representing percentage time that system is in state 1,...,n-th,repair
        for (int state = 1; state <= lifeSpans.length; state++) {
            statePercentages[state-1] = (MatrixOperations.sumRow(lifeSpans, state-1)/overallTime)*100;
        }

        return statePercentages;
    }

    private boolean isEmergencyCalled(int state, int startingState, int currentInterval, double lastSeenValues) {
        return (state >= parameters.getEmStateDropsTo())//em settings allow calling emergency inspection
                && (state > startingState)// system deteriorated since last repair
                && (parameters.getEmNextInspectionIn() < Mathematics.roundUpToTheNearestMultiple(
                lastSeenValues, currentInterval) - lastSeenValues);
    }

    private boolean isRepairAllowed(int state) {
        return state > parameters.getInspectionObjectives(state);
    }

    private boolean willRepairOccurInThisState(int state, int cycle, int currentInterval, int currentState, double lastSeenValues) {
        return randomData[state - 1][cycle - 1][currentState - 1] + (lastSeenValues % currentInterval) >= currentInterval;
    }

    //calculate new scales if shock degradation mode is active
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

    public double[] getOverallCostValues() {
        return overallCostValues;
    }

    public LayeredCostValues getLayeredCostValues() {
        return layeredCostValues;
    }

    public LayeredStateValues getLayeredStateValues() {return  layeredStateValues;
    }
}
