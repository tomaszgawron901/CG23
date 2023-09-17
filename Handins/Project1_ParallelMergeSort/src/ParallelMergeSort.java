package Handins.Project1_ParallelMergeSort.src;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RecursiveTask;


// based on "Algorithm 1 from msort.pdf"
// This implementation only uses a one-time allocated scratch space, instead of allocating scratch space at every recursion level
public class ParallelMergeSort extends RecursiveTask<List<Long>> {

    @FunctionalInterface
    public interface MergeFunction {
        void call(List<Long> array1, List<Long> array2, List<Long> output);
    }

    private List<Long> array;
    private List<Long> scratch; // scratch space used for merging
    private MergeFunction mergeFunction; // function used to merge two arrays

    public ParallelMergeSort(List<Long> array, MergeFunction mergeFunction) {
        this(array, Arrays.asList(new Long[array.size()]), mergeFunction);
    }

    private ParallelMergeSort(List<Long> array, List<Long> scratch, MergeFunction mergeFunction) {
        this.array = array;
        this.scratch = scratch;
        this.mergeFunction = mergeFunction;
    }

    @Override
    protected List<Long> compute() {
        if(this.array.size() < 2) {
            return this.array;
        }

        int middleSplit = this.array.size()/2;
        var leftArray = this.array.subList(0, middleSplit);
        var leftScratch = this.scratch.subList(0, middleSplit);
        var rightArray = this.array.subList(middleSplit, this.array.size());
        var rightScratch = this.scratch.subList(middleSplit, this.scratch.size());
        // sublist does not create a new array
        // it creates a view of a portion of the array 
        // changing sub-array changes original array
        // the benefit is that it does not allocate new memory

        var leftTask = new ParallelMergeSort(leftArray, leftScratch, this.mergeFunction);
        var rightTask = new ParallelMergeSort(rightArray, rightScratch, this.mergeFunction);
        invokeAll(leftTask, rightTask);

        Transfer(leftTask.join(), leftScratch);   // rewrite leftTask output (which is stored in this.array) into leftScratch
        Transfer(rightTask.join(), rightScratch); // same but with right side
        this.mergeFunction.call(leftScratch, rightScratch, this.array); // and merge them into this.array

        return this.array;
    }

    // write elements from 'from' into 'to'
    private static void Transfer(List<Long> from, List<Long> to) {
        for(int i = 0; i < from.size(); i++) {
            to.set(i, from.get(i));
        }
    }
}
