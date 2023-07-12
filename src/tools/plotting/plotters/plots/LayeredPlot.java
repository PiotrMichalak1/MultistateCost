package tools.plotting.plotters.plots;

import tools.FunctionTools;
import tools.Functions.Mathematics;
import tools.Functions.MatrixOperations;
import tools.plotting.plotters.MainPlotter;
import tools.plotting.plotters.plots.graphics.PlotColors;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class LayeredPlot extends Plot {
    private ArrayList<double[]> segmentData = new ArrayList<>();//values of all costs without summing

    private ArrayList<String> legendStrings = new ArrayList<>();

    public LayeredPlot(MainPlotter layeredPlotter) {
        super(layeredPlotter);
    }

    public void addLayeredFunctionData(double[] domain, double[] codomain, String label) {
        functionsDomains.add(domain);
        functionsValues.add(codomain);
        legendStrings.add(label);
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

            if (rangeOfValues != 0) {
                double exactValue = (biggestValue * 5.0 * plotter.coordinateSystem.smallGridSpacing) / (plotter.coordinateSystem.scaleMultiplier * plotter.height);
                plotter.coordinateSystem.scaleUnitY = Mathematics.roundUpToTheNearestPowerOf(exactValue, 2);
                plotter.coordinateSystem.updateRanges(plotter.width, plotter.height);


                plotter.coordinateSystem.yRange[0] = 0;
                plotter.coordinateSystem.updateRanges(plotter.width, plotter.height);
            }


        }


    }

    //change function values to appropriate sums
    public void updateFunctionValuesToStacked() {
        for (int stack = 1; stack < functionsValues.size(); stack++) {
            for (int index = 0; index < functionsValues.get(stack).length; index++) {
                double stackedValue = 0.0;
                stackedValue = functionsValues.get(stack)[index];

                stackedValue += functionsValues.get(stack - 1)[index];

                functionsValues.get(stack)[index] = stackedValue;
            }

        }
        if (functionsDomains.size() > 0) {
            adjustCameraToPlot(functionsDomains.get(functionsDomains.size() - 1),
                    functionsValues.get(functionsDomains.size() - 1));
        }

    }

    public void fillLayers(Graphics2D g2) {
        PlotColors.resetColor();
        long start = System.currentTimeMillis();

        double[] domain = functionsDomains.get(0);
        double[] domainRev = MatrixOperations.getReversedArray(domain); // required by the way polygon is drawn in fillPoly()
        double[] twoDomains =MatrixOperations.concatenateVectors(domain,domainRev);//num of points in polygon is twice the domain length
        int[] polyX = domainVectorToPixels(twoDomains); //X coordinates in all layers are the same

        int[] topPolyY = valuesVectorToPixels(functionsValues.get(0));
        int[] bottomPolyY;
        int[] polyY;

        for (int layer = 0; layer < functionsValues.size(); layer++) {
            g2.setColor(PlotColors.nextLightColor());
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
        long end = System.currentTimeMillis();
        System.out.println("Time to fill layers : " + (end-start));
    }

    @Override
    public void drawAllFunctions(Graphics2D g2) {
        plotPOI.resetDistanceAndVisibility();

        if (functionsDomains.size() > 0) {
            fillLayers(g2);
            PlotColors.resetColor();
            for (int i = functionsDomains.size() - 1; i >= 0; i--) {
                drawFunction(g2, functionsDomains.get(i), functionsValues.get(i),true);
            }
        }

    }

}
