package Handins.Project1_ParallelMergeSort.experiments;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

import Handins.Project1_ParallelMergeSort.src.BaseParallelMergeSort;
import Handins.Project1_ParallelMergeSort.src.FullyParallelMergeSort;

public class BaseMergeSortExpNT {
    public static void main(String[] args) throws Exception {

        // get available number of processors
        int cores = Runtime.getRuntime().availableProcessors();
        int N =  10000000;
        int steps = 2;
        
        for(int n=steps; n <= N; n*=steps) {
            ForkJoinPool pool = new ForkJoinPool(cores);
            var random = new Random(69);
            int testsToRun = 100;
            long totalTime = 0;

            for(int i = 0; i < testsToRun; i++) {
                List<Long> array = Arrays.asList(Helpers.GenerateUniqueArray(n, random::nextLong));
                //var task = new FullyParallelMergeSort(array);
                var task = new BaseParallelMergeSort(array);
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
            System.out.println(n + ";" + average);
        }
    }
}
