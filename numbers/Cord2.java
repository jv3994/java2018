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

public class Cord2 extends Applet implements Runnable, MouseMotionListener{
	
	double c = 1000;
	double g = 1;
	double m = 10;
	double dt = 0.0004;
	double t = 0;
	
	double[] cord_x;
	double[] cord_y;
	double[] cord_vx;
	double[] cord_vy;
	int n = 40;
	
	double field_x = 10;
	double field_y = 10;
	int scale_x = 1000;
	int scale_y = 1000;
	double r = field_x / n / 2;
	
	double my = field_y / 2;
	
	Graphics bufferGraphics;
    Image offscreen;
    Dimension dim;
	
	public void init() {
		setBackground(Color.black);
		setSize(scale_x, scale_y);
        dim = getSize();
        offscreen = createImage(dim.width,dim.height);
        bufferGraphics = offscreen.getGraphics(); 
        
		cord_x = new double[n];
		cord_y = new double[n];
		cord_vx = new double[n];
		cord_vy = new double[n];
		for(int i = 0; i<n; i++) {
			cord_x[i] = field_x / n * i;
			cord_y[i] = field_y / 2;
			cord_vx[i] = 0;
			cord_vy[i] = 0;
		}
		addMouseMotionListener(this);
		new Thread(this).start();
	}
	
	public void paint(Graphics g) {
		setSize(scale_x, scale_y);
		bufferGraphics.clearRect(0,0,dim.width,dim.width);
		Graphics2D g2 = (Graphics2D) bufferGraphics;
		g2.setColor(Color.yellow);
		g2.setStroke(new BasicStroke(4.0f));
		Point p1 = getPoint(0);
        for(int i = 1; i<n; i++) {
        	Point p2 = getPoint(i);
        	g2.drawLine(p1.x, p1.y, p2.x, p2.y);
        	p1 = p2;
        }
        g.drawImage(offscreen,0,0,this);
	}
	
	Point getPoint(int i) {
		Point p = new Point();
		p.x = (int)(cord_x[i]/field_x*scale_x);
		p.y = scale_y-(int)(cord_y[i]/field_y*scale_y);
		return p;
	}
	
	void step () {
		cord_y[0] = sin(t*5) / 3 + field_y / 2;
		for(int i = 1; i<n-1; i++) {
			double r1 = sqrt(pow(cord_x[i-1]-cord_x[i], 2) + pow(cord_y[i-1]-cord_y[i], 2));
			if (r1 > r) {
				cord_vx[i] += dt * (cord_x[i-1]-cord_x[i]) / r1 * (r1-r) * c;
				cord_vy[i] += dt * (cord_y[i-1]-cord_y[i]) / r1 * (r1-r) * c;
			}
			double r2 = sqrt(pow(cord_x[i+1]-cord_x[i], 2) + pow(cord_y[i+1]-cord_y[i], 2));
			if (r1 > r) {
				cord_vx[i] += dt * (cord_x[i+1]-cord_x[i]) / r2 * (r2-r) * c;
				cord_vy[i] += dt * (cord_y[i+1]-cord_y[i]) / r2 * (r2-r) * c;
			}
			cord_vy[i] -= dt * g;
			cord_vx[i] *= pow(0.7, dt);
			cord_vy[i] *= pow(0.7, dt);
			cord_x[i] += dt * cord_vx[i];
			cord_y[i] += dt * cord_vy[i];
			
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
				Thread.sleep(10);
				for (int i = 0; i<10; i++)
					step();
				repaint();
			}
		} catch(Exception e) {}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		my = field_y - e.getY() * field_y / scale_y;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}
	
}
