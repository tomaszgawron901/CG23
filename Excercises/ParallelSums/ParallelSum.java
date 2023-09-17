import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class ParallelSum {
    static Long[] arr;
    
    private static class SumTask extends RecursiveTask<Long> {
        private long left;
        private long right;

        public SumTask(long left, long right){
            this.left = left;
            this.right = right;
        }

        @Override
        protected Long compute(){
            if (left < right) {
                long mid = (left + right)/2;
                SumTask leftTask = new SumTask(left,mid);
                SumTask rightTask = new SumTask(mid+1,right);
                invokeAll(leftTask,rightTask);
                Long leftVal = leftTask.join();
                Long rightVal = rightTask.join();
                Long val = Long.valueOf(leftVal.longValue()+rightVal.longValue());
                return val;
            } else {
                return arr[(int)left];
            }
        }
    }

        private static class MinTask extends RecursiveTask<Long> {
        private long left;
        private long right;

        public MinTask(long left, long right){
            this.left = left;
            this.right = right;
        }

        @Override
        protected Long compute(){
            if (left < right) {
                long mid = (left + right)/2;
                MinTask leftTask = new MinTask(left,mid);
                MinTask rightTask = new MinTask(mid+1,right);
                invokeAll(leftTask,rightTask);
                Long leftVal = leftTask.join();
                Long rightVal = rightTask.join();
                Long val = Long.valueOf(Long.min(leftVal.longValue(), rightVal.longValue()));
                return val;
            } else {
                return arr[(int)left];
            }
        }
    }

    // ExecutorService  <- check this out

    public static void main(String[] args){
        int size = 1<<27;
        arr = new Long[size];
        for (int i=0 ; i<arr.length ; i++){
            arr[i]= Long.valueOf((long)(Math.random()*10000000));
        }

        long start = System.currentTimeMillis();

        ForkJoinPool pool = new ForkJoinPool();
        MinTask topTask = new MinTask(0,arr.length-1);
        pool.invoke(topTask);

        Long min = topTask.join();

        long endt = System.currentTimeMillis();

        System.out.println("min: "+min);
        System.out.println("Took "+(endt-start)+ " Millis");
    }
}




