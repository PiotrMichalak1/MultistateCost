package tools.plotting.plottingmodels.plots;

import settings.Parameters;
import simulation.Simulation;
import tools.plotting.PlotPointOfInterest;
import tools.plotting.plottingmodels.PlotterModel;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class StateStructPlot implements IPlot {
    public final static Color BAR_PLOT_COLOR = new Color(0, 114, 189);
    public final PlotterModel parentPlotterModel;

    LinkedHashMap<String, Double> bars = new LinkedHashMap<>();

    public StateStructPlot(PlotterModel parentPlotterModel) {
        this.parentPlotterModel = parentPlotterModel;
    }

    public static final double BAR_THICKNESS = 0.9; //0 means bar having width = 0, 1 means no free distance between bars

    @Override
    public void draw(Graphics2D g2) {

        int barNum = 1;
        for (Map.Entry<String, Double> bar : bars.entrySet()) {
            drawBar(g2, bar.getValue(), barNum, bar.getKey());
            barNum++;
        }
    }

    private void drawBar(Graphics2D g2, double value, int barNum, String label) {
        g2.setColor(BAR_PLOT_COLOR);
        int margin = parentPlotterModel.getMargin();
        int drawingWidth = parentPlotterModel.drawingWidth;
        int drawingHeight = parentPlotterModel.drawingHeight;
        int numOfBars = bars.size();

        double centerOfBase = margin + (2 * barNum - 1) * (drawingWidth / (2.0 * numOfBars));

        int[] xVertices = getXVertices(drawingWidth, numOfBars, centerOfBase);

        int[] yVertices = getYVertices(value, margin, drawingHeight);

        g2.fillPolygon(xVertices, yVertices, 4);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(xVertices,yVertices,4);


    }

    //divides plotting width into 6 parts. Center of every bar is in (2n-1) division point. XVertices are moved from center
    // to the left and right by 1/6*thickness amount.

    private static int[] getXVertices(int drawingWidth, int numOfBars, double centerOfBase) {
        int[] xVertices = new int[4];
        double leftXVertices = centerOfBase - (drawingWidth / (2.0 * numOfBars)) * BAR_THICKNESS;
        double rightXVertices = centerOfBase + (drawingWidth / (2.0 * numOfBars)) * BAR_THICKNESS;
        xVertices[0] = (int) leftXVertices;
        xVertices[1] = (int) leftXVertices;
        xVertices[2] = (int) rightXVertices;
        xVertices[3] = (int) rightXVertices;
        return xVertices;
    }

    private int[] getYVertices(double value, int margin, int drawingHeight) {
        int[] yVertices = new int[4];

        yVertices[0] = margin + drawingHeight - (int) Math.round(drawingHeight * (0.0 - parentPlotterModel.coordinateSystem.yRange[0]) / (parentPlotterModel.coordinateSystem.yRange[1] - parentPlotterModel.coordinateSystem.yRange[0]));
        yVertices[1] = margin + drawingHeight - (int) Math.round(drawingHeight * (value - parentPlotterModel.coordinateSystem.yRange[0]) / (parentPlotterModel.coordinateSystem.yRange[1] - parentPlotterModel.coordinateSystem.yRange[0]));
        yVertices[2] = margin + drawingHeight - (int) Math.round(drawingHeight * (value - parentPlotterModel.coordinateSystem.yRange[0]) / (parentPlotterModel.coordinateSystem.yRange[1] - parentPlotterModel.coordinateSystem.yRange[0]));
        yVertices[3] = margin + drawingHeight - (int) Math.round(drawingHeight * (0.0 - parentPlotterModel.coordinateSystem.yRange[0]) / (parentPlotterModel.coordinateSystem.yRange[1] - parentPlotterModel.coordinateSystem.yRange[0]));
        return yVertices;
    }

    public void drawLabels(Graphics2D g2) {

        int margin = parentPlotterModel.getMargin();
        int drawingWidth = parentPlotterModel.drawingWidth;
        FontMetrics fontMetrics = g2.getFontMetrics(g2.getFont());
        g2.setColor(Color.BLACK);

        int centerOfBase;
        int numOfBars = bars.size();
        int barNum = 1;
        for (String label: bars.keySet()) {
            centerOfBase = (int) (margin + (2 * barNum - 1) * (drawingWidth / (2.0 * numOfBars)));
            g2.drawString(label,
                    centerOfBase- fontMetrics.stringWidth(label) / 2,
                    parentPlotterModel.drawingHeight + margin + fontMetrics.getHeight());

            barNum++;
        }

    }

    @Override
    public PlotPointOfInterest getPlotPOI() {
        return null;
    }

    @Override
    public void onMouseMovement(Point mousePosition) {

    }

    @Override
    public void clearFunctionData() {
        bars.clear();
    }

    @Override
    public void addData(Simulation sim) throws CloneNotSupportedException {
        HashMap<String,double[]> simResult = sim.simulateForGivenInterval(Parameters.getInstance().getStructuralInterval());
        double[] stateStructure = simResult.get("state");

        for (int stateInd = 0; stateInd < stateStructure.length - 1; stateInd++) {
            String label = "State "+ (stateInd+1);
            bars.put(label, stateStructure[stateInd]);
        }
        bars.put("Repair", stateStructure[stateStructure.length-1]);

    }
}
