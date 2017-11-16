package edu.gatech.pavyl.pavyl;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.gatech.pavyl.pavyl.model.NetworkUtils;

import static org.junit.Assert.*;

/**
 * Testing NetworkUtils.sendMessages()
 */
public class ValerieUnitTest {
    /**
     * Checks if sendMessages() returns null when null is passed in.
     * @throws Exception encountered during testing
     */
    @Test
    public void sendMessages_null() throws Exception {

        assertEquals(NetworkUtils.sendMessages(null), null);
    }


    /**
     * Checks to make sure sendMessages() trims the front and end of each line before
     * creating a message.
     *@throws Exception encountered during testing
     */
    @Test
    public void sendMessage_trimEnds() throws Exception {
        List<String> array = new ArrayList<>();
        array.add("hello i am you");
        assertEquals(NetworkUtils.sendMessages("    hello i am you   "), array);

    }

    /**
     *Check to make sure sendMessages() does not trim space between words
     * @throws Exception encountered during testing
     */
    @Test
    public void sendMessage_trimWords() throws Exception {
        List<String> array = new ArrayList<>();
        array.add("blah    blahblah    blah");
        assertEquals(NetworkUtils.sendMessages("blah", "    blah",
                "blah    ", "blah"), array);
    }








}
