package Handins.Project2_IO;

import java.util.Arrays;
import java.util.Random;

public class WriteOptimizedSorting2 {



    public static void main(String[] args) {
        final int N = 8;
        final int M = 4;

        var array = FillRandom(new Integer[N]);
        var output = new Integer[N];
        var memory = new Integer[M];

        int bsn = Integer.MIN_VALUE;  // biggest sorted value
        for (int i = 0; i < N; i += M) {
            
            memory = GetSmallestNumbersBiggerThen(array, M, bsn);
            WriteAt(memory, output, i);

            bsn = memory[memory.length-1];
        }
        
    }

    private static void WriteAt(Integer[] source, Integer[] destination, int destinationPosition) {
        for (int i = 0; i < source.length; i++) {
            destination[destinationPosition+i] = source[i];
        }
    }

    private static Integer[] GetSmallestNumbersBiggerThen(Integer[] array, int amount, int minAllowedValue) {
        return Arrays.stream(array)
            .filter(x -> x > minAllowedValue)
            .sorted()
            .limit(amount)
            .toArray(Integer[]::new);
    }


    private static Integer[] FillRandom(Integer[] array) {
        var random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(100);
        }
        return array;
    }
}
