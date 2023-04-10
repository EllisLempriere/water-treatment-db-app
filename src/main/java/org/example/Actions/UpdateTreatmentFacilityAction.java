package org.example.Actions;

import org.example.BusinessModels.TreatmentFacility;
import org.example.ConsoleUtils;
import org.example.IAction;
import org.example.IConsoleUtils;
import org.example.IDbUtils;
import java.util.List;

public class UpdateTreatmentFacilityAction extends Action implements IAction {

    public UpdateTreatmentFacilityAction(IDbUtils dbUtils, IConsoleUtils consoleUtils) {
        super(dbUtils, consoleUtils);
    }

    @Override
    public void execute() {
        consoleUtils.displayScreenTitle("Update Treatment Facility");

        List<TreatmentFacility> facilities = dbUtils.getTreatmentFacilities();
        if (facilities == null) {
            consoleUtils.displayErrorMessage("Failed to retrieve treatment facilities");
            return;
        }

        consoleUtils.displayTreatmentFacilities(facilities);

        // getInteger() can take a list of valid Integers and a number of retries
        Integer facilityId = consoleUtils.getInteger("Enter FacilityID of facility to update: ");

        TreatmentFacility facility = findFacility(facilities, facilityId);
        if (facility == null) {
            consoleUtils.displayErrorMessage("Facility not found");
            return;
        }

        ConsoleUtils.IntegerResult waterQuantity = consoleUtils.getInteger("Enter new monthly water quantity", facility.monthlyWaterQuantity);
        if (!waterQuantity.isChanged) {
            consoleUtils.displayErrorMessage("Leaving value unchanged");
            return;
        }
        if (waterQuantity.result == null) {
            consoleUtils.displayErrorMessage("Invalid water quantity");
            return;
        }

        facility.monthlyWaterQuantity = waterQuantity.result;
        try {
            dbUtils.updateTreatmentFacility(facility);
            consoleUtils.displayMessage("Successfully saved data");
        } catch (UnsupportedOperationException e) {
            consoleUtils.displayErrorMessage("Failed to save new data: " + e.getMessage());
        }
    }


    private TreatmentFacility findFacility(List<TreatmentFacility> facilities, Integer facilityId) {
        TreatmentFacility facility = null;

        if (facilityId != null) {
            for (TreatmentFacility f : facilities) {
                if (facilityId == f.facilityId)
                    facility = f;
            }
        }

        return facility;
    }
}
