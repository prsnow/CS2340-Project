package edu.gatech.pavyl.pavyl;

import edu.gatech.pavyl.pavyl.model.RatData;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;

/**
 * Tests for edu.gatech.pavyl.pavyl.model.RatData.parse(String s).
 *
 * This method parses a string containing data for a single rat sighting.
 * Returns null if there is an error during parsing.
 *
 */
public class yhoshi3UnitTest {


    private String[] data_fields = {"data_key", "date", "location", "zip", "address", "city", "borough", "latitude", "longitude"};


    /**
     * Make sure correct RatData object is created when a string with the correct number of fields
     * which are all non-empty is passed.
     */
    @Test
    public void testValidString() {
        String str = "(12345,11/11/2017 10:32:52 AM,CULC,30332,123 NW,Atlanta,borough,102,34)";
        RatData toTest = RatData.parse(str);
        assertNotNull(toTest);

        String[] parsedData = {"12345", "11/11/2017 10:32:52 AM", "CULC", "30332", "123 NW", "Atlanta", "borough", "102", "34"};
        HashMap<String, String> dataMap = new HashMap<>();
        for (int i = 0; i < data_fields.length; i++) {
            dataMap.put(data_fields[i], parsedData[i]);
        }
        RatData correct = new RatData(dataMap);


        for (int i = 0; i < data_fields.length; i++) {
            String field = data_fields[i];
            assertEquals(correct.getData(field), toTest.getData(field));
        }
    }

    /**
     * Make sure null is returned when there are too many fields, and all fields are empty.
     *
     *
     **/
    @Test
    public void testTooLongEmptyFields() {
        String str = "(,,,,,,,,,)";
        RatData toTest = RatData.parse(str);
        assertNull(toTest);
    }

    /**
     * Make sure null is returned when there are not enough fields.
     */
    @Test
    public void testShortString() {
        String str = "(12345,11/11/2017 10:32:52 AM,CULC)";
        RatData toTest = RatData.parse(str);
        assertNull(toTest);
    }

    /**
     * Make sure null is returned when there are too many fields.
     */
    @Test
    public void testLongString() {
        String str = "(12345,11/11/2017 10:32:52 AM,CULC,CULC,30332,123 NW,Atlanta,borough,102,34)";
        RatData toTest = RatData.parse(str);
        assertNull(toTest);
    }

    /**
     * Make sure correct rat data is created when the number of fields are correct, but some are empty.
     */
    @Test
    public void testValidWithEmpty() {
        String str = "(12345,11/11/2017 10:32:52 AM,CULC,30332,123 NW,Atlanta,,102,34)";
        RatData toTest = RatData.parse(str);
        assertNotNull(toTest);

        RatData correct;
        String[] dataArr = {"12345", "11/11/2017 10:32:52 AM", "CULC", "30332", "123 NW", "Atlanta", "", "102", "34"};
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < data_fields.length; i++) {
            map.put(data_fields[i], dataArr[i]);
        }
        correct = new RatData(map);

        for (int i = 0; i < data_fields.length; i++) {
            String field = data_fields[i];
            assertEquals(correct.getData(field), toTest.getData(field));
        }
    }

    /**
     * Make sure null is returned when no fields exist.
     */
    @Test
    public void testEmpty() {
        String str = "()";
        RatData toTest = RatData.parse(str);
        assertNull(toTest);
    }





}