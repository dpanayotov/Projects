package bg.uni_sofia.fmi.corejava.tests;

import bg.uni_sofia.fmi.corejava.project.Store;
import bg.uni_sofia.fmi.corejava.project.StringProduct;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StoreTest {

    @Test
    public void testFullStore() {
        Store store = new Store();
        for (int i = 0; i < 1002; i++) {
            store.add(new StringProduct());
        }

        assertEquals(true, store.isFull());
    }

    @Test
    public void testNotFullStore() {
        Store store = new Store();
        store.add(new StringProduct());
        assertEquals(false, store.isFull());
    }

}
