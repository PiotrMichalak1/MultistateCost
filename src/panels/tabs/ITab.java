package panels.tabs;

import simulation.Simulation;

public interface ITab {

    void updateSpinners();

    void clearFunctionData();

    void addDataToPlots(Simulation sim) throws CloneNotSupportedException;

    void setPlottersSizes(int width, int height);

    void repaint();
}
