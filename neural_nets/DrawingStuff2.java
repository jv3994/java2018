package neural_nets;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
 
import javax.swing.JApplet;
import javax.swing.JPanel;
 
@SuppressWarnings("serial")
public class DrawingStuff2 extends JApplet {
   public void init() {
      try {
         javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
               createGUI();
            }
         });
      } catch (Exception e) {
         System.err.println("createGUI didn't successfully complete");
      }
   }
 
   private void createGUI() {
	  DrawingStuff2Panel panel = new DrawingStuff2Panel();
      getContentPane().add(panel);
      setSize(panel.xscale, panel.yscale);
   }
}
 
@SuppressWarnings("serial")
class DrawingStuff2Panel extends JPanel implements AdjustmentListener{
	double ffunc = 3;
	double fvis = 1.21;
	
	double dt = 0.002;
	double t0 = 0;
	double tmax = 4.5;
	
	double xmin = -2.1;
	double xmax = 2.1;
	double ymin = -2.1;
	double ymax = 2.1;
	
	int xscale = 600;
	int yscale = 600;
	
	Scrollbar slider;
	int sliderValue;
	
	double f (double x) {
		return sin(2 * PI * x * ffunc) + 1;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
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
		
		g2.setStroke(new BasicStroke(2f));
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
		double r = f(t);
		double x = cos(2 * PI * t * fvis) * r;
		double y = sin(2 * PI * t * fvis) * r;
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
	
	
	
	public DrawingStuff2Panel() {
		setSize(xscale, yscale+25);
		setBackground(Color.black);
		
		slider = new Scrollbar(Scrollbar.HORIZONTAL, 0, 1, 0, 601);
		
		slider.setPreferredSize(new Dimension(xscale,20));
		add(slider);
		
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent arg0) {
		sliderValue = slider.getValue();
		fvis = sliderValue / 600. * 3;
		repaint();
	}
}

class Point {
	int x;
	int y;
}
