package bg.uni_sofia.fmi.corejava.project;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Store store = new Store();
    private static String stringToFind;
    private static Path searchDir;
    private static int filesVisited = 0;
    private static int textFiles = 0;
    private static boolean found = false;
    private static Logger logger = LogManager.getLogger(Main.class);
    private static List<Thread> consumers = new ArrayList<>();
    private static int CONSUMERS_COUNT = 30;

    public static void main(String[] args) throws InterruptedException {

        gatherInput();
        addConsumers();

        long t0 = System.currentTimeMillis();
        System.out.println("Parallel search: ");

        consumers.forEach(Thread::start);

        try {
            startSearchingParallel();
        } catch (IOException e) {
            logger.error(e);
        }

        for (Thread t : consumers) {
            t.interrupt();
            t.join();
        }

        found = Consumer.foundString();

        long t1 = System.currentTimeMillis();
        long t = t1 - t0;

        System.out.println("Parallel execution time: " + t + " ms");

        t0 = System.currentTimeMillis();
        System.out.println("Linear search: ");

        try {
            startSearchingLinear();
        } catch (IOException e) {
            logger.error(e);
        }

        t1 = System.currentTimeMillis();
        t = t1 - t0;

        System.out.println("Linear execution time: " + t + " ms");
        System.out.println("Visited files: " + filesVisited);
        System.out.println("Text files: " + textFiles);

        if (!found) {
            System.err.println("String was not found!");
        }
    }

    private static void gatherInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a string to look for: ");
        stringToFind = scanner.next();

        System.out.print("Enter a directory in which to search: ");
        String dir = scanner.next();
        while (!isValidPath(dir)) {
            System.err.println("Invalid path!");
            System.out.print("Enter a directory in which to search: ");
            dir = scanner.next();
        }

        searchDir = Paths.get(dir);
    }

    private static void startSearchingLinear() throws IOException {
        filesVisited = 1;
        Files.walkFileTree(searchDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                logger.info(this.getClass().getEnclosingMethod().getName());
                logger.error(String.format("Inaccessible file - %s", file.toFile().toString()));
                return FileVisitResult.SKIP_SUBTREE; //access is denied
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.getFileName().toString().endsWith(".txt")) {
                    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file.toFile())))) {
                        String line;
                        int lineNumber = 1;
                        while ((line = bufferedReader.readLine()) != null) {
                            StringProduct product = new StringProduct(line, file.toString(), lineNumber++);
                            if (product.findString(stringToFind)) {
                                System.out.printf("Found in file: %s on line %d : %s\n", product.getFileName(), product.getLineNumber(), product.getLineOfText());
                                found = true;
                            }
                        }
                    }
                }
                filesVisited++;
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void startSearchingParallel() throws IOException {
        filesVisited = 1;
        Files.walkFileTree(searchDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                logger.info(this.getClass().getEnclosingMethod().getName());
                logger.error(String.format("Inaccessible file - %s", file.toFile().toString()));
                return FileVisitResult.SKIP_SUBTREE; //access is denied
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".txt")) {
                    textFiles++;
                    try {
                        executeThreads(file);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                filesVisited++;
                return FileVisitResult.CONTINUE;
            }
        });
    }

    //if method can't get path throws exception, meaning that the string is invalid directory
    private static boolean isValidPath(String path) {
        File file = new File(path);
        try {
            file.getCanonicalPath();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static void addConsumers() {
        for (int i = 0; i < CONSUMERS_COUNT; i++) {
            consumers.add(new Thread(new Consumer(store, stringToFind)));
        }
    }

    private static void executeThreads(Path file) throws InterruptedException {
        Thread p = new Thread(new Producer(store, file));
        p.start();
        p.join();
    }
}
