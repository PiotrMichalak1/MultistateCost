package panels.simulationsubpanels.tabbedPanels;

import tools.plotting.AlternativePlotter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PlotPanel extends JPanel implements MouseWheelListener{
    private final AlternativePlotter plotter;
    private Point previousMousePosition;

    public PlotPanel() {
        plotter = new AlternativePlotter();
        ClickListener clickListener = new ClickListener();
        DragListener dragListener = new DragListener();
        this.addMouseListener(clickListener);
        this.addMouseMotionListener(dragListener);
        this.addMouseWheelListener(this);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth()- plotter.getMargin()*2;
        int height = getHeight()- plotter.getMargin()*2;
        if(width > 0 && height> 0){
            plotter.drawCoordinateSystem(g,width,height);
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (isMouseOnPlot(e.getPoint())) {
            plotter.onMouseScroll(e.getPoint(),e.getWheelRotation(),getWidth()-2* plotter.getMargin(),getHeight()-2* plotter.getMargin());
            repaint();
        }
    }
    private boolean isMouseOnPlot(Point mousePosition){
        int margin = plotter.getMargin();
        boolean horizontally = mousePosition.getX() >= margin && mousePosition.getX() <= getWidth() -margin;
        boolean vertically = mousePosition.getY() >= margin && mousePosition.getY()<= getHeight()-margin;
        return horizontally && vertically;
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
            plotter.dragPlot(
                    (int)(currentMousePosition.getX()-previousMousePosition.getX()),
                    (int)(currentMousePosition.getY()-previousMousePosition.getY())
            );
            previousMousePosition = currentMousePosition;
            repaint();
        }
    }
}
