package tools.plotting.plottingmodels;

import tools.plotting.plottingmodels.plots.IPlot;
import tools.plotting.plottingmodels.plots.Plot;

import java.awt.*;

public interface IPlotterModel {
    void setPlot();
    void setCoordinateSystem();

    int getMargin();

    void drawPlot(Graphics2D g2);

    void onMouseScroll(Point mousePosition, int wheelRotation, int width, int height);

    void dragPlot(int dx, int dy);

    void onMouseMovement(Point point);

    void clearFunctionData();

    void setDrawingWidth(int max);

    void setDrawingHeight(int max);

    IPlot getPlot();
}
