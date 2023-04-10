package org.example.Actions;

import org.example.BusinessModels.BusinessStats;
import org.example.IAction;
import org.example.IConsoleUtils;
import org.example.IDbUtils;

public class ListIndustrialBusinessStatsAction extends Action implements IAction {

    public ListIndustrialBusinessStatsAction(IDbUtils dbUtils, IConsoleUtils consoleUtils) {
        super(dbUtils, consoleUtils);
    }


    @Override
    public void execute() {

        BusinessStats stats = dbUtils.getIndustrialBusinessStats();

        System.out.println("\n\033[1mIndustrial Businesses\033[0m");
        if (stats == null)
            System.out.println("Failed to retrieve industrial business stats");
        else {
            System.out.println("Total Monthly Water Consumption: " + stats.totalWaterConsumption);
            System.out.println("Total Monthly Waste Generation: " + stats.totalWasteGeneration);
        }
        System.out.println();
    }



}
