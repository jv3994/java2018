package numbers;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import static java.lang.Math.*;

public class Interference extends Applet implements Runnable {
	
	double v = 0.001;
	double f = 0.00001;
	double t = 0;
	
	int n = 300;
	int scale = 900;
	
	int d = 200;
	Point s1 = new Point(scale/2,scale/2-d/2);
	Point s2 = new Point(scale/2,scale/2+d/2);
	double b = 500;
	
	Graphics bufferGraphics;
    Image offscreen;
    Dimension dim; 
	
	public void init() {
		setSize(scale, scale);
		
		dim = getSize();
        offscreen = createImage(dim.width,dim.height);
        bufferGraphics = offscreen.getGraphics();
		setBackground(Color.black);
		new Thread(this).start();
	}
	
	public void run() {
		try {
			Thread.sleep(500);
			for(;;) {
				t += 0.1;
				repaint();
			}
		}catch (Exception e) {}
	}
	
	double u (Point p) {
		double r1 = sqrt(pow(p.x - s1.x, 2) + pow(p.y - s1.y, 2));
		double r2 = sqrt(pow(p.x - s2.x, 2) + pow(p.y - s2.y, 2));
		double d = 0;
		if (t>r1/v)
			d += sin(f*2*PI*(r1/v-t))/pow(r1/b+1,2);
		if (t>r2/v)
			d += sin(f*2*PI*(r2/v-t))/pow(r2/b+1,2);
		return -d/2;
	}
	
	public void paint(Graphics g) {
		bufferGraphics.clearRect(0,0,dim.width,dim.width); 
		for(int x =0; x<n; x++) {
			for(int y=0; y<n; y++) {
				Point p = new Point((int)(scale/n*(x+0.5)),(int)(scale/n*(y+0.5)));
				bufferGraphics.setColor(color(u(p)));
				bufferGraphics.fillRect(scale/n*(x), scale/n*(y), scale/n, scale/n);
			}
		}
		bufferGraphics.setColor(Color.white);
		bufferGraphics.fillOval(s1.x-3, s1.y-3, 6, 6);
		bufferGraphics.fillOval(s2.x-3, s2.y-3, 6, 6);
		g.drawImage(offscreen,0,0,this); 
	}
	
	public void update(Graphics g) { 
         paint(g); 
    } 
	
	Color color(double d) {
		Color c;
		d /= 0.5 + 1/pow(this.d/b+1, 2);
		if (d>0) {
			c = new Color((int)(Math.round(255*d)),0,0);
		} else {
			c = new Color(0,0,(int)(Math.round(-255*d)));
		}
		return c;
	}
}
