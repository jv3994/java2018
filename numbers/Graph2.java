package numbers;

import java.applet.Applet;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Scrollbar;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import static java.lang.Math.*;

public class Graph2 extends Applet implements AdjustmentListener {
	
	double dt = 0.001;
	double t0 = 0;
	double tmax = 100;
	
	double xmin = -1.1;
	double xmax = 1.1;
	double ymin = -1.1;
	double ymax = 1.1;
	
	int xscale = 1200;
	int yscale = 1200;
	
	Scrollbar slider;
	int sliderValue = 500;
	Frame title;
	
	public void init() {
		setSize(xscale, yscale+25);
		setBackground(Color.black);
		
		slider = new Scrollbar(Scrollbar.HORIZONTAL, 500, 1, 0, 1001);
		
		slider.setPreferredSize(new Dimension(1000,20));
		add(slider);
		slider.addAdjustmentListener(this);

		title = (Frame)this.getParent().getParent();
	    
	}
	
	double fx (double t) {
		return sin(t * 1000. / sliderValue);
	}
	
	double fy (double t) {
		return cos(t);
	}
	
	public void paint(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(1.5f));
		g2.setColor(Color.gray);
		
		if(xmin < 0 && xmax > 0) {
			double x = (0-xmin) / (xmax - xmin) * xscale;
			g2.drawLine((int)x, 25, (int)x, yscale+25);
		}
		if(ymin < 0 && ymax > 0) {
			double y = (0-ymin) / (ymax - ymin) * yscale;
			g2.drawLine(0, (int)y+25, xscale, (int)y+25);
		}
		
		g2.setStroke(new BasicStroke(1f));
		g2.setColor(Color.yellow);
		double t = t0;
		Point p1 = getPoint(t);
		t += dt;
		while (t<tmax) {
			Point p2 = getPoint(t);
			if (check(p1) && check(p2)) {
				g2.drawLine(p1.x, p1.y+25, p2.x, p2.y+25);
			}
			t += dt;
			p1 = p2;
		}
	}
	
	Point getPoint (double t) {
		double x = fx(t);
		double y = fy(t);
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
	
	public void adjustmentValueChanged(AdjustmentEvent e) {
		sliderValue = slider.getValue();
		repaint();
		title.setTitle("" + fvis);
	}
	
}