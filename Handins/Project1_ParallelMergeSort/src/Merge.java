package Handins.Project1_ParallelMergeSort.src;

import java.util.List;

public class Merge {
    public static void SequentialMerge(List<Long> array1, List<Long> array2, List<Long> output) {
        int i1 = 0, i2 = 0;
        for (int i = 0; i < output.size(); i++) {
            if (i2 >= array2.size() || (i1 < array1.size() && array1.get(i1) < array2.get(i2))) {
                output.set(i, array1.get(i1++));
            } else {
                output.set(i, array2.get(i2++));
            }
        }
    }
}
