package Handins.Project1_ParallelMergeSort;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class BasicParallelMergeSort extends RecursiveTask<List<Long>> {
    private List<Long> array;
    private List<Long> scratch;

    public BasicParallelMergeSort(List<Long> array) {
        this(array, Arrays.asList(new Long[array.size()]));
    }

    private BasicParallelMergeSort(List<Long> array, List<Long> scratch) {
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

        var leftTask = new BasicParallelMergeSort(leftArray, leftScratch);
        var rightTask = new BasicParallelMergeSort(rightArray, rightScratch);
        invokeAll(leftTask, rightTask);
        this.Transfer(leftTask.join(), leftScratch);   // rewrite leftTask output (which is stored in this.array) into leftScratch
        this.Transfer(rightTask.join(), rightScratch); // same but with right side

        Merge.SequentialMerge(leftScratch, rightScratch, this.array);
        return this.array;
    }

    private void Transfer(List<Long> from, List<Long> to) {
        for(int i = 0; i < from.size(); i++) {
            to.set(i, from.get(i));
        }
    }
}
