package settings;

public class Parameters {

    private static Parameters instance =null;
    private final int NUM_OF_STATES = 4;
    private final int  REPAIR_COST_STEP = 10;
    private final int REPAIR_DURATION_STEP = 1;
    private int[][] repairCostMatrix;
    private int[][] repairDurationMatrix;

    private Parameters() {
        initializeRepairCosts();
        initializeRepairDuration();
    }

    public static Parameters getInstance() {
        if (instance ==null){
            instance = new Parameters();
        }
        return  instance;
    }

    private void initializeRepairCosts() {
        repairCostMatrix = new int[NUM_OF_STATES - 1][NUM_OF_STATES - 1];
        for (int i = 0; i < repairCostMatrix.length; i++) {
            int repairCost = REPAIR_COST_STEP;
            for (int j = i; j < repairCostMatrix[i].length; j++) {
                repairCostMatrix[i][j] = repairCost;
                repairCost += REPAIR_COST_STEP;
            }
        }
    }

    private void initializeRepairDuration(){
        repairDurationMatrix = new int[NUM_OF_STATES - 1][NUM_OF_STATES - 1];
        for (int i = 0; i < repairDurationMatrix.length; i++) {
            int repairDuration = REPAIR_DURATION_STEP;
            for (int j = i; j < repairDurationMatrix[i].length; j++) {
                repairDurationMatrix[i][j] = repairDuration;
                repairDuration += REPAIR_DURATION_STEP;
            }
        }
    }

    public int getRepairCost(int fromState, int toState) {
        return repairCostMatrix[toState-1][fromState-2];
    }

    public int getRepairDuration(int fromState, int toState) {
        return repairDurationMatrix[toState-1][fromState-2];
    }

}
