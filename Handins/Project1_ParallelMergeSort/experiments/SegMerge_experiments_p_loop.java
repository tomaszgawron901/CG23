package Handins.Project1_ParallelMergeSort.experiments;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import Handins.Project1_ParallelMergeSort.src.SegMergeTask;

public class SegMerge_experiments_p_loop {
    public static void main(String[] args) throws Exception {
        final int max_cores = 8;
        final int n = 1<<20;
        final int testsPerStep = 20;
        System.out.println("Running Merge; with n=" + n);
        System.out.println("p;time;average");

        
        for(int p=1; p<=max_cores; p++) {
            try (ForkJoinPool pool = new ForkJoinPool(p)) {
                long totalTime = 0L;
                for(int i = 0; i < testsPerStep; i++) {
                    //var arrays = Helpers.GetWorstCaseMergeArrays(n);
                    var arrays = Helpers.GetBestCaseMergeArrays(n);

                    List<Long> array1 = Arrays.asList(arrays[0]);
                    List<Long> array2 = Arrays.asList(arrays[1]);
                    List<Long> scratch = Arrays.asList(new Long[n*2]);

                    var task = new SegMergeTask(array1, array2, scratch, p);
                    long start = System.currentTimeMillis();
                    pool.invoke(task);
                    task.join();
                    long end = System.currentTimeMillis();
                    totalTime += end - start;
                }
                Long average = totalTime/ testsPerStep;
                System.out.println( p + ";" + totalTime + ";" + average);
            }
        }
    }
}
