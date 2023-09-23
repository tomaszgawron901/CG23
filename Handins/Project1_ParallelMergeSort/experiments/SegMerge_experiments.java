package Handins.Project1_ParallelMergeSort.experiments;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import Handins.Project1_ParallelMergeSort.src.SegMergeTask;

public class SegMerge_experiments {
    public static void main(String[] args) throws Exception {
        final int cores = Runtime.getRuntime().availableProcessors();
        final int number_of_splits = 4;
        final int testsPerStep = 20;
        System.out.println("Running Merge; with " + testsPerStep + " tests per step; using p = " + cores + " processors; using " + number_of_splits + " splits");
        System.out.println("step;n;time;average");

        
        for(int step=10; step<=25; step++) {
            int n = 1 << step;
            ForkJoinPool pool = new ForkJoinPool(cores);

            long totalTime = 0L;
            for(int i = 0; i < testsPerStep; i++) {
                //var arrays = GetWorstCaseArrays(n);
                var arrays = GetBestCaseArrays(n);

                List<Long> array1 = Arrays.asList(arrays[0]);
                List<Long> array2 = Arrays.asList(arrays[1]);
                List<Long> scratch = Arrays.asList(new Long[n*2]);

                var task = new SegMergeTask(array1, array2, scratch, number_of_splits);
                long start = System.currentTimeMillis();
                pool.invoke(task);
                task.join();
                long end = System.currentTimeMillis();
                totalTime += end - start;
            }
            Long average = totalTime/ testsPerStep;
            System.out.println(step + ";" + n + ";" + totalTime + ";" + average);
        }
    }

    /**
     * Generates two arrays of size n
     * The arrays are sorted and unique
     * Values in arrays are equally distributed meaning: that there is ~the same amount of values larger then w and smaller then w in both arrays, for any w.
     * @param n number of elements in each array
     * @param random
     * @return
     * @throws Exception
     */
    private static Long[][] GetBestCaseArrays(int n) {

        var array1 = new Long[n];
        var array2 = new Long[n];
        for(int i = 0; i < n; i++) {
            array1[i] = Long.valueOf(i*2);
            array2[i] = Long.valueOf(i*2+1);
        }

        return new Long[][] {array1, array2};
    }


    /**
     * Generates two arrays of size n
     * The arrays are sorted and unique
     * All values in array1 are smaller then all values in array2
     * @param n
     * @return
     */
    private static Long[][] GetWorstCaseArrays(int n) {
        var array1 = new Long[n];
        var array2 = new Long[n];
        for(int i = 0; i < n; i++) {
            array1[i] = Long.valueOf(i);
            array2[i] = Long.valueOf(n+i);
        }

        return new Long[][] {array1, array2};
    }
}
