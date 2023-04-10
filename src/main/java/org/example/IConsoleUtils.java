package org.example;

import org.example.BusinessModels.TreatmentFacility;
import java.util.List;

public interface IConsoleUtils {

    String getUserInfo(String prompt);

    void displayErrorMessage(String message);

    void displayScreenTitle(String title);

    void displayMessage(String message);

    void displayTreatmentFacilities(List<TreatmentFacility> facilities);

    Integer getInteger(String prompt);

    ConsoleUtils.IntegerResult getInteger(String prompt, Integer initialValue);
}
