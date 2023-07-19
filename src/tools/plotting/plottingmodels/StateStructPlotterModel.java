package tools.plotting.plottingmodels;


import tools.plotting.plottingmodels.coordsys.PercentBarCoordinateSystem;
import tools.plotting.plottingmodels.plots.StateStructPlot;

import java.awt.*;

public class StateStructPlotterModel extends PlotterModel implements IPlotterModel {
    @Override
    public void setPlot() {
        this.plot = new StateStructPlot(this);
    }

    @Override
    public void setCoordinateSystem() {
        this.coordinateSystem = new PercentBarCoordinateSystem(this);
    }

    @Override
    public void drawPlot(Graphics2D g2) {
        coordinateSystem.updateRanges(drawingWidth, drawingHeight);
        coordinateSystem.drawGrid(g2, drawingWidth, drawingHeight);
        coordinateSystem.drawMargins(g2, drawingWidth, drawingHeight);
        coordinateSystem.drawAxes(g2, drawingWidth, drawingHeight);
        coordinateSystem.drawLabels(g2, drawingWidth, drawingHeight);
        plot.draw(g2);
    }
}
