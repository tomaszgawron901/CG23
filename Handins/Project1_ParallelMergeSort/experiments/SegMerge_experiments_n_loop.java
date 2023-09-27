package Handins.Project1_ParallelMergeSort.experiments;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import Handins.Project1_ParallelMergeSort.src.SegMergeTask;

public class SegMerge_experiments_n_loop {
    public static void main(String[] args) throws Exception {
        final int cores = Runtime.getRuntime().availableProcessors();
        final int number_of_splits = 4;
        final int testsPerStep = 20;
        System.out.println("Running Merge; with " + testsPerStep + " tests per step; using p = " + cores + " processors; using " + number_of_splits + " splits");
        System.out.println("step;n;time;average");

        ForkJoinPool pool = new ForkJoinPool(cores);
        for(int step=10; step<=20; step++) {
            int n = 1 << step;
            long totalTime = 0L;
            for(int i = 0; i < testsPerStep; i++) {
                //var arrays = Helpers.GetWorstCaseMergeArrays(n);
                var arrays = Helpers.GetBestCaseMergeArrays(n);

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
        pool.close();
    }
}
