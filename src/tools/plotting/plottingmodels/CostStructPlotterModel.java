package tools.plotting.plottingmodels;

import tools.plotting.plottingmodels.plots.CostStructPlot;

public class CostStructPlotterModel extends StateStructPlotterModel{
    @Override
    public void setPlot() {
        this.plot = new CostStructPlot(this);
    }
}
