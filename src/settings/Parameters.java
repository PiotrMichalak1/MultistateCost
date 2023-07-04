package settings;

import java.util.Arrays;

public class Parameters {

    private static Parameters instance = null;

    private boolean emergencyRepair;
    private boolean holdTheData;
    private boolean runMultipleTimes;

    private boolean shockDegradation = true;
    private int runMultipleTimesNum;

    private int[][] repairCostMatrix;
    private int[][] repairDurationMatrix;

    private int[] staticCostVector;
    private int[] weibullScaleVector;
    private double[] weibullShapeVector;
    private int inspectionCost = 5;

    private int[] inspectionObjectives;

    private int emStateDropsTo;
    private int emNextInspectionIn;
    private int emEmergencyCost;
    private int emDelay;

    private int prodCycles;
    private int minInterval;
    private int maxInterval;
    private int step;


    private Parameters() {
        initializeRepairCosts();
        initializeRepairDuration();
        initializeStaticCostVector();
        initializeWeibullScaleVector();
        initializeWeibullShapeVector();
        initializeInspectionObjectives();
        initializeEmergencyRepairParameters();
        initializeSimulationPanelParameters();
    }

    public static Parameters getInstance() {
        if (instance == null) {
            instance = new Parameters();
        }
        return instance;
    }

    private void initializeRepairCosts() {
        repairCostMatrix = new int[InitialSettings.DEFAULT_NUM_OF_STATES - 1][InitialSettings.DEFAULT_NUM_OF_STATES - 1];
        for (int i = 0; i < repairCostMatrix.length; i++) {
            int repairCost = InitialSettings.DEFAULT_REPAIR_COST_STEP;
            for (int j = i; j < repairCostMatrix[i].length; j++) {
                repairCostMatrix[i][j] = repairCost;
                repairCost += InitialSettings.DEFAULT_REPAIR_COST_STEP;
            }
        }
    }

    private void initializeRepairDuration() {
        repairDurationMatrix = new int[InitialSettings.DEFAULT_NUM_OF_STATES - 1][InitialSettings.DEFAULT_NUM_OF_STATES - 1];
        for (int i = 0; i < repairDurationMatrix.length; i++) {
            int repairDuration = InitialSettings.DEFAULT_REPAIR_DURATION_STEP;
            for (int j = i; j < repairDurationMatrix[i].length; j++) {
                repairDurationMatrix[i][j] = repairDuration;
                repairDuration += InitialSettings.DEFAULT_REPAIR_DURATION_STEP;
            }
        }
    }

    private void initializeStaticCostVector() {
        staticCostVector = new int[InitialSettings.DEFAULT_NUM_OF_STATES];
        for (int state = 0; state < InitialSettings.DEFAULT_NUM_OF_STATES; state++) {
            if (state == 0) {
                staticCostVector[state] = 0;
            } else if (state == 1) {
                staticCostVector[state] = 1;
            } else if (state == 2) {
                staticCostVector[state] = 2;
            } else {
                staticCostVector[state] = staticCostVector[state - 1] + staticCostVector[state - 2];
            }
        }
    }

    private void initializeWeibullScaleVector() {
        weibullScaleVector = new int[InitialSettings.DEFAULT_NUM_OF_STATES - 1];
        for (int state = 1; state < InitialSettings.DEFAULT_NUM_OF_STATES; state++) {
            switch (state){
                case 2-> weibullScaleVector[state - 1] = 30;
                case 3-> weibullScaleVector[state - 1] = 40;
                default -> weibullScaleVector[state - 1] = 50;
            }
        }
    }

    private void initializeWeibullShapeVector() {
        weibullShapeVector = new double[InitialSettings.DEFAULT_NUM_OF_STATES - 1];
        for (int state = 1; state < InitialSettings.DEFAULT_NUM_OF_STATES; state++) {
            weibullShapeVector[state - 1] = 5.0;
        }
    }

    private void initializeInspectionObjectives() {
        inspectionObjectives = new int[InitialSettings.DEFAULT_NUM_OF_STATES - 1];
        Arrays.fill(inspectionObjectives, 1);
    }

    private void initializeEmergencyRepairParameters(){
        this.emStateDropsTo = InitialSettings.DEFAULT_STATE_DROPS_TO;
        this.emNextInspectionIn = InitialSettings.DEFAULT_NEXT_INSPECTION_IN;
        this.emEmergencyCost = InitialSettings.DEFAULT_EMERGENCY_COST;
        this.emDelay = InitialSettings.DEFAULT_EMERGENCY_DELAY;
        this.emergencyRepair = InitialSettings.DEFAULT_EMERGENCY_REPAIR_CHK;
    }

    private void initializeSimulationPanelParameters() {
        this.prodCycles = InitialSettings.DEFAULT_PROD_CYCLES_NUM;
        this.minInterval = InitialSettings.DEFAULT_MIN_INTERVAL;
        this.maxInterval = InitialSettings.DEFAULT_MAX_INTERVAL;
        this.step = InitialSettings.DEFAULT_STEP;
        this.holdTheData = InitialSettings.DEFAULT_HOLD_THE_DATA;
        this.runMultipleTimes = InitialSettings.DEFAULT_RUN_MULTIPLE_TIMES;
        this.runMultipleTimesNum = InitialSettings.DEFAULT_RUN_MULTIPLE_TIMES_NUM;
    }

    public int getRepairCost(int fromState, int toState) {
        return repairCostMatrix[toState - 1][fromState - 2];
    }

    public void setRepairCost(int fromState, int toState, int value) {
        repairCostMatrix[toState - 1][fromState - 2] = value;
    }

    public int getRepairDuration(int fromState, int toState) {
        return repairDurationMatrix[toState - 1][fromState - 2];
    }

    public void setRepairDuration(int fromState, int toState, int value) {
        repairDurationMatrix[toState - 1][fromState - 2] = value;
    }

    public int getStaticCost(int state) {
        return staticCostVector[state - 1];
    }

    public void setStaticCost(int state, int value) {
        staticCostVector[state - 1] = value;
    }

    public int getWeibullScale(int state) {
        return weibullScaleVector[state - 1];
    }

    public void setWeibullScale(int state, int scale) {
        this.weibullScaleVector[state - 1] = scale;
    }

    public double getWeibullShape(int state) {
        return weibullShapeVector[state - 1];
    }

    public void setWeibullShape(int state, double shape) {
        this.weibullShapeVector[state - 1] = shape;
    }

    public int getInspectionCost() {
        return inspectionCost;
    }

    public void setInspectionCost(int cost) {
        this.inspectionCost = cost;
    }

    public int getInspectionObjectives(int state){return inspectionObjectives[state-2];}

    public void setInspectionObjectives(int state, int repairToState){this.inspectionObjectives[state-2] = repairToState;}

    public int getEmStateDropsTo() {
        return emStateDropsTo;
    }

    public void setEmStateDropsTo(int emStateDropsTo) {
        this.emStateDropsTo = emStateDropsTo;
    }

    public int getEmNextInspectionIn() {
        return emNextInspectionIn;
    }

    public void setEmNextInspectionIn(int emNextInspectionIn) {
        this.emNextInspectionIn = emNextInspectionIn;
    }

    public int getEmEmergencyCost() {
        return emEmergencyCost;
    }

    public void setEmEmergencyCost(int emEmergencyCost) {
        this.emEmergencyCost = emEmergencyCost;
    }

    public int getEmDelay() {
        return emDelay;
    }

    public void setEmDelay(int emDelay) {
        this.emDelay = emDelay;
    }

    public boolean isEmergencyRepair() {
        return emergencyRepair;
    }

    public void setEmergencyRepair(boolean emergencyRepair) {
        this.emergencyRepair = emergencyRepair;
    }

    public int getProdCycles() {
        return prodCycles;
    }

    public void setProdCycles(int prodCycles) {
        this.prodCycles = prodCycles;
    }

    public int getMinInterval() {
        return minInterval;
    }

    public void setMinInterval(int minInterval) {
        this.minInterval = minInterval;
    }

    public int getMaxInterval() {
        return maxInterval;
    }

    public void setMaxInterval(int maxInterval) {
        this.maxInterval = maxInterval;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public boolean isHoldTheData() {
        return holdTheData;
    }

    public void setHoldTheData(boolean holdTheData) {
        this.holdTheData = holdTheData;
    }

    public boolean isRunMultipleTimes() {
        return runMultipleTimes;
    }

    public void setRunMultipleTimes(boolean runMultipleTimes) {
        this.runMultipleTimes = runMultipleTimes;
    }

    public int getRunMultipleTimesNum() {
        return runMultipleTimesNum;
    }

    public void setRunMultipleTimesNum(int runMultipleTimesNum) {
        this.runMultipleTimesNum = runMultipleTimesNum;
    }

    public boolean isShockDegradation() {
        return shockDegradation;
    }

    public void setShockDegradation(boolean shockDegradation) {
        this.shockDegradation = shockDegradation;
    }

    public int getValueFromParameters(int type, int fromState, int toState) {
        return switch (type) {
            case InitialSettings.REPAIR_COST -> getRepairCost(fromState, toState);
            case InitialSettings.REPAIR_DURATION -> getRepairDuration(fromState, toState);
            default -> throw new IllegalStateException(
                    "Type of data to retrieve mismatch");
        };
    }


    public void setValueInParameters(int type, int fromState, int toState, int value) {

        switch (type) {
            case InitialSettings.REPAIR_COST -> setRepairCost(fromState, toState, value);
            case InitialSettings.REPAIR_DURATION -> setRepairDuration(fromState, toState, value);
            default -> throw new IllegalStateException(
                    "Type of data to set mismatch");
        }
    }

    public int getValueFromParameters(int type, int state) {
        return switch (type) {
            case InitialSettings.STATIC_COST -> getStaticCost(state);
            case InitialSettings.WEIBULL_SCALE -> getWeibullScale(state);
            case InitialSettings.INSPECTION_COST -> getInspectionCost();
            case InitialSettings.INSPECTION_OBJECTIVES -> getInspectionObjectives(state);
            default -> throw new IllegalStateException("Data to retrieve mismatch");
        };
    }

    public void setValueInParameters(int type, int state, int value) {
        switch (type) {
            case InitialSettings.STATIC_COST -> setStaticCost(state, value);
            case InitialSettings.WEIBULL_SCALE -> setWeibullScale(state, value);
            case InitialSettings.INSPECTION_COST -> setInspectionCost(value);
            case InitialSettings.INSPECTION_OBJECTIVES -> setInspectionObjectives(state, value);
            default -> throw new IllegalStateException(
                    "Data type mismatch");
        }
    }

    public void setValueInParameters(int type, int value){
        switch (type){
            case InitialSettings.STATE_DROPS_TO -> setEmStateDropsTo(value);
            case InitialSettings.NEXT_INSPECTION_IN -> setEmNextInspectionIn(value);
            case InitialSettings.EMERGENCY_COST -> setEmEmergencyCost(value);
            case InitialSettings.EMERGENCY_DELAY -> setEmDelay(value);
            case InitialSettings.PRODUCTION_CYCLES -> setProdCycles(value);
            case InitialSettings.MIN_INTERVAL -> setMinInterval(value);
            case InitialSettings.MAX_INTERVAL -> setMaxInterval(value);
            case InitialSettings.STEP -> setStep(value);
            case InitialSettings.RUN_MULTIPLE_TIMES -> setRunMultipleTimesNum(value);
            default -> throw new IllegalStateException(
                    "Data type mismatch");
        }
    }
    public int getValueFromParameters(int type){
        return switch (type){
            case InitialSettings.STATE_DROPS_TO -> getEmStateDropsTo();
            case InitialSettings.NEXT_INSPECTION_IN -> getEmNextInspectionIn();
            case InitialSettings.EMERGENCY_COST -> getEmEmergencyCost();
            case InitialSettings.EMERGENCY_DELAY -> getEmDelay();
            case InitialSettings.PRODUCTION_CYCLES -> getProdCycles();
            case InitialSettings.MIN_INTERVAL -> getMinInterval();
            case InitialSettings.MAX_INTERVAL -> getMaxInterval();
            case InitialSettings.STEP -> getStep();
            case InitialSettings.RUN_MULTIPLE_TIMES -> getRunMultipleTimesNum();
            default -> throw new IllegalStateException(
                    "Data type mismatch");
        };
    }

}
