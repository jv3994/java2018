package numbers;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import static java.lang.Math.*;

public class Sphere extends Applet{
	
	int res = 1;
	int r = 200;
	int scale = 1000;
	
	int offset = 100;
	
	public void init() {
		setSize(scale,scale);
	}
	
	public void paint(Graphics g) {
		double maxr = 0;
		for(int i = 0; i<r/res*2; i++) {
			for(int j = 0; j<r/res*2; j++) {
				int x = i*res-r+scale/2;
				int y = j*res-r+scale/2;
				if(pow(x-scale/2, 2)+pow(y-scale/2, 2) < r*r) {
					double r = sqrt(pow(scale/2-offset-x,2)+pow(scale/2-offset-y,2));
					r /= offset+r/sqrt(2);
					r = r / 1.3 + 0.15;
					if(r>maxr)
						maxr=r;
					if(r<0.5) {
						int q = (int)Math.round(255*(1-2*r));
						g.setColor(new Color(q,q,255));
					} else {
						int q = (int)Math.round(255*(2-2*r));
						g.setColor(new Color(0,0,q));
					}
					g.fillRect(x-res/2, y-res/2, res, res);
				}
			}
		}
	}

}
