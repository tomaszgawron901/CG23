package Excercises.d2ch;

import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Point2D.Float;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

public class GiftWrapping {
    public static ArrayList<Float> wrap(Float[] array) {
        final int n = array.length;
        final ArrayList<Float> output = new ArrayList<Float>();

        var lm = getLeftMostPointIndex(array);

        var p = lm;
        do {
            output.add(array[p]);

            var q = (p+1)%n; // could be just 1 but this way we handle sets with length of 2
            for (int i = 0; i < n; i++) {
                if (Task1.GetOrientation_1(array[p], array[q], array[i]) > 0F) {
                    q = i;
                }
            }
            p = q;

        } while (lm != p);


        return output;
    }

    private static int getLeftMostPointIndex(Float[] array) {
        var mostLeft = 0;
        for(int i=1; i < array.length; i++ ) {
            if (array[i].x < array[mostLeft].x) {
                mostLeft = i;
            }
        }
        return mostLeft;
    }

    
    public static void main(String[] args) {
        final var random = new Random();

        final int n = 10;
        Float[] points = new Float[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Float(random.nextFloat(), random.nextFloat());
        }
        
        var out = wrap(points);

        for (Float point : out) {
            System.out.println(point);
        }

        JFrame frame = new JFrame();
        int h = 500;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(h, h);  
        frame.setLocation(200, 200);  
        frame.setVisible(true);
        frame.setBackground(Color.white);

        var grf =  (Graphics2D)frame.getGraphics();
        grf.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 

        grf.setPaint(Color.RED);
        for (Float point : points) {
            grf.fill(new Ellipse2D.Double(point.x*(h/2) + (h/4) - 2, point.y*(h/2) + (h/4) - 2, 4, 4));
        }

        grf.setColor(Color.blue);
        int[] xes = out.stream().mapToInt(p -> (int) (p.x*(h/2) + (h/4))).toArray();
        int[] yes = out.stream().mapToInt(p -> (int) (p.y*(h/2) + (h/4))).toArray();
        grf.drawPolygon(xes, yes, xes.length);
    }
}