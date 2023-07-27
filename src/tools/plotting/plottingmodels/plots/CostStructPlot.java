package tools.plotting.plottingmodels.plots;

import settings.Parameters;
import simulation.LayeredCostValues;
import simulation.Simulation;
import tools.plotting.plottingmodels.PlotterModel;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CostStructPlot extends StateStructPlot implements IPlot {



    public CostStructPlot(PlotterModel parentPlotterModel) {
        super(parentPlotterModel);
    }

    @Override
    public void addData(Simulation sim) throws CloneNotSupportedException {
        HashMap<String,double[]> simResult = sim.simulateForGivenInterval(Parameters.getInstance().getStructuralInterval());
        double[] costStructure = simResult.get("cost");

        double operational = costStructure[0];
        double repair = costStructure[1];
        double inspection = costStructure[2];

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
