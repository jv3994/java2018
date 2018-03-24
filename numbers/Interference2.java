package numbers;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.lang.Math.*;

public class Interference2 extends Applet implements Runnable, KeyListener{
	
	int d = 10;
	int g = 10;
	double f = 4;
	double c = 200;
	double decay = 0.9;
	
	int fieldx = 625;
	int fieldy = 310;
	int r = 4;
	
	double dt = 0.01;
	
	
	double[][] field;
	double[][] fieldv;
	int xscale = fieldx*r;
	int yscale = fieldy*r;
	double t = 0;
	boolean time = true;
	Graphics bufferGraphics;
    Image offscreen;
    Dimension dim; 
	
	public void run() {
		
		try {
			for(;;) {
				Thread.sleep(20);
				if(time) {
					for (int i = 0; i<10; i++)
						step();
					repaint();
				}
			}
		}catch (Exception e) {e.printStackTrace(System.err);;}
	}
	
	public void init() {
		setSize(xscale, yscale);
		dim = getSize();
        offscreen = createImage(dim.width,dim.height);
        bufferGraphics = offscreen.getGraphics();
		setBackground(Color.black);
		
		field = new double[fieldy][fieldx];
		fieldv = new double[fieldy][fieldx];
		for (int y = 0; y<fieldy; y++) {
			for (int x = 0; x<fieldx; x++) {
				field[y][x] = 0;
				fieldv[y][x] = 0;
			}
		}
		addKeyListener(this);
		
		new Thread(this).start();
	}
	
	void step() {
		
		for (int y = 0; y<fieldy; y++) {
			for (int x = 0; x<fieldx; x++) {
				if (x == 0) {
					field[y][x] = sin(t*f);
				} else if(x == 100 && !((y>fieldy/2-d-g && y<fieldy/2-d) || (y>fieldy/2+d/2 && y<fieldy/2+d/2+g))) {
					field[y][x] = 0;
				} else {
					if (y>0)
						fieldv[y][x] += dt * c * (field[y-1][x] - field[y][x]);
					if (y<fieldy-1)
						fieldv[y][x] += dt * c * (field[y+1][x] - field[y][x]);
					if (x>0)
						fieldv[y][x] += dt * c * (field[y][x-1] - field[y][x]);
					if (x<fieldx-1)
						fieldv[y][x] += dt * c * (field[y][x+1] - field[y][x]);
					
				}
			}
		}
		for (int y = 0; y<fieldy; y++) {
			for (int x = 0; x<fieldx; x++) {
				field[y][x] += dt * fieldv[y][x];
				fieldv[y][x] *= pow(decay, dt);
			}
		}
		t += dt;
		
	}
	
	public void paint(Graphics g) {
		bufferGraphics.clearRect(0,0,dim.width,dim.width); 
		for(int y =0; y<fieldy; y++) {
			for(int x=0; x<fieldx; x++) {
				bufferGraphics.setColor(color(field[y][x]));
				bufferGraphics.fillRect(x*xscale/fieldx, y*yscale/fieldy, xscale/fieldx, yscale/fieldy);
			}
		}
		g.drawImage(offscreen,0,0,this);
	}
	
	public void update(Graphics g) { 
		paint(g); 
	} 

	Color color(double d) {
		d = tanh(d);
		int i = min(255,(int)(abs(d)*256));
		if (d>0) {
			return new Color(i, 0, 0);
		} else {
			return new Color(0, 0, i);
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar() == ' ')
			time = !time;
	}
	
}
