package tools.plotting.plottingmodels;


import tools.plotting.plottingmodels.coordsys.PercentBarCoordinateSystem;
import tools.plotting.plottingmodels.plots.StructuralPlot;

public class StructuralPlotterModel extends PlotterModel implements IPlotterModel{
    @Override
    public void setPlot() {
        this.plot = new StructuralPlot(this);
    }

    @Override
    public void setCoordinateSystem(){
        this.coordinateSystem = new PercentBarCoordinateSystem(this);
    }
}
