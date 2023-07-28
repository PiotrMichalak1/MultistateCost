package tools.plotting.plottingmodels.plots;

import settings.GraphicSettings;
import simulation.Simulation;
import tools.FunctionTools;
import tools.functions.Mathematics;
import tools.plotting.DoublePoint;
import tools.plotting.PlotPointOfInterest;
import tools.plotting.plottingmodels.PlotterModel;
import tools.plotting.plottingmodels.plots.graphics.LabelSize;
import tools.plotting.plottingmodels.plots.graphics.PlotColors;
import tools.plotting.plottingmodels.plots.graphics.PlotLabels;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Plot implements IPlot {

    PlotLabels plotLabels;
    public final PlotterModel parentPlotterModel;
    public ArrayList<double[]> functionsDomains = new ArrayList<>();
    public ArrayList<double[]> functionsValues = new ArrayList<>();

    private final int margin;
    public final PlotPointOfInterest plotPOI = new PlotPointOfInterest();
    ;


    public Plot(PlotterModel parentPlotterModel) {
        this.parentPlotterModel = parentPlotterModel;
        this.margin = parentPlotterModel.coordinateSystem.getMargin();
        setPlotLabels();
    }

    void setPlotLabels() {
        this.plotLabels = new PlotLabels("Main Plot", "Inspection Interval", "Cost per time unit");
    }

    @Override
    public void draw(Graphics2D g2) {
        plotPOI.resetDistanceAndVisibility();
        PlotColors.resetColor();
        for (int i = 0; i < functionsDomains.size(); i++) {
            drawFunction(g2, functionsDomains.get(i), functionsValues.get(i), false);
        }

    }

    @Override
    public void drawLabels(Graphics2D g2) {
        g2.setColor(GraphicSettings.TITLE_COLOR);


        //drawing title
        LabelSize.setFontSize(g2, LabelSize.PLOT_TITLE);
        FontMetrics fontMetrics = g2.getFontMetrics(g2.getFont());
        int stringWidth = fontMetrics.stringWidth(plotLabels.title());
        int x = margin + (parentPlotterModel.drawingWidth - stringWidth) / 2;
        int y = margin - (int) (margin - LabelSize.PLOT_TITLE) / 2;
        g2.drawString(plotLabels.title(), x, y);

        //drawing xAxis label
        LabelSize.setFontSize(g2, LabelSize.AXIS_LABEL);
        fontMetrics = g2.getFontMetrics(g2.getFont());
        stringWidth = fontMetrics.stringWidth(plotLabels.xAxisLabel());
        x = margin + (parentPlotterModel.drawingWidth - stringWidth) / 2;
        y = 2 * margin + parentPlotterModel.drawingHeight - (int) (margin - LabelSize.AXIS_LABEL) / 2;
        ;
        g2.drawString(plotLabels.xAxisLabel(), x, y);

        //drawing yAxis label

        stringWidth = fontMetrics.stringWidth(plotLabels.yAxisLabel());
        x = (int) (margin / 4.0);
        y = margin + (parentPlotterModel.drawingHeight + fontMetrics.stringWidth(plotLabels.yAxisLabel())) / 2;

        AffineTransform originalTransform = g2.getTransform();
        g2.rotate(-Math.PI / 2, x, y);
        g2.drawString(plotLabels.yAxisLabel(), x, y);
        g2.setTransform(originalTransform);


    }

    public void onMouseMovement(Point mousePosition) {
        plotPOI.setMouseX(mousePosition.x);
        plotPOI.setMouseY(mousePosition.y);
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

    @Override
    public PlotPointOfInterest getPlotPOI() {
        return plotPOI;
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
        for (int index = 0; index < vector.length; index++) {
            vectorPx[index] = margin + (int) Math.round(parentPlotterModel.drawingWidth * (vector[index] - parentPlotterModel.coordinateSystem.xRange[0]) / (parentPlotterModel.coordinateSystem.xRange[1] - parentPlotterModel.coordinateSystem.xRange[0]));
        }
        return vectorPx;
    }

    public int[] valuesVectorToPixels(double[] vector) {
        int[] vectorPx = new int[vector.length];
        for (int index = 0; index < vector.length; index++) {
            vectorPx[index] = margin + parentPlotterModel.drawingHeight - (int) Math.round(parentPlotterModel.drawingHeight * (vector[index] - parentPlotterModel.coordinateSystem.yRange[0]) / (parentPlotterModel.coordinateSystem.yRange[1] - parentPlotterModel.coordinateSystem.yRange[0]));
        }
        return vectorPx;
    }


    public void addData(Simulation sim) throws CloneNotSupportedException {
        double[] domain = sim.getSimulationDomain();
        double[] values = sim.getOverallCostValues();
        functionsDomains.add(domain);
        functionsValues.add(values);
        adjustCameraToPlot();
    }


}
