package Handins.Project3_ConvexHull;

import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Point2D.Double;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JFrame;

public class Visualize {
    public static void main(String[] args) throws InterruptedException {
        final var random = new Random();

        final int n = 4;
        Double[] points = new Double[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Double(random.nextDouble(), random.nextDouble());
        }
        
        //var out = INC_CH.wrap(points);
        //var out = GIFT_CH.wrap(points);
        var out = MbC_CH.wrap(Arrays.asList(points));

        for (Double point : out) {
            System.out.println(point);
        }

        JFrame frame = new JFrame();
        int h = 500;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(h, h);  
        frame.setLocation(200, 200);  
        frame.setVisible(true);
        frame.setBackground(Color.white);

        Thread.sleep(10);

        var grf =  (Graphics2D)frame.getGraphics();
        grf.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 

        grf.setPaint(Color.RED);
        for (Double point : points) {
            grf.fill(new Ellipse2D.Double(point.x*(h/2) + (h/4) - 2, point.y*(h/2) + (h/4) - 2, 4, 4));
        }

        grf.setColor(Color.blue);
        int[] xes = out.stream().mapToInt(p -> (int) (p.x*(h/2) + (h/4))).toArray();
        int[] yes = out.stream().mapToInt(p -> (int) (p.y*(h/2) + (h/4))).toArray();
        grf.drawPolygon(xes, yes, xes.length);
    }
}
