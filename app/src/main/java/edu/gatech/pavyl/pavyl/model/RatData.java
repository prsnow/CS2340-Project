package edu.gatech.pavyl.pavyl.model;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains data pertaining to a single rat sighting. All data is organized into a single
 * HashMap, which can be accessed via some helper methods.
 */
public class RatData {
    /** The map containing all data pertaining to this rat sighting. */
    private HashMap<String, String> data = new HashMap<>();

    /** Creates a new RatData object from an existing data map. */
    public RatData(HashMap<String, String> map) {
        data = map;
    }

    /**
     * Retrieves a specific value from this rat sighting's data map.
     * @param key - key of value to retrieve
     * @return value associated with key in data map
     */
    public String getData(String key) {
        return data.get(key);
    }

    /**
     * Returns this rat sighting's data map in its entirety.
     * @return the data map
     */
    public HashMap<String, String> getDataMap() {
        return data;
    }

    /**
     * Parses a string containing data for a single rat sighting.
     * @param s - string to parse
     * @return parsed RatData object, null if there's an error during parsing
     */
    public static RatData parse(String s) {
        //trim parenthesis
        s = s.substring(1, s.length() - 1);
        String[] split = s.split(",", -1);

        //ensure we have all the data
        if (split.length != 9) {
            return null;
        }

        HashMap<String, String> newData = new HashMap<>();

        //add the data to the map
        newData.put("data_key", split[0]);
        newData.put("date", split[1]);
        newData.put("location", split[2]);
        newData.put("zip", split[3]);
        newData.put("address", split[4]);
        newData.put("city", split[5]);
        newData.put("borough", split[6]);
        newData.put("latitude", split[7]);
        newData.put("longitude", split[8]);

        return new RatData(newData);
    }
}
