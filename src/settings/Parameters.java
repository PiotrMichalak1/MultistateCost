package settings;

import java.util.Arrays;

public class Parameters {

    private static Parameters instance = null;

    private boolean emergencyRepair = InitialSettings.DEFAULT_EMERGENCY_REPAIR_CHK;

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


    private Parameters() {
        initializeRepairCosts();
        initializeRepairDuration();
        initializeStaticCostVector();
        initializeWeibullScaleVector();
        initializeWeibullShapeVector();
        initializeInspectionObjectives();
        initializeEmergencyRepairParameters();
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
            if (state == 1) {
                weibullScaleVector[state - 1] = 50;
            } else if (state == 2) {
                weibullScaleVector[state - 1] = 30;
            } else if (state == 3) {
                weibullScaleVector[state - 1] = 40;
            } else {
                weibullScaleVector[state - 1] = 50;
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

    public int getValueFromSettings(int type, int fromState, int toState) {
        return switch (type) {
            case InitialSettings.REPAIR_COST -> getRepairCost(fromState, toState);
            case InitialSettings.REPAIR_DURATION -> getRepairDuration(fromState, toState);
            default -> throw new IllegalStateException(
                    "Type of data to retrieve mismatch");
        };
    }


    public void setValueInSettings(int type, int fromState, int toState, int value) {

        switch (type) {
            case InitialSettings.REPAIR_COST -> setRepairCost(fromState, toState, value);
            case InitialSettings.REPAIR_DURATION -> setRepairDuration(fromState, toState, value);
            default -> throw new IllegalStateException(
                    "Type of data to set mismatch");
        }
    }

    public int getValueFromSettings(int type, int state) {
        return switch (type) {
            case InitialSettings.STATIC_COST -> getStaticCost(state);
            case InitialSettings.WEIBULL_SCALE -> getWeibullScale(state);
            case InitialSettings.INSPECTION_COST -> getInspectionCost();
            case InitialSettings.INSPECTION_OBJECTIVES -> getInspectionObjectives(state);
            default -> throw new IllegalStateException("Data to retrieve mismatch");
        };
    }

    public void setValueInSettings(int type, int state, int value) {
        switch (type) {
            case InitialSettings.STATIC_COST -> setStaticCost(state, value);
            case InitialSettings.WEIBULL_SCALE -> setWeibullScale(state, value);
            case InitialSettings.INSPECTION_COST -> setInspectionCost(value);
            case InitialSettings.INSPECTION_OBJECTIVES -> setInspectionObjectives(state, value);
            default -> throw new IllegalStateException(
                    "Data type mismatch");
        }
    }

    public void setValueInSettings(int type, int value){
        switch (type){
            case InitialSettings.STATE_DROPS_TO -> setEmStateDropsTo(value);
            case InitialSettings.NEXT_INSPECTION_IN -> setEmNextInspectionIn(value);
            case InitialSettings.EMERGENCY_COST -> setEmEmergencyCost(value);
            case InitialSettings.EMERGENCY_DELAY -> setEmDelay(value);
            default -> throw new IllegalStateException(
                    "Data type mismatch");
        }
    }
    public int getValueFromSettings(int type){
        return switch (type){
            case InitialSettings.STATE_DROPS_TO -> getEmStateDropsTo();
            case InitialSettings.NEXT_INSPECTION_IN -> getEmNextInspectionIn();
            case InitialSettings.EMERGENCY_COST -> getEmEmergencyCost();
            case InitialSettings.EMERGENCY_DELAY -> getEmDelay();
            default -> throw new IllegalStateException(
                    "Data type mismatch");
        };
    }

}
