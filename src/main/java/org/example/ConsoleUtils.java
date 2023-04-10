package org.example;

import org.example.BusinessModels.TreatmentFacility;
import java.io.IOException;
import java.util.List;

public class ConsoleUtils implements IConsoleUtils {

    public String getUserInfo(String prompt) {
        try {
            StringBuilder buffer = new StringBuilder();
            System.out.print(prompt);
            System.out.flush();
            int c = System.in.read();
            while(c != '\n' && c != -1) {
                buffer.append((char)c);
                c = System.in.read();
            }
            return buffer.toString().trim();
        } catch (IOException e) {
            return "";
        }
    }

    public void displayErrorMessage(String message) {
        System.out.println("Error: " + message);
    }

    public void displayScreenTitle(String title) {
        System.out.println("\n\033[1m" + title + "\033[0m\n");
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayTreatmentFacilities(List<TreatmentFacility> facilities) {
        for (TreatmentFacility f : facilities) {
            System.out.printf("%5d %-40s %-60s %-20s %14s %11d %4d\n",
                    f.facilityId, f.facilityName, f.facilityAddress, f.contactName, f.contactPhone, f.monthlyWaterQuantity, f.waterSourceId);
        }
        System.out.println();
    }

    public Integer getInteger(String prompt) {
        Integer returnVal = null;

        String userInput = getUserInfo(prompt);
        try {
            returnVal = Integer.parseInt(userInput);
        } catch (NumberFormatException ignored) {
        }

        return returnVal;
    }

    public IntegerResult getInteger(String prompt, Integer initialValue) {
        IntegerResult returnVal = new IntegerResult();

        String userInput = getUserInfo(prompt + " (return to keep value same): ");
        System.out.println();

        if (userInput.trim().length() == 0)
            returnVal.isChanged = false;
        else {
            try {
                returnVal.result = Integer.parseInt(userInput);
                returnVal.isChanged = !returnVal.result.equals(initialValue);
            } catch (NumberFormatException ignored) {
            }
        }

        return returnVal;
    }

    public static class IntegerResult {
        public Integer result;
        public boolean isChanged;
    }
}
