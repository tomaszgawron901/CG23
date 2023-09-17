import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class ExpParallelSum {
    static long[] arr;
    
    private static class SumTask extends Thread {
        private long left;
        private long right;
        private long result;

        public SumTask(long left, long right){
            this.left = left;
            this.right = right;
            this.result = 0;
        }

        @Override
        public void run(){
            for (long i=left ; i< right ; i++) {
                result +=arr[(int)i];
            }
        }

        public long getResult(){
            return result;
        }
    }

    private static class MinTask extends Thread {
        private long left;
        private long right;
        private long result;

        public MinTask(long left, long right){
            this.left = left;
            this.right = right;
            this.result = Long.MAX_VALUE;
        }

        @Override
        public void run(){
            for (long i=left ; i< right ; i++) {
                if(arr[(int)i] < this.result) {
                    this.result = arr[(int)i];
                }
            }
        }

        public long getResult(){
            return result;
        }
    }


    public static void main(String[] args){
        int size = 1<<27;
        int pnum = 16;

        MinTask tasks[] = new MinTask[pnum];

        arr = new long[size];
        for (int i=0 ; i<arr.length ; i++){
            //fill arr with random longs
            arr[i]= (long)(Math.random()*10000000);
        }

        long start = System.currentTimeMillis();

        for (int i=0 ; i<pnum ; i++) {
            tasks[i] = new MinTask(size/pnum*i,size/pnum*(i+1));
            tasks[i].start();
        }

        try {
            for (int i=0; i<pnum ; i++) {
                tasks[i].join();
            }
        } catch (Exception e){
            System.out.println("Error " + e);
        }

        long endt = System.currentTimeMillis();

        long globalMin = Long.MAX_VALUE;
        for (int i=0 ; i<pnum ; i++){
            long localMin = tasks[i].getResult();
            if(localMin<globalMin) {
                globalMin = localMin;
            }
        }

        System.out.println("Min: "+globalMin);
        System.out.println("Took "+(endt-start)+ " Millis");
    }
}




