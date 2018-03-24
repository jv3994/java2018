package numbers;

import java.applet.*;
import java.awt.*;      // Graphics, Shape
import java.awt.geom.*; //Graphics2D
/*
<applet code = Oval1.class height=300 width=300 >
</applet>
*/
public class Oval1 extends Applet implements Runnable {
    Shape circle;
    Color c;
    public void init() {
        circle = new Ellipse2D.Float(90,100, 90, 90);
        repaint();
        Thread th = new Thread(this);
        th.start();
    }
    public void run() {
        try {
        	while(true) {

                System.out.println(1);
                c = Color.cyan;
                repaint();
                Thread.sleep(5);
                System.out.println(2);
                c = Color.gray;
                repaint();
                Thread.sleep(5);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void update(Graphics g) {
        paint(g);
    }
    public void paint(Graphics g) {
        Graphics2D d = (Graphics2D) g;
        d.setColor(c);
        d.fill(circle);
    }
}