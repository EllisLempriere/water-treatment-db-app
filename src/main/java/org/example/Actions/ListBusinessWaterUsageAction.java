package org.example.Actions;

import org.example.BusinessModels.WaterUsageStats;
import org.example.IAction;
import org.example.IConsoleUtils;
import org.example.IDbUtils;

public class ListBusinessWaterUsageAction extends Action implements IAction {

    public ListBusinessWaterUsageAction(IDbUtils dbUtils, IConsoleUtils consoleUtils) {
        super(dbUtils, consoleUtils);
    }

    @Override
    public void execute() {
        WaterUsageStats stats = dbUtils.getBusinessWaterUsageStats();

        System.out.println("\n\033[1mBusiness Water Usage\033[0m");
        if (stats == null)
            System.out.println("Failed to retrieve business water usage stats");
        else {
            System.out.println("Average Monthly Water Consumption: " + stats.avgWaterConsumption);
            System.out.println("Average Monthly Waste Generation: " + stats.avgWasteGeneration);
            System.out.println("Total Water Needing Treatment: " + stats.waterNeedingTreatment);
        }
        System.out.println();
    }
}
