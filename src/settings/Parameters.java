package settings;

public class Parameters {

    private static Parameters instance = null;
    public static final int NUM_OF_STATES = 4;
    public final int DEFAULT_REPAIR_COST_STEP = 10;
    public final int DEFAULT_REPAIR_DURATION_STEP = 1;

    private int[][] repairCostMatrix;
    private int[][] repairDurationMatrix;

    private int[] staticCostVector;
    private int[] weibullScaleVector;
    private double[] weibullShapeVector;
    private int inspectionCost = 5;


    public static final int REPAIR_COST = 1;
    public static final int REPAIR_DURATION = 2;
    public static final int OTHER_PROPERTIES =3;
    public static final int STATIC_COST = 4;
    public static final int WEIBULL_SCALE = 5;
    public static final int WEIBULL_SHAPE = 6;
    public static final int INSPECTION_COST = 67;


    private Parameters() {
        initializeRepairCosts();
        initializeRepairDuration();
        initializeStaticCostVector();
        initializeWeibullScaleVector();
        initializeWeibullShapeVector();
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

    private void initializeWeibullScaleVector() {
        weibullScaleVector = new int[NUM_OF_STATES - 1];
        for (int state = 1; state < NUM_OF_STATES; state++) {
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
        weibullShapeVector = new double[NUM_OF_STATES - 1];
        for (int state = 1; state < NUM_OF_STATES; state++) {
            weibullShapeVector[state - 1] = 5.0;
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

    public int getValueFromSettings(int type, int fromState, int toState) {
        int value;
        switch (type) {
            case REPAIR_COST:
                value = getRepairCost(fromState, toState);
                break;
            case REPAIR_DURATION:
                value = getRepairDuration(fromState, toState);
                break;
            default:
                throw new IllegalStateException(
                        "Type of data to retrieve mismatch");
        }
        return value;
    }


    public void setValueInSettings(int type, int fromState, int toState, int value) {

        switch (type) {
            case REPAIR_COST:
                setRepairCost(fromState, toState, value);
                break;
            case REPAIR_DURATION:
                setRepairDuration(fromState, toState, value);
                break;
            default:
                throw new IllegalStateException(
                        "Type of data to set mismatch");
        }
    }

    public int getValueFromSettings(int type, int state) {
        int value;
        switch (type) {
            case STATIC_COST:
                value = getStaticCost(state);
                break;
            case WEIBULL_SCALE:
                value = getWeibullScale(state);
                break;
            case INSPECTION_COST:
                value = getInspectionCost();
                break;
            default:
                value = 0;
        }
        return value;
    }

    public void setValueInSettings(int type, int state, int value) {
        switch (type) {
            case STATIC_COST:
                setStaticCost(state, value);
                break;
            case WEIBULL_SCALE:
                setWeibullScale(state, value);
                break;
            case INSPECTION_COST:
                setInspectionCost(value);
                break;
            default:
                throw new IllegalStateException(
                        "Data type not matching any parameter");
        }
    }

}
