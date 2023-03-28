package application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class tutTest {

    private tut tutInstance;

    @Before
    public void setUp() throws Exception {
        tutInstance = new tut();
    }

    @Test
    public void testLoadWordsMap() throws Exception {
        tutInstance.loadWordsMap();

        int expectedCount = 5;
        int actualCount = tutInstance.map.get("word");

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testCountButtonWithInput() {
        tutInstance.WordField.setText("will");
        tutInstance.countButton.fire();

        String expectedCount = "1";
        String actualCount = tutInstance.CountField.getText();

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testCountButtonWithNoInput() {
        tutInstance.WordField.setText("");
        tutInstance.countButton.fire();

        assertTrue(tutInstance.alert.isShowing());
    }

    @Test
    public void testClearButton() {
        tutInstance.WordField.setText("Only");
        tutInstance.CountField.setText("1");
        tutInstance.clearButton.fire();

        String expectedWord = "";
        String actualWord = tutInstance.WordField.getText();
        String expectedCount = "";
        String actualCount = tutInstance.CountField.getText();

        assertEquals(expectedWord, actualWord);
        assertEquals(expectedCount, actualCount);
    }
}