import org.example.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MenuItemTests {

    @Test
    void Constructor_MainConstructor_FillsData() {
        // arrange

        // act
        // Fill with arbitrary values
        MenuItem item = new MenuItem('A', "Title", false, null, null);

        // assert
        assertAll(
                () -> assertEquals('A', item.character),
                () -> assertEquals("Title", item.title),
                () -> assertFalse(item.isQuitItem),
                () -> assertNull(item.authenticator),
                () -> assertNull(item.action)
        );
    }

    @Test
    void Constructor_TwoParam_ReturnsInstance() {
        // arrange

        // act
        MenuItem item = new MenuItem('A', "Title");

        // assert
        assertNotNull(item);
    }

    @Test
    void Constructor_ThreeParam_ReturnsInstance() {
        // arrange

        // act
        MenuItem item = new MenuItem('A', "Title", false);

        // assert
        assertNotNull(item);
    }

    @Test
    void Constructor_NullTitle_ThrowsException() {
        // arrange

        // act/assert
        assertThrows(IllegalArgumentException.class, () -> new MenuItem('A', null));
    }

    @Test
    void AddSubItems_EmptyList_ListUnchanged() {
        // arrange
        MenuItem item = new MenuItem('A', "Title");
        MenuItem expectedItem = item;

        List<MenuItem> toAdd = new ArrayList<>();

        // act
        item.addSubItems(toAdd);

        // assert
        assertEquals(expectedItem, item);
    }

    @Test
    void AddSubItems_AddOneItemToEmptyList_ListPopulated() {
        // arrange
        MenuItem item = new MenuItem('A', "Title");

        List<MenuItem> toAdd = new ArrayList<>(1);
        toAdd.add(new MenuItem('B', "Title2"));

        // act
        item.addSubItems(toAdd);

        // assert
        assertEquals(toAdd, item.getSubItems());
    }

    @Test
    void AddSubItems_AddMultipleToEmptyList_ListPopulated() {
        // arrange
        MenuItem item = new MenuItem('A', "Title");

        List<MenuItem> toAdd = new ArrayList<>();
        toAdd.add(new MenuItem('B', "Title2"));
        toAdd.add(new MenuItem('C', "Title3"));
        toAdd.add(new MenuItem('D', "Title4"));

        // act
        item.addSubItems(toAdd);

        // assert
        assertEquals(toAdd, item.getSubItems());
    }

    @Test
    void AddSubItems_NullList_ThrowsException() {
        // arrange
        MenuItem item = new MenuItem('A', "Title");

        List<MenuItem> toAdd = null;

        // act/assert
        assertThrows(IllegalArgumentException.class, () -> item.addSubItems(toAdd));
    }

    @Test
    void AddSubItems_AddNullsToPreexistingList_ListUnchanged() {
        // arrange
        MenuItem item = new MenuItem('A', "Title");

        List<MenuItem> toAdd1 = new ArrayList<>();
        toAdd1.add(new MenuItem('B', "Title2"));
        toAdd1.add(new MenuItem('C', "Title3"));
        toAdd1.add(new MenuItem('D', "Title4"));
        item.addSubItems(toAdd1);

        List<MenuItem> toAdd2 = new ArrayList<>();
        toAdd2.add(null);
        toAdd2.add(null);

        // act
        item.addSubItems(toAdd2);

        // assert
        assertEquals(toAdd1, item.getSubItems());
    }

    @Test
    void AddSubItems_AddToListWithPreeixstingItems_ItemsAddedToList() {
        // arrange
        MenuItem item = new MenuItem('A', "Title");

        List<MenuItem> toAdd1 = new ArrayList<>();
        toAdd1.add(new MenuItem('B', "Title2"));
        toAdd1.add(new MenuItem('C', "Title3"));
        toAdd1.add(new MenuItem('D', "Title4"));
        item.addSubItems(toAdd1);

        List<MenuItem> toAdd2 = new ArrayList<>();
        toAdd2.add(new MenuItem('E', "Title5"));
        toAdd2.add(new MenuItem('F', "Title6"));

        List<MenuItem> expectedItems = toAdd1;
        expectedItems.addAll(toAdd2);

        // act
        item.addSubItems(toAdd2);

        // assert
        assertEquals(expectedItems, item.getSubItems());
    }

    @Test
    void GetParentItem_RootItem_ReturnsNull() {
        // arrange
        MenuItem root = new MenuItem('A', "Title");

        // act
        MenuItem parent = root.getParentItem();

        // assert
        assertNull(parent);
    }

    @Test
    void GetParentItem_RootWithSubItems_ReturnsRoot() {
        // arrange
        MenuItem root = new MenuItem('A', "Title");

        List<MenuItem> toAdd = new ArrayList<>();
        toAdd.add(new MenuItem('B', "Title2"));
        root.addSubItems(toAdd);

        MenuItem subItem = root.getSubItems().get(0);

        // act
        MenuItem parent = subItem.getParentItem();

        // assert
        assertEquals(root, parent);
    }

    @Test
    void Equals_ParameterObjectIsNull_ReturnsFalse() {
        // arrange
        MenuItem item1 = new MenuItem('A', "Title");
        MenuItem item2 = null;

        // act
        boolean result = item1.equals(item2);

        // assert
        assertFalse(result);
    }

    @Test
    void Equals_ParameterObjectIsDifferentClass_ReturnsFalse() {
        // arrange
        MenuItem item1 = new MenuItem('A', "Title");
        String item2 = "Hello";

        // act
        boolean result = item1.equals(item2);

        // assert
        assertFalse(result);
    }

    @Test
    void Equals_CharactersDiffer_ReturnsFalse() {
        // arrange
        MenuItem item1 = new MenuItem('A', "Title");
        MenuItem item2 = new MenuItem('B', "Title");

        // act
        boolean result = item1.equals(item2);

        // assert
        assertFalse(result);
    }

    @Test
    void Equals_TitlesDiffer_ReturnsFalse() {
        // arrange
        MenuItem item1 = new MenuItem('A', "Title");
        MenuItem item2 = new MenuItem('A', "Title2");

        // act
        boolean result = item1.equals(item2);

        // assert
        assertFalse(result);
    }
}