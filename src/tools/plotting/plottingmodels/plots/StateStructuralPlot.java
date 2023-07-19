package tools.plotting.plottingmodels.plots;

import simulation.Simulation;
import tools.plotting.PlotPointOfInterest;
import tools.plotting.plottingmodels.PlotterModel;

import java.awt.*;

public class StateStructuralPlot implements IPlot {
    public final static Color BAR_PLOT_COLOR = new Color(0, 114, 189);
    public final PlotterModel parentPlotterModel;

    public StateStructuralPlot(PlotterModel parentPlotterModel) {
        this.parentPlotterModel = parentPlotterModel;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(BAR_PLOT_COLOR);

    }

    @Override
    public PlotPointOfInterest getPlotPOI() {
        return null;
    }

    @Override
    public void onMouseMovement(Point mousePosition) {

    }

    @Override
    public void clearFunctionData() {

    }

    @Override
    public void addData(Simulation sim) throws CloneNotSupportedException {

    }
}
