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
import java.util.Random;

import static java.lang.Math.*;

public class OrbitMechanics extends Applet implements Runnable{
	
	int fpr = 100;
	double dt = 0.0002;
	static int traillength = 400;
	
	double G = 50;
	
	double xmin = 0;
	double xmax = 100;
	double ymin = 0;
	double ymax = 100;
	
	int xscale = 1200;
	int yscale = 1200;
	
	Frame title;
	
	Body[] bodies;
	
	Graphics bufferGraphics;
    Image offscreen;
    Dimension dim;
	
	public void init() {
		setSize(xscale, yscale+25);
        dim = getSize();
        setBackground(Color.black);
        offscreen = createImage(dim.width,dim.height);
        bufferGraphics = offscreen.getGraphics(); 
		
		title = (Frame)this.getParent().getParent();
		
		//bodies = new Body[2];
		//bodies[0] = new Body(50,50,0,-0.5,10);
		//bodies[1] = new Body(75,50,0,5,1);
		bodies = new Body[10];
		for(int i = 0; i<10; i++)
			bodies[i] = new Body(random()*100,random()*100,random()*6-3,random()*6-3,random()*10);
		Thread cd = new Thread(this); cd.start();
	    
	}
	
	void physics() {
		for (Body b : bodies) {
			b.ax = 0;
			b.ay = 0;
			for (Body x : bodies) {
				if (x != b) {
					double dx = x.x - b.x;
					double dy = x.y - b.y;
					double r = sqrt(pow(dx, 2) + pow(dy, 2));
					double a = G * x.m / r / r;
					b.ax += dx / r * a;
					b.ay += dy / r * a;
				}
			}
			b.vx += b.ax * dt;
			b.vy += b.ay * dt;
			b.x += b.vx * dt;
			b.y += b.vy * dt;
			for (int i = 1; i < traillength; i++) {
				b.trailx[i] = b.trailx[i-1];
				b.traily[i] = b.traily[i-1];
				b.trailx[0] = b.x;
				b.traily[0] = b.y;
			}
		}
		
	}
	
	public void update(Graphics g){ 
		paint(g); 
	}

	public void paint(Graphics g) {
		bufferGraphics.clearRect(0,0,dim.width,dim.width);
		
		Graphics2D g2 = (Graphics2D) bufferGraphics;
		
		for (Body b : bodies) {
			Point p = getPoint(b.x, b.y);
			if(check(p)) {
				g2.setColor(b.c);
				g2.fillOval((int)(p.x-b.r), (int)(p.y-b.r), (int)(2*b.r), (int)(2*b.r));
			}
		}
        g.drawImage(offscreen,0,0,this); 
	}
	
	Point getPoint (double x, double y) {
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

	@Override
	public void run() {
		for(int i = 0; i<100000; i++) {
			for(int j = 0; j<fpr; j++) {
				physics();
			}
			repaint();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}

class Body{
	double x;
	double y;
	double vx;
	double vy;
	double ax;
	double ay;
	double r;
	
	double m;
	
	double[] trailx;
	double[] traily;
	
	Color c;
	
	public Body(double x, double y, double vx, double vy, double m) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.m = m;
		trailx = new double[OrbitMechanics.traillength];
		traily = new double[OrbitMechanics.traillength];
		Random r = new Random();
		c = new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256));
		this.r = pow(m, 1./3)*8;
	}
}