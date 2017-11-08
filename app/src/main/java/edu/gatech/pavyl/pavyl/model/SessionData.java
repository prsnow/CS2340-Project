package edu.gatech.pavyl.pavyl.model;

import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Data that is maintained for a single user session.
 */
public class SessionData
{
	/** Current user's username */
	@Nullable
	private static String username;
	/** Extra data imported into a map for O(1) lookups */
	public static final Map<String, String> extraData = new HashMap<>();

	/**
	 * Loads extra data from a string format, with SharedData.DATA_SPLIT as the delimiter.
	 * @param s - extra data string
	 */
	public static void loadExtraData(String s)
	{
		String[] split = s.split(SharedData.DATA_SPLIT);

		for(String dataPair : split)
		{
			String[] data = dataPair.split("=");
			extraData.put(data[0], data[1]);
		}
	}

	/**
	 * Returns the username stored in this session cache.
	 * @return username in the session cache
	 */
	public static String getUsername() {
		return username;
	}

	/**
	 * Sets the username stored in this session cache.
	 * @param user - username to set
	 */
	public static void setUsername(String user) {
		username = user;
	}

	/**
	 * Clears all session data.
	 */
	public static void reset()
	{
		username = null;
		extraData.clear();
	}
}
