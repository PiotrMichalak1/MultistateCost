package tools.interfaces;

import java.awt.*;

public interface IPlotter {
    void setPlot();
    void setCoordinateSystem();

    int getMargin();

    void drawPlot(Graphics2D g2);

    void onMouseScroll(Point point, int wheelRotation, int i, int i1);

    void dragPlot(int i, int i1);

    void onMouseMovement(Point point);

    void clearFunctionData();

    void setWidth(int max);

    void setHeight(int max);


}
