package numbers;

import java.applet.Applet;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Scrollbar;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import static java.lang.Math.*;

public class SinWrap extends Applet implements Runnable{
	
	double ffunc = 1;
	double fvis = 1;
	
	Graphics bufferGraphics;
    Image offscreen;
    Dimension dim; 
    
    int count = 0;
	
	double dt = 0.0001;
	double t0 = 0;
	double tmax = 5;
	
	double xmin = -2.1;
	double xmax = 2.1;
	double ymin = -2.1;
	double ymax = 2.1;
	
	int xscale = 1200;
	int yscale = 1200;
	
	public void run() {
        try {
        	while(true) {

                fvis = count++ / 700.;
                repaint();
                Thread.sleep(20);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
	
	public void init() {
		setSize(xscale, yscale);
		setBackground(Color.black);
		
		dim = getSize();
        offscreen = createImage(dim.width,dim.height);
        bufferGraphics = offscreen.getGraphics(); 
		
		new Thread(this).start();
	    
	}
	
	double f (double x) {
		return 2*cos(3*x*2*PI);
	}
	
	public void paint(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) bufferGraphics;
		g2.clearRect(0,0,dim.width,dim.width);
		g2.setStroke(new BasicStroke(1.5f));
		g2.setColor(Color.gray);
		
		if(xmin < 0 && xmax > 0) {
			double x = (0-xmin) / (xmax - xmin) * xscale;
			g2.drawLine((int)x, 0, (int)x, yscale);
		}
		if(ymin < 0 && ymax > 0) {
			double y = (0-ymin) / (ymax - ymin) * yscale;
			g2.drawLine(0, (int)y, xscale, (int)y);
		}
		
		g2.setStroke(new BasicStroke(3f));
		g2.setColor(Color.yellow);
		double t = t0;
		Point p1 = getPoint(t);
		t += dt;
		while (t<tmax) {
			Point p2 = getPoint(t);
			if (check(p1) && check(p2)) {
				g2.drawLine(p1.x, p1.y, p2.x, p2.y);
			}
			t += dt;
			p1 = p2;
		}
		g.drawImage(offscreen,0,0,this); 
	}
	
	public void update(Graphics g) {
		paint(g);
	}
	
	Point getPoint (double t) {
		double r = f(t);
		double x = cos(2 * PI * t * fvis) * r;
		double y = sin(2 * PI * t * fvis) * r;
		x = (x-xmin) / (xmax - xmin) * xscale;
		y = (y-ymin) / (ymax - ymin) * yscale;
		Point p = new Point();
		p.x = (int)x;
		p.y = (int)y;
		return p;
		
	}
	
	boolean check (Point p) {
		if (p.x<0 || p.x>xscale)
			return false;
		if (p.y<0 || p.y>yscale)
			return false;
		return true;
	}
	
}

class Point {
	int x;
	int y;
	
	public Point() {
	}
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}