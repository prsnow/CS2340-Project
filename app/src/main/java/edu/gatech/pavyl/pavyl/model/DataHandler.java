package edu.gatech.pavyl.pavyl.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * The DataHandler class provides tools to access the database of rat sightings. Everything is
 * processed through the server intermediary for convenience and safety.
 */
public final class DataHandler {
    /**
     * Private constructor- this is a utility class and should not be instantiated.
     */
    private DataHandler() {}

    /**
     * Request a certain amount of rat data from the server. The limit and offset will be passed
     * into the database query.
     *  @param offset            - the index to read the data values from
     * @param completionHandler - a completion handler, fired on UI thread
     */
    public static void requestData(final int offset, final NetworkUtils.ResponseHandler completionHandler) {
        new NetworkUtils.AsyncWrapper(completionHandler) {
            @Override
            public NetworkUtils.Response doInBackground(Void... params) {
                List<String> response = NetworkUtils.sendMessages(NetworkUtils.compileMsg("GETDATA", 100, offset));

                try {
                    if (response != null) {
                        String[] split = response.get(0).split(SharedData.SPLITTER);

                        if ("ACCEPT".equals(split[0])) {
                            NetworkUtils.Response ret = new NetworkUtils.Response(true, null);
                            ret.setData(split);
                            return ret;
                        } else {
                            return new NetworkUtils.Response(false, split[1]);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return NetworkUtils.Response.ERROR;
            }
        }.execute();
    }

    /**
     * Request a certain amount of rat data from the server between a provided start and end date.
     *
     * @param startDate         - the date to begin reading data from
     * @param endDate           - the date to stop reading data from
     * @param completionHandler - a completion handler, fired on UI thread
     *                          when the response is received
     */
    public static void requestDataInRange(final Calendar startDate, final Calendar endDate, final NetworkUtils.ResponseHandler completionHandler) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        final String start = fmt.format(startDate.getTime());
        final String end = fmt.format(endDate.getTime());

        new NetworkUtils.AsyncWrapper(completionHandler) {
            @Override
            public NetworkUtils.Response doInBackground(Void... params) {
                List<String> response = NetworkUtils.sendMessages(NetworkUtils.compileMsg("GETRANGE", start, end));

                try {
                    if (response != null) {
                        String[] split = response.get(0).split(SharedData.SPLITTER);

                        if ("ACCEPT".equals(split[0])) {
                            NetworkUtils.Response ret = new NetworkUtils.Response(true, null);
                            ret.setData(split);
                            return ret;
                        } else {
                            return new NetworkUtils.Response(false, split[1]);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return NetworkUtils.Response.ERROR;
            }
        }.execute();
    }

    /**
     * Request monthly chart data between a specified period.
     *
     * @param startDate         - the date to begin reading data from
     * @param endDate           - the date to stop reading data from
     * @param completionHandler - a completion handler, fired on UI thread
     *                          when the response is received
     */
    public static void requestMonthlyChartData(final Calendar startDate, final Calendar endDate, final NetworkUtils.ResponseHandler completionHandler) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        final String start = fmt.format(startDate.getTime());
        final String end = fmt.format(endDate.getTime());

        new NetworkUtils.AsyncWrapper(completionHandler) {
            @Override
            public NetworkUtils.Response doInBackground(Void... params) {
                List<String> response = NetworkUtils.sendMessages(NetworkUtils.compileMsg("MGRAPH", start, end));

                try {
                    if (response != null) {
                        String[] split = response.get(0).split(SharedData.SPLITTER);

                        if ("ACCEPT".equals(split[0])) {
                            NetworkUtils.Response ret = new NetworkUtils.Response(true, null);
                            ret.setData(split);
                            return ret;
                        } else {
                            return new NetworkUtils.Response(false, split[1]);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return NetworkUtils.Response.ERROR;
            }
        }.execute();
    }

    /**
     * Sends a request to add a new rat sighting data element to the database. The RatData object
     * does not require a data_key value, as this is generated by the server.
     *
     * @param newData           - the RatData object to add to the server
     * @param completionHandler - a completion handler, fired on UI thread when the response is
     *                          received
     */
    public static void addData(final RatData newData, final NetworkUtils.ResponseHandler completionHandler) {
        new NetworkUtils.AsyncWrapper(completionHandler) {
            @Override
            public NetworkUtils.Response doInBackground(Void... params) {
                List<String> response = NetworkUtils.sendMessages(NetworkUtils.compileMsg("ADDDATA", newData.compileNoKey()));

                if (response != null) {
                    String[] split = response.get(0).split(SharedData.SPLITTER);

                    if ("ACCEPT".equals(split[0])) {
                        return NetworkUtils.Response.ACCEPT;
                    } else {
                        return new NetworkUtils.Response(false, split[1]);
                    }
                }

                return NetworkUtils.Response.ERROR;
            }
        }.execute();
    }

    /**
     * Sends a request to edit a specific rat sighting data element in the database. This is done by
     * removing the data element with the provided data object's key, and then adding a new element
     * with the values in the provided data object.
     *
     * @param editedData        - the RatData object with updated values
     * @param completionHandler - a completion handler, fired on UI thread when the response is
     *                          received
     */
    public static void editData(final RatData editedData, final NetworkUtils.ResponseHandler completionHandler) {
        new NetworkUtils.AsyncWrapper(completionHandler) {
            @Override
            public NetworkUtils.Response doInBackground(Void... params) {
                List<String> response = NetworkUtils.sendMessages(NetworkUtils.compileMsg("EDITDATA", editedData.compile()));

                if (response != null) {
                    String[] split = response.get(0).split(SharedData.SPLITTER);

                    if ("ACCEPT".equals(split[0])) {
                        return NetworkUtils.Response.ACCEPT;
                    } else {
                        return new NetworkUtils.Response(false, split[1]);
                    }
                }

                return NetworkUtils.Response.ERROR;
            }
        }.execute();
    }

    /**
     * Sends a request to delete a specific rat sighting data element from the database. All that is
     * needed for this is the "data_key" parameter in the data map.
     *
     * @param toDelete          - the RatData object to delete
     * @param completionHandler - a completion handler, fired on UI thread when the response is
     *                          received
     */
    public static void deleteData(final RatData toDelete, final NetworkUtils.ResponseHandler completionHandler) {
        new NetworkUtils.AsyncWrapper(completionHandler) {
            @Override
            public NetworkUtils.Response doInBackground(Void... params) {
                List<String> response = NetworkUtils.sendMessages(NetworkUtils.compileMsg("DELDATA",
                        toDelete.getData(SharedData.DATA_KEY_ID)));

                if (response != null) {
                    String[] split = response.get(0).split(SharedData.SPLITTER);

                    if ("ACCEPT".equals(split[0])) {
                        return NetworkUtils.Response.ACCEPT;
                    } else {
                        return new NetworkUtils.Response(false, split[1]);
                    }
                }

                return NetworkUtils.Response.ERROR;
            }
        }.execute();
    }
}
