package bg.uni_sofia.fmi.corejava.project;

import java.util.Iterator;

public class Consumer implements Runnable {
    private static boolean found = false;
    private final Store store;
    private String stringToFind;

    public Consumer(Store store, String stringToFind) {
        this.store = store;
        setStringToFind(stringToFind);
    }

    private void setStringToFind(String stringToFind){
        if(stringToFind == null){
            throw new IllegalArgumentException("String cannot be null");
        }

        this.stringToFind = stringToFind;
    }

    public static boolean foundString() {
        return found;
    }

    @Override
    public void run() {
        boolean interrupted = false;
        while (true) {
            synchronized (store) {
                Iterator<StringProduct> i = store.iterator();
                while (i.hasNext()) {
                    StringProduct product = i.next();
                    try {
                        consume(product);
                    } catch (InterruptedException ex) {
                        interrupted = true;
                    }
                    i.remove();
                }

                if (interrupted) {
                    return;
                }

                try {
                    store.wait();
                } catch (InterruptedException e) {
                    interrupted = true;
                }
            }
        }

    }

    private void consume(StringProduct product) throws InterruptedException {
        if (product.findString(stringToFind)) {
            System.out.printf("Found in file: %s on line %d : %s\n", product.getFileName(), product.getLineNumber(), product.getLineOfText());
            found = true;
        }
    }
}
