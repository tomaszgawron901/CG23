package Handins.Project2_IO;

import java.util.Arrays;
import java.util.Random;

public class Partitioning {
    public static void main(String[] args) {
        final int H = 4;  // number of wraps
        final int B = 8;  // block size
        final int L = 8;  // track length
        final int N = H*B*L;
        final int M = B*4;  // memory size
        final int p = 5_000;  // partition point

        var tape = GetRandomTape(N);
        var memory = new Integer[M];

        int clp = 0;  // lower block pointer;
        int cup = N;  // upper block pointer;

        while (clp < cup) {
            tape.MoveToIndex(clp);
            int readElements1 = Math.min(M-B, cup-clp);
            WriteTo(memory, 0, tape.ReadBlock(readElements1));
            clp += readElements1;

            for(int m = 0; clp < cup;) {
                int readElements2 = Math.min(cup-clp, B);
                tape.MoveToIndex(cup-readElements2);
                WriteTo(memory, M-readElements2, tape.ReadBlock(readElements2));

                for (int i = M-readElements2; i < M && m < readElements1; i++) {
                    if(memory[i] < p) {
                        for (;m < readElements1; m++) {
                            if(memory[m] > p) {
                                swap(memory, m, i);
                                break;
                            }
                        }
                    }
                }
                tape.WriteBlock(PopRange(memory, M-B, M));
                if(m >= M-B) break;
                cup -= readElements2;
            }
            
            // last run might still contain unpartitioned elements in the memory
            // so we solve it there
            if(clp >= cup) {
                for (int i = 0, j = readElements1-1; i < j; i++) {
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

            tape.MoveToIndex(clp-readElements1);
            tape.WriteBlock(PopRange(memory, 0, readElements1));
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

    private static SuperSimpleTape<Integer> GetRandomTape(int N) {
        var random = new Random();

        Integer[] tape = new Integer[N];
        for (int i = 0; i < N; i++) {
            tape[i]= random.nextInt(10_000);
        }
        return new SuperSimpleTape<Integer>(tape);
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
