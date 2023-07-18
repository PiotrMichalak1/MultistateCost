package tools.plotting.plottingmodels.plots;

import settings.GraphicSettings;
import simulation.Simulation;
import tools.FunctionTools;
import tools.functions.Mathematics;
import tools.plotting.DoublePoint;
import tools.plotting.PlotPointOfInterest;
import tools.plotting.plottingmodels.PlotterModel;
import tools.plotting.plottingmodels.plots.graphics.PlotColors;

import java.awt.*;
import java.util.ArrayList;

public class Plot {

    public final PlotterModel parentPlotterModel;
    public ArrayList<double[]> functionsDomains = new ArrayList<>();
    public ArrayList<double[]> functionsValues = new ArrayList<>();

    private final int margin;
    public final PlotPointOfInterest plotPOI;


    public Plot(PlotterModel parentPlotterModel) {
        this.parentPlotterModel = parentPlotterModel;
        this.margin = parentPlotterModel.coordinateSystem.getMargin();
        this.plotPOI = new PlotPointOfInterest();
    }

    public void onMouseMovement(Point mousePosition) {
        plotPOI.setMouseX(mousePosition.x);
        plotPOI.setMouseY(mousePosition.y);
    }

    public void addFunctionData(double[] domain, double[] codomain) {
        functionsDomains.add(domain);
        functionsValues.add(codomain);
        adjustCameraToPlot();
    }

    public void clearFunctionData() {
        functionsDomains.clear();
        functionsValues.clear();
    }

    public void drawFunction(Graphics2D g2, double[] functionDomain, double[] functionValues, boolean isLayered) {
        g2.setStroke(new BasicStroke(GraphicSettings.FUNCTION_THICKNESS));
        if (functionDomain != null && functionValues != null) {

            if (functionDomain.length == functionValues.length) {
                Point pointPx;
                DoublePoint point;
                if (functionDomain.length == 1) {
                    point = new DoublePoint(functionDomain[0], functionValues[0]);
                    if (isPointInVisibleRange(point)) {
                        pointPx = pointToPixels(point);
                        plotPOI.updatePOIValues(pointPx, point);
                        g2.setColor(PlotColors.next());
                        g2.fillOval((int) pointPx.getX() - GraphicSettings.PLOT_POINT_THICKNESS / 2,
                                (int) pointPx.getY() - GraphicSettings.PLOT_POINT_THICKNESS / 2,
                                GraphicSettings.PLOT_POINT_THICKNESS,
                                GraphicSettings.PLOT_POINT_THICKNESS);
                    }
                } else {
                    if (isLayered)
                        g2.setColor(PlotColors.nextLayered());
                    else
                        g2.setColor(PlotColors.next());

                    DoublePoint prevPoint;
                    DoublePoint nextPoint;
                    Point prevPointPx;
                    Point nextPointPx;

                    prevPoint = new DoublePoint(functionDomain[0], functionValues[0]);
                    nextPoint = new DoublePoint(functionDomain[1], functionValues[1]);

                    prevPointPx = pointToPixels(prevPoint);
                    nextPointPx = pointToPixels(nextPoint);
                    plotPOI.updatePOIValues(prevPointPx, prevPoint);
                    plotPOI.updatePOIValues(nextPointPx, nextPoint);
                    g2.drawLine((int) prevPointPx.getX(),
                            (int) prevPointPx.getY(),
                            (int) nextPointPx.getX(),
                            (int) nextPointPx.getY()
                    );

                    for (int i = 1; i < functionDomain.length - 1; i++) {
                        prevPoint = new DoublePoint(functionDomain[i], functionValues[i]);
                        nextPoint = new DoublePoint(functionDomain[i + 1], functionValues[i + 1]);

                        prevPointPx = pointToPixels(prevPoint);
                        nextPointPx = pointToPixels(nextPoint);
                        plotPOI.updatePOIValues(nextPointPx, nextPoint);
                        g2.drawLine((int) prevPointPx.getX(),
                                (int) prevPointPx.getY(),
                                (int) nextPointPx.getX(),
                                (int) nextPointPx.getY()
                        );

                    }

                }

            } else {
                throw new IllegalArgumentException("Domain and codomain are not the same length");
            }

        } else {
            throw new IllegalArgumentException((functionDomain == null) ? "Domain of function is null" :
                    "Codomain of function is null");
        }
        g2.setStroke(new BasicStroke(1));
    }

    public void drawAllFunctions(Graphics2D g2) {
        plotPOI.resetDistanceAndVisibility();
        PlotColors.resetColor();
        for (int i = 0; i < functionsDomains.size(); i++) {
            drawFunction(g2, functionsDomains.get(i), functionsValues.get(i), false);
        }

    }

    public void adjustCameraToPlot() {
        double[] functionDomain;
        double[] functionCodomain;
        if (!functionsDomains.isEmpty()) {
            functionDomain = functionsDomains.get(functionsDomains.size() - 1);
            functionCodomain = functionsValues.get(functionsValues.size() - 1);

            if (functionDomain.length > 1) {

                double smallestValue = FunctionTools.getSmallestValueOfFunction(functionCodomain);
                double biggestValue = FunctionTools.getBiggestValueOfFunction(functionCodomain);

                double rangeOfValues = biggestValue - smallestValue;

                parentPlotterModel.coordinateSystem.xRange[0] = functionDomain[0];
                parentPlotterModel.coordinateSystem.yRange[0] = smallestValue;
                parentPlotterModel.coordinateSystem.updateRanges(parentPlotterModel.drawingWidth, parentPlotterModel.drawingHeight);

                double visibleXWidth = parentPlotterModel.coordinateSystem.xRange[1] - parentPlotterModel.coordinateSystem.xRange[0];
                double domainWidth = functionDomain[functionCodomain.length - 1] - functionDomain[0];

                if (visibleXWidth < domainWidth) {

                    while (visibleXWidth < domainWidth) {

                        parentPlotterModel.coordinateSystem.numOfMouseScrolls -= 1;
                        parentPlotterModel.coordinateSystem.updateGridSpacing();
                        parentPlotterModel.coordinateSystem.updateScaleOnMouseScroll();
                        parentPlotterModel.coordinateSystem.updateRanges(parentPlotterModel.drawingWidth, parentPlotterModel.drawingHeight);
                        visibleXWidth = parentPlotterModel.coordinateSystem.xRange[1] - parentPlotterModel.coordinateSystem.xRange[0];

                    }

                    parentPlotterModel.coordinateSystem.xRange[0] = functionDomain[0] - (visibleXWidth - domainWidth) / 2;
                    parentPlotterModel.coordinateSystem.updateRanges(parentPlotterModel.drawingWidth, parentPlotterModel.drawingHeight);

                } else if (visibleXWidth > domainWidth) {
                    while (visibleXWidth > domainWidth) {

                        parentPlotterModel.coordinateSystem.numOfMouseScrolls += 1;
                        parentPlotterModel.coordinateSystem.updateGridSpacing();
                        parentPlotterModel.coordinateSystem.updateScaleOnMouseScroll();
                        parentPlotterModel.coordinateSystem.updateRanges(parentPlotterModel.drawingWidth, parentPlotterModel.drawingHeight);
                        visibleXWidth = parentPlotterModel.coordinateSystem.xRange[1] - parentPlotterModel.coordinateSystem.xRange[0];

                    }
                    parentPlotterModel.coordinateSystem.numOfMouseScrolls -= 1;
                    parentPlotterModel.coordinateSystem.updateGridSpacing();
                    parentPlotterModel.coordinateSystem.updateScaleOnMouseScroll();
                    parentPlotterModel.coordinateSystem.updateRanges(parentPlotterModel.drawingWidth, parentPlotterModel.drawingHeight);
                    visibleXWidth = parentPlotterModel.coordinateSystem.xRange[1] - parentPlotterModel.coordinateSystem.xRange[0];
                    parentPlotterModel.coordinateSystem.xRange[0] = functionDomain[0] - (visibleXWidth - domainWidth) / 2;
                    parentPlotterModel.coordinateSystem.updateRanges(parentPlotterModel.drawingWidth, parentPlotterModel.drawingHeight);

                }

                if (rangeOfValues != 0) {
                    double exactValue = (rangeOfValues * 5.0 * parentPlotterModel.coordinateSystem.smallGridSpacing) / (parentPlotterModel.coordinateSystem.scaleMultiplier * parentPlotterModel.drawingHeight);
                    parentPlotterModel.coordinateSystem.scaleUnitY = Mathematics.roundUpToTheNearestPowerOf(exactValue, 2);
                    parentPlotterModel.coordinateSystem.updateRanges(parentPlotterModel.drawingWidth, parentPlotterModel.drawingHeight);

                    double visibleYHeight = parentPlotterModel.coordinateSystem.yRange[1] - parentPlotterModel.coordinateSystem.yRange[0];
                    parentPlotterModel.coordinateSystem.yRange[0] = smallestValue - (visibleYHeight - rangeOfValues) / 2;
                    parentPlotterModel.coordinateSystem.updateRanges(parentPlotterModel.drawingWidth, parentPlotterModel.drawingHeight);
                }


            }
        }


    }

    boolean isPointInVisibleRange(DoublePoint point) {
        boolean isInXRange = (parentPlotterModel.coordinateSystem.xRange[0] < point.getX() &&
                parentPlotterModel.coordinateSystem.xRange[1] > point.getX());
        boolean isInYRange = (parentPlotterModel.coordinateSystem.yRange[0] < point.getY() &&
                parentPlotterModel.coordinateSystem.yRange[1] > point.getY());

        return isInXRange && isInYRange;
    }


    public Point pointToPixels(DoublePoint point) {
        int xPx = margin + (int) Math.round(parentPlotterModel.drawingWidth * (point.getX() - parentPlotterModel.coordinateSystem.xRange[0]) / (parentPlotterModel.coordinateSystem.xRange[1] - parentPlotterModel.coordinateSystem.xRange[0]));
        int yPx = margin + parentPlotterModel.drawingHeight - (int) Math.round(parentPlotterModel.drawingHeight * (point.getY() - parentPlotterModel.coordinateSystem.yRange[0]) / (parentPlotterModel.coordinateSystem.yRange[1] - parentPlotterModel.coordinateSystem.yRange[0]));
        return new Point(xPx, yPx);
    }

    //gets vector of double domain values and translates it to pixel coordinates in plotterModel
    public int[] domainVectorToPixels(double[] vector) {
        int[] vectorPx = new int[vector.length];
        for (int index =0;index<vector.length;index++){
            vectorPx[index] = margin + (int) Math.round(parentPlotterModel.drawingWidth * (vector[index] - parentPlotterModel.coordinateSystem.xRange[0]) / (parentPlotterModel.coordinateSystem.xRange[1] - parentPlotterModel.coordinateSystem.xRange[0]));
        }
        return vectorPx;
    }

    public int[] valuesVectorToPixels(double[] vector) {
        int[] vectorPx = new int[vector.length];
        for (int index =0;index<vector.length;index++){
            vectorPx[index] = margin + parentPlotterModel.drawingHeight - (int) Math.round(parentPlotterModel.drawingHeight * (vector[index] - parentPlotterModel.coordinateSystem.yRange[0]) / (parentPlotterModel.coordinateSystem.yRange[1] - parentPlotterModel.coordinateSystem.yRange[0]));
        }
        return vectorPx;
    }


    public void addLayeredFunctionData(Simulation sim) throws CloneNotSupportedException {
    }


    public void updateFunctionValuesToStacked() {
    }
}
