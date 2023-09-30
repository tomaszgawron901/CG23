package Excercises.d2ch;

import java.util.Random;
import java.awt.geom.Point2D;

public class Task1 {
    public static float GetOrientation_1(Point2D.Float p1, Point2D.Float p2, Point2D.Float p3) {

        return p1.x*(p2.y - p3.y) + p2.x*(p3.y - p1.y) + p3.x*(p1.y - p2.y);
    }

    public static float GetOrientation_2(Point2D.Float p1, Point2D.Float p2, Point2D.Float p3) {

        return p1.x*p2.y - p1.x*p3.y + p2.x*p3.y - p2.x*p1.y + p3.x*p1.y - p3.x*p2.y;
    }

    public static float GetOrientation_3(Point2D.Float p1, Point2D.Float p2, Point2D.Float p3) {

        return p1.x*p2.y + p2.x*p3.y + p3.x*p1.y - p1.x*p3.y - p2.x*p1.y - p3.x*p2.y;
    }

    public static void main(String[] args) {
        int n = (int)Math.pow(10, 5);
        
        var random = new Random(6);
        Point2D.Float[] array = new Point2D.Float[n];
        float x = 0;
        for(int i = 0; i < n; i++) 
        {
            array[i] = new Point2D.Float(x, x*x);
            x += random.nextFloat();
        }

        int errors = 0;
        for(int i = 2; i < n; i++) {
            var orientation = GetOrientation_3(array[i-2], array[i-1], array[i]);
            if(orientation <= 0) {
                errors++;
            }
        }
        System.out.println(errors);

    }
}
