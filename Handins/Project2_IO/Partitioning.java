package Handins.Project2_IO;

import java.util.Arrays;
import java.util.Random;

public class Partitioning {
    

    public static void main(String[] args) {
        final int H = 4;  // number of wraps
        final int B = 8;  // block size
        final int L = 8;  // track length

        final int N = H*B*L;

        final int p = 5_000;  // partition point

        var tape = GetRandomTape(H, B, L);

        int M = B*8;
        var memory = new Integer[M];

        int clp = 0;  // checked lower pointer
        int cup = N/B - 1;  // checked upper pointer

        while (clp <= cup) {
            int readBlocks = 0;
            for (int m = 0; m < M-B && clp <= cup; m+=B) {
                tape.MoveToBlock(clp+readBlocks);
                WriteTo(memory, m, tape.readCurrent());
                readBlocks++;
            }
            clp += readBlocks;

            for(int m = 0; clp <= cup;) {
                tape.MoveToBlock(cup);
                WriteTo(memory, M-B, tape.readCurrent());

                for (int i = M-B; i < M && m < readBlocks*B; i++) {
                    if(memory[i] < p) {
                        for (;m < M-B; m++) {
                            if(memory[m] > p) {
                                swap(memory, m, i);
                                break;
                            }
                        }
                    }
                }
                tape.WriteCurrent(PopRange(memory, M-B, M));

                if(m >= M-B) break;
                cup--;
            }
            
            if(clp >= cup) {
                for (int i = 0, j = readBlocks*B-1; i < j; i++) {
                    if(memory[i] > p) {
                        for(; j > i; j--) {
                            if(memory[j] < p) {
                                swap(memory, i, j);
                                break;
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < readBlocks; i++) {
                tape.MoveToBlock(clp-readBlocks+i);
                tape.WriteCurrent(PopRange(memory, i*B, i*B+B));
            }
        }

        PrintTape(tape, p);
        
    }

    private static <T> T[] PopRange(T[] array, int start, int end) {
        T[] copy = Arrays.copyOfRange(array, start, end);
        Arrays.fill(array, start, end, null);
        return copy;
    }

    private static void PrintTape(Iterable<Integer> tape, int p) {
        for (var v : tape) {
            var color = v < p 
                ? "\033[0;32m"  // green
                : "\033[0;31m";  // read
            System.out.print(color + v + "\033[0m ");
        }
        System.out.println();
        System.out.println();
    }

    private static TapeSimulator<Integer> GetRandomTape(int noWraps, int wrapHeight, int trackLength) {
        var random = new Random();

        Integer[][][] tape = new Integer[noWraps][trackLength][wrapHeight];
        for (int i = 0; i < noWraps; i++) {
            for (int j = 0; j < trackLength; j++) {
                for (int k = 0; k < wrapHeight; k++) {
                    tape[i][j][k] = random.nextInt(10_000);
                }
            }
        }
        return new TapeSimulator<Integer>(tape);
    }

    private static final <T> void WriteTo(T[] destination, int index, T[] values) {
        for (int i = 0; i < values.length && index+i < destination.length; i++) {
            destination[index+i] = values[i];
        }
    }

    public static final <T> void swap (T[] a, int i, int j) {
        T t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
