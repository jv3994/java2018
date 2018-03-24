package numbers;

import static java.lang.Math.*;

import java.applet.Applet;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Scrollbar;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Graph extends Applet implements MouseMotionListener{
	double xmin = 0;
	double xmax = 6;
	double ymin = -1;
	double ymax = 1;
	
	double dx = 0.01;
	int xscale = 1200;
	int yscale = 1200;
	
	Frame title;
	
	Color[] colors = {Color.yellow, Color.red, Color.blue};
	
	double func(double x) {
		//return 0.1*cos(4*x) + 0.8*cos(3*x) + 0.5*cos(10*x) + 1.2*cos(3.2 * x) + 0.4*cos(9.995 * x);
		return cos(3*x)+ 0.7*cos(2.5*x);
	}
	
	double[] f(double x) {
		double l = 30;
		int n = (int) (l * 500);
		double sumx = 0;
		double sumy = 0;
		for (int i = 0; i < n; i++) {
			double o = l*i/n;
			double r = func(o*2*PI);
			sumx += r*cos(-2*PI*o*x);
			sumy += r*sin(-2*PI*o*x);
		}
		sumx /= n;
		sumy /= n;
		return new double[] {sumx, sumy, sqrt(sumx*sumx+sumy*sumy)};
		//return func(x);
	}
	
	public void init() {
		setSize(xscale, yscale);
		setBackground(Color.black);
		addMouseMotionListener(this);
		title = (Frame)this.getParent().getParent();
	}
	
	public void paint(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2f));
		g2.setColor(Color.gray);
		
		if(xmin <= 0 && xmax >= 0) {
			double x = (0-xmin) / (xmax - xmin) * xscale;
			g2.drawLine((int)x, yscale, (int)x, 0);
		}
		if(ymin <= 0 && ymax >= 0) {
			double y = (0-ymin) / (ymax - ymin) * yscale;
			g2.drawLine(0, yscale-(int)y, xscale, yscale-(int)y);
		}
		g2.setStroke(new BasicStroke(2f));
		for(int i = 0; i<1; i++) {
			g2.setColor(colors[i]);
			double x = xmin;
			Point p1 = getPoint(x, i);
			x += dx;
			while (x<xmax) {
				Point p2 = getPoint(x, i);
				if (check(p1) && check(p2)) {
					g2.drawLine(p1.x, yscale-p1.y, p2.x, yscale-p2.y);
				}
				x += dx;
				p1 = p2;
			}
		}
	}
	
	Point getPoint (double x, int i) {
		double y = f(x)[i];
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
	
	public void mouseMoved(MouseEvent e) {
		updateTitle(e.getX(), e.getY());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		updateTitle(e.getX(), e.getY());
	}
	
	void updateTitle(int x, int y) {
		double gx = 1. * x / xscale * (xmax - xmin) + xmin;
		double gy = 1. * (yscale-y) / yscale * (ymax - ymin) + ymin;
		title.setTitle(gx + ";" + gy);
	}
}
