import org.example.*;
import org.example.Actions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class MenuTests {

    @BeforeAll
    public static void setUp() {
        buildExpectedMenu();
    }

    @Test
    void Constructor_Default_NotNull() {
        // arrange

        // act
        Menu menu = new Menu();

        // assert
        assertNotNull(menu);
    }

    @Test
    void Title_Default_ReturnsMainMenu() {
        // arrange
        Menu menu = new Menu();
        String expectedTitle = "Main Menu";

        // act
        String actualTitle = menu.getTitle();

        // assert
        assertEquals(expectedTitle, actualTitle);
    }

    @Test
    void Items_Default_ReturnsMainMenuItems() {
        // arrange
        Menu menu = new Menu();

        List<MenuItem> expectedItems = rootMenuItem.getSubItems();

        // act
        List<MenuItem> actualItems = menu.getItems();

        // assert
        assertIterableEquals(expectedItems, actualItems);
    }

    @Test
    void GetValidChoices_Default_ReturnsValidMainMenuChoices() {
        // arrange
        Menu menu = new Menu();

        HashSet<Character> expectedValidChoices = new HashSet<>(Arrays.asList('1', '2', '3', '4', '5'));

        // act
        HashSet<Character> actualValidChoices = menu.getValidChoices();

        // assert
        assertIterableEquals(expectedValidChoices, actualValidChoices);
    }

    @ParameterizedTest
    @CsvSource({
            "1, false, false",
            "3, false, true",
            "5, true, false",
            "/, false, false"
    })
    void GetSubItemData_MainMenuOptions_ReturnsCorrectData(char choice, boolean expectedQuit, boolean basicAuthenticatorExpected) {
        // arrange
        Menu menu = new Menu();

        IAuthenticator expectedAuthenticator = basicAuthenticatorExpected ? new BasicAuthenticator(new DbUtils(), new ConsoleUtils()) : null;

        MenuItemData expectedData = new MenuItemData(expectedQuit, expectedAuthenticator, null);

        // act
        MenuItemData actualData = menu.getSubItemData(choice);

        // assert
        assertEquals(expectedData, actualData);
    }

    @Test
    void GetSubItemData_InvalidChoice_ThrowsException() {
        // arrange
        Menu menu = new Menu();
        char choice = '9';

        // act/assert
        assertThrows(IllegalArgumentException.class, () -> menu.getSubItemData(choice));
    }

    @ParameterizedTest
    @MethodSource("MakeSelectionTestCaseProvider")
    void MakeSelection_MainMenuOptions_TitleAndSubItemsAreCorrect(MakeSelectionTestData s) {
        // arrange
        Menu menu = new Menu();
        for (int i = 0; i < s.selectionPath.length - 2; i++) {
            menu.makeSelection(s.selectionPath[i]);
        }

        // act
        menu.makeSelection(s.selectionPath[s.selectionPath.length - 1]);

        // assert
        assertEquals(s.expectedItem, menu.getCurrentMenu());
    }
    // TODO - testCaseProvider needs to provide a char[], and a MenuItem to step through the tree
    // What's the depth we want to test of the tree?

    static class MakeSelectionTestData {
        public char[] selectionPath;
        public MenuItem expectedItem;
    }

    private static Stream<MakeSelectionTestData> MakeSelectionTestCaseProvider() {
        MakeSelectionTestData testCase1 = new MakeSelectionTestData();
        testCase1.selectionPath = new char[1];
        testCase1.selectionPath[0] = '1';
        testCase1.expectedItem = menuItemM1;

        return Stream.of(testCase1);
    }

    @Test
    void MakeSelection_MainMenuOption1_TitleAndSubItemsAreCorrect() {
        // arrange
        Menu menu = new Menu();
        char choice = '1';

        String expectedTitle = menuItemM1.title;
        List<MenuItem> expectedSubItems = menuItemM1.getSubItems();

        // act
        menu.makeSelection(choice);

        // assert
        assertAll(
                () -> assertEquals(expectedTitle, menu.getTitle()),
                () -> assertIterableEquals(expectedSubItems, menu.getItems())
        );
    }

    @Test
    void MakeSelection_InvalidOption_ThrowsException() {
        // arrange
        Menu menu = new Menu();
        char choice = '9';

        // act/assert
        assertThrows(IllegalArgumentException.class, () -> menu.makeSelection(choice));
    }


    @Test
    void MakeSelection_GoBackFromMainMenu_DoesNotMoveBack() {
        // arrange
        Menu menu = new Menu();
        char choice = '/';

        String expectedTitle = rootMenuItem.title;
        List<MenuItem> expectedSubItems = rootMenuItem.getSubItems();

        // act
        menu.makeSelection(choice);

        // assert
        assertAll(
                () -> assertEquals(expectedTitle, menu.getTitle()),
                () -> assertIterableEquals(expectedSubItems, menu.getItems())
        );
    }

    @Test
    void MakeSelection_GoBackFromSubMenu_StepsBackAMenu() {
        // arrange
        Menu menu = new Menu();
        menu.makeSelection('1');
        char choice = '/';

        String expectedTitle = rootMenuItem.title;
        List<MenuItem> expectedSubItems = rootMenuItem.getSubItems();

        // act
        menu.makeSelection(choice);

        // assert
        assertAll(
                () -> assertEquals(expectedTitle, menu.getTitle()),
                () -> assertIterableEquals(expectedSubItems, menu.getItems())
        );
    }


    private static void buildExpectedMenu() {
        IDbUtils dbUtils = new DbUtils();
        IConsoleUtils consoleUtils = new ConsoleUtils();
        MenuItem root = new MenuItem('A', "Main Menu");
        rootMenuItem = root;

        MenuItem m1 = new MenuItem('1', "Water Source, Treatment, & Regulations");
        MenuItem m2 = new MenuItem('2', "Businesses");
        MenuItem m3 = new MenuItem('3', "Statistics and Data Analysis", false, new BasicAuthenticator(dbUtils, consoleUtils), null);
        MenuItem m4 = new MenuItem('4', "Updates", false, new AdminAuthenticator(dbUtils, consoleUtils), null);
        MenuItem m5 = new MenuItem('5', "Quit", true);
        root.addSubItems(Arrays.asList(m1, m2, m3, m4, m5));
        menuItemM1 = m1;

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
    }

    private static MenuItem rootMenuItem;
    private static MenuItem menuItemM1;
}
