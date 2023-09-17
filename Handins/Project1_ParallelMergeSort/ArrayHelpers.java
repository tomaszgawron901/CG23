package Handins.Project1_ParallelMergeSort;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

public class ArrayHelpers {
    
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

}
