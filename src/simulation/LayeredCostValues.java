package simulation;

public class LayeredCostValues {

    private final double[] operationalCost;
    private final double[] repairCost;
    private final double[] inspectionsCost;



    public LayeredCostValues(int domainLength) {
        operationalCost = new double[domainLength];
        repairCost = new double[domainLength];
        inspectionsCost = new double[domainLength];
    }

    public void setOperationalCost(int index, double value) {
        operationalCost[index] = value;
    }

    public void setRepairCost(int index, double value) {
        repairCost[index] = value;
    }

    public void setInspectionsCost(int index, double value) {
        inspectionsCost[index] = value;
    }

    public double[] getOperationalCost() {
        return operationalCost;
    }

    public double[] getRepairCost() {
        return repairCost;
    }

    public double[] getInspectionsCost() {
        return inspectionsCost;
    }
}
