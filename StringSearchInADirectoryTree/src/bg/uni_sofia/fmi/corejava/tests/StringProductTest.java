package bg.uni_sofia.fmi.corejava.tests;

import bg.uni_sofia.fmi.corejava.project.StringProduct;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringProductTest {
    StringProduct stringProduct = new StringProduct("lineOfText", "fileName", 0);

    @Test
    public void testGetLineNumber() throws Exception {
        assertEquals(0, stringProduct.getLineNumber());
    }

    @Test
    public void testGetLineOfText() throws Exception {
        assertEquals("lineOfText", stringProduct.getLineOfText());
    }

    @Test
    public void testFindString() throws Exception {
        assertEquals(true, stringProduct.findString("line"));
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("lineOfText, fileName", stringProduct.toString());
    }
}