package org.example.BusinessModels;

public class BusinessStats {

    public int totalWaterConsumption;
    public int totalWasteGeneration;


    public BusinessStats() {
    }


    public BusinessStats(int totalWaterConsumption, int totalWasteGeneration) {
        this.totalWaterConsumption = totalWaterConsumption;
        this.totalWasteGeneration = totalWasteGeneration;
    }
}
