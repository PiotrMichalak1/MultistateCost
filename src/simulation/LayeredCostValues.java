package simulation;

public class LayeredCostValues implements Cloneable{

    private double[] operationalCost;
    private double[] repairCost;
    private double[] inspectionsCost;



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


    //creates deep copy of this object
    @Override
    public Object clone() throws CloneNotSupportedException {
        LayeredCostValues cloned = (LayeredCostValues) super.clone();
        cloned.operationalCost = this.operationalCost.clone();
        cloned.repairCost = this.repairCost.clone();
        cloned.inspectionsCost = this.inspectionsCost.clone();
        return cloned;
    }


}
