package edu.gatech.pavyl.pavyl.network;

import java.util.HashMap;
import java.util.Map;

/**
 * Data that is mainained for a single user session.
 */
public class SessionData
{
	/** Current user's username */
	public static String username;
	/** Extra data in string format */
	private static String extraDataString;
	/** Extra data imported into a map for O(1) lookups */
	public static Map<String, String> extraData = new HashMap<>();

	/**
	 * Loads extra data from a string format, with SharedData.DATA_SPLIT as the delimiter.
	 * @param s - extra data string
	 */
	public static void loadExtraData(String s)
	{
		extraDataString = s;

		String[] split = s.split(SharedData.DATA_SPLIT);

		for(String dataPair : split)
		{
			String[] data = dataPair.split("=");
			extraData.put(data[0], data[1]);
		}
	}

	/**
	 * Clears all session data.
	 */
	public static void reset()
	{
		username = null;
		extraDataString = null;
		extraData.clear();
	}
}
