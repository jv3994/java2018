package neural_nets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class NN {

	public static void main(String[] args) throws IOException {
		MNIST_data data =  new MNIST_data();
		
		//NN net = read("Z:\\000_Jannes\\Tech\\nets\\digits02.dat");
		
		
		//for(int b = 5; b<100; b+=10) {
		
		//System.out.println(MNIST_reader.testValid(net, data));
		NN net = new NN(new int[] {784, 30, 10});
		System.out.println(MNIST_reader.test(net, data));
		//for (int i = 0; i<10; i++) {
			
			MNIST_reader.train(net, 0.03, 10, 5000, 0, data);
			System.out.println(MNIST_reader.testValid(net, data));
		//}
		double d = MNIST_reader.test(net, data);
		System.out.println(MNIST_reader.test(net, data));
		net.write("Z:\\000_Jannes\\Tech\\nets\\digits05.dat", d);
		
		
		//NN net = read("Z:\\000_Jannes\\Tech\\nets\\digits04.dat");
		
		
		//double[] out = net.output(data.train_img[0]);
		//for(double x : out)
		//	System.out.println(x);
		//System.out.println();
		
		
		//}
		
		
		
	}
	
	double cost(double[][] input, double[][] y) {
		double cost = 0;
		for(int b = 0; b<input.length; b++) {
			double[] out = output(input[b]);
			for(int i = 0; i < out.length; i++) {
				cost += Utils.cost(out, y[b]);
			}
		}
		return cost;
	}
	
	void printOutput() {
		Cell[] c = cells[layers-1];
		for(Cell x:c)
			System.out.print(x.prevA + ", ");
		System.out.println();
	}
	
	void backpropagateBatch(double[][] input, double[][] y, double n, double lmb) {
		// clear changes in biases and weights
		for(int l = 1; l<layers; l++) {
			for(Cell c : cells[l]) {
				c.delta_b = 0;
				for (int j = 0; j<c.delta_w.length; j++) {
					c.delta_w[j] = 0;
				}
			}
		}
		// loop training cases
		for(int b = 0; b<input.length; b++) {
			// feed network with inputs
			double[] out = output(input[b]);
			// calc dC/dA of last layer
			for(int i = 0; i < size[layers-1]; i++) {
				//cells[layers-1][i].delta_a = Utils.costDeriv(out[i], y[b][i]);
				cells[layers-1][i].delta_a = (y[b][i] - out[i]) / Utils.sigmoidDeriv(cells[layers-1][i].prevZ);
			}
			// calc dC/dA of other layers
			for(int l = layers-2; l>0; l--) {
				for(int j = 0; j < size[l]; j++) {
					cells[l][j].delta_a = 0;
				}
				for(int i = 0; i < size[l+1]; i++) {
					for(int j = 0; j < size[l]; j++) {
						cells[l][j].delta_a += cells[l+1][i].delta_z() * cells[l+1][i].weights[j];
						//System.out.println(cells[l+1][i].delta_z() * cells[l+1][i].weights[j]);
					}
				}
			}
			// calc changes in weights and biases
			for(int l = 1; l<layers; l++) {
				for(int i = 0; i<size[l]; i++) {
					cells[l][i].delta_b += cells[l][i].delta_z();
					for(int j = 0; j<size[l-1]; j++) {
						cells[l][i].delta_w[j] += cells[l][i].delta_z() * cells[l-1][j].prevA;
						cells[l][i].delta_w[j] -= cells[l][i].weights[j] * lmb / 50000;
					}
				}
			}
		}
		// apply changes
		for(int l = 1; l<layers; l++) {
			for(int i = 0; i<size[l]; i++) {
				cells[l][i].applyChange(n);
			}
		}
	}
	
	int[] size;
	int layers;
	Cell[][] cells;
	
	void write (String str, double d) {
		
		DataOutputStream out;
		try {
			
			out = new DataOutputStream(new FileOutputStream(str));
			out.writeDouble(d);
			out.writeInt(layers);
			for (int i : size)
				out.writeInt(i);
			for (int l = 1; l < layers; l++) {
				for (int i = 0; i < size[l]; i++) {
					for (double j : cells[l][i].weights) {
						out.writeDouble(j);
					}
					out.writeDouble(cells[l][i].bias);
				}
			}
		}catch (IOException e) {
			System.err.println(e);
		}
		
		
	}
	
	public static NN read (String file) throws IOException {
		DataInputStream input = new DataInputStream(new FileInputStream(new File(file)));
		double d = input.readDouble();
		// System.out.println(d);
		int layers = input.readInt();
		int[] size = new int[layers];
		for (int i = 0; i<layers; i++) {
			size[i] = input.readInt();
		}
		NN net = new NN(size);
		for (int l = 1; l<layers; l++) {
			for (int i = 0; i<size[l]; i++) {
				for (int j = 0; j<size[l-1]; j++) {
					net.cells[l][i].weights[j] = input.readDouble();
				}
				net.cells[l][i].bias = input.readDouble();
			}
		}
		return net;
	}
	
	public NN (int[] size) {
		this.size = size;
		layers = size.length;
		cells = new Cell[layers][];
		for (int i = 0; i<layers; i++) {
			cells[i] = new Cell[size[i]];
		}
		for (int l = 0; l<layers; l++) {
			for(int i = 0; i<size[l]; i++) {
				if (l == 0)
					cells[l][i] = new Cell();
				else
					cells[l][i] = Cell.getRandomCell(size[l-1]);
			}
		}
	}
	
	public double[] output (double[] input) {
		for (int i = 0; i<size[0]; i++) {
			cells[0][i].prevA = input[i];
		}
		for (int l = 1; l<layers; l++) {
			for (int i = 0; i<size[l]; i++) {
				cells[l][i].updateOutput(cells[l-1]);
			}
		}
		double[] out = new double[size[layers-1]];
		for (int i = 0; i<out.length; i++) {
			out[i] = cells[layers-1][i].prevA;
		}
		return out;
	}
	
}
