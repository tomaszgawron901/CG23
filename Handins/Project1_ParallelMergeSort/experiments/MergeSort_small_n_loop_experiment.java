package Handins.Project1_ParallelMergeSort.experiments;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

import Handins.Project1_ParallelMergeSort.src.BaseParallelMergeSort;
import Handins.Project1_ParallelMergeSort.src.FullyParallelMergeSort;

public class MergeSort_small_n_loop_experiment {
    public static void main(String[] args) throws Exception {

        final int cores = Runtime.getRuntime().availableProcessors();
        final int testsPerStep = 10000;
        System.out.println("Running hypothesis 1 (BaseMergeSort with small n; log(n) < p); on " + cores + " cores; with " + testsPerStep + " tests per step");
        System.out.println("step;array_size;total_time;average_time");

        int n = 1;
        ForkJoinPool pool = new ForkJoinPool(cores);
        for(int step=1; step<=cores; step++) {
            n<<=1;
            var random = new Random(69);

            Long totalTime = 0L;
            for(int i = 0; i < testsPerStep; i++) {
                List<Long> array = Arrays.asList(Helpers.GenerateUniqueArray(n, random::nextLong));
                var task = new BaseParallelMergeSort(array);
                //var task = new FullyParallelMergeSort(array);
                long start = System.currentTimeMillis();
                pool.invoke(task);
                task.join();
                long end = System.currentTimeMillis();
                totalTime += end - start;
            }
            long average = totalTime / testsPerStep;
            System.out.println(step + ";" + n + ";" + totalTime + ";" + average);

        }
        pool.close();
    }
}
