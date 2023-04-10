package org.example.BusinessModels;

public class TreatmentFacility {
    public int facilityId;
    public String facilityName;
    public String facilityAddress;
    public String contactName;
    public String contactPhone;
    public int monthlyWaterQuantity;
    public int waterSourceId;

    public TreatmentFacility() {
    }

    public TreatmentFacility(int facilityId, String facilityName, String facilityAddress,
                             String contactName, String contactPhone, int monthlyWaterQuantity, int waterSourceId) {
        this.facilityId = facilityId;
        this.facilityName = facilityName;
        this.facilityAddress = facilityAddress;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.monthlyWaterQuantity = monthlyWaterQuantity;
        this.waterSourceId = waterSourceId;
    }
}
