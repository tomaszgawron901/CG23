import java.util.Random;

class Shuff {

    static Random rand;

    static long startTime;
    static long endTime;

    static final int POSMAP=~(1<<31);  // 0 followed by 31 1s

    static void startTimer(){
        startTime = System.nanoTime();
    }

    static int seeda = 858028103;
    static int seedi = 911147574;

    // pairwise independent pseudo random numbers
    static int cheapRand() {
        seedi+=seeda;
        return seedi&POSMAP; // Anding with POSMAP 0s the MSB, makes it positive
    }
    
    static double stopTimer(){
            endTime = System.nanoTime();
            return (double)(endTime-startTime)/1000000;
    }

    static void swap(int[] arr, int i, int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        return;
    }

    static void ram_shuff(int[] A, int lo, int hi){
        //shuffle A between lo and hi
        int size = hi-lo;
        for (int i = size-1 ; i>0 ; i--){
            int randnum =  cheapRand();
            int ri = randnum%(i+1);
            swap(A,lo+i,lo+ri);
        }
    }


	// Driver Code
	public static void main(String[] args) {
        if (args.length != 1){
            System.err.println("Please enter the log of the size of the array.");
            return;
        }
        int logsize =Integer.parseInt(args[0]);
        if (logsize < 6 || logsize > 30) { // dumb java does not suport 2^31 size arrays.
            System.err.println("Please enter a valid size between 6 and 30 and not "+logsize);
            return;
        }
        int size = 1<<logsize;
        rand = new java.util.Random();
        int []arr = new int[size];
        for (int i=0 ; i<arr.length ; i++){
            arr[i]=i;
        }

        for (int i=4 ; i<logsize ; i=i+1) {  // divide into pieces of size i and shuffle
            int subsize = 1 <<i; 
            startTimer();
            for (int j=0 ; j<=size-subsize ; j+=subsize){
                ram_shuff(arr,j,j+subsize);
            }
            double elapsed = stopTimer();
            System.out.println("logsize "+ logsize +  
                    " divided into pieces of log size " + i + " took " +elapsed+ " Millis");
        }
	}
}
