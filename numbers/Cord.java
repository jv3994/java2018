package numbers;

import java.applet.Applet;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static java.lang.Math.*;

public class Cord extends Applet implements Runnable, MouseMotionListener{
	
	double c = 0.0;
	double dt = 0.002;
	double t = 0;
	boolean up = true;
	double x = 50000;
	
	double my = 0;
	
	double amp = 5;
	
	double[] cord;
	double[] cordv;
	int n = 250;
	
	int xscale = 1000;
	int yscale = 800;
	
	Graphics bufferGraphics;
    Image offscreen;
    Dimension dim;
	
	public void init() {
		setSize(xscale, yscale);
        dim = getSize();
        offscreen = createImage(dim.width,dim.height);
        bufferGraphics = offscreen.getGraphics(); 
        
		cord = new double[n];
		cordv = new double[n];
		for(int i = 0; i<n; i++) {
			cord[i] = 0;
			cordv[i] = 0;
		}
		cord[0] = 0.5;
		addMouseMotionListener(this);
		new Thread(this).start();
	}
	
	public void paint(Graphics g) {
		bufferGraphics.clearRect(0,0,dim.width,dim.width);
		Graphics2D g2 = (Graphics2D) bufferGraphics;
		g2.setStroke(new BasicStroke(4.0f));
		Point p1 = new Point(0, (int)(yscale*(0.5 - cord[0]/amp/2)));
        for(int i = 1; i<n; i++) {
        	Point p2 = new Point((int)(xscale/n*i), (int)(yscale*(0.5 - cord[i]/amp/2)));
        	g2.drawLine(p1.x, p1.y, p2.x, p2.y);
        	p1 = p2;
        }
        g.drawImage(offscreen,0,0,this);
	}
	
	void step () {
		cord[0] = my;//cord[n-1] = sin(5.4*t);
		if (cord[0] > 1) up = false;
		if (cord[0] < -1) up = true;
		for(int i = 1; i<n-1; i++) {
			cordv[i] += x * dt * ((cord[i-1] + cord[i+1])/2 - cord[i] - cord[i]*c);
			cordv[i] *= 0.999;
		}
		for(int i = 1; i<n-1; i++) {
			cord[i] += cordv[i] * dt;
		}
		t += dt;
	}
	
	public void update(Graphics g) {
		paint(g);
	}
	
	@Override
	public void run() {
		try {
			for(;;) {
				Thread.sleep(20);
				for(int i = 0; i<10; i++)
					step();
				repaint();
			}
		} catch(Exception e) {}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		my = -2*(0.+e.getY()-yscale/2)/yscale*amp;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	
}
