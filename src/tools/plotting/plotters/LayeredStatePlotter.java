package tools.plotting.plotters;

import tools.plotting.plotters.plots.LayeredCostPlot;
import tools.plotting.plotters.plots.LayeredStatePlot;

public class LayeredStatePlotter extends MainPlotter {

    @Override
    public void setPlot() {
        this.plot = new LayeredStatePlot(this);
    }
}
