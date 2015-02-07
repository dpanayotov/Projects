package bg.uni_sofia.fmi.corejava.project;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;

public class Producer implements Runnable {
    private final Store store;
    private Path path;
    private Logger logger = LogManager.getLogger(Producer.class);

    public Producer(Store store, Path path) {
        this.store = store;
        this.path = path;
    }

    @Override
    public void run() {
        ArrayList<StringProduct> products = new ArrayList<>();
        int productCount = 0;
        try(InputStreamReader isr =  new InputStreamReader(new FileInputStream(path.toFile().toString()))) {
            try (BufferedReader bufferedReader = new BufferedReader(isr)) {
                String line;
                int lineNumber = 1;
                while ((line = bufferedReader.readLine()) != null && productCount <= 5) {
                    products.add(new StringProduct(line, path.toString(), lineNumber++));
                    productCount++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        synchronized (store) {
            while (store.isFull()) {
                try {
                    logger.info("Store is full - waiting");
                    store.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logger.error(e);
                }
            }

            store.addAll(products);
            store.notify();
        }
    }
}
