package panels.simulationsubpanels.tabbedPanels;

import tools.plotting.Plotter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class PlotPanel extends JPanel {
    private Plotter plotter;
    private Point previousMousePosition;
    private Point currentMousePosition;
    public PlotPanel() {
        plotter = new Plotter();
        ClickListener clickListener = new ClickListener();
        DragListener dragListener = new DragListener();
        this.addMouseListener(clickListener);
        this.addMouseMotionListener(dragListener);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();

        plotter.newOrigin(height);
        plotter.drawCoordinateSystem(g,width,height);
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
            currentMousePosition = event.getPoint();
            plotter.changeOffsetBy(
                    (int)(currentMousePosition.getX()-previousMousePosition.getX()),
                    (int)(currentMousePosition.getY()-previousMousePosition.getY())
            );
            previousMousePosition = currentMousePosition;
            repaint();
        }
    }
}
