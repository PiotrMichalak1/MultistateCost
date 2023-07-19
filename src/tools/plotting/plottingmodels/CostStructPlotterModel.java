package tools.plotting.plottingmodels;

import tools.plotting.plottingmodels.plots.CostStructuralPlot;

public class CostStructPlotterModel extends StateStructPlotterModel{
    @Override
    public void setPlot() {
        this.plot = new CostStructuralPlot(this);
    }
}
