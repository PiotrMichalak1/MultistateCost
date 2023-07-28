package tools.plotting.plottingmodels.plots;

import simulation.Simulation;
import tools.plotting.PlotPointOfInterest;
import tools.plotting.plottingmodels.plots.graphics.PlotLabels;

import java.awt.*;

public interface IPlot {

    void draw(Graphics2D g2);

    PlotPointOfInterest getPlotPOI();

    void onMouseMovement(Point mousePosition);

    void clearFunctionData();

    void addData(Simulation sim) throws CloneNotSupportedException;

    void drawLabels(Graphics2D g2);
}
