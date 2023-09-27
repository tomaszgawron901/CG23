package Handins.Project1_ParallelMergeSort.experiments;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

public class Helpers {
    
    public static Long[] GenerateArray(int size, Callable<Long> getNextLong) throws Exception  {
        Long[] array = new Long[size];
        for(int i = 0; i < size; i++) {
            array[i] = getNextLong.call();
        }
        return array;
    }

    public static Long[] GenerateUniqueArray(int size, Callable<Long> getNextLong) throws Exception {
        Long[] array = new Long[size];
        Set<Long> uniqueValues = new HashSet<>();
        for (int i = 0; i < size; i++) {
            long randomValue;
            do {
                randomValue = getNextLong.call();
            } while (!uniqueValues.add(randomValue));
            
            array[i] = randomValue;
        }
        return array;
    }

    public static boolean IsSorted(List<Long> array) {
        for(int i = 1; i < array.size(); i++) {
            if(array.get(i-1) > array.get(i)) {
                return false;
            }
        }
        return true;
    }

     /**
     * Generates two arrays of size n
     * The arrays are sorted and unique
     * Values in arrays are equally distributed meaning: that there is ~the same amount of values larger then w and smaller then w in both arrays, for any w.
     * @param n number of elements in each array
     * @param random
     * @return
     * @throws Exception
     */
    public static Long[][] GetBestCaseMergeArrays(int n) {

        var array1 = new Long[n];
        var array2 = new Long[n];
        for(int i = 0; i < n; i++) {
            array1[i] = Long.valueOf(i*2);
            array2[i] = Long.valueOf(i*2+1);
        }

        return new Long[][] {array1, array2};
    }


    /**
     * Generates two arrays of size n
     * The arrays are sorted and unique
     * All values in array1 are smaller then all values in array2
     * @param n
     * @return
     */
    public static Long[][] GetWorstCaseMergeArrays(int n) {
        var array1 = new Long[n];
        var array2 = new Long[n];
        for(int i = 0; i < n; i++) {
            array1[i] = Long.valueOf(i);
            array2[i] = Long.valueOf(n+i);
        }

        return new Long[][] {array1, array2};
    }

}
