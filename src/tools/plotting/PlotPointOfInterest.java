package tools.plotting;

import settings.GraphicSettings;
import tools.Functions.Mathematics;

import java.awt.*;

public class PlotPointOfInterest {
    private int mouseX;
    private int mouseY;

    private int argumentPX;
    private int functionValuePX;
    private double argument;
    private double functionValue;

    private double mouseDistanceToClosest = GraphicSettings.POINT_OF_INTEREST_VISIBILITY_THRESHOLD +1;

    private boolean isVisible = false;



    public PlotPointOfInterest() {

    }

    public void drawPOI(Graphics2D g2) {
        if (mouseDistanceToClosest<GraphicSettings.POINT_OF_INTEREST_VISIBILITY_THRESHOLD) {
            setVisible(true);
            g2.setColor(GraphicSettings.POI_COLOR);
            g2.fillOval((int) argumentPX - GraphicSettings.PLOT_POINT_THICKNESS / 2,
                    (int) functionValuePX - GraphicSettings.PLOT_POINT_THICKNESS / 2,
                    GraphicSettings.PLOT_POINT_THICKNESS,
                    GraphicSettings.PLOT_POINT_THICKNESS);
        }
    }

    public void drawPOIdata(Graphics2D g2) {
        if (isVisible) {
            FontMetrics fontMetrics = g2.getFontMetrics(g2.getFont());

            String argumentString = String.valueOf(argument);
            String functionValueString = String.valueOf(functionValue);

            int stringWidth = Math.max(fontMetrics.stringWidth("x: " + argumentString),
                    fontMetrics.stringWidth(functionValueString));
            int stringHeight = (int) fontMetrics.getStringBounds(argumentString, g2).getHeight();


            g2.setColor(new Color(223, 213, 186));

            int topLeftCornerY = mouseY + GraphicSettings.POINT_OF_INTEREST_OFFSET.y;
            int width = GraphicSettings.POINT_OF_INTEREST_MARGIN_X * 2 + stringWidth;
            int height = GraphicSettings.POINT_OF_INTEREST_MARGIN_Y * 2 + stringHeight * 2 + GraphicSettings.POINT_OF_INTEREST_STRING_SPACING;

            g2.fillRect(mouseX, topLeftCornerY, width, height);
            g2.setColor(Color.black);
            g2.drawRect(mouseX, topLeftCornerY, width, height);
            g2.drawString("x: " + argumentString, mouseX + GraphicSettings.POINT_OF_INTEREST_MARGIN_X, topLeftCornerY + (int) (height - stringHeight) / 2);
            g2.drawString("y: " + functionValueString, mouseX + GraphicSettings.POINT_OF_INTEREST_MARGIN_X, topLeftCornerY + (int) (height + GraphicSettings.POINT_OF_INTEREST_STRING_SPACING) / 2 + stringHeight);

        }

    }

    public void updatePOIValues(Point pointPx, DoublePoint point) {
        double distance = Mathematics.cartesianDistance(pointPx,new Point(mouseX,mouseY));
        if (distance<mouseDistanceToClosest) {
            mouseDistanceToClosest = distance;
            argumentPX = pointPx.x;
            functionValuePX = pointPx.y;
            argument = point.getX();
            functionValue = point.getY();
        }
    }

    public void resetDistanceAndVisibility(){
        setMouseDistanceToClosest(GraphicSettings.POINT_OF_INTEREST_VISIBILITY_THRESHOLD +1);
        setVisible(false);
    }


    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

    public void setMouseDistanceToClosest(int mouseDistanceToClosest) {
        this.mouseDistanceToClosest = mouseDistanceToClosest;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
