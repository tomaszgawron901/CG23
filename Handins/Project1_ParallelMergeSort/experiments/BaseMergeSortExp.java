package Handins.Project1_ParallelMergeSort.experiments;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

import Handins.Project1_ParallelMergeSort.src.Merge;
import Handins.Project1_ParallelMergeSort.src.ParallelMergeSort;

public class BaseMergeSortExp {
    public static void main(String[] args) throws Exception {

        // get available number of processors
        int cores = Runtime.getRuntime().availableProcessors();
        
        for(int p=1; p <= cores; p++) {
            ForkJoinPool pool = new ForkJoinPool(p);
            var random = new Random(69);
            int testsToRun = 50;
            long totalTime = 0;

            for(int i = 0; i < testsToRun; i++) {
                List<Long> array = Arrays.asList(Helpers.GenerateUniqueArray(1 << 15, random::nextLong));
                var task = new ParallelMergeSort(array, Merge::SequentialMerge);
                long start = System.currentTimeMillis();
                pool.invoke(task);
                task.join();
                long end = System.currentTimeMillis();
                totalTime += end - start;

                if(!Helpers.IsSorted(array)) {
                    System.out.println("Array is not sorted");
                }
            }
            long average = totalTime / testsToRun;
            System.out.println("Running Parallel Merge Sort with " + p +" processors took by average: " + average + "ms");
        }
    }
}
