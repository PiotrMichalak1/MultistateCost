package tools.plotting;

import settings.InitialSettings;
import tools.Mathematics;

import java.awt.*;


public class Plotter {
    public CoordinateSystem coordinateSystem;

    public Plotter() {
        this.coordinateSystem = new CoordinateSystem();
    }

    public void drawCoordinateSystem(Graphics2D g2,int width,int height){
        coordinateSystem.drawCoordinateSystem(g2,width,height);
    }

    public void onMouseScroll(Point mousePosition, int wheelRotation, int width, int height){
        coordinateSystem.onMouseScroll(mousePosition,wheelRotation,width,height);
    }

    public void dragPlot(int dx, int dy){
        coordinateSystem.dragPlot(dx,dy);
    }

    public int getMargin(){
        return coordinateSystem.getMargin();
    }


    static class CoordinateSystem {
        private final int margin;

        private double scaleMultiplier;
        private int numOfMouseScrolls;
        int numOfGridZoomInScaling;

        private int smallGridSpacing;
        private int bigGridSpacing;
        private final double scaleUnitX;
        private final double scaleUnitY;
        private final double[] xRange;
        private final double[] yRange;

        public CoordinateSystem() {
            this.margin = InitialSettings.DEFAULT_PLOT_MARGIN;

            this.scaleUnitX = InitialSettings.DEFAULT_SCALE_UNIT_X;
            this.scaleUnitY = InitialSettings.DEFAULT_SCALE_UNIT_Y;
            this.scaleMultiplier = InitialSettings.DEFAULT_SCALE_MULTIPLIER;
            this.numOfMouseScrolls = 0;


            this.smallGridSpacing = InitialSettings.DEFAULT_SMALL_GRID_SPACING;
            this.bigGridSpacing = 5 * this.smallGridSpacing;

            this.xRange = new double[2];
            this.yRange = new double[2];
        }
        public void drawCoordinateSystem(Graphics2D g2, int width, int height) {

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            updateRanges(width, height);
            drawLabelsAndBigGrid(g2, width, height);
            drawGrid(g2, width, height);
            drawAxes(g2, width, height);
        }

        private void drawAxes(Graphics2D g2, int width, int height) {
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

        private void drawGrid(Graphics2D g2, int width, int height) {
            drawSmallGrid(g2, width, height);
            drawLabelsAndBigGrid(g2, width, height);
        }

        private void drawLabelsAndBigGrid(Graphics2D g2, int width, int height) {
            g2.setColor(new Color(150, 145, 145));
            FontMetrics fontMetrics = g2.getFontMetrics(g2.getFont());
            double labelNum = Mathematics.roundUpToTheNearestMultiple(xRange[0], scaleUnitX * scaleMultiplier);
            int xLabelXCoord = (int) ((labelNum - xRange[0]) / (xRange[1] - xRange[0]) * width);
            String label;
            String format = "%." + decimalPlacesNeededInLabels(numOfGridZoomInScaling) + "f";
            while (xLabelXCoord <= width) {

                if (numOfMouseScrolls < InitialSettings.DEFAULT_SMALL_GRID_SPACING) {
                    label = String.valueOf((int) labelNum);
                } else {
                    label = String.format(format, labelNum);
                }

                g2.drawLine(xLabelXCoord + margin, height + margin, xLabelXCoord + margin, margin);
                g2.drawString(label, xLabelXCoord + margin - fontMetrics.stringWidth(label) / 2, height + margin + 20);
                xLabelXCoord += bigGridSpacing;
                labelNum += scaleUnitX * scaleMultiplier;
            }

            labelNum = Mathematics.roundUpToTheNearestMultiple(yRange[0], scaleUnitY * scaleMultiplier);
            int yLabelYCoord = (int) ((labelNum - yRange[0]) / (yRange[1] - yRange[0]) * height);
            while (yLabelYCoord <= height) {

                if (numOfMouseScrolls < InitialSettings.DEFAULT_SMALL_GRID_SPACING) {
                    label = String.valueOf((int) labelNum);
                } else {
                    label = String.format(format, labelNum);
                }

                g2.drawLine(margin, margin + height - yLabelYCoord, margin + width, margin + height - yLabelYCoord);
                g2.drawString(label, margin - 5 - fontMetrics.stringWidth(label), margin + height - yLabelYCoord + (int) fontMetrics.getStringBounds(label, g2).getHeight() / 2-2);
                yLabelYCoord += bigGridSpacing;
                labelNum += scaleUnitY * scaleMultiplier;
            }


        }

        private void drawSmallGrid(Graphics2D g2, int width, int height) {
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
            updateScale(numOfMouseScrolls);
            updateGridSpacing();
            scaleRanges(mousePosition, width, height);
        }

        public void updateRanges(int width, int height) {
            xRange[1] = xRange[0] + (width) / (5.0 * smallGridSpacing) * scaleMultiplier;
            yRange[1] = yRange[0] + (height) / (5.0 * smallGridSpacing) * scaleMultiplier;
        }

        private void updateScale(int numOfMouseScrolls) {
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

        private void updateGridSpacing() {
            if (numOfMouseScrolls < 0) {
                smallGridSpacing = InitialSettings.DEFAULT_SMALL_GRID_SPACING - Math.floorMod(-numOfMouseScrolls, InitialSettings.DEFAULT_SMALL_GRID_SPACING / 2);

            } else {
                smallGridSpacing = InitialSettings.DEFAULT_SMALL_GRID_SPACING + Math.floorMod(numOfMouseScrolls, InitialSettings.DEFAULT_SMALL_GRID_SPACING);
            }
            bigGridSpacing = 5 * smallGridSpacing;
        }

        private void scaleRanges(Point mousePosition, int width, int height) {
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
            double translationX = -dx * (scaleMultiplier * scaleUnitX / bigGridSpacing);
            double translationY = dy * (scaleMultiplier * scaleUnitY / bigGridSpacing);

            xRange[0] = xRange[0] + translationX;
            xRange[1] = xRange[1] + translationX;

            yRange[0] = yRange[0] + translationY;
            yRange[1] = yRange[1] + translationY;

        }

        private int decimalPlacesNeededInLabels(int numOfGridZoomInScaling) {
            return (int) Math.ceil(numOfGridZoomInScaling / 3.0);
        }

        public int getMargin() {
            return margin;
        }

    }

    class Plot{

    }


}
