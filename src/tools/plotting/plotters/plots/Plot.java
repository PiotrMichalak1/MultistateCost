package tools.plotting.plotters.plots;

import settings.GraphicSettings;
import tools.FunctionTools;
import tools.Mathematics;
import tools.plotting.DoublePoint;
import tools.plotting.PlotPointOfInterest;
import tools.plotting.plotters.MainPlotter;

import java.awt.*;
import java.util.ArrayList;

public class Plot {

    public final MainPlotter plotter;
    public ArrayList<double[]> functionsDomains = new ArrayList<>();
    public ArrayList<double[]> functionsCodomains = new ArrayList<>();

    private final int margin;
    public final PlotPointOfInterest plotPOI;


    public Plot(MainPlotter mainPlotter) {
        this.plotter = mainPlotter;
        this.margin = mainPlotter.coordinateSystem.getMargin();
        this.plotPOI = new PlotPointOfInterest();
    }

    public void onMouseMovement(Point mousePosition) {
        plotPOI.setMouseX(mousePosition.x);
        plotPOI.setMouseY(mousePosition.y);
    }

    public void addFunctionData(double[] domain, double[] codomain) {
        functionsDomains.add(domain);
        functionsCodomains.add(codomain);
        adjustCameraToPlot(domain, codomain);
    }

    public void clearFunctionData() {
        functionsDomains.removeAll(functionsDomains);
        functionsCodomains.removeAll(functionsCodomains);
    }

    public void drawFunction(Graphics2D g2, double[] functionDomain, double[] functionCodomain) {
        if (functionDomain != null && functionCodomain != null) {
            if (functionDomain.length == functionCodomain.length) {
                Point pointPx;
                DoublePoint point;
                if (functionDomain.length == 1) {
                    point = new DoublePoint(functionDomain[0], functionCodomain[0]);
                    if (isPointInVisibleRange(point)) {
                        pointPx = pointToPixels(point);
                        plotPOI.updatePOIValues(pointPx, point);
                        g2.setColor(GraphicSettings.MAIN_GRAPH_COLOR);
                        g2.fillOval((int) pointPx.getX() - GraphicSettings.PLOT_POINT_THICKNESS / 2,
                                (int) pointPx.getY() - GraphicSettings.PLOT_POINT_THICKNESS / 2,
                                GraphicSettings.PLOT_POINT_THICKNESS,
                                GraphicSettings.PLOT_POINT_THICKNESS);
                    }
                } else {
                    g2.setColor(GraphicSettings.MAIN_GRAPH_COLOR);

                    DoublePoint prevPoint;
                    DoublePoint nextPoint;
                    Point prevPointPx;
                    Point nextPointPx;

                    prevPoint = new DoublePoint(functionDomain[0], functionCodomain[0]);
                    nextPoint = new DoublePoint(functionDomain[1], functionCodomain[1]);

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
                        prevPoint = new DoublePoint(functionDomain[i], functionCodomain[i]);
                        nextPoint = new DoublePoint(functionDomain[i + 1], functionCodomain[i + 1]);

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
    }

    public void drawAllFunctions(Graphics2D g2) {
        plotPOI.resetDistanceAndVisibility();
        for (int i = 0; i < functionsDomains.size(); i++) {
            drawFunction(g2, functionsDomains.get(i), functionsCodomains.get(i));
        }

    }

    public void adjustCameraToPlot(double[] functionDomain, double[] functionCodomain) {
        if (functionDomain.length > 1) {

            double smallestValue = FunctionTools.getSmallestValueOfFunction(functionCodomain);
            double biggestValue = FunctionTools.getBiggestValueOfFunction(functionCodomain);

            double rangeOfValues = biggestValue - smallestValue;

            plotter.coordinateSystem.xRange[0] = functionDomain[0];
            plotter.coordinateSystem.yRange[0] = smallestValue;
            plotter.coordinateSystem.updateRanges(plotter.width, plotter.height);

            double visibleXWidth = plotter.coordinateSystem.xRange[1] - plotter.coordinateSystem.xRange[0];
            double domainWidth = functionDomain[functionCodomain.length - 1] - functionDomain[0];

            if (visibleXWidth < domainWidth) {

                while (visibleXWidth < domainWidth) {

                    plotter.coordinateSystem.numOfMouseScrolls -= 1;
                    plotter.coordinateSystem.updateGridSpacing();
                    plotter.coordinateSystem.updateScaleOnMouseScroll();
                    plotter.coordinateSystem.updateRanges(plotter.width, plotter.height);
                    visibleXWidth = plotter.coordinateSystem.xRange[1] - plotter.coordinateSystem.xRange[0];

                }

                plotter.coordinateSystem.xRange[0] = functionDomain[0] - (visibleXWidth - domainWidth) / 2;
                plotter.coordinateSystem.updateRanges(plotter.width, plotter.height);

            } else if (visibleXWidth > domainWidth) {
                while (visibleXWidth > domainWidth) {

                    plotter.coordinateSystem.numOfMouseScrolls += 1;
                    plotter.coordinateSystem.updateGridSpacing();
                    plotter.coordinateSystem.updateScaleOnMouseScroll();
                    plotter.coordinateSystem.updateRanges(plotter.width, plotter.height);
                    visibleXWidth = plotter.coordinateSystem.xRange[1] - plotter.coordinateSystem.xRange[0];

                }
                plotter.coordinateSystem.numOfMouseScrolls -= 1;
                plotter.coordinateSystem.updateGridSpacing();
                plotter.coordinateSystem.updateScaleOnMouseScroll();
                plotter.coordinateSystem.updateRanges(plotter.width, plotter.height);
                visibleXWidth = plotter.coordinateSystem.xRange[1] - plotter.coordinateSystem.xRange[0];
                plotter.coordinateSystem.xRange[0] = functionDomain[0] - (visibleXWidth - domainWidth) / 2;
                plotter.coordinateSystem.updateRanges(plotter.width, plotter.height);

            }

            if (rangeOfValues != 0) {
                double exactValue = (rangeOfValues * 5.0 * plotter.coordinateSystem.smallGridSpacing) / (plotter.coordinateSystem.scaleMultiplier * plotter.height);
                plotter.coordinateSystem.scaleUnitY = Mathematics.roundUpToTheNearestPowerOf(exactValue, 2);
                plotter.coordinateSystem.updateRanges(plotter.width, plotter.height);

                double visibleYHeight = plotter.coordinateSystem.yRange[1] - plotter.coordinateSystem.yRange[0];
                plotter.coordinateSystem.yRange[0] = smallestValue - (visibleYHeight - rangeOfValues) / 2;
                plotter.coordinateSystem.updateRanges(plotter.width, plotter.height);
            }


        }


    }

    private boolean isPointInVisibleRange(DoublePoint point) {
        boolean isInXRange = (plotter.coordinateSystem.xRange[0] < point.getX() &&
                plotter.coordinateSystem.xRange[1] > point.getX());
        boolean isInYRange = (plotter.coordinateSystem.yRange[0] < point.getY() &&
                plotter.coordinateSystem.yRange[1] > point.getY());

        return isInXRange && isInYRange;
    }


    private Point pointToPixels(DoublePoint point) {
        int xPx = margin + (int) Math.round(plotter.width * (point.getX() - plotter.coordinateSystem.xRange[0]) / (plotter.coordinateSystem.xRange[1] - plotter.coordinateSystem.xRange[0]));
        int yPx = margin + plotter.height - (int) Math.round(plotter.height * (point.getY() - plotter.coordinateSystem.yRange[0]) / (plotter.coordinateSystem.yRange[1] - plotter.coordinateSystem.yRange[0]));
        return new Point(xPx, yPx);
    }

    private DoublePoint pixelsToPoint(Point mousePosition) {
        double mouseX = mousePosition.getX() - margin;
        double mouseY = margin + plotter.height - mousePosition.getY();

        double RangeX = plotter.coordinateSystem.xRange[1] - plotter.coordinateSystem.xRange[0];
        double RangeY = plotter.coordinateSystem.yRange[1] - plotter.coordinateSystem.yRange[0];

        double argument = plotter.coordinateSystem.xRange[0] + (mouseX / plotter.width) * RangeX;
        double value = plotter.coordinateSystem.yRange[0] + (mouseY / plotter.height) * RangeY;

        return new DoublePoint(argument, value);

    }


}
