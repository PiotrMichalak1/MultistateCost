package tools.plotting.plotters;

import tools.plotting.plotters.plots.LayeredPlot;
import tools.plotting.plotters.plots.Plot;


public class LayeredPlotter extends MainPlotter {

    @Override
    public void setPlot() {
        this.plot = new LayeredPlot(this);
    }

}
