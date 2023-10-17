package Handins.Project2_IO;

import java.util.Arrays;
import java.util.Iterator;

public class SimpleTape<T> implements Iterable<T> {
    public final int N;
    public final int B;

    private final T[] tape;

    private int currentIndex = 0;

    public SimpleTape(T[] tape, int B) {
        this.N = tape.length;
        this.B = B;

        this.tape = tape;
    }

    public void MoveToBlock(int blockIndex) {
        this.currentIndex = blockIndex*this.B;
    }

    public T[] ReadBlock() {
        var out = Arrays.copyOfRange(this.tape, this.currentIndex, this.currentIndex+B);
        Arrays.fill(this.tape, this.currentIndex, this.currentIndex+this.B, null);
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
