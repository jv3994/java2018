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
import java.util.Random;

public class Graph3 extends Applet implements MouseMotionListener{
	double xmin = 0;
	double xmax = 99;
	double ymin = 0;
	double ymax = 50000;
	
	double dx = 2;
	int xscale = 1200;
	int yscale = 1200;
	
	Frame title;
	
	Color[] colors = {Color.yellow, Color.red, Color.blue};
	
	int[] data;
	
	double f(double x) {
		int i = (int)Math.round(x);
		return data[i];
	}
	
	public void init() {
		setSize(xscale, yscale);
		setBackground(Color.black);
		addMouseMotionListener(this);
		title = (Frame)this.getParent().getParent();
		data = new int[100];
		for(int i = 0; i<100; i++)
			data[i] = 0;
		Random r = new Random();
		for(int i = 0; i<1000000; i++) {
			int j = min(max(r.nextInt(100),r.nextInt(100))*2,max(r.nextInt(100),r.nextInt(100)));
			data[j]++;
		}
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
		
		g2.setColor(Color.blue);
		double x = xmin;
		Point p1 = getPoint(x);
		x += dx;
		while (x<xmax) {
			Point p2 = getPoint(x);
			if (check(p1) && check(p2)) {
				g2.drawLine(p1.x, yscale-p1.y, p2.x, yscale-p2.y);
			}
			x += dx;
			p1 = p2;
		}
		
	}
	
	Point getPoint (double x) {
		double y = f(x);
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
