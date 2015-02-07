package bg.uni_sofia.fmi.corejava.tests;

import bg.uni_sofia.fmi.corejava.project.Producer;
import bg.uni_sofia.fmi.corejava.project.Store;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

public class ProducerTest {

    @Test(expected = IllegalArgumentException.class)
    public void testAddProductNullPathArgument() {
        Producer producer = new Producer(new Store(), null);
        new Thread(producer).start();
    }

    @Test(expected = IOException.class)
    public void testAddProductFileNotExist() {
        Producer producer = new Producer(new Store(), Paths.get("D:\\sdjf.txt"));
        new Thread(producer).start();
    }
}