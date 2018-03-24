package neural_nets;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class Test extends Applet implements MouseListener{
	
	int c = 0;
	double[][] num;
	
	
	public void paint(Graphics g) {
		for(int y = 0; y<28; y++)
			for(int x = 0; x<28; x++) {
				double d = num[c%10][y*28 + x];
				int i = (int)((1-d)*255);
				i = Math.max(Math.min(i, 255), 0);
				g.setColor(new Color(i,i,i));
				g.fillRect(x*20, y*20, 20, 20);
			}
	}
	
	public void init () {
		addMouseListener(this);
		
		setSize(560, 560);
		
		MNIST_data data = null;
		try {
			data = new MNIST_data();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double[][] avr = new double[10][784];
		int[] n = new int[10];
		for (int i = 0; i<10; i++) {
			for (int j = 0; j<784; j++) {
				avr[i][j] = 0;
			}
			n[i] = 0;
		}
		for (int i = 0; i < data.train_img.length; i++) {
			n[data.train_labels[i]]++;
			for(int j = 0; j<784; j++) {
				avr[data.train_labels[i]][j] += data.train_img[i][j];
			}
		}
		for (int i = 0; i<10; i++) {
			for (int j = 0; j<784; j++) {
				avr[i][j] /= n[data.train_labels[i]];
			}
		}
		num = avr;
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		c++;
		repaint();
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
