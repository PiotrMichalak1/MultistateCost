package tools.plotting.plottingmodels.coordsys;

import settings.GraphicSettings;
import settings.InitialSettings;
import tools.functions.Mathematics;

import java.awt.*;

public class CoordinateSystem {

    protected final int margin;

    public double scaleMultiplier;
    public int numOfMouseScrolls;
    int numOfGridZoomInScaling;

    public int smallGridSpacing;
    int bigGridSpacing;
    protected double scaleUnitX;
    public double scaleUnitY;
    public final double[] xRange = new double[2];
    public final double[] yRange= new double[2];

    public CoordinateSystem() {
        this.margin = GraphicSettings.DEFAULT_PLOT_MARGIN;

        this.scaleUnitX = GraphicSettings.DEFAULT_SCALE_UNIT_X;
        this.scaleUnitY = GraphicSettings.DEFAULT_SCALE_UNIT_Y;
        this.scaleMultiplier = GraphicSettings.DEFAULT_SCALE_MULTIPLIER;
        this.numOfMouseScrolls = 0;


        this.smallGridSpacing = InitialSettings.DEFAULT_SMALL_GRID_SPACING;
        this.bigGridSpacing = 5 * this.smallGridSpacing;

    }

    public void drawMargins(Graphics2D g2, int width, int height) {
        g2.setColor(GraphicSettings.MARGIN_COLOR);
        g2.fillRect(0, 0, 2 * margin + width, margin);
        g2.fillRect(0, 0, margin, 2 * margin + height);
        g2.fillRect(0, margin + height, 2 * margin + width, margin);
        g2.fillRect(margin + width, 0, margin, 2 * margin + height);
    }

    public void drawAxes(Graphics2D g2, int width, int height) {
        g2.setColor(Color.black);

        g2.drawLine(margin, margin, margin, height + margin);//OY
        int[] YArrowXVertices = {margin - 5, margin, margin + 5};
        int[] YArrowYVertices = {margin + 6, margin - 4, margin + 6};
        g2.fillPolygon(YArrowXVertices, YArrowYVertices, 3);

        g2.drawLine(margin, height + margin, width + margin, height + margin);//OX
        int[] XArrowXVertices = {width + margin - 4, width + margin + 6, width + margin - 4};
        int[] XArrowYVertices = {height + margin - 5, height + margin, height + margin + 5};
        g2.fillPolygon(XArrowXVertices, XArrowYVertices, 3);
    }

    public void drawGrid(Graphics2D g2, int width, int height) {
        drawSmallGrid(g2, width, height);
        drawBigGrid(g2, width, height);
    }

    public void drawLabels(Graphics2D g2, int width, int height) {
        //xLabels
        g2.setColor(Color.BLACK);
        FontMetrics fontMetrics = g2.getFontMetrics(g2.getFont());
        double labelNum = Mathematics.roundUpToTheNearestMultiple(xRange[0], scaleUnitX * scaleMultiplier);
        int xLabelXCoord = (int) ((labelNum - xRange[0]) / (xRange[1] - xRange[0]) * width);
        String label;
        String format = "%." + decimalPlacesNeededInLabels(numOfGridZoomInScaling, 'x') + "f";
        while (xLabelXCoord <= width) {

            if (numOfMouseScrolls < InitialSettings.DEFAULT_SMALL_GRID_SPACING) {
                label = String.valueOf((int) labelNum);
            } else {
                label = String.format(format, labelNum);
            }


            g2.drawString(label, xLabelXCoord + margin - fontMetrics.stringWidth(label) / 2, height + margin + 20);
            xLabelXCoord += bigGridSpacing;
            labelNum += scaleUnitX * scaleMultiplier;
        }
        //yLabels
        labelNum = Mathematics.roundUpToTheNearestMultiple(yRange[0], scaleUnitY * scaleMultiplier);
        int yLabelYCoord = (int) ((labelNum - yRange[0]) / (yRange[1] - yRange[0]) * height);
        while (yLabelYCoord <= height) {

            format = "%." + decimalPlacesNeededInLabels(numOfGridZoomInScaling, 'y') + "f";
            label = String.format(format, labelNum);

            g2.drawString(label, margin - 5 - fontMetrics.stringWidth(label), margin + height - yLabelYCoord + (int) fontMetrics.getStringBounds(label, g2).getHeight() / 2 - 2);
            yLabelYCoord += bigGridSpacing;
            labelNum += scaleUnitY * scaleMultiplier;
        }


    }

    void drawBigGrid(Graphics2D g2, int width, int height) {
        g2.setColor(new Color(181, 177, 177));
        //vertical big grid
        double bigGridX = Mathematics.roundUpToTheNearestMultiple(xRange[0], scaleUnitX * scaleMultiplier);
        int bigGridXPx = (int) ((bigGridX - xRange[0]) / (xRange[1] - xRange[0]) * width);
        while (bigGridXPx <= width) {


            g2.drawLine(bigGridXPx + margin, height + margin, bigGridXPx + margin, margin);
            bigGridXPx += bigGridSpacing;
        }

        //horizontal Big Grid
        double bigGridY = Mathematics.roundUpToTheNearestMultiple(yRange[0], scaleUnitY * scaleMultiplier);
        int bigGridYPx = (int) ((bigGridY - yRange[0]) / (yRange[1] - yRange[0]) * height);
        while (bigGridYPx <= height) {
            g2.drawLine(margin, margin + height - bigGridYPx, margin + width, margin + height - bigGridYPx);
            bigGridYPx += bigGridSpacing;
        }
    }

    void drawSmallGrid(Graphics2D g2, int width, int height) {
        g2.setColor(new Color(216, 209, 209));

        double smallGridNum = Mathematics.roundUpToTheNearestMultiple(xRange[0], scaleUnitX * scaleMultiplier / 5);
        int smallGridCoord = (int) ((smallGridNum - xRange[0]) / (xRange[1] - xRange[0]) * width);

        while (smallGridCoord <= width) {

            g2.drawLine(smallGridCoord + margin, height + margin, smallGridCoord + margin, margin);
            smallGridCoord += smallGridSpacing;

        }


        smallGridNum = Mathematics.roundUpToTheNearestMultiple(yRange[0], scaleUnitY * scaleMultiplier / 5);
        smallGridCoord = (int) ((smallGridNum - yRange[0]) / (yRange[1] - yRange[0]) * height);

        while (smallGridCoord <= height) {

            g2.drawLine(margin, margin + height - smallGridCoord, margin + width, margin + height - smallGridCoord);
            smallGridCoord += smallGridSpacing;

        }

    }


    public void onMouseScroll(Point mousePosition, int wheelRotation, int width, int height) {
        numOfMouseScrolls -= wheelRotation;
        updateScaleOnMouseScroll();
        updateGridSpacing();
        scaleRanges(mousePosition, width, height);
    }

    public void updateRanges(int width, int height) {
        xRange[1] = xRange[0] + (width) / (5.0 * smallGridSpacing) * scaleMultiplier * scaleUnitX;
        yRange[1] = yRange[0] + (height) / (5.0 * smallGridSpacing) * scaleMultiplier * scaleUnitY;
    }

    public void updateScaleOnMouseScroll() {
        if (numOfMouseScrolls < 0) {
            int numOfGridZoomOutScaling = -numOfMouseScrolls / (InitialSettings.DEFAULT_SMALL_GRID_SPACING / 2);
            int moduloMultiplier = 0;
            switch (numOfGridZoomOutScaling % 3) {
                case 0 -> moduloMultiplier = 1;
                case 1 -> moduloMultiplier = 2;
                case 2 -> moduloMultiplier = 5;
            }
            int power = numOfGridZoomOutScaling / 3;
            scaleMultiplier = Math.pow(10, power) * moduloMultiplier;
        } else {
            numOfGridZoomInScaling = numOfMouseScrolls / (InitialSettings.DEFAULT_SMALL_GRID_SPACING);
            int moduloMultiplier = 0;
            switch (numOfGridZoomInScaling % 3) {
                case 0 -> moduloMultiplier = 1;
                case 1 -> moduloMultiplier = 5;
                case 2 -> moduloMultiplier = 2;
            }
            double power = Math.ceil(numOfGridZoomInScaling / 3.0);
            scaleMultiplier = Math.pow(0.1, power) * moduloMultiplier;
        }
    }

    public void updateGridSpacing() {
        if (numOfMouseScrolls < 0) {
            smallGridSpacing = InitialSettings.DEFAULT_SMALL_GRID_SPACING - Math.floorMod(-numOfMouseScrolls, InitialSettings.DEFAULT_SMALL_GRID_SPACING / 2);

        } else {
            smallGridSpacing = InitialSettings.DEFAULT_SMALL_GRID_SPACING + Math.floorMod(numOfMouseScrolls, InitialSettings.DEFAULT_SMALL_GRID_SPACING);
        }
        bigGridSpacing = 5 * smallGridSpacing;
    }

    //
    protected void scaleRanges(Point mousePosition, int width, int height) {
        double mouseX = mousePosition.getX() - margin;
        double mouseY = margin + height - mousePosition.getY();

        double valueOfXPixel = (scaleUnitX * scaleMultiplier) / bigGridSpacing;
        double scalingPivotX = xRange[0] + (xRange[1] - xRange[0]) * (mouseX / width);
        xRange[0] = scalingPivotX - mouseX * valueOfXPixel;
        xRange[1] = (width - mouseX) * valueOfXPixel;

        double valueOfYPixel = (scaleUnitY * scaleMultiplier) / bigGridSpacing;
        double scalingPivotY = yRange[0] + (yRange[1] - yRange[0]) * (mouseY / height);
        yRange[0] = scalingPivotY - mouseY * valueOfYPixel;
        yRange[1] = scalingPivotY + (height - mouseY) * valueOfYPixel;
    }

    public void dragPlot(int dx, int dy) {
        double translationX = -dx * (scaleMultiplier / bigGridSpacing) * scaleUnitX;
        double translationY = dy * (scaleMultiplier / bigGridSpacing) * scaleUnitY;

        xRange[0] = xRange[0] + translationX;
        xRange[1] = xRange[1] + translationX;

        yRange[0] = yRange[0] + translationY;
        yRange[1] = yRange[1] + translationY;

    }

    int decimalPlacesNeededInLabels(int numOfGridZoomInScaling, char axis) {
        if (Character.toLowerCase(axis) == 'x') {
            return (int) Math.min(Math.ceil(numOfGridZoomInScaling / 3.0), 4);
        } else {
            return (int) Math.min(Math.ceil(numOfGridZoomInScaling / 3.0) + (int) Math.round(Math.sqrt(1 / scaleUnitY)) - 1, 4);
        }

    }

    public int getMargin() {
        return margin;
    }


}
