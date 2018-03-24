package neural_nets;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class NeuronVisualiser extends Applet implements MouseListener {

	int c = 0;
	NN net;
	
	
	public void paint(Graphics g) {
		for(int y = 0; y<28; y++)
			for(int x = 0; x<28; x++) {
				double[] num = net.cells[1][c%100].weights;
				double d = num[y*28 + x];
				Color c;
				if (d>0) {
					d = (Utils.sigmoid(d)-0.5)*2*255;
					c = new Color((int)d,0,0);
				} else {
					d = -d;
					d = (Utils.sigmoid(d)-0.5)*2*255;
				c = new Color(0,0,(int)d);
				}
				g.setColor(c);
				g.fillRect(x*20, y*20, 20, 20);
			}
	}
	
	public void init () {
		setSize(560, 560);
		addMouseListener(this);
		try {
			net = NN.read("Z:\\000_Jannes\\Tech\\nets\\ok.dat");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*net = new NN(new int[] {784,10});
		MNIST_data data;
		try {
			data = new MNIST_data();
			MNIST_reader.train(net, 0.1, 10, 10000, 0, data);
			System.out.println(MNIST_reader.test(net, data));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		Frame title = (Frame)this.getParent().getParent();
	    title.setTitle("" + 0);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		c++;
		Frame title = (Frame)this.getParent().getParent();
	    title.setTitle("" + c%100);
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
