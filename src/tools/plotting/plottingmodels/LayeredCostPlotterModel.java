package tools.plotting.plottingmodels;

import tools.plotting.plottingmodels.plots.LayeredCostPlot;


public class LayeredCostPlotterModel extends PlotterModel implements IPlotterModel {

    @Override
    public void setPlot() {
        this.plot = new LayeredCostPlot(this);
    }

}
