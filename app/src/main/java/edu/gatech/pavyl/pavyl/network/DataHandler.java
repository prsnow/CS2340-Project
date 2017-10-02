package edu.gatech.pavyl.pavyl.network;

import java.util.List;

import static edu.gatech.pavyl.pavyl.network.NetworkUtils.*;

/**
 * The DataHandler class provides tools to access the database of rat sightings. Everything is
 * processed through the server intermediary for convenience and safety.
 */
public class DataHandler {
    /**
     * Request a certain amount of rat data from the server. The limit and offset will be passed
     * into the database query.
     *
     * @param limit             - how many data values we want to retrieve
     * @param offset            - the index to read the data values from
     * @param completionHandler - a completion handler, fired on UI thread
     *                          when the response is received
     */
    public static void requestData(final int limit, final int offset, final ResponseHandler completionHandler) {
        new NetworkUtils.AsyncWrapper(completionHandler) {
            @Override
            public Response doInBackground(Void... params) {
                List<String> response = sendMessages(compileMsg("GETDATA", limit, offset));
                Response ret = null;

                try {
                    if (response != null) {
                        String[] split = response.get(0).split(SharedData.SPLITTER);

                        if (split[0].equals("ACCEPT")) {
                            ret = new Response(true, null);
                            ret.setData(split);
                            return ret;
                        } else {
                            return new Response(false, split[1]);
                        }
                    }
                } catch (Exception e) {
                    SessionData.reset();
                    e.printStackTrace();
                }

                return Response.ERROR;
            }
        }.execute();
    }
}
