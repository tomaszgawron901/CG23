package Handins.Project2_IO;

import java.util.Arrays;
import java.util.Iterator;

public class TapeSimulator<T> implements Iterable<T> {
    public final int H;
    public final int B;
    public final int L;

    private int currentWrap = 0;
    private int currentColumn = 0;

    private int verticalMoveCounter = 0;
    private int horizontalMoveCounter = 0;
    private int readCounter = 0;
    private int writeCounter = 0;

    private T[][][] tape;

    public TapeSimulator(int noWraps, int wrapHeight, int trackLength) {
        this.H = noWraps;
        this.B = wrapHeight;
        this.L = trackLength;

        this.tape = (T[][][]) new Object[this.H][this.L][this.B];
    }

    public TapeSimulator(T[][][] tape) {
        this.H = tape.length;
        this.B = tape[0][0].length;
        this.L = tape[0].length;

        this.tape = tape;
    }

    public void MoveTo(int wrap, int column) {
        this.verticalMoveCounter += Math.abs(wrap - this.currentWrap);
        this.horizontalMoveCounter += Math.abs(column - this.currentColumn);

        this.currentWrap = wrap;
        this.currentColumn = column;
    }

    public void MoveToBlock(int blockIndex) {
        int wrap = blockIndex / this.L;
        boolean evenWrap = wrap % 2 == 0;
        int column = evenWrap 
            ? blockIndex % this.L 
            : this.L - (blockIndex % this.L) - 1;

        this.MoveTo(wrap, column);
    }

    public T[] readCurrent() {
        this.readCounter++;
        var out = this.tape[this.currentWrap][this.currentColumn].clone();
        Arrays.fill(this.tape[this.currentWrap][this.currentColumn], null);
        return out;
    }

    public void WriteCurrent(T[] block) {
        this.writeCounter++;
        this.tape[this.currentWrap][this.currentColumn] = block.clone();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int N = TapeSimulator.this.H * TapeSimulator.this.B * TapeSimulator.this.L;
            private int index = -1;

            @Override
            public boolean hasNext() {
                return this.index+1 < this.N;
            }

            @Override
            public T next() {
                this.index++;

                int wrap = this.index / (TapeSimulator.this.L*TapeSimulator.this.B);
                boolean evenWrap = wrap % 2 == 0;
                int column = evenWrap 
                    ? (this.index / TapeSimulator.this.B) % TapeSimulator.this.L
                    : TapeSimulator.this.L - (this.index / TapeSimulator.this.B) % TapeSimulator.this.L - 1;
                int row = this.index % TapeSimulator.this.B;

                return TapeSimulator.this.tape[wrap][column][row];
            }
            
        };
    }
}
