package Handins.Project2_IO;

import java.util.Arrays;
import java.util.Random;

public class WriteOptimizedSorting {
    public static void main(String[] args) {
        final int N = 8;
        final int M = 4;
        var array = FillRandom(new Integer[N]);
        var memory = new Integer[2][M];


        for (int i = 0; i < N; i += M) {
            memory[0] = Arrays.copyOfRange(array, i, i+M);
            do {
                Arrays.fill(memory[1], 0);
                for (int j = i+M; j < N; j++) {
                    for (int k = 0; k < M; k++) {
                        if(array[j] < memory[0][k]) {
                            memory[1][k]++;
                        }
                    }
                }

                for (int j = 0; j < M; j++) {
                    if(memory[1][j] > 0) {
                        var tmp = array[i + memory[1][j]];  // read from tape
                        array[i + M + memory[1][j]] = memory[0][j];  // write into tape
                        memory[0][j] = tmp;
                    }
                }

            } while (Arrays.stream(memory[1]).anyMatch(x -> x > 0));
            Arrays.sort(memory[0]);
            memory[1] = Arrays.copyOfRange(array, i, i+M);

            for (int j = 0; j < M; j++) {
                if(memory[0][j] != memory[1][j]) {
                    array[i+j] = memory[0][j];
                }
            }
        }
    }

    private static boolean Empty(Integer[] array) {
        return Arrays.asList(array).stream().allMatch(x -> x == null);
    }

    private static Integer[] FillRandom(Integer[] array) {
        var random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(100);
        }
        return array;
    }
}
