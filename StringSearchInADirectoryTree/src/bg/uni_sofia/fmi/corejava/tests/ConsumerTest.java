package bg.uni_sofia.fmi.corejava.tests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bg.uni_sofia.fmi.corejava.project.Consumer;
import bg.uni_sofia.fmi.corejava.project.Producer;
import bg.uni_sofia.fmi.corejava.project.Store;
import bg.uni_sofia.fmi.corejava.project.StringProduct;

public class ConsumerTest {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullStringToFind() {
        Store store = new Store();
        Consumer consumer = new Consumer(store, null);
    }

    @Test(expected = NullPointerException.class)
    public void testConsumeNullArgument() {
        Store store = new Store();
        store.add(null);
        store.add(new StringProduct());
        Consumer consumer = new Consumer(store, "test");
        new Thread(consumer).start();
    }

    @Test
    public void testConsumeFoundStringText() {
        Store store = new Store();
        Producer producer = new Producer(store, Paths.get("D:\\"));
        Consumer consumer = new Consumer(new Store(), "lorem");
        new Thread(producer).start();
        new Thread(consumer).start();
        System.out
                .print("Found in file: D:\\Games\\FIFA 14\\Game\\data\\loc\\leagues\\stringSearch.txt on line 4 : In vitae tellus est. Aenean sit amet nisl euismod, consectetur lorem eu, ");
        assertEquals(
                "Found in file: D:\\Games\\FIFA 14\\Game\\data\\loc\\leagues\\stringSearch.txt on line 4 : In vitae tellus est. Aenean sit amet nisl euismod, consectetur lorem eu, ",
                outContent.toString());
    }

    @Test
    public void testFoundStringFalse() {
        Store store = new Store();
        Producer producer = new Producer(store, Paths.get("D:\\"));
        Consumer consumer = new Consumer(new Store(), "sdfsd");
        new Thread(producer).start();
        new Thread(consumer).start();
        assertEquals(false, Consumer.foundString());
    }

}
