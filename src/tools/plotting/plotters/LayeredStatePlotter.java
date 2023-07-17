package tools.plotting.plotters;

import tools.interfaces.IPlotter;
import tools.plotting.plotters.plots.LayeredCostPlot;
import tools.plotting.plotters.plots.LayeredStatePlot;

public class LayeredStatePlotter extends MainPlotter implements IPlotter {

    @Override
    public void setPlot() {
        this.plot = new LayeredStatePlot(this);
    }
}
