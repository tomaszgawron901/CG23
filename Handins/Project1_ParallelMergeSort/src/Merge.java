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

    // based on "Work-Optimal Merging from par-III.pdf page 13/20"
    public static void SegMerge(List<Long> A, List<Long> B, List<Long> M, int p) {
        // A and B are sorted and contain unique elements
        // it works the best when A.size() <= B.size()

        if(B.size() < p) {
            p = B.size();
        }

        int n = B.size();

        int[] rankA = new int[p+1];
        int[] rankB = new int[p+1];
        rankA[0] = 0;
        rankA[p] = A.size();
        rankB[0] = 0;
        rankB[p] = B.size();

        var rankTasks = new Thread[p-1];
        for(int i = 1; i < p; i++) {
            int finalI = i;
            int finalPnum = p;
            rankTasks[i-1] = new Thread(() -> {
                rankB[finalI] = finalI*n/finalPnum;
                rankA[finalI] = BinarySearch(A, B.get(rankB[finalI]));
            });
            rankTasks[i-1].start();
        }

        for(Thread task : rankTasks) {
            try {
                task.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        var mergeTasks = new Thread[p];
        for(int i = 0; i < p; i++) {
            int finalI = i;
            mergeTasks[i] = new Thread(() -> {
                var ASubList = A.subList(rankA[finalI], rankA[finalI+1]);
                var BSubList = B.subList(rankB[finalI], rankB[finalI+1]);
                var MSubList = M.subList(rankA[finalI] + rankB[finalI], rankA[finalI+1] + rankB[finalI+1]);
                SequentialMerge(ASubList, BSubList, MSubList);
                // on the slides it says "AssymMerge"
                // but Peyman said that in practice SequentialMerge is OK
                // and we can use it in our implementation
            });
            mergeTasks[i].start();
        }

        for(Thread task : mergeTasks) {
            try {
                task.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private static int BinarySearch(List<Long> array, long value) {
        // return a that: arr[a-1] < x < arr[a]
        int left = 0;
        int right = array.size();
        while(left < right) {
            int middle = (left + right) / 2;
            if(array.get(middle) < value) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }
        return left;
    }
}
