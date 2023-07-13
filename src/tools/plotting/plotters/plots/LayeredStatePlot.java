package tools.plotting.plotters.plots;

import simulation.LayeredCostValues;
import simulation.Simulation;
import tools.Functions.Mathematics;
import tools.plotting.plotters.MainPlotter;

public class LayeredStatePlot extends Plot {

    public LayeredStatePlot(MainPlotter layeredStatePlotter) {
        super(layeredStatePlotter);
    }

    @Override
    public void addFunctionData(double[] domain, double[] codomain) {
        functionsDomains.add(domain);
        functionsValues.add(codomain);
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
    public void addLayeredFunctionData(Simulation sim) throws CloneNotSupportedException {
        double[] domain = sim.getSimulationDomain();
        LayeredCostValues lcv = (LayeredCostValues) sim.getLayeredCostValues().clone();

        double[] values;
        for (int i = 0; i < 3; i++) {
            switch (i) {
                case 0 -> values = lcv.getOperationalCost();
                case 1 -> values = lcv.getRepairCost();
                case 2 -> values = lcv.getInspectionsCost();
                default -> throw new RuntimeException("Unrecognised state");
            }
            functionsDomains.add(domain);
            functionsValues.add(values);
        }


    }
}
