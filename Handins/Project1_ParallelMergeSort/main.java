package Handins.Project1_ParallelMergeSort;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class main {

    public static void main(String[] args){
        List<Long> array = Arrays.asList(5L, 6L, 1L, 2L, 9L, 0L);
        
        // get available number of processors
        // int cores = Runtime.getRuntime().availableProcessors();

        ForkJoinPool pool = new ForkJoinPool(4);
        var task = new BasicParallelMergeSort(array);
        pool.invoke(task);
        var sorted = task.join();
        
        System.out.println("");
        
    }
}