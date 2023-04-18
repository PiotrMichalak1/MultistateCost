package tools.plotting;

import settings.InitialSettings;

import java.awt.*;

public class Plotter {

    private final int margin;
    private int[] xAxisRange;
    private int[] yAxisRange;

    private final Point originOffset;
    private double scaleUnitX;
    private double scaleUnitY;

    private int numOfGridZoomInScaling;

    private double scaleMultiplier;
    private int numOfMouseScrolls = 0;

    private int smallGridSpacing;


    public Plotter() {
        this.margin = InitialSettings.DEFAULT_PLOT_MARGIN;
        this.originOffset = new Point(0, 0);
        this.scaleUnitX = InitialSettings.DEFAULT_SCALE_UNIT_X;
        this.scaleUnitY = InitialSettings.DEFAULT_SCALE_UNIT_Y;
        this.scaleMultiplier = InitialSettings.DEFAULT_SCALE_MULTIPLIER;
        this.xAxisRange = InitialSettings.DEFAULT_X_AXIS_RANGE;
        this.yAxisRange = InitialSettings.DEFAULT_Y_AXIS_RANGE;
        this.smallGridSpacing = InitialSettings.DEFAULT_SMALL_GRID_SPACING;
    }

    public void drawCoordinateSystem(Graphics g, int panelWidth, int panelHeight) {
        updateScaleMultiplier(numOfMouseScrolls);
        drawGrid(g, panelWidth, panelHeight);
        drawAxes(g, panelWidth, panelHeight);
        drawLabels(g, panelWidth, panelHeight);
    }

    private void drawAxes(Graphics g, int panelWidth, int panelHeight) {
        g.setColor(Color.black);

        g.drawLine(margin, margin, margin, panelHeight - margin);//OY
        int[] YArrowXVertices = {margin - 5, margin, margin + 5};
        int[] YArrowYVertices = {margin + 6, margin - 4, margin + 6};
        g.fillPolygon(YArrowXVertices, YArrowYVertices, 3);

        g.drawLine(margin, panelHeight - margin, panelWidth - margin, panelHeight - margin);//OX
        int[] XArrowXVertices = {panelWidth - margin - 4, panelWidth - margin + 6, panelWidth - margin - 4};
        int[] XArrowYVertices = {panelHeight - margin - 5, panelHeight - margin, panelHeight - margin + 5};
        g.fillPolygon(XArrowXVertices, XArrowYVertices, 3);
    }

    private void drawGrid(Graphics g, int panelWidth, int panelHeight) {
        drawSmallGrid(g, panelWidth, panelHeight);
        drawBigGrid(g, panelWidth, panelHeight);
    }


    private void drawLabels(Graphics g, int panelWidth, int panelHeight) {
        g.setColor(new Color(150, 145, 145));
        int xLabelXCoord = margin + Math.floorMod((int) originOffset.getX(), 5 * smallGridSpacing);
        double labelNum = (-Math.floor(originOffset.getX() / (5 * smallGridSpacing))) * scaleMultiplier;
        if (labelNum == -0.0) {
            labelNum = 0.0;
        }
        String label;
        String format = "%." + decimalPlacesNeeded(numOfGridZoomInScaling) + "f";
        while (xLabelXCoord <= panelWidth - margin) {

            if (numOfMouseScrolls < InitialSettings.DEFAULT_SMALL_GRID_SPACING) {
                label = String.valueOf((int) labelNum);
            } else {
                label = String.format(format, labelNum);
            }

            g.drawString(label, xLabelXCoord, panelHeight - margin + 20);
            xLabelXCoord += 5 * smallGridSpacing;
            labelNum += scaleUnitX * scaleMultiplier;
        }


        int yLabelYCoord = panelHeight - margin - Math.floorMod((int) originOffset.getY(), 5 * smallGridSpacing);
        labelNum = (-Math.floor(originOffset.getY() / (5 * smallGridSpacing))) * scaleMultiplier;
        if (labelNum == -0.0) {
            labelNum = 0.0;
        }
        while (yLabelYCoord >= margin) {
            if (numOfMouseScrolls <= InitialSettings.DEFAULT_SMALL_GRID_SPACING) {
                label = String.valueOf((int) labelNum);
            } else {
                label = String.format("%.2f", labelNum);
            }
            g.drawString(label, margin - 20, yLabelYCoord);
            yLabelYCoord -= 5 * smallGridSpacing;
            labelNum += scaleUnitY * scaleMultiplier;
        }
    }

    private void drawBigGrid(Graphics g, int panelWidth, int panelHeight) {
        g.setColor(new Color(150, 145, 145));
        int bigGridX = margin + Math.floorMod((int) originOffset.getX(), 5 * smallGridSpacing);
        while (bigGridX <= panelWidth - margin) {
            g.drawLine(bigGridX, margin, bigGridX, panelHeight - margin);
            bigGridX += 5 * smallGridSpacing;
        }
        int bigGridY = panelHeight - margin - Math.floorMod((int) originOffset.getY(), 5 * smallGridSpacing);
        while (bigGridY >= margin) {
            g.drawLine(margin, bigGridY, panelWidth - margin, bigGridY);
            bigGridY -= 5 * smallGridSpacing;
        }
    }

    private void drawSmallGrid(Graphics g, int panelWidth, int panelHeight) {
        g.setColor(new Color(216, 209, 209));
        int smallGridX = margin + Math.floorMod((int) originOffset.getX(), smallGridSpacing);
        while (smallGridX <= panelWidth - margin) {
            g.drawLine(smallGridX, margin, smallGridX, panelHeight - margin);
            smallGridX += smallGridSpacing;
        }
        int smallGridY = panelHeight - margin - Math.floorMod((int) originOffset.getY(), smallGridSpacing);
        while (smallGridY >= margin) {
            g.drawLine(margin, smallGridY, panelWidth - margin, smallGridY);
            smallGridY -= smallGridSpacing;
        }
    }

    public void updateSmallGridSpacing() {
        if (numOfMouseScrolls < 0) {
            this.smallGridSpacing = InitialSettings.DEFAULT_SMALL_GRID_SPACING - Math.floorMod(-this.numOfMouseScrolls, InitialSettings.DEFAULT_SMALL_GRID_SPACING / 2);

        } else {
            this.smallGridSpacing = InitialSettings.DEFAULT_SMALL_GRID_SPACING + Math.floorMod(this.numOfMouseScrolls, InitialSettings.DEFAULT_SMALL_GRID_SPACING);

        }
    }

    public void updateScaleMultiplier(int numOfMouseScrolls) {
        if (numOfMouseScrolls < 0) {
            int numOfGridZoomOutScaling = -numOfMouseScrolls / (InitialSettings.DEFAULT_SMALL_GRID_SPACING / 2);
            int moduloMultiplier = 0;
            switch (numOfGridZoomOutScaling % 3) {
                case 0 -> moduloMultiplier = 1;
                case 1 -> moduloMultiplier = 2;
                case 2 -> moduloMultiplier = 5;
            }
            int power = numOfGridZoomOutScaling / 3;
            this.scaleMultiplier = Math.pow(10, power) * moduloMultiplier;
        } else {
            numOfGridZoomInScaling = numOfMouseScrolls / (InitialSettings.DEFAULT_SMALL_GRID_SPACING);
            int moduloMultiplier = 0;
            switch (numOfGridZoomInScaling % 3) {
                case 0 -> moduloMultiplier = 1;
                case 1 -> moduloMultiplier = 5;
                case 2 -> moduloMultiplier = 2;
            }
            double power = Math.ceil(numOfGridZoomInScaling / 3.0);
            this.scaleMultiplier = Math.pow(0.1, power) * moduloMultiplier;
        }
    }

    public void changeOffsetBy(int dx, int dy) {
        this.originOffset.setLocation(originOffset.getX() + dx, originOffset.getY() - dy);
    }

    public void incrementNumOfMouseScrolls(int ds) {
        this.numOfMouseScrolls -= ds;
    }

    private int decimalPlacesNeeded(int numOfGridZoomInScaling) {
        return (int) Math.ceil(numOfGridZoomInScaling / 3.0);
    }

    public int getMargin() {
        return margin;
    }
}
