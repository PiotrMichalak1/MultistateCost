package tools.plotting.plotters;

import tools.interfaces.IPlotter;
import tools.plotting.plotters.plots.LayeredCostPlot;


public class LayeredCostPlotter extends Plotter implements IPlotter {

    @Override
    public void setPlot() {
        this.plot = new LayeredCostPlot(this);
    }

}
