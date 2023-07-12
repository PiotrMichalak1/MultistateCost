package tools.plotting.plotters;

import tools.plotting.plotters.plots.LayeredCostPlot;


public class LayeredCostPlotter extends MainPlotter {

    @Override
    public void setPlot() {
        this.plot = new LayeredCostPlot(this);
    }

}
