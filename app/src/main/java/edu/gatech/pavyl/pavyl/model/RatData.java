package edu.gatech.pavyl.pavyl.model;

import java.util.HashMap;

import edu.gatech.pavyl.pavyl.network.SharedData;

/**
 * This class contains data pertaining to a single rat sighting. All data is organized into a single
 * HashMap, which can be accessed via some helper methods.
 */
public class RatData {
    /**
     * The map containing all data pertaining to this rat sighting.
     */
    private HashMap<String, String> data = new HashMap<>();

    /**
     * Creates a new RatData object from an existing data map.
     */
    public RatData(HashMap<String, String> map) {
        data = map;
    }

    /**
     * Retrieves a specific value from this rat sighting's data map.
     *
     * @param key - key of value to retrieve
     * @return value associated with key in data map
     */
    public String getData(String key) {
        return data.get(key);
    }

    /**
     * Returns this rat sighting's data map in its entirety.
     *
     * @return the data map
     */
    public HashMap<String, String> getDataMap() {
        return data;
    }

    /**
     * Returns a String representation of this RatData object for transfer to server, containing
     * all data fields.
     *
     * @return String representation of RatData object for server transfer
     */
    public String compile() {
        return getData(SharedData.DATA_KEY_ID) + SharedData.DATA_SPLIT + compileNoKey();
    }

    /**
     * Returns a String representation of this RatData object for transfer to server, excluding
     * the data_key field.
     *
     * @return String representation of RatData object without data_key field
     */
    public String compileNoKey() {
        String ret = "";

        for (int i = 0; i < SharedData.DATA_FIELDS.length; i++) {
            ret += getData(SharedData.DATA_FIELDS[i]);

            if (i < SharedData.DATA_FIELDS.length - 1) {
                ret += SharedData.DATA_SPLIT;
            }
        }

        return ret;
    }

    /**
     * Parses a string containing data for a single rat sighting.
     *
     * @param s - string to parse
     * @return parsed RatData object, null if there's an error during parsing
     */
    public static RatData parse(String s) {
        //trim parenthesis
        s = s.substring(1, s.length() - 1);
        String[] split = s.split(SharedData.DATA_SPLIT, -1);

        //ensure we have all the data
        if (split.length != 9) {
            return null;
        }

        HashMap<String, String> newData = new HashMap<>();

        //add the data to the map
        newData.put(SharedData.DATA_KEY_ID, split[0]);

        for (int i = 0; i < SharedData.DATA_FIELDS.length; i++) {
            newData.put(SharedData.DATA_FIELDS[i], split[i + 1]);
        }

        return new RatData(newData);
    }
}
