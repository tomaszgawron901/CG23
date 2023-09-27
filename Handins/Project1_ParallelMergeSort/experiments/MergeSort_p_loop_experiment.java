package Handins.Project1_ParallelMergeSort.experiments;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

import Handins.Project1_ParallelMergeSort.src.BaseParallelMergeSort;
import Handins.Project1_ParallelMergeSort.src.FullyParallelMergeSort;

public class MergeSort_p_loop_experiment {
    public static void main(String[] args) throws Exception {

        final int cores = Runtime.getRuntime().availableProcessors();
        final int testsPerStep = 5;
        final int n = 1 << 20;
        System.out.println("Running hypothesis 2 (BaseMergeSort with large n; p doubles -> time 2x faster); with " + testsPerStep + " tests per step; n = " + n + ";");
        System.out.println("p;total_time;average_time");

        for(int p=1; p <= cores; p*=2) {
            try (ForkJoinPool pool = new ForkJoinPool(p)) {
                var random = new Random(69);
                
                long totalTime = 0;
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
                System.out.println(p + ";" + totalTime + ";" + average);
            }
        }
    }
}
