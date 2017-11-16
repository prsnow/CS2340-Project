package edu.gatech.pavyl.pavyl.model;

import java.util.HashSet;
import java.util.Set;

public class SharedData 
{
	//Server data
	public static final String SERVER_IP = "104.236.13.142";
	public static final int SERVER_PORT = 29421;
	
	//CSV splitters
	public static final String SPLITTER = "%1%";
	public static final String DATA_SPLIT = ",";

	public static final int MAX_USERNAME_LENGTH = 32;

	/**
	 * All the data values contained in a RatData object.
	 */
	public static final String[] DATA_FIELDS = new String[]
			{"date", "location", "zip", "address", "city", "borough", "latitude", "longitude"};

	/**
	 * The database name for the data key.
	 */
	public static final String DATA_KEY_ID = "data_key";
}
