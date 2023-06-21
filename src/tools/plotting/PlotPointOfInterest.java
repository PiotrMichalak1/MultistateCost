package tools.plotting;

import settings.GraphicSettings;
import tools.Mathematics;

import java.awt.*;
import java.util.HashMap;

public class PlotPointOfInterest {
    private int xPxCoordinate;
    private int yPxCoordinate;
    private double argument;
    private double functionValue;

    private boolean isVisible;

    public PlotPointOfInterest(Plotter.Plot plot) {

    }

    public void drawPointOfInterest(Graphics2D g2) {
        if (isVisible) {
            FontMetrics fontMetrics = g2.getFontMetrics(g2.getFont());

            String argumentString = String.valueOf(argument);
            String functionValueString = String.valueOf(functionValue);

            int stringWidth = Math.max(fontMetrics.stringWidth("x: " + argumentString),
                    fontMetrics.stringWidth(functionValueString));
            int stringHeight = (int) fontMetrics.getStringBounds(argumentString, g2).getHeight();


            g2.setColor(new Color(223, 213, 186));

            int topLeftCornerY = yPxCoordinate + GraphicSettings.POINT_OF_INTEREST_OFFSET.y;
            int width = GraphicSettings.POINT_OF_INTEREST_MARGIN_X * 2 + stringWidth;
            int height = GraphicSettings.POINT_OF_INTEREST_MARGIN_Y * 2 + stringHeight * 2 + GraphicSettings.POINT_OF_INTEREST_STRING_SPACING;

            g2.fillRect(xPxCoordinate, topLeftCornerY, width, height);
            g2.setColor(Color.black);
            g2.drawRect(xPxCoordinate, topLeftCornerY, width, height);
            g2.drawString("x: " + argumentString, xPxCoordinate + GraphicSettings.POINT_OF_INTEREST_MARGIN_X, topLeftCornerY + (int) (height - stringHeight) / 2);
            g2.drawString("y: " + functionValueString, xPxCoordinate + GraphicSettings.POINT_OF_INTEREST_MARGIN_X, topLeftCornerY + (int) (height + GraphicSettings.POINT_OF_INTEREST_STRING_SPACING) / 2 + stringHeight);

        }

    }

    public Point getTheNearestPlottingPoint(Point mousePosition, HashMap<Point, DoublePoint> POIs) {
        int mouseX = mousePosition.x;
        int mouseY = mousePosition.y;
        Point closestPoint = null;

        Integer initialDistance = null;
        int distance = 0;

        for (Point p : POIs.keySet()) {
            int currentDistance;
            if (initialDistance == null) {
                initialDistance = Mathematics.manhattan(mousePosition,p);
                distance = initialDistance;
                closestPoint = p;
            }
            currentDistance = Mathematics.manhattan(mousePosition,p);
            if (currentDistance < distance) {
                closestPoint = p;
            }
        }
        return closestPoint;
    }

    public void setxPxCoordinate(int xPxCoordinate) {
        this.xPxCoordinate = xPxCoordinate;
    }

    public void setyPxCoordinate(int yPxCoordinate) {
        this.yPxCoordinate = yPxCoordinate;
    }

    public void setArgument(double argument) {
        this.argument = argument;
    }

    public void setFunctionValue(double functionValue) {
        this.functionValue = functionValue;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
