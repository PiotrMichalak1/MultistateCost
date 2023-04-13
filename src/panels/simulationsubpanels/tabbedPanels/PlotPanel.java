package panels.simulationsubpanels.tabbedPanels;

import tools.plotting.Plotter;

import javax.swing.*;
import java.awt.*;

public class PlotPanel extends JPanel {
    Plotter plotter;
    public PlotPanel() {
        plotter = new Plotter();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        plotter.newOrigin(height);
        plotter.drawCoordinateSystem(g,width,height);
    }
}
