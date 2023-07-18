package tools.plotting.plottingmodels;

import tools.plotting.plottingmodels.coordsys.CoordinateSystem;
import tools.plotting.plottingmodels.plots.Plot;

import java.awt.*;


public class PlotterModel implements IPlotterModel {
    public CoordinateSystem coordinateSystem;
    protected Plot plot;

    public int drawingWidth;
    public int drawingHeight;

    public PlotterModel() {
        setCoordinateSystem();
        setPlot();
    }

    public void setCoordinateSystem() {
        this.coordinateSystem = new CoordinateSystem();
    }


    @Override
    public void setPlot() {
        this.plot = new Plot(this);
    }

    public void drawPlot(Graphics2D g2) {
        coordinateSystem.updateRanges(drawingWidth, drawingHeight);
        coordinateSystem.drawGrid(g2, drawingWidth, drawingHeight);
        plot.drawAllFunctions(g2);
        coordinateSystem.drawMargins(g2, drawingWidth, drawingHeight);
        coordinateSystem.drawAxes(g2, drawingWidth, drawingHeight);
        coordinateSystem.drawLabels(g2, drawingWidth, drawingHeight);
        plot.plotPOI.drawPOI(g2);
        plot.plotPOI.drawPOIdata(g2);
    }


    public void onMouseScroll(Point mousePosition, int wheelRotation, int drawingWidth, int drawingHeight) {
        coordinateSystem.onMouseScroll(mousePosition, wheelRotation, drawingWidth, drawingHeight);
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

    public void clearFunctionData() {
        plot.clearFunctionData();
    }

    public void setDrawingWidth(int drawingWidth) {
        this.drawingWidth = drawingWidth;
    }

    public void setDrawingHeight(int drawingHeight) {
        this.drawingHeight = drawingHeight;
    }

    @Override
    public Plot getPlot() {
        return plot;
    }

}



