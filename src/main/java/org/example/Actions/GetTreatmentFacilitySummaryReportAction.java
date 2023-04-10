package org.example.Actions;

import org.example.BusinessModels.TreatmentFacilitySummaryReportLine;
import org.example.IAction;
import org.example.IConsoleUtils;
import org.example.IDbUtils;

import java.util.List;

public class GetTreatmentFacilitySummaryReportAction extends Action implements IAction {

    public GetTreatmentFacilitySummaryReportAction(IDbUtils dbUtils, IConsoleUtils consoleUtils) {
        super(dbUtils, consoleUtils);
    }

    @Override
    public void execute() {
        List<TreatmentFacilitySummaryReportLine> report = dbUtils.getTreatmentFacilitiesSummaryReport();

        System.out.println("\n\033[1mTreatment Facilities Summary\033[0m");
        if (report == null)
            System.out.println("Failed to retrieve treatment facilities report data");
        else {
            for (TreatmentFacilitySummaryReportLine l : report) {
                System.out.printf("%-50s %4d %11d\n",l.facilityName, l.yearsOfOperation, l.monthlyWaterQuantity);
            }
        }
        System.out.println();
    }
}
