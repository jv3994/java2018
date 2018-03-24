package neural_nets;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

public class Detector extends Applet implements MouseMotionListener{

	NN net;
	double[] field;
	Button  clear;
	Button  detect;
	Button random;
	Label label;
	MNIST_data data;
	
	public void paint(Graphics g) {
	    update(g);
	}

	public void update(Graphics g) {

		for(int y = 0; y<28; y++) {
			for(int x = 0; x<28; x++) {
				double d = field[y*28 + x];
				int i = (int)((1-d)*255);
				g.setColor(new Color(i,i,i));
				g.fillRect(x*20, y*20, 20, 20);
			}
		}
	}
	
	public void init () {
		setSize(560, 660);
		addMouseMotionListener(this);
		field = new double[784];
		reset();
		
		this.setLayout(null);
		
	    clear = new Button("Clear");
	    detect = new Button("Detect");
	    random = new Button("Random");
	    label = new Label();
	    
	    clear.setBounds(50, 580, 100, 60);
	    detect.setBounds(175,580,100,60);
	    random.setBounds(300, 580, 100, 60);
	    label.setBounds(425,580,100,60);
	    
	    label.setText("draw something");
	    
	    this.add(label);
	    this.add(clear);
	    this.add(random);
	    this.add(detect);
	    
	    
	    clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				reset();
				repaint();
			}
	    });
	    detect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				double m = 0;
				double mx = 0;
				double my = 0;
				for (int i = 0; i<784; i++) {
					m += field[i];
					mx += field[i] * i%28;
					my += field[i] * (i/28);
				}
				
				int tx = (int)Math.round(13.5-mx/m);
				int ty = (int)Math.round(13.5-my/m);
				
				double[] x_map = new double[784];
				if(tx == 0) {
					x_map = field;
				}else if(tx > 0) {
					for (int i = 0; i<tx; i++) {
						for (int j = i; j<784; j+= 28) {
							x_map[j] = 0;
						}
					}
					for (int i = tx; i<28; i++) {
						for (int j = i; j<784; j+=28) {
							x_map[j] = field[j-tx];
						}
					}
				}else {
					for (int i = 28; i>28+tx; i--) {
						for (int j = 28+i; j<784; j+= 28) {
							x_map[j] = 0;
						}
					}
					for (int i = 28+tx; i>0; i--) {
						for (int j = i; j<784+tx; j+=28) {
							x_map[j] = field[j-tx];
						}
					}
				}
				field = x_map;
				double[] y_map = new double[784];
				if (ty == 0) {
					y_map = field;
				}else if(ty>0) {
					for (int i = 0; i<ty; i++) {
						for (int j = i*28; j<i*28+28; j++){
							y_map[j] = 0;
						}
					}
					for (int i = ty; i<28; i++) {
						for (int j = i*28; j<i*28+28; j++){
							y_map[j] = field[j-28*ty];
						}
					}
				}else {
					for(int i = 27; i>28+ty; i--) {
						for (int j = i*28; j<i*28+28; j++){
							y_map[j] = 0;
						}
					}
					for(int i = 27+ty; i>=0; i--) {
						for (int j = i*28; j<i*28+28; j++){
							y_map[j] = field[j-28*ty];
						}
					}
				}
				field = y_map;
				repaint();
				double[] out = net.output(field);
				double max = 0;
				int maxIndex = -1;
				for(int j = 0; j< out.length; j++) {
					if (out[j] > max) {
						max = out[j];
						maxIndex = j;
					}
				}
				label.setText(String.valueOf(maxIndex));
			}
	    });
	    random.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int i = (int)(Math.random()*data.test_img.length);
				field = data.test_img[i];
				double[] out = net.output(field);
				double max = 0;
				int maxIndex = -1;
				for(int j = 0; j< out.length; j++) {
					if (out[j] > max) {
						max = out[j];
						maxIndex = j;
					}
				}
				label.setText("d: " + maxIndex + ", l: " + data.test_labels[i]);
				repaint();
				
			}
	    });
	    
		try {
			net = NN.read("Z:\\000_Jannes\\Tech\\nets\\ok.dat");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			data = new MNIST_data();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	void reset() {
		for (int i = 0; i<784; i++)
			field[i] = 0;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		try{
			int x = (int)this.getMousePosition().getX() / 20;
			int y = (int)this.getMousePosition().getY() / 20;
			x = Math.min(Math.max(x, 0), 27);
			y = Math.min(Math.max(y, 0), 27);
			int p =  (28 * y + x);
			
			for(int i : new int[]{0,1,28,29}) {
				field[i+p] = 1;
			}
			repaint();
		}catch(Exception e) {
			
		}
			
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	

	

}
