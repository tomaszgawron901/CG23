package Handins.Project2_IO;

import java.util.Arrays;
import java.util.Random;

public class Helpers {
    public static <T> T[] PopRange(T[] array, int start, int end) {
        T[] copy = Arrays.copyOfRange(array, start, end);
        Arrays.fill(array, start, end, null);
        return copy;
    }

    public static void PrintTape(Iterable<Integer> tape, int p) {
        for (var v : tape) {
            var color = v < p 
                ? "\033[0;32m"  // green
                : "\033[0;31m";  // read
            System.out.print(color + v + "\033[0m ");
        }
        System.out.println();
        System.out.println();
    }

    public static SuperSimpleTape<Integer> GetRandomTape(int N) {
        var random = new Random();

        Integer[] tape = new Integer[N];
        for (int i = 0; i < N; i++) {
            tape[i]= random.nextInt(10_000);
        }
        return new SuperSimpleTape<Integer>(tape);
    }

    public static final <T> void WriteTo(T[] destination, int index, T[] values) {
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
