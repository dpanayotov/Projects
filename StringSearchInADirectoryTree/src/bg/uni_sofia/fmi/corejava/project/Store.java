package bg.uni_sofia.fmi.corejava.project;


import java.util.ArrayDeque;

public final class Store extends ArrayDeque<StringProduct> {
    private final int SIZE = 1000;

    public boolean isFull() {
        return size() >= SIZE;
    }
}
