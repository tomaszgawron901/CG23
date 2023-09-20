package Handins.Project1_ParallelMergeSort.src;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RecursiveTask;


// based on "Algorithm 1 from msort.pdf"
// This implementation only uses a one-time allocated scratch space, instead of allocating scratch space at every recursion level
public class FullyParallelMergeSort extends RecursiveTask<List<Long>> {

    private List<Long> array;
    private List<Long> scratch; // scratch space used for merging

    public FullyParallelMergeSort(List<Long> array) {
        this(array, Arrays.asList(new Long[array.size()]));
    }

    private FullyParallelMergeSort(List<Long> array, List<Long> scratch ) {
        this.array = array;
        this.scratch = scratch;
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

        var leftTask = new FullyParallelMergeSort(leftArray, leftScratch);
        var rightTask = new FullyParallelMergeSort(rightArray, rightScratch);
        invokeAll(leftTask, rightTask);

        Transfer(leftTask.join(), leftScratch);   // rewrite leftTask output (which is stored in this.array) into leftScratch
        Transfer(rightTask.join(), rightScratch); // same but with right side
        
        var mergeTask = new SegMergeTask(leftScratch, rightScratch, this.array, 4);
        mergeTask.fork();
        mergeTask.join();

        return this.array;
    }

    // write elements from 'from' into 'to'
    private static void Transfer(List<Long> from, List<Long> to) {
        for(int i = 0; i < from.size(); i++) {
            to.set(i, from.get(i));
        }
    }
}
