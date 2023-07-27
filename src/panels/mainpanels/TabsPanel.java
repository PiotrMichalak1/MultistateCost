package panels.mainpanels;

import panels.tabs.ITab;
import panels.tabs.costplottab.CostPlotTab;
import panels.tabs.distributionstabs.DistributionsTab;
import panels.tabs.layeredcostplottab.LayeredCostPlotTab;
import panels.tabs.layeredstateplottab.LayeredStatePlotTab;
import panels.tabs.structuralplottab.StructuralPlotsTab;
import simulation.Simulation;

import javax.swing.*;
import java.util.List;

public class TabsPanel extends JTabbedPane {
    private List<ITab> plotTabs;


    public TabsPanel() {
        initializeTabs();
        addSpinnersUpdater();

    }

    private void initializeTabs(){
        //index 0
        CostPlotTab costPlotTab = new CostPlotTab(this);
        this.addTab("Cost Plot",costPlotTab);
        //index 1
        LayeredCostPlotTab layeredCostPlotTab = new LayeredCostPlotTab(this);
        this.addTab("Layered Cost Plot",layeredCostPlotTab);
        //index 2
        LayeredStatePlotTab layeredStatePlotTab = new LayeredStatePlotTab(this);
        this.addTab("Layered State Plot",layeredStatePlotTab);
        //index 3
        StructuralPlotsTab structuralPlots = new StructuralPlotsTab(this);
        this.addTab("Structural Plots", structuralPlots);
        //index 4
        DistributionsTab distributions = new DistributionsTab();
        this.addTab("Distributions", distributions);

        addPlotTabsToList(costPlotTab, layeredCostPlotTab, layeredStatePlotTab, structuralPlots);
    }

    private void addPlotTabsToList(CostPlotTab costPlotTab, LayeredCostPlotTab layeredCostPlotTab, LayeredStatePlotTab layeredStatePlotTab, StructuralPlotsTab structuralPlots) {
        plotTabs = List.of(costPlotTab,
                layeredCostPlotTab,
                layeredStatePlotTab,
                structuralPlots);
    }


    //Updates spinner values whenever tab is changed (to include changes made in other tabs as they don't share common RunSimulationPanel)
    private void addSpinnersUpdater() {
        this.addChangeListener(e -> {
            for (ITab plotTab : plotTabs) {
                plotTab.updateSpinners();
            }
        });
    }

    public void clearFunctionData(){
        for (ITab plotTab : plotTabs) {
            plotTab.clearFunctionData();
        }
    }

    public void addDataToPlots(Simulation sim) throws CloneNotSupportedException {
        for (ITab plotTab : plotTabs) {
            plotTab.addDataToPlots(sim);
        }
    }

    public void addDataToStructuralPlots(Simulation sim) throws CloneNotSupportedException {
        for (ITab plotTab: plotTabs){
            if (plotTab instanceof StructuralPlotsTab){
                plotTab.addDataToPlots(sim);
            }
        }
    }


    public void setPlottersSizes(int width, int height) {
        for (ITab plotTab : plotTabs) {
            plotTab.setPlottersSizes(width,height);
        }
    }

    public void repaintAllPlots() {
        for (ITab plotTab : plotTabs) {
            plotTab.repaint();
        }
    }
}
