package Handins.Project1_ParallelMergeSort;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class main {
    private static boolean IsSorted(List<Long> array) {
        for(int i = 1; i < array.size(); i++) {
            if(array.get(i-1) > array.get(i)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws Exception{
        var random = new Random(69);
        List<Long> array = Arrays.asList(ArrayHelpers.GenerateArray(10, () -> random.nextLong(0, 100)));
        
        // get available number of processors
        // int cores = Runtime.getRuntime().availableProcessors();

        ForkJoinPool pool = new ForkJoinPool();
        var task = new BasicParallelMergeSort(array);
        pool.invoke(task);
        var sorted = task.join();
        System.out.println("Is sorted:" + IsSorted(sorted));
        
    }
}