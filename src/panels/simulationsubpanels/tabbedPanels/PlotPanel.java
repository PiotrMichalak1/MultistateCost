package panels.simulationsubpanels.tabbedPanels;

import tools.plotting.Plotter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PlotPanel extends JPanel implements MouseWheelListener{
    private final Plotter plotter;
    private Point previousMousePosition;

    public PlotPanel() {
        plotter = new Plotter();
        ClickListener clickListener = new ClickListener();
        DragListener dragListener = new DragListener();
        this.addMouseListener(clickListener);
        this.addMouseMotionListener(dragListener);
        this.addMouseWheelListener(this);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();

        plotter.newOrigin(height);
        plotter.drawCoordinateSystem(g,width,height);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        plotter.changeSmallGridSpacing(e.getWheelRotation());
        repaint();
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
            plotter.changeOffsetBy(
                    (int)(currentMousePosition.getX()-previousMousePosition.getX()),
                    (int)(currentMousePosition.getY()-previousMousePosition.getY())
            );
            previousMousePosition = currentMousePosition;
            repaint();
        }
    }
}
