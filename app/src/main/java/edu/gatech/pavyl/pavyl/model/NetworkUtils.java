package edu.gatech.pavyl.pavyl.model;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NetworkUtils {
    /**
     * Sends a collection of messages to the server utilizing a quick socket connection.
     *
     * @param messages - list of messages to send
     * @return returned messages
     */
    public static List<String> sendMessages(String... messages) {
        try {
            List<String> responses = new ArrayList<>();
            Socket socket = new Socket(SharedData.SERVER_IP, SharedData.SERVER_PORT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            for (String message : messages) {
                writer.println(message);
            }

            String reading;

            while ((reading = reader.readLine()) != null) {
                responses.add(reading.trim());
            }

            socket.close();

            return responses;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * A mutable object representing a response from the server.
     */
    public static class Response {
        //Default responses for convenience
        public static final Response ERROR = new Response(false, "Error");
        public static final Response ACCEPT = new Response(true, null);

        /**
         * Whether or not the server accepted the client's request.
         */
        public boolean accept;
        /**
         * The message the server responded with, if it exists.
         */
        public String message;
        /**
         * Additional data received from server
         */
        public String[] data;

        /**
         * Creates a new response object from a boolean 'accept' field and an optional message
         * field.
         *
         * @param accept  - if the server accepted the client's request
         * @param message - the message the server responded with (possibly null)
         */
        public Response(boolean accept, String message) {
            this.accept = accept;
            this.message = message;
        }

        /**
         * Sets the stored data of this Response object.
         * @param data - data to store
         */
        public void setData(String[] data) {
            this.data = data;
        }
    }

    /**
     * A wrapper of Android's "AsyncTask" data structure, allowing for some more dynamic code on my
     * end.
     */
    public abstract static class AsyncWrapper extends AsyncTask<Void, Integer, Response> {
        private ResponseHandler handler;

        /**
         * Creates an AsyncWrapper with a provided ResponseHandler, which will be triggered after
         * a response is received
         * @param h - completion handler
         */
        public AsyncWrapper(ResponseHandler h) {
            handler = h;
        }

        @Override
        protected void onPostExecute(Response result) {
            handler.handle(result);
        }
    }

    /**
     * This should be implemented as an anonymous class when handling responses of auth methods.
     */
    public static interface ResponseHandler {
        /**
         * Handles a response from the server.
         *
         * @param response - response to handle
         */
        public void handle(Response response);
    }

    /**
     * Compiles a message with the common delimiter between server and client, stored in SharedData.
     *
     * @param strings - collection of strings to compile into one-line message
     * @return completed string
     */
    public static String compileMsg(Object... strings) {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < strings.length; i++) {
            str.append(strings[i]);

            if (i < strings.length - 1) {
                str.append(SharedData.SPLITTER);
            }
        }

        return str.toString();
    }
}
