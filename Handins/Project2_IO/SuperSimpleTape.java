package Handins.Project2_IO;

import java.util.Arrays;
import java.util.Iterator;

public class SuperSimpleTape<T> implements Iterable<T> {
    private final T[] tape;

    public int position = 0;
    public int start;
    public int end;

    public SuperSimpleTape(T[] tape) {
        this.tape = tape;
        this.start = 0;
        this.end = tape.length;
    }

    public void MoveToIndex(int index) {
        this.position = index;
    }

    public void MoveBy(int positions) {
        this.position += positions;
    }

    public T[] ReadBlock(int length) {
        var out = Arrays.copyOfRange(this.tape, this.position, this.position+length);
        Arrays.fill(this.tape, this.position, this.position+length, null);
        return out;
    }

    public T ReadElement(int index) {
        this.position = index;
        return this.tape[this.position];
    }

    public T ReadElement() {
        return this.tape[this.position];
    }

    public void WriteBlock(T[] block) {
        for (int i = 0; i < block.length && this.position+i < this.tape.length; i++) {
            this.tape[this.position+i] = block[i];
        }
    }

    public void WriteElement(T value) {
        this.tape[this.position] = value;
    }


    @Override
    public Iterator<T> iterator() {
        return Arrays.stream(this.tape).iterator();
    }
}
