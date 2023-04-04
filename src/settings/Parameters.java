package settings;

public class Parameters {

    private static Parameters instance =null;
    private final int NUM_OF_STATES = 4;
    private int REPAIR_COST_STEP = 10;
    private int[][] repairCostMatrix;

    private Parameters() {
        initializeRepairCosts();
    }

    public static Parameters getInstance() {
        if (instance ==null){
            instance = new Parameters();
        }
        return instance;
    }

    private void initializeRepairCosts() {
        repairCostMatrix = new int[NUM_OF_STATES - 1][NUM_OF_STATES - 1];
        for (int i = 0; i < repairCostMatrix.length; i++) {
            int repairCost = REPAIR_COST_STEP;
            for (int j = i; j < repairCostMatrix[i].length; j++) {
                repairCostMatrix[i][j] = repairCost;
                repairCost += REPAIR_COST_STEP;
                System.out.println(repairCostMatrix[i][j]+ "Is a cost");
            }
        }
    }

}
