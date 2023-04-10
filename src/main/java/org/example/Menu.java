package org.example;

import org.example.Actions.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/*
    org.example.Menu is responsible for:
        Knowing the full menu item tree
        Keeping track of menu item currently positioned on
        Knowing what valid menu item selections are given the current position
        Keeping track of menu item meta-data, like the associated action, authentication guard, and quit behavior
    org.example.Menu is not responsible for:
        Rendering itself to console
        Obtaining user input from console
        Performing actions
 */


public class Menu {

    public String getTitle() {
        return currentMenu.title;
    }

    public MenuItem getCurrentMenu() {
        return currentMenu;
    }

    public List<MenuItem> getItems() {
        return currentMenu.getSubItems();
    }


    public HashSet<Character> getValidChoices() {
        HashSet<Character> validChoices = new HashSet<>();

        for (MenuItem subItem : currentMenu.getSubItems())
            validChoices.add(subItem.character);

        return validChoices;
    }


    public MenuItemData getSubItemData(char choice) {
        if (choice == '/') {
            // Dummy up innocuous MenuItemData instance to facilitate moving up tree
            return new MenuItemData(false, null, null);
        }

        char itemKey = Character.toUpperCase(choice);
        MenuItem chosenItem = null;
        for (MenuItem item : currentMenu.getSubItems())
            if (item.character == itemKey) {
                chosenItem = item;
                break;
            }

        if (chosenItem == null)
            throw new IllegalArgumentException("sub-item for choice not found");

        return new MenuItemData(chosenItem.isQuitItem, chosenItem.authenticator, chosenItem.action);
    }


    public void makeSelection(char choice) {
        if (choice == '/') {
            MenuItem newMenu = currentMenu.getParentItem();
            if (newMenu != null) {
                currentMenu = newMenu;
            }
            return;
        }

        char itemKey = Character.toUpperCase(choice);
        for (MenuItem item : currentMenu.getSubItems())
            if (item.character == itemKey) {
                currentMenu = item;
                return;
            }

        throw new IllegalArgumentException("sub-item for choice not found");
    }


    private MenuItem buildMenu() {
        IDbUtils dbUtils = new DbUtils();
        IConsoleUtils consoleUtils = new ConsoleUtils();
        MenuItem root = new MenuItem('A', "Main Menu");

        MenuItem m1 = new MenuItem('1', "Water Source, Treatment, & Regulations");
        MenuItem m2 = new MenuItem('2', "Businesses");
        MenuItem m3 = new MenuItem('3', "Statistics and Data Analysis", false, new BasicAuthenticator(dbUtils, consoleUtils), null);
        MenuItem m4 = new MenuItem('4', "Updates", false, new AdminAuthenticator(dbUtils, consoleUtils), null);
        MenuItem m5 = new MenuItem('5', "Quit", true);
        root.addSubItems(Arrays.asList(m1, m2, m3, m4, m5));

        MenuItem m1a = new MenuItem('A', "Current Activities");
        MenuItem m1b = new MenuItem('B', "Water Treatment Regulations");
        MenuItem m1c = new MenuItem('C', "The Compliance with the Regulations");
        MenuItem m1q = new MenuItem('Q', "Quit", true);
        m1.addSubItems(Arrays.asList(m1a, m1b, m1c, m1q));

        MenuItem m2a = new MenuItem('A', "Industrial Businesses", false, null, new ListIndustrialBusinessStatsAction(dbUtils, consoleUtils));
        MenuItem m2b = new MenuItem('B', "Agricultural Businesses", false, null, new ListAgriculturalBusinessStatsAction(dbUtils, consoleUtils));
        MenuItem m2c = new MenuItem('C', "Both Business Types");
        MenuItem m2q = new MenuItem('Q', "Quit", true);
        m2.addSubItems(Arrays.asList(m2a, m2b, m2c, m2q));

        MenuItem m4a = new MenuItem('A', "Insert New Information");
        MenuItem m4b = new MenuItem('B', "Delete Some Information");
        MenuItem m4c = new MenuItem('C', "Update Current Information");
        MenuItem m4q = new MenuItem('Q', "Quit", true);
        m4.addSubItems(Arrays.asList(m4a, m4b, m4c, m4q));

        MenuItem m1a1 = new MenuItem('1', "Treatment Facilities Summary Report", false, null, new GetTreatmentFacilitySummaryReportAction(dbUtils, consoleUtils));
        MenuItem m1a2 = new MenuItem('2', "Water Source Summary Report");
        MenuItem m1a3 = new MenuItem('3', "Quit", true);
        m1a.addSubItems(Arrays.asList(m1a1, m1a2, m1a3));

        MenuItem m2c1 = new MenuItem('1', "Water Usage", false, null, new ListBusinessWaterUsageAction(dbUtils, consoleUtils));
        MenuItem m2c2 = new MenuItem('2', "New Businesses");
        MenuItem m2c3 = new MenuItem('3', "Quit", true);
        m2c.addSubItems(Arrays.asList(m2c1, m2c2, m2c3));

        MenuItem m4a1 = new MenuItem('1', "Add New Water Treatment Facilities");
        MenuItem m4a2 = new MenuItem('2', "Add New Water Sources");
        MenuItem m4a3 = new MenuItem('3', "Add a New Business");
        MenuItem m4a4 = new MenuItem('4', "Add New Regulations");
        MenuItem m4a5 = new MenuItem('5', "Quit", true);
        m4a.addSubItems(Arrays.asList(m4a1, m4a2, m4a3, m4a4, m4a5));

        MenuItem m4b1 = new MenuItem('1', "Delete Specific Water Treatment Facility");
        MenuItem m4b2 = new MenuItem('2', "Delete a Water Sources");
        MenuItem m4b3 = new MenuItem('3', "Delete a Business");
        MenuItem m4b4 = new MenuItem('4', "Delete a Regulation");
        MenuItem m4b5 = new MenuItem('5', "Quit", true);
        m4b.addSubItems(Arrays.asList(m4b1, m4b2, m4b3, m4b4, m4b5));

        MenuItem m4c1 = new MenuItem('1', "Update Specific Water Treatment Facility", false, null, new UpdateTreatmentFacilityAction(dbUtils, consoleUtils));
        MenuItem m4c2 = new MenuItem('2', "Update a Water Sources");
        MenuItem m4c3 = new MenuItem('3', "Update a Business");
        MenuItem m4c4 = new MenuItem('4', "Update a Regulation");
        MenuItem m4c5 = new MenuItem('5', "Quit", true);
        m4c.addSubItems(Arrays.asList(m4c1, m4c2, m4c3, m4c4, m4c5));

        return root;
    }


    private final MenuItem rootMenu = buildMenu();
    private MenuItem currentMenu = rootMenu;
}
