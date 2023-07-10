package panels.mainpanels;

import panels.simulationsubpanels.tabbedPanels.*;
import panels.simulationsubpanels.tabbedPanels.costplot.CostPlotTab;
import panels.simulationsubpanels.tabbedPanels.layeredplot.LayeredCostPlotTab;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class TabbedPlotPanel extends JTabbedPane {

    public CostPlotTab costPlotTab;

    public LayeredCostPlotTab layeredCostPlotTab;

    public TabbedPlotPanel() {
        //index 0
        costPlotTab = new CostPlotTab(this);
        this.addTab("Cost Plot",costPlotTab);
        //index 1
        layeredCostPlotTab = new LayeredCostPlotTab(this);
        this.addTab("Layered Cost Plot",layeredCostPlotTab);
        //index 2
        LayeredStatePlotTab layeredStatePlot = new LayeredStatePlotTab();
        this.addTab("Layered State Plot",layeredStatePlot);
        //index 3
        StructuralPlotsTab structuralPlots = new StructuralPlotsTab();
        this.addTab("Structural Plots", structuralPlots);
        //index 4
        DistributionsTab distributions = new DistributionsTab();
        this.addTab("Distributions", distributions);

        this.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = getSelectedIndex();

                if (selectedIndex == 0) {
                    costPlotTab.updateSpinners();
                } else if (selectedIndex == 1) {
                    layeredCostPlotTab.updateSpinners();
                }
            }
        });

    }


}
