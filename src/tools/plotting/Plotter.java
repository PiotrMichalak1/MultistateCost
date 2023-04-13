package tools.plotting;

import settings.InitialSettings;

import java.awt.*;

public class Plotter {

    private int margin;
    private int[] xAxisRange;
    private int[] yAxisRange;

    private Point originOffset;

    private final int smallGridSpacing;

    private int originX;
    private int originY;

    public Plotter() {
        this.margin = InitialSettings.DEFAULT_PLOT_MARGIN;
        this.originX = margin;
        this.originOffset = new Point(0, 0);
        this.xAxisRange = InitialSettings.DEFAULT_X_AXIS_RANGE;
        this.yAxisRange = InitialSettings.DEFAULT_Y_AXIS_RANGE;
        this.smallGridSpacing = InitialSettings.SMALL_GRID_SPACING;
    }

    public void drawCoordinateSystem(Graphics g, int panelWidth, int panelHeight) {
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
        int labelNum = 0;
        while (xLabelXCoord <= panelWidth - margin) {
            g.drawLine(xLabelXCoord, margin, xLabelXCoord, panelHeight - margin);
            String label = String.valueOf(-(int) originOffset.getX() / (5 * smallGridSpacing)+labelNum);
            g.drawString(label, xLabelXCoord, panelHeight - margin + 20);
            xLabelXCoord += 5 * smallGridSpacing;
            labelNum++;
        }
        int bigGridY = panelHeight - margin - Math.floorMod((int) originOffset.getY(), 5 * smallGridSpacing);
        while (bigGridY >= margin) {
            g.drawLine(margin, bigGridY, panelWidth - margin, bigGridY);
            bigGridY -= 5 * smallGridSpacing;
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


    public void newOrigin(int panelHeight) {
        this.originY = panelHeight - margin;
    }

    public void changeOffsetBy(int dx, int dy) {
        this.originOffset.setLocation(originOffset.getX() + dx, originOffset.getY() - dy);
    }
}
