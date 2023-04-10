import org.example.*;
import org.example.Actions.UpdateTreatmentFacilityAction;
import org.example.BusinessModels.TreatmentFacility;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

public class UpdateTreatmentFacilityActionTests {

    @Test
    public void Execute_Default_CallsGetTreatmentFacility() {
        // arrange
        IDbUtils mockDbUtils = mock();
        IConsoleUtils mockConsoleUtils = mock();
        IAction action = new UpdateTreatmentFacilityAction(mockDbUtils, mockConsoleUtils);

        // act
        action.execute();

        // assert
        verify(mockDbUtils, times(1)).getTreatmentFacilities();
    }

    @Test
    void Execute_GetTreatmentFacilitiesReturnsNull_ErrorMessageDisplayed() {
        // arrange
        IDbUtils mockDbUtils = mock();
        when(mockDbUtils.getTreatmentFacilities()).thenReturn(null);

        IConsoleUtils mockConsoleUtils = mock();

        IAction action = new UpdateTreatmentFacilityAction(mockDbUtils, mockConsoleUtils);

        // act
        action.execute();

        // assert
        verify(mockConsoleUtils).displayErrorMessage(eq("Failed to retrieve treatment facilities"));
    }

    @Test
    void Execute_GetTreatmentFacilitiesReturnsTreatmentFacilities_TreatmentFacilitiesGetDisplayed() {
        // arrange
        IDbUtils mockDbUtils = mock();

        List<TreatmentFacility> expectedFacilities = new ArrayList<>();
        expectedFacilities.add(new TreatmentFacility(1, "Name", "Address", "ContactName", "ContactPhone", 5, 1));
        when(mockDbUtils.getTreatmentFacilities()).thenReturn(expectedFacilities);

        IConsoleUtils mockConsoleUtils = mock();

        IAction action = new UpdateTreatmentFacilityAction(mockDbUtils, mockConsoleUtils);

        // act
        action.execute();

        // assert
        verify(mockConsoleUtils).displayTreatmentFacilities(eq(expectedFacilities));
    }

    @Test
    void Execute_GetIntegerReturnsNull_ErrorMessageDisplayed() {
        // arrange
        IDbUtils mockDbUtils = mock();
        when(mockDbUtils.getTreatmentFacilities()).thenReturn(new ArrayList<>());
        
        IConsoleUtils mockConsoleUtils = mock();
        when(mockConsoleUtils.getInteger("Enter FacilityID of facility to update: ")).thenReturn(null);

        IAction action = new UpdateTreatmentFacilityAction(mockDbUtils, mockConsoleUtils);

        // act
        action.execute();

        // assert
        verify(mockConsoleUtils).displayErrorMessage(eq("Facility not found"));
    }

    @Test
    void Execute_GetIntegerValue_NewWaterQualityPromptDisplayed() {
        // arrange
        IDbUtils mockDbUtils = mock();
        List<TreatmentFacility> dummyFacilities = new ArrayList<>();
        dummyFacilities.add(new TreatmentFacility(1, "name", "address", "person", "phone", 5, 1));
        when(mockDbUtils.getTreatmentFacilities()).thenReturn(dummyFacilities);

        IConsoleUtils mockConsoleUtils = mock();
        when(mockConsoleUtils.getInteger("Enter FacilityID of facility to update: ")).thenReturn(1);


        IAction action = new UpdateTreatmentFacilityAction(mockDbUtils, mockConsoleUtils);

        // act
        action.execute();

        // assert
        verify(mockConsoleUtils).getInteger("Enter new monthly water quantity", 5);
    }

}
