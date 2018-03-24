package neural_nets;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class Drawer extends Applet implements MouseListener {

	double[][] img;
	double[][] num;
	int c = 0;
	
	public void paint(Graphics g) {
		for(int y = 0; y<28; y++)
			for(int x = 0; x<28; x++) {
				double d = num[c%10][y*28 + x];
				int i = (int)((1-d)*255);
				g.setColor(new Color(i,i,i));
				g.fillRect(x*20, y*20, 20, 20);
			}
	}
	
	public void init () {
		setSize(560, 560);
		addMouseListener(this);
		NN net = new NN(new int[] {10,30,784});
		
		double[][] img = null;
		try {
			img = new MNIST_data().train_img;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int[] labels = null;
		try {
			labels = new MNIST_data().train_labels;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double[][] y = MNIST_reader.outputVector(labels);
		for(int j = 0; j<2000; j++) {
			int[] indices = new int[100];
			for (int i = 0; i<10; i++) {
				indices[i] = (int)(Math.random() * img.length);
			}
			double[][] input = new double[10][];
			double[][] output = new double[10][];
			for (int i = 0; i<10; i++) {
				input[i] = y[indices[i]];
				output[i] =img[indices[i]];
			}
			net.backpropagateBatch(input, output, 0.1, 0);
		}
		num = new double[10][784];
		for(int i = 0; i<10; i++) {
			num[i] = net.output(MNIST_reader.outputVector(new int[] {i})[0]);
		}
	}
	
	public void mouseClicked (MouseEvent me) { 
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
