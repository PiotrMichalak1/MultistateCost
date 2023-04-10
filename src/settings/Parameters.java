package settings;

public class Parameters {

    private static Parameters instance = null;
    public final int NUM_OF_STATES = 4;
    public final int DEFAULT_REPAIR_COST_STEP = 10;
    public final int DEFAULT_REPAIR_DURATION_STEP = 1;

    private int[][] repairCostMatrix;
    private int[][] repairDurationMatrix;

    private int[] staticCostVector;

    private Parameters() {
        initializeRepairCosts();
        initializeRepairDuration();
        initializeStaticCostVector();
    }

    public static Parameters getInstance() {
        if (instance == null) {
            instance = new Parameters();
        }
        return instance;
    }

    private void initializeRepairCosts() {
        repairCostMatrix = new int[NUM_OF_STATES - 1][NUM_OF_STATES - 1];
        for (int i = 0; i < repairCostMatrix.length; i++) {
            int repairCost = DEFAULT_REPAIR_COST_STEP;
            for (int j = i; j < repairCostMatrix[i].length; j++) {
                repairCostMatrix[i][j] = repairCost;
                repairCost += DEFAULT_REPAIR_COST_STEP;
            }
        }
    }

    private void initializeRepairDuration() {
        repairDurationMatrix = new int[NUM_OF_STATES - 1][NUM_OF_STATES - 1];
        for (int i = 0; i < repairDurationMatrix.length; i++) {
            int repairDuration = DEFAULT_REPAIR_DURATION_STEP;
            for (int j = i; j < repairDurationMatrix[i].length; j++) {
                repairDurationMatrix[i][j] = repairDuration;
                repairDuration += DEFAULT_REPAIR_DURATION_STEP;
            }
        }
    }

    private void initializeStaticCostVector() {
        staticCostVector = new int[NUM_OF_STATES];
        for (int state = 0; state < NUM_OF_STATES; state++) {
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

    public int getRepairCost(int fromState, int toState) {
        return repairCostMatrix[toState - 1][fromState - 2];
    }

    public void setRepairCost(int fromState, int toState, int value) {
        repairCostMatrix[toState - 1][fromState - 2] = value;
    }

    public int getRepairDuration(int fromState, int toState) {
        return repairDurationMatrix[toState - 1][fromState - 2];
    }

    public int getStaticCost(int state) {
        return staticCostVector[state - 1];
    }

    public void setStaticCost(int state, int value) {
        staticCostVector[state - 1] = value;
    }

    public void setRepairDuration(int fromState, int toState, int value) {
        repairDurationMatrix[toState - 1][fromState - 2] = value;
    }

    public int getValueFromSettings(String type, int fromState, int toState) {
        int value;
        switch (type) {
            case "REPAIR_COST":
                value = getRepairCost(fromState, toState);
                break;
            case "REPAIR_DURATION":
                value = getRepairDuration(fromState, toState);
                break;
            default:
                throw new IllegalStateException(
                        "Type of data to retrieve mismatch");
        }
        return value;
    }

    public int getValueFromSettings(String type, int fromState) {
        int value;
        switch (type) {
            case "STATIC_COST":
                value = getStaticCost(fromState);
            default:
                value = 0;
        }
        return value;
    }

    public void setValueInSettings(String type, int fromState, int toState, int value) {

        switch (type) {
            case "REPAIR_COST":
                setRepairCost(fromState, toState, value);
                break;
            case "REPAIR_DURATION":
                setRepairDuration(fromState, toState, value);
                break;
            default:
                throw new IllegalStateException(
                        "Type of data to set mismatch");
        }
    }

    public void setValueInSettings(String type, int state, int value) {
        switch (type) {
            case "STATIC_COST":
                setStaticCost(state, value);
            default:
                throw new IllegalStateException(
                        "Type of data to set mismatch");
        }
    }

}
