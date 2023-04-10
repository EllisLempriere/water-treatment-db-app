package org.example;

import org.example.BusinessModels.BusinessStats;
import org.example.BusinessModels.TreatmentFacility;
import org.example.BusinessModels.TreatmentFacilitySummaryReportLine;
import org.example.BusinessModels.WaterUsageStats;
import java.util.List;

public interface IDbUtils {

    boolean canConnectToDB(String userId, String userPass);

    BusinessStats getIndustrialBusinessStats();

    List<TreatmentFacilitySummaryReportLine> getTreatmentFacilitiesSummaryReport();

    List<TreatmentFacility> getTreatmentFacilities();
    public void updateTreatmentFacility(TreatmentFacility facility);
    WaterUsageStats getBusinessWaterUsageStats();
}
