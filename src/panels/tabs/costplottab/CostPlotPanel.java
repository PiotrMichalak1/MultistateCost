package panels.tabs.costplottab;

import panels.mainpanels.TabsPanel;
import panels.tabs.IPlotPanel;
import tools.plotting.plottingmodels.IPlotterModel;
import tools.plotting.plottingmodels.PlotterModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CostPlotPanel extends JPanel implements IPlotPanel {
    public IPlotterModel plotterModel;
    private Point previousMousePosition;

    private final TabsPanel parentTabbedPanel;

    public CostPlotPanel(TabsPanel parentTab) {
        this.parentTabbedPanel = parentTab;
        setPlotter();
        initializeMouseControls();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth() - plotterModel.getMargin() * 2;//width of drawing space
        int height = getHeight() - plotterModel.getMargin() * 2;//height of drawing space

        setPlottersSizes(width, height);

        if (width > 0 && height > 0) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            setPlottersSizes(width, height);
            plotterModel.drawPlot(g2);
        }

    }

    private void initializeMouseControls() {
        ClickListener clickListener = new ClickListener();
        DragListener dragListener = new DragListener();
        WheelListener wheelListener = new WheelListener();
        this.addMouseListener(clickListener);
        this.addMouseMotionListener(dragListener);
        this.addMouseWheelListener(wheelListener);
    }

    private void setPlottersSizes(int width, int height) {
        parentTabbedPanel.setPlottersSizes(width,height);
    }

    @Override
    public void setPlotter() {
        this.plotterModel = new PlotterModel();
    }

    private boolean isMouseOnPlot(Point mousePosition) {
        int margin = plotterModel.getMargin();
        boolean horizontally = mousePosition.getX() >= margin && mousePosition.getX() <= getWidth() - margin;
        boolean vertically = mousePosition.getY() >= margin && mousePosition.getY() <= getHeight() - margin;
        return horizontally && vertically;
    }

    private class WheelListener implements MouseWheelListener {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (isMouseOnPlot(e.getPoint())) {
                plotterModel.onMouseScroll(e.getPoint(), e.getWheelRotation(), getWidth() - 2 * plotterModel.getMargin(), getHeight() - 2 * plotterModel.getMargin());
                repaint();
            }
        }
    }

    private class ClickListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent event) {
            previousMousePosition = event.getPoint();
        }
    }

    private class DragListener extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent event) {
            Point currentMousePosition = event.getPoint();
            plotterModel.dragPlot(
                    (int) (currentMousePosition.getX() - previousMousePosition.getX()),
                    (int) (currentMousePosition.getY() - previousMousePosition.getY())
            );
            previousMousePosition = currentMousePosition;
            plotterModel.onMouseMovement(event.getPoint());
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            plotterModel.onMouseMovement(e.getPoint());
            repaint();

        }
    }


}
