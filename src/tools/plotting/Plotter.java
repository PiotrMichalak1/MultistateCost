package tools.plotting;

import settings.InitialSettings;

import java.awt.*;

public class Plotter {

    private int margin;
    private int[] xAxisRange;
    private int[] yAxisRange;

    private final int smallGridSpacing;

    private int originX;
    private int originY;

    public Plotter() {
        this.margin = InitialSettings.DEFAULT_PLOT_MARGIN;
        this.originX = margin;
        this.xAxisRange = InitialSettings.DEFAULT_X_AXIS_RANGE;
        this.yAxisRange = InitialSettings.DEFAULT_Y_AXIS_RANGE;
        this.smallGridSpacing = InitialSettings.SMALL_GRID_SPACING;
    }

    public void drawCoordinateSystem(Graphics g, int panelWidth, int panelHeight) {
        drawGrid(g, panelWidth, panelHeight);
        drawAxes(g, panelWidth, panelHeight);
    }

    private void drawAxes(Graphics g, int panelWidth, int panelHeight) {
        g.setColor(Color.black);

        g.drawLine(margin, margin, margin, panelHeight - margin);//OY
        int[] YArrowXVertices = {margin-5,margin,margin+5};
        int[] YArrowYVertices = {margin+6,margin-4,margin+6};
        g.fillPolygon(YArrowXVertices,YArrowYVertices,3);

        g.drawLine(margin, panelHeight - margin, panelWidth - margin, panelHeight - margin);//OX
        int[] XArrowXVertices = {panelWidth-margin-4,panelWidth-margin+6,panelWidth-margin-4};
        int[] XArrowYVertices = {panelHeight-margin-5,panelHeight-margin,panelHeight-margin+5};
        g.fillPolygon(XArrowXVertices,XArrowYVertices,3);
    }

    private void drawGrid(Graphics g, int panelWidth, int panelHeight) {
        g.setColor(new Color(216, 209, 209));
        int smallGridX = margin;
        while (smallGridX <= panelWidth - margin) {
            g.drawLine(smallGridX, margin, smallGridX, panelHeight - margin);
            smallGridX += smallGridSpacing;
        }
        int smallGridY = 0;
        while (smallGridY <= panelHeight - 2*margin) {
            g.drawLine(margin, originY-smallGridY, panelWidth - margin, originY-smallGridY);
            smallGridY += smallGridSpacing;
        }

        g.setColor(new Color(150, 145, 145));
        int bigGridX = margin;
        while (bigGridX <= panelWidth - margin) {
            g.drawLine(bigGridX, margin, bigGridX, panelHeight - margin);
            bigGridX += 5* smallGridSpacing;
        }
        int bigGridY = 0;
        while (bigGridY <= panelHeight - 2*margin) {
            g.drawLine(margin, originY-bigGridY, panelWidth - margin, originY-bigGridY);
            bigGridY += 5* smallGridSpacing;
        }
    }

    private void drawLabels(Graphics g){

    }

    public void newOrigin(int panelHeight) {
        this.originY = panelHeight-margin;
    }

}
