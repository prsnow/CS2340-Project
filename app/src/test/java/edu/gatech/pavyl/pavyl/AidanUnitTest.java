package edu.gatech.pavyl.pavyl;

import org.junit.Test;

import edu.gatech.pavyl.pavyl.model.SharedData;

import static edu.gatech.pavyl.pavyl.model.NetworkUtils.*;
import static org.junit.Assert.assertEquals;

/**
 * Testing NetworkUtils.compileMsg()
 */
public class AidanUnitTest {
    /**
     * Ensure compileMsg() returns null when null is passed in.
     * @throws Exception - exception encountered during testing
     */
    @Test
    public void compileMsg_null() throws Exception {
        assertEquals(compileMsg(null), null);
    }
    /**
     * Make sure calling compileMsg() with only one string doesn't place the splitter before or
     * after the string.

     * @throws Exception - exception encountered during testing
     */
    @Test
    public void compileMsg_single() throws Exception {
        assertEquals(compileMsg("test"), "test");
    }

    /**
     * Make sure calling compileMsg() with multiple strings doesn't place a splitter after the last
     * string and includes every string.
     * @throws Exception - exception encountered during testing
     */
    @Test
    public void compileMsg_many() throws Exception {
        String split = SharedData.SPLITTER;
        String expected = "test1" + split + "test2" + split + "test3";
        assertEquals(compileMsg("test1", "test2", "test3"), expected);
    }

    /**
     * Make sure calling compileMsg() with a non-string object doesn't fail.
     * @throws Exception - exception encountered during testing
     */
    @Test
    public void compileMsg_object() throws Exception {
        assertEquals(compileMsg(new TestObject()), "test");
    }

    private static class TestObject {
        @Override
        public String toString() {
            return "test";
        }
    }
}
