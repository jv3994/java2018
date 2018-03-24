package numbers;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.applet.*; 
import java.awt.event.*; 
import java.awt.*; 

public class Rope extends Applet implements Runnable, MouseMotionListener{
	Graphics bufferGraphics;
	Image offscreen;
	Dimension dim;
	
	double dt = 0.01;
	double c = 5;
	
	double[] x;
	double[] y;
	double[] vx;
	double[] vy;
	int n = 20;
	
	double ax = 500;
	double ay = 300;
	
	double dist = 25;
	double g = 2;
	
	public void init() { 
    	  setSize(1000,1000);
          dim = getSize();
          offscreen = createImage(dim.width,dim.height);
          bufferGraphics = offscreen.getGraphics();
          addMouseMotionListener(this);
          
          x = new double[n];
          y = new double[n];
          vx = new double[n];
          vy = new double[n];
          
          for(int i = 0; i<n; i++) {
        	  x[i] = 500;
        	  vx[i] = 0;
        	  y[i] = 300 + 25 * (i+1);
        	  vy[i] = 0;
          }
          
          new Thread(this).start();
     }
     
     public void paint(Graphics g) { 
    	 bufferGraphics.clearRect(0,0,dim.width,dim.width);
    	 bufferGraphics.setColor(Color.black);
    	 bufferGraphics.drawLine((int)x[0], (int)y[0], (int)ax, (int)ay);
    	 for(int i = 1; i<n; i++)
    		 bufferGraphics.drawLine((int)x[i-1], (int)y[i-1], (int)x[i], (int)y[i]);
    	 bufferGraphics.setColor(Color.blue);
    	 for(int i = 0; i<n; i++)
    		 bufferGraphics.fillOval((int)x[i]-10, (int)y[i]-10, 20, 20);
    	 g.drawImage(offscreen,0,0,this); 
	}
     
	public void update(Graphics g) { 
		paint(g); 
	}
	
	void step() {
		double r;
		r = sqrt(pow(ax-x[0], 2) + pow(ay-y[0], 2));
		if (r > dist) {
			vx[0] += dt * (ax-x[0]) / r * (r-dist) * c;
			vy[0] += dt * (ay-y[0]) / r * (r-dist) * c;
		}
		r = sqrt(pow(x[1]-x[0], 2) + pow(y[1]-y[0], 2));
		if (r > dist) {
			vx[0] += dt * (x[1]-x[0]) / r * (r-dist) * c;
			vy[0] += dt * (y[1]-y[0]) / r * (r-dist) * c;
		}
		for (int i = 1; i< n-1; i++) {
			r = sqrt(pow(x[i-1]-x[i], 2) + pow(y[i-1]-y[i], 2));
			if (r > dist) {
				vx[i] += dt * (x[i-1]-x[i]) / r * (r-dist) * c;
				vy[i] += dt * (y[i-1]-y[i]) / r * (r-dist) * c;
			}
			r = sqrt(pow(x[i-1]-x[i], 2) + pow(y[i-1]-y[i], 2));
			if (r > dist) {
				vx[i] += dt * (x[i+1]-x[i]) / r * (r-dist) * c;
				vy[i] += dt * (y[i+1]-y[i]) / r * (r-dist) * c;
			}
		}
		r = sqrt(pow(x[n-2]-x[n-1], 2) + pow(y[n-2]-y[n-1], 2));
		if (r > dist) {
			vx[n-1] += dt * (x[n-2]-x[n-1]) / r * (r-dist) * c;
			vy[n-1] += dt * (y[n-2]-y[n-1]) / r * (r-dist) * c;
		}
		for(int i = 0; i<n; i++) {
			vy[i] += dt * g;
			vx[i] *= pow(0.85, dt);
			vy[i] *= pow(0.85, dt);
			x[i] += dt * vx[i];
			y[i] += dt * vy[i];
		}
	}

	public void run() {
		try {
			for(;;) {
				Thread.sleep(20);
				for (int i = 0; i<10; i++)
					step();
				repaint();
			}
		} catch (Exception e) {}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		ax = e.getX();
		ay = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	} 
	
}