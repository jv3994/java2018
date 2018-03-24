package numbers;

import java.applet.Applet;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static java.lang.Math.*;

public class SlopeField extends Applet implements MouseListener{
	
	int scale;
	
	double xmin;
	double xmax;
	double ymin;
	double ymax;
	
	double px0 = -6;
	double dx = 0.0001;
	double py0 = 10;
	
	double size = 1;
	int res = 50;
	int r = 10;
	
	double slope(double x, double y) {
		return -x/y;
	}
	
	@Override
	public void init() {
		scale = res * (2 * r + 1);
		setSize(scale, scale);
		xmin = ymin = -size * (r+0.5);
		xmax = ymax = size * (r+0.5);
		setBackground(Color.black);
		this.addMouseListener(this);
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(1f));
		g2.setColor(new Color(0x333333));
		for (int i = 0; i < (2*r+1); i++) {
			g2.drawLine(0, res/2 + res*i, scale, res/2 + res*i);
			g2.drawLine(res/2 + res*i, 0, res/2 + res*i, scale);
		}
		g2.setStroke(new BasicStroke(3f));
		g2.setColor(new Color(0x2356ff));
		g2.drawLine(0, scale / 2, scale, scale / 2);
		g2.drawLine(scale / 2, 0, scale / 2, scale);
		g2.setColor(new Color(0xCCCCCC));
		for (int i = -r; i <= r; i++) {
			String str = "" + i * size;
			while(str.length()<5)
				str = " " + str;
			int pos = i * res + scale / 2;
			g2.drawString(str, pos-25, scale - scale/2+12);
			if(i != 0)
				g2.drawString(str, scale/2-25, scale - pos+12);
		}
		
		
		g2.setStroke(new BasicStroke(2f));
		g2.setColor(new Color(0x52948c));
		for(int ix = -r; ix<=r; ix++) {
			for(int iy = -r; iy<=r; iy++) {
				double slope = slope(ix*size, iy*size);
				double x = ix * res + scale / 2;
				double y = iy * res + scale / 2;
				if(Math.abs(slope)>1) {
					int x1 = (int)(x - 1/slope/2.*res);
					int x2 = (int)(x + 1/slope/2.*res);
					int y1 = scale-(int)(y - res / 2.);
					int y2 = scale-(int)(y + res / 2.);
					g2.drawLine(x1, y1, x2, y2);
				}else {
					int x1 = (int)(x - res / 2.);
					int x2 = (int)(x + res / 2.);
					int y1 = scale-(int)(y - slope/2.*res);
					int y2 = scale-(int)(y + slope/2.*res);
					g2.drawLine(x1, y1, x2, y2);
				}
			}
		}
		g2.setStroke(new BasicStroke(3f));
		g2.setColor(new Color(0xFF0000));
		double x = px0;
		double y = py0;
		Point p1 = getPoint(x, y);
		y += dx * slope(x, y);
		x += dx;
		while (x <= xmax && y <= ymax && x >= xmin && y >= ymin) {
			Point p2 = getPoint(x, y);
			if (true) {
				g2.drawLine(p1.x, scale-p1.y, p2.x, scale-p2.y);
			}
			y += dx * slope(x, y);
			x += dx;
			p1 = p2;
		}
		x = px0;
		y = py0;
		p1 = getPoint(x, y);
		y -= dx * slope(x, y);
		x -= dx;
		while (x <= xmax && y <= ymax && x >= xmin && y >= ymin) {
			Point p2 = getPoint(x, y);
			if (true) {
				g2.drawLine(p1.x, scale-p1.y, p2.x, scale-p2.y);
			}
			y -= dx * slope(x, y);
			x -= dx;
			p1 = p2;
		}
	}
	
	Point getPoint (double x, double y) {
		x = (x-xmin) / (xmax - xmin) * scale;
		y = (y-ymin) / (ymax - ymin) * scale;
		Point p = new Point();
		p.x = (int)x;
		p.y = (int)y;
		return p;
		
		
	}
	
	boolean check (Point p) {
		if (p.x<0 || p.x>scale)
			return false;
		if (p.y<0 || p.y>scale)
			return false;
		return true;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		px0 = 1.*e.getX()/scale*(xmax-xmin)+xmin;
		py0 = (ymax-ymin)-1.*e.getY()/scale*(ymax-ymin)+ymin;
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
