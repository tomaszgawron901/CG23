package Handins.Project2_IO;

import java.util.Random;

public class drafts {
    public static void main(String[] args) {
        var rng = new Random();

        int n = 10_000;
        float sum = 0;
        for (int i = 0; i < n; i++) {
            var x = rng.nextFloat(0, 1);
            var y = rng.nextFloat(0, 1);
            sum += Math.abs(0-y);
        }
        System.out.println(sum/n);
    }
}
