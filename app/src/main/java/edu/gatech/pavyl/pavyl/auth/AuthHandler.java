package edu.gatech.pavyl.pavyl.auth;

/**
 * Created by aidancbrady on 9/25/17.
 */

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles all interactions with the app's authentication server. The authentication
 * server is a Java-based intermediary between the client and the MySQL database, handling
 * encryption, authentication lockout, and other important details.
 *
 * Encryption details:
 * Each time a new account is generated, a series of steps are taken to ensure user privacy on the
 * server side.
 *
 * Registration process (assuming new account is valid):
 *     (1) A username and password (as well as additional data) are received by the server's
 *         connection handler.
 *     (2) A 32-byte "salt" is generated from a SecureRandom generator and converted into hex.
 *     (3) The salt and client-provided password are concatenated together and encrypted (hashed)
 *         with the SHA-256 algorithm.
 *     (4) The username, salt and hashed salt + password are stored in a new row in the database's
 *         account table.
 *     (5) A success message is sent back to the client.
 *
 * By "salting" passwords as well as encrypting them, an additional layer of security is provided
 * to prevent rainbow tables or hash dictionaries from being used by intruders to brute force the
 * solution to a password. Even if a hacker managed to obtain a hash and salt for a password, it
 * would take years of modern computing power to work out the true key.
 *
 * Login process (assuming account exists):
 *     (1) A username and password are received by the server's connection handler.
 *     (2) The server loads the corresponding salt and password hash values from the database.
 *     (3) A "testing" hash is created by hashing the salt and client-provided password utilizing
 *         SHA-256.
 *     (4) The testing hash and password hash stored in the database are tested for equality.
 *     (5) A message is returned to the client with a success or failure message, depending on the
 *         result of the previous equality check.
 */
public class AuthHandler
{
    /**
     * Sends a collection of messages to the server utilizing a quick socket connection.
     * @param messages - list of messages to send
     * @return returned messages
     */
    private static List<String> sendMessages(String... messages)
    {
        try {
            List<String> responses = new ArrayList<String>();
            Socket socket = new Socket(SharedData.SERVER_IP, SharedData.SERVER_PORT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            for(String message : messages)
            {
                writer.println(message);
            }

            String reading = "";

            while((reading = reader.readLine()) != null)
            {
                responses.add(reading.trim());
            }

            socket.close();
            
            return responses;
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Asynchronously runs the login protocol, running the completion handler in the UI thread when
     * the operation is complete.
     * @param username - username to log in with
     * @param password - password to log in with
     * @param completionHandler - completion handler to trigger upon completion
     */
    public static void login(final String username, final String password, final ResponseHandler completionHandler)
    {
        new AsyncWrapper(completionHandler) {
            @Override
            public Response doInBackground(Void... params)
            {
                List<String> response = sendMessages(compileMsg("AUTH", username, password));
                Response ret = null;

                try {
                    if(response != null)
                    {
                        String[] split = response.get(0).split(SharedData.SPLITTER);

                        if(split[0].equals("ACCEPT"))
                        {
                            SessionData.username = username;

                            if(split.length == 2)
                            {
                                SessionData.loadExtraData(split[1]);
                            }

                            return Response.ACCEPT;
                        }
                        else {
                            return new Response(false, split[1]);
                        }
                    }
                } catch(Exception e) {
                    SessionData.reset();
                    e.printStackTrace();
                }

                return Response.ERROR;
            }
        }.execute();
    }

    /**
     * Asynchronously runs the register protocol, running the completion handler in the UI thread
     * when the operation is complete.
     * @param username - username for the new account
     * @param password - password for the new account
     * @param extra - extra data for this user to store in the account database
     * @param completionHandler - completion handler to trigger upon completion
     */
    public static void register(final String username, final String password, final String extra, final ResponseHandler completionHandler)
    {
        new AsyncWrapper(completionHandler) {
            @Override
            public Response doInBackground(Void... params)
            {
                List<String> response = sendMessages(compileMsg("REGISTER", username, password, extra));
                Response ret = null;

                try {
                    if(response != null)
                    {
                        String[] split = response.get(0).split(SharedData.SPLITTER);

                        if(split[0].equals("ACCEPT"))
                        {
                            SessionData.username = username;
                            SessionData.loadExtraData(extra);
                            return Response.ACCEPT;
                        }
                        else {
                            return new Response(false, split[1]);
                        }
                    }
                } catch(Exception e) {
                    SessionData.reset();
                    e.printStackTrace();
                }

                return Response.ERROR;
            }
        }.execute();
    }

    /**
     * Compiles a message with the common delimiter between server and client, stored in SharedData.
     * @param strings - collection of strings to compile into one-line message
     * @return completed string
     */
    private static String compileMsg(Object... strings)
    {
        StringBuilder str = new StringBuilder();

        for(int i = 0; i < strings.length; i++)
        {
            str.append(strings[i]);

            if(i < strings.length-1)
            {
                str.append(SharedData.SPLITTER);
            }
        }

        return str.toString();
    }

    /**
     * A mutable object representing a response from the server.
     */
    public static class Response
    {
        //Default responses for convenience
        private static final Response ERROR = new Response(false, "Error");
        private static final Response ACCEPT = new Response(true, null);

        /** Whether or not the server accepted the client's request. */
        public boolean accept;
        /** The message the server responded with, if it exists. */
        public String message;

        /**
         * Creates a new response object from a boolean 'accept' field and an optional message
         * field.
         * @param accept - if the server accepted the client's request
         * @param message - the message the server responded with (possibly null)
         */
        public Response(boolean accept, String message)
        {
            this.accept = accept;
            this.message = message;
        }
    }

    /**
     * A wrapper of Android's "AsyncTask" data structure, allowing for some more dynamic code on my
     * end.
     */
    public static abstract class AsyncWrapper extends AsyncTask<Void, Integer, Response>
    {
        private ResponseHandler handler;

        public AsyncWrapper(ResponseHandler h)
        {
            handler = h;
        }

        @Override
        protected void onPostExecute(Response result)
        {
            handler.handle(result);
        }
    }

    /**
     * This should be implemented as an anonymous class when handling responses of auth methods.
     */
    public static interface ResponseHandler
    {
        /**
         * Handles a response from the server.
         * @param response - response to handle
         */
        public void handle(Response response);
    }
}