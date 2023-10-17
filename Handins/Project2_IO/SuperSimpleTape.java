package Handins.Project2_IO;

import java.util.Arrays;
import java.util.Iterator;

public class SuperSimpleTape<T> implements Iterable<T> {
    public final int N;
    private final T[] tape;

    private int currentIndex = 0;

    public SuperSimpleTape(T[] tape) {
        this.N = tape.length;
        this.tape = tape;
    }

    public void MoveToIndex(int index) {
        this.currentIndex = index;
    }

    public T[] ReadBlock(int length) {
        var out = Arrays.copyOfRange(this.tape, this.currentIndex, this.currentIndex+length);
        Arrays.fill(this.tape, this.currentIndex, this.currentIndex+length, null);
        return out;
    }

    public void WriteBlock(T[] block) {
        for (int i = 0; i < block.length && this.currentIndex+i < this.tape.length; i++) {
            this.tape[this.currentIndex+i] = block[i];
        }
    }

    @Override
    public Iterator<T> iterator() {
        return Arrays.stream(this.tape).iterator();
    }
}
