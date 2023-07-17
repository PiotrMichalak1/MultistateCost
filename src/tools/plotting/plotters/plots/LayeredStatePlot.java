package tools.plotting.plotters.plots;

import simulation.LayeredStateValues;
import simulation.Simulation;
import tools.functions.Mathematics;
import tools.functions.MatrixOperations;
import tools.plotting.plotters.MainPlotter;
import tools.plotting.plotters.plots.graphics.PlotColors;

import java.awt.*;
import java.util.ArrayList;

public class LayeredStatePlot extends Plot {

    public LayeredStatePlot(MainPlotter layeredStatePlotter) {
        super(layeredStatePlotter);
    }

    @Override
    public void addLayeredFunctionData(Simulation sim) throws CloneNotSupportedException {
        double[] domain = sim.getSimulationDomain();
        LayeredStateValues lsv = (LayeredStateValues) sim.getLayeredStateValues().clone();
        ArrayList<double[]> statePercentages = lsv.getStatePercentages();
        double[] values;
        for (int i = 0; i < statePercentages.size(); i++) {
            values = statePercentages.get(i);
            functionsDomains.add(domain);
            functionsValues.add(values);
        }


    }

    @Override
    public void adjustCameraToPlot() {

        double[] functionDomain = functionsDomains.get(0);
        if (functionDomain.length > 1) {

            plotter.coordinateSystem.xRange[0] = functionDomain[0];
            plotter.coordinateSystem.updateRanges(plotter.width, plotter.height);

            double visibleXWidth = plotter.coordinateSystem.xRange[1] - plotter.coordinateSystem.xRange[0];

            double domainWidth = functionDomain[functionDomain.length - 1] - functionDomain[0];

            //adjusting xRange
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

            double biggestValue = 100.0;

            double exactValue = (biggestValue * 5.0 * plotter.coordinateSystem.smallGridSpacing) / (plotter.coordinateSystem.scaleMultiplier * plotter.height);
            plotter.coordinateSystem.scaleUnitY = Mathematics.roundUpToTheNearestPowerOf(exactValue, 2);
            plotter.coordinateSystem.updateRanges(plotter.width, plotter.height);


            plotter.coordinateSystem.yRange[0] = 0;
            plotter.coordinateSystem.updateRanges(plotter.width, plotter.height);


        }


    }

    @Override
    public void updateFunctionValuesToStacked() {
        for (int stack = 1; stack < functionsValues.size(); stack++) {
            for (int index = 0; index < functionsValues.get(stack).length; index++) {
                functionsValues.get(stack)[index] += functionsValues.get(stack - 1)[index];
            }

        }
        if (functionsDomains.size() > 0) {
            adjustCameraToPlot();
        }

    }

    @Override
    public void drawAllFunctions(Graphics2D g2) {
        plotPOI.resetDistanceAndVisibility();

        if (functionsDomains.size() > 0) {
            fillLayers(g2);
            for (int i = functionsDomains.size() - 1; i >= 0; i--) {
                PlotColors.setColorToBlack();
                drawFunction(g2, functionsDomains.get(i), functionsValues.get(i), true);
            }
        }

    }

    public void fillLayers(Graphics2D g2) {
        PlotColors.resetColor();
        double[] domain = functionsDomains.get(0);
        double[] domainRev = MatrixOperations.getReversedArray(domain); // required by the way polygon is drawn in fillPoly()
        double[] twoDomains = MatrixOperations.concatenateVectors(domain, domainRev);//num of points in polygon is twice the domain length
        int[] polyX = domainVectorToPixels(twoDomains); //X coordinates in all layers are the same

        int[] topPolyY = valuesVectorToPixels(functionsValues.get(0));
        int[] bottomPolyY;
        int[] polyY;

        for (int layer = 0; layer < functionsValues.size(); layer++) {
            g2.setColor(PlotColors.nextStateColor());
            if (layer == 0) {
                bottomPolyY = valuesVectorToPixels(new double[topPolyY.length]);//for first layer bottom ix X axis
                polyY = MatrixOperations.concatenateVectors(topPolyY, bottomPolyY);


                g2.fillPolygon(polyX, polyY, polyY.length);
            } else {
                bottomPolyY = MatrixOperations.getReversedArray(topPolyY);
                topPolyY = valuesVectorToPixels(functionsValues.get(layer));
                polyY = MatrixOperations.concatenateVectors(topPolyY, bottomPolyY);

                g2.fillPolygon(polyX, polyY, polyY.length);
            }
        }

    }
}
