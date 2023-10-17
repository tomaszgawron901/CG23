package Handins.Project2_IO;

import java.util.Arrays;
import java.util.Random;

public class Partitioning {
    

    public static void main(String[] args) {
        final int H = 4;  // number of wraps
        final int B = 8;  // block size
        final int L = 8;  // track length
        final int N = H*B*L;
        final int M = B*8;  // memory size
        final int p = 5_000;  // partition point

        var tape = GetRandomTape(N, B);
        var memory = new Integer[M];

        int clp = 0;  // checked lower block pointer
        int cup = N/B - 1;  // checked upper block pointer

        while (clp <= cup) {
            // load M-B elements from the beginning of the tape to the memory
            // keep one empty block (of size B) in memory for future reads
            int readBlocks = 0;
            for (int m = 0; m < M-B && clp <= cup; m+=B) {
                tape.MoveToBlock(clp+readBlocks);
                WriteTo(memory, m, tape.ReadBlock());
                readBlocks++;
            }
            clp += readBlocks;


            // swap elements greater then p from memory up to last block with element smaller then p from last memory block
            // swap elements greater then p from memory with elements smaller then p from the tape
            for(int m = 0; clp <= cup; cup--) {
                // move to the end of the tape
                // read B elements from the tape to last memory block
                tape.MoveToBlock(cup);
                WriteTo(memory, M-B, tape.ReadBlock());

                // swap elements greater then p from memory up to last block with element smaller then p from last memory block
                // repeat until 
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
                tape.WriteBlock(PopRange(memory, M-B, M));

                if(m >= M-B) break;
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
                tape.WriteBlock(PopRange(memory, i*B, i*B+B));
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

    private static SimpleTape<Integer> GetRandomTape(int N, int B) {
        var random = new Random();

        Integer[] tape = new Integer[N];
        for (int i = 0; i < N; i++) {
            tape[i]= random.nextInt(10_000);
        }
        return new SimpleTape<Integer>(tape, B);
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
