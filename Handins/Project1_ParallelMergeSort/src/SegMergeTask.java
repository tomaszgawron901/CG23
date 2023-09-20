package Handins.Project1_ParallelMergeSort.src;

import java.util.List;
import java.util.concurrent.ForkJoinTask;

public class SegMergeTask extends ForkJoinTask<List<Long>> {
    private final List<Long> A;
    private final List<Long> B;
    private List<Long> M;
    private final int p;

    public SegMergeTask(List<Long> array1, List<Long> array2, List<Long> output, int p) {
        this.A = array1;
        this.B = array2;
        this.M = output;

        if(this.B.size() < p) {
            this.p = B.size();
        }
        else {
            this.p = p;
        }
    }

    @Override
    public List<Long> getRawResult() {
        return M;
    }

    @Override
    protected void setRawResult(List<Long> value) {
        this.M = value;
    }

    @Override
    protected boolean exec() {
        final int n = B.size();

        int[] rankA = new int[p+1];
        int[] rankB = new int[p+1];
        rankA[0] = 0;
        rankA[p] = A.size();
        rankB[0] = 0;
        rankB[p] = B.size();

        var rankTasks = new ForkJoinTask[p-1];
        for(int i = 1; i < p; i++) {
            final int _i = i;
            rankTasks[i-1] = ForkJoinTask.adapt(() -> {
                rankB[_i] = _i*n/p;
                rankA[_i] = BinarySearch(A, B.get(rankB[_i]));
            }).fork();
        }
        for(var task : rankTasks) {
            task.join();
        }

        var mergeTasks = new ForkJoinTask[p];
        for(int i = 0; i < p; i++) {
            final int _i = i;
            mergeTasks[i] = ForkJoinTask.adapt(() -> {
                var ASubList = A.subList(rankA[_i], rankA[_i+1]);
                var BSubList = B.subList(rankB[_i], rankB[_i+1]);
                var MSubList = M.subList(rankA[_i] + rankB[_i], rankA[_i+1] + rankB[_i+1]);
                Merge.SequentialMerge(ASubList, BSubList, MSubList);
                // on the slides it says "AssymMerge"
                // but Peyman said that in practice SequentialMerge is OK
                // and we can use it in our implementation
            }).fork();
        }

        for(var task : mergeTasks) {
            task.join();
        }
        
        return true;
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
