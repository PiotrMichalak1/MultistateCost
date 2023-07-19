package tools.plotting.plottingmodels.coordsys;

import settings.InitialSettings;
import tools.functions.Mathematics;
import tools.plotting.plottingmodels.PlotterModel;

import java.awt.*;

public class PercentBarCoordinateSystem extends CoordinateSystem {
    private boolean areCoordinatesAdjusted = false;
    PlotterModel parentPlotterModel;
    public PercentBarCoordinateSystem(PlotterModel parentPlotterModel) {
        this.parentPlotterModel = parentPlotterModel;
    }

    //modifies initial number of mouse scrolls to fit [0,100] percent values on the screen
    public void adjustCoordinates(PlotterModel plotterModel) {
        int drawingWidth = plotterModel.drawingWidth;
        int drawingHeight = plotterModel.drawingHeight;

        double visibleYWidth = xRange[1] - xRange[0];

        if (visibleYWidth < 100) {

            while (visibleYWidth < 100) {

                numOfMouseScrolls -= 1;
                updateGridSpacing();
                updateScaleOnMouseScroll();
                updateRanges(drawingWidth,drawingHeight);
                visibleYWidth = yRange[1] - yRange[0];

            }

            updateRanges(drawingWidth,drawingHeight);

        }
    }

    @Override
    public void drawGrid(Graphics2D g2, int width, int height) {
        if (!areCoordinatesAdjusted) {
            adjustCoordinates(parentPlotterModel);
        }

        drawBigGrid(g2, width, height);
        areCoordinatesAdjusted = true;
    }

    @Override
    public void updateRanges(int width, int height) {
        xRange[1] = xRange[0] + (width) / (5.0 * smallGridSpacing) * scaleMultiplier * scaleUnitX;
        yRange[1] = yRange[0] + (height) / (5.0 * smallGridSpacing) * scaleMultiplier * scaleUnitY;
    }

    //drags plot if it will not cause yRange[0] go below 0. Dragging in x direction disabled
    @Override
    public void dragPlot(int dx, int dy) {
        double translationY = dy * (scaleMultiplier / bigGridSpacing) * scaleUnitY;
        if (yRange[0] + translationY >= 0.0) {
            yRange[0] = yRange[0] + translationY;
            yRange[1] = yRange[1] + translationY;
        }
    }

    //draw axes without x arrowhead
    @Override
    public void drawAxes(Graphics2D g2, int width, int height) {
        g2.setColor(Color.black);

        g2.drawLine(margin, margin, margin, height + margin);//OY
        int[] YArrowXVertices = {margin - 5, margin, margin + 5};
        int[] YArrowYVertices = {margin + 6, margin - 4, margin + 6};
        g2.fillPolygon(YArrowXVertices, YArrowYVertices, 3);

        g2.drawLine(margin, height + margin, width + margin, height + margin);//OX
    }
    //drawing only horizontal big grid
    @Override
    void drawBigGrid(Graphics2D g2, int width, int height) {
        g2.setColor(new Color(181, 177, 177));

        double bigGridY = Mathematics.roundUpToTheNearestMultiple(yRange[0], scaleUnitY * scaleMultiplier);
        int bigGridYPx = (int) ((bigGridY - yRange[0]) / (yRange[1] - yRange[0]) * height);
        while (bigGridYPx <= height) {
            g2.drawLine(margin, margin + height - bigGridYPx, margin + width, margin + height - bigGridYPx);
            bigGridYPx += bigGridSpacing;
        }
    }
    protected void scaleRanges(Point mousePosition, int width, int height) {
        double mouseY = margin + height - mousePosition.getY();


        double valueOfYPixel = (scaleUnitY * scaleMultiplier) / bigGridSpacing;
        double scalingPivotY = yRange[0] + (yRange[1] - yRange[0]) * (mouseY / height);

        yRange[1] = scalingPivotY + (height - mouseY) * valueOfYPixel;
    }

    //draws labels on y axis
    public void drawLabels(Graphics2D g2, int width, int height) {

        FontMetrics fontMetrics = g2.getFontMetrics(g2.getFont());

        //yLabels
        double labelNum = Mathematics.roundUpToTheNearestMultiple(yRange[0], scaleUnitY * scaleMultiplier);
        int yLabelYCoord = (int) ((labelNum - yRange[0]) / (yRange[1] - yRange[0]) * height);
        while (yLabelYCoord <= height) {

            String format = "%." + decimalPlacesNeededInLabels(numOfGridZoomInScaling, 'y') + "f";
            String label = String.format(format, labelNum);

            g2.drawString(label, margin - 5 - fontMetrics.stringWidth(label), margin + height - yLabelYCoord + (int) fontMetrics.getStringBounds(label, g2).getHeight() / 2 - 2);
            yLabelYCoord += bigGridSpacing;
            labelNum += scaleUnitY * scaleMultiplier;
        }


    }

}
