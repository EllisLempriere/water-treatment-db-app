import org.example.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ViewTest {

    @Test
    void Constructor_Default_ReturnsInstance() {
        // arrange

        // act
        View view = new View();

        // assert
        assertNotNull(view);
    }

    @Test
    void Render_MainMenu_ReturnsMainMenu() {
        // arrange
        View view = new View();
        Menu menu = new Menu();

        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("**********************************************************************");
        expectedResult.add("    Welcome to the DDS for Washington's Water Treatment Facilities");
        expectedResult.add("                              Main Menu");
        expectedResult.add("**********************************************************************");
        expectedResult.add("              1. Water Source, Treatment, & Regulations");
        expectedResult.add("                            2. Businesses");
        expectedResult.add("                   3. Statistics and Data Analysis");
        expectedResult.add("                              4. Updates");
        expectedResult.add("                               5. Quit");

        // act
        List<String> actualResult = view.render(menu);

        // assert
        assertIterableEquals(expectedResult, actualResult);
    }

    @Test
    void Render_BusinessMenu_ReturnsCorrectMenu() {
        // arrange
        View view = new View();
        Menu menu = new Menu();
        menu.makeSelection('2');

        List<String> expectedResult = new ArrayList<>();
        expectedResult.add("**********************************************************************");
        expectedResult.add("    Welcome to the DDS for Washington's Water Treatment Facilities");
        expectedResult.add("                              Businesses");
        expectedResult.add("**********************************************************************");
        expectedResult.add("                       A. Industrial Businesses");
        expectedResult.add("                      B. Agricultural Businesses");
        expectedResult.add("                        C. Both Business Types");
        expectedResult.add("                               Q. Quit");

        // act
        List<String> actualResult = view.render(menu);

        // assert
        assertIterableEquals(expectedResult, actualResult);
    }
}