package tools.plotting.plottingmodels.plots;

import simulation.LayeredCostValues;
import simulation.Simulation;
import tools.FunctionTools;
import tools.functions.Mathematics;
import tools.functions.MatrixOperations;
import tools.plotting.plottingmodels.PlotterModel;
import tools.plotting.plottingmodels.plots.graphics.PlotColors;

import java.awt.*;
import java.util.ArrayList;

public class LayeredCostPlot extends Plot implements IPlot{
    private ArrayList<double[]> segmentData = new ArrayList<>();//values of all costs without summing

    private ArrayList<String> legendStrings = new ArrayList<>();

    public LayeredCostPlot(PlotterModel layeredCostPlotterModel) {
        super(layeredCostPlotterModel);
    }

    //adds operational, repair, and inspection cost data to functionDomains and functionValues in plot
    public void addData(Simulation sim) throws CloneNotSupportedException {
        double[] domain = sim.getSimulationDomain();
        LayeredCostValues lcv = (LayeredCostValues) sim.getLayeredCostValues().clone();

        String label;
        double[] values;
        for (int i = 0; i < 3; i++) {
            switch (i) {
                case 0 -> {
                    values = lcv.getOperationalCost();
                    label = "Operational";
                }
                case 1 -> {
                    values = lcv.getRepairCost();
                    label = "Repair";
                }
                case 2 -> {
                    values = lcv.getInspectionsCost();
                    label = "Inspections";
                }
                default -> throw new RuntimeException("Unrecognised type of cost");
            }
            functionsDomains.add(domain);
            functionsValues.add(values);
            legendStrings.add(label);
        }

        updateFunctionValuesToStacked();

    }


    public void adjustCameraToPlot(double[] functionDomain, double[] functionCodomain) {
        if (functionDomain.length > 1) {

            double smallestValue = FunctionTools.getSmallestValueOfFunction(functionCodomain);
            double biggestValue = FunctionTools.getBiggestValueOfFunction(functionCodomain);

            double rangeOfValues = biggestValue - smallestValue;

            parentPlotterModel.coordinateSystem.xRange[0] = functionDomain[0];
            parentPlotterModel.coordinateSystem.yRange[0] = smallestValue;
            parentPlotterModel.coordinateSystem.updateRanges(parentPlotterModel.drawingWidth, parentPlotterModel.drawingHeight);

            double visibleXWidth = parentPlotterModel.coordinateSystem.xRange[1] - parentPlotterModel.coordinateSystem.xRange[0];
            double domainWidth = functionDomain[functionCodomain.length - 1] - functionDomain[0];

            //adjusting xRange
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
                double exactValue = (biggestValue * 5.0 * parentPlotterModel.coordinateSystem.smallGridSpacing) / (parentPlotterModel.coordinateSystem.scaleMultiplier * parentPlotterModel.drawingHeight);
                parentPlotterModel.coordinateSystem.scaleUnitY = Mathematics.roundUpToTheNearestPowerOf(exactValue, 2);
                parentPlotterModel.coordinateSystem.updateRanges(parentPlotterModel.drawingWidth, parentPlotterModel.drawingHeight);


                parentPlotterModel.coordinateSystem.yRange[0] = 0;
                parentPlotterModel.coordinateSystem.updateRanges(parentPlotterModel.drawingWidth, parentPlotterModel.drawingHeight);
            }


        }


    }

    //change function values to appropriate sums
    public void updateFunctionValuesToStacked() {
        for (int stack = 1; stack < functionsValues.size(); stack++) {
            for (int index = 0; index < functionsValues.get(stack).length; index++) {
                functionsValues.get(stack)[index] += functionsValues.get(stack - 1)[index];
            }

        }
        if (functionsDomains.size() > 0) {
            adjustCameraToPlot(functionsDomains.get(functionsDomains.size() - 1),
                    functionsValues.get(functionsDomains.size() - 1));
        }

    }

    @Override
    public void draw(Graphics2D g2) {
        plotPOI.resetDistanceAndVisibility();

        if (functionsDomains.size() > 0) {
            fillLayers(g2);
            PlotColors.resetColor();
            for (int i = functionsDomains.size() - 1; i >= 0; i--) {
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

    }

}
