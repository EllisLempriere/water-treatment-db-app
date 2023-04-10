package org.example.BusinessModels;

public class WaterUsageStats {

    public double avgWaterConsumption;
    public double avgWasteGeneration;
    public double waterNeedingTreatment;

    public WaterUsageStats() {
    }

    public WaterUsageStats(double avgWaterConsumption, double avgWasteGeneration, double waterNeedingTreatment) {
        this.avgWaterConsumption = avgWaterConsumption;
        this.avgWasteGeneration = avgWasteGeneration;
        this.waterNeedingTreatment = waterNeedingTreatment;
    }
}
