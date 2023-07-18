package tools.plotting.plottingmodels;

import tools.plotting.plottingmodels.plots.LayeredStatePlot;

public class LayeredStatePlotterModel extends PlotterModel implements IPlotterModel {

    @Override
    public void setPlot() {
        this.plot = new LayeredStatePlot(this);
    }
}
