package numbers;

import java.applet.Applet;
import java.awt.Graphics;

public class Fourier extends Applet{
	
	double min_x = 0;
	double min_y = 0;
	double max_x = 1;
	double max_y = 1;
	
	public void paint(Graphics g) {
		double steps = 1000;
		
		
		double old_x = min_x;
		double old_y = func(old_x);
		for (int i = 1; i<=steps; i++) {
			double new_x = (max_x - min_x) * i/steps + min_x;
			double new_y = func(old_x);
			
			
			g.drawLine((int)(old_x/(max_x-min_x)*560), (int)(old_y/(max_y-min_y)*560), (int)(new_x/(max_x-min_x)*560), (int)(new_y/(max_y-min_y)*560));
			
			old_x = new_x;
			old_y = new_y;
		}
	}
	
	public void init() {
		setSize(560, 560);
	}
	
	
	
	public double func(double d) {
		
	}
	
}

