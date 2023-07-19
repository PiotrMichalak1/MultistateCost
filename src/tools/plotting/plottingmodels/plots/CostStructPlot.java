package tools.plotting.plottingmodels.plots;

import simulation.LayeredCostValues;
import simulation.Simulation;
import tools.plotting.plottingmodels.PlotterModel;

import java.awt.*;
import java.util.Map;

public class CostStructPlot extends StateStructPlot implements IPlot {



    public CostStructPlot(PlotterModel parentPlotterModel) {
        super(parentPlotterModel);
    }

    @Override
    public void addData(Simulation sim) throws CloneNotSupportedException {
        int testIndex =8; //TODO: change this to get value of index from Parameters
        int chosenInterval = (int)sim.getSimulationDomain()[testIndex];
        LayeredCostValues lcv = (LayeredCostValues) sim.getLayeredCostValues().clone();

        double operational = lcv.getOperationalCost()[chosenInterval];
        double repair = lcv.getRepairCost()[chosenInterval];
        double inspection = lcv.getInspectionsCost()[chosenInterval];

        double sum = operational + repair + inspection;

        putPercentsInBars(operational, repair, inspection, sum);

    }

    private void putPercentsInBars(double operational, double repair, double inspection, double sum) {
        bars.put("Operational",(operational / sum)*100);
        bars.put("Repair",(repair / sum)*100);
        bars.put("Inspection",(inspection / sum)*100);
    }


    private void drawBarLabel(Graphics2D g2, String label, int barNum) {

    }

}
