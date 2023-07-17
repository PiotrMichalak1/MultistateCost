package tools.plotting.plotters;

import tools.interfaces.IPlotter;
import tools.plotting.plotters.coordsys.MainCoordinateSystem;
import tools.plotting.plotters.plots.Plot;

import java.awt.*;


public class Plotter implements IPlotter{
    public MainCoordinateSystem coordinateSystem;
    public Plot plot;

    public int width;
    public int height;

    public Plotter() {
        setCoordinateSystem();
        setPlot();
    }

    public void setCoordinateSystem() {
        this.coordinateSystem = new MainCoordinateSystem();
    }


    @Override
    public void setPlot() {
        this.plot = new Plot(this);
    }

    public void drawPlot(Graphics2D g2) {
        coordinateSystem.updateRanges(width, height);
        coordinateSystem.drawGrid(g2, width, height);
        plot.drawAllFunctions(g2);
        coordinateSystem.drawMargins(g2, width, height);
        coordinateSystem.drawAxes(g2, width, height);
        coordinateSystem.drawLabels(g2, width, height);
        plot.plotPOI.drawPOI(g2);
        plot.plotPOI.drawPOIdata(g2);
    }


    public void onMouseScroll(Point mousePosition, int wheelRotation, int width, int height) {
        coordinateSystem.onMouseScroll(mousePosition, wheelRotation, width, height);
    }

    public void onMouseMovement(Point mousePosition) {
        plot.onMouseMovement(mousePosition);
    }

    public void dragPlot(int dx, int dy) {
        coordinateSystem.dragPlot(dx, dy);
    }

    public int getMargin() {
        return coordinateSystem.getMargin();
    }

    public void addFunctionData(double[] domain, double[] codomain) {
        plot.addFunctionData(domain, codomain);
    }

    public void clearFunctionData() {
        plot.clearFunctionData();
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}



