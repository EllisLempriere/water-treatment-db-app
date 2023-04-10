package org.example.BusinessModels;

public class TreatmentFacilitySummaryReportLine {
    public String facilityName;
    public int yearsOfOperation;
    public int monthlyWaterQuantity;


    public TreatmentFacilitySummaryReportLine() {
    }


    public TreatmentFacilitySummaryReportLine(String facilityName, int yearsOfOperation, int monthlyWaterQuantity) {
        this.facilityName = facilityName;
        this.yearsOfOperation = yearsOfOperation;
        this.monthlyWaterQuantity = monthlyWaterQuantity;
    }
}
