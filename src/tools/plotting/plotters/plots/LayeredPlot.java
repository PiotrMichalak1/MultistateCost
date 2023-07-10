package tools.plotting.plotters.plots;

import tools.FunctionTools;
import tools.Mathematics;
import tools.plotting.plotters.MainPlotter;

import java.awt.*;
import java.util.ArrayList;

public class LayeredPlot extends Plot {

    private ArrayList<String> legend = new ArrayList<>();

    public LayeredPlot(MainPlotter layeredPlotter) {
        super(layeredPlotter);
    }

    public void addFunctionData(double[] domain, double[] codomain, String label) {
        functionsDomains.add(domain);
        functionsCodomains.add(codomain);
        legend.add(label);
        adjustCameraToPlot(domain, codomain);
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

                double visibleYHeight = plotter.coordinateSystem.yRange[1] - plotter.coordinateSystem.yRange[0];
                plotter.coordinateSystem.yRange[0] = 0;
                plotter.coordinateSystem.updateRanges(plotter.width, plotter.height);
            }


        }


    }


}
