package neural_nets2;

import static java.lang.Math.E;
import static java.lang.Math.pow;

import java.util.Random;

public class Layer {

	int outputs;
	int inputs;
	
	double[] biases;
	double[][] weights;
	
	double[] d_biases;
	double[][] d_weights;
	
	double[] z;
	double[] a;
	
	double[] d_z;
	
	public Layer(int in, int out) {
		outputs = out;
		inputs = in;
		biases = new double[out];
		for(int i = 0; i<out; i++) {
			biases[i] = new Random().nextGaussian();
		}
		d_biases = new double[out];
		weights = new double[out][in];
		for(int i = 0; i<out; i++) {
			for(int j = 0; j<in; j++) {
				weights[i][j] = new Random().nextGaussian() / Math.sqrt(in);
			}
		}
		d_weights = new double[out][in];
		z = new double[outputs];
		d_z = new double[outputs];
		a = new double[outputs];
	}
	
	void put (double[] inputs) {
		
		for(int i = 0; i<z.length; i++) {
			z[i] = 0;
			for(int j = 0; j<inputs.length; j++) {
				z[i] += inputs[j] * weights[i][j];
			}
			z[i] += biases[i];
			a[i] = sigmoid(z[i]);
		}
	}
	
	static double sigmoid(double z) {
		return 1/(1+pow(E,-z));
	}
	
	static double sigmoid_deriv(double z) {
		return sigmoid(z) * (1-sigmoid(z));
	}
	
	void apply(double n) {
		for(int i = 0; i<biases.length; i++) {
			biases[i] -= n * d_biases[i];
			for(int j = 0; j<weights[i].length; j++) {
				weights[i][j] -= n * d_weights[i][j];
			}
		}
		
		
	}
	
	void output_z_change(double[] y) {
		for(int i = 0; i<d_z.length; i++) {
			d_z[i] = sigmoid(z[i]) - y[i];
		}
		
	}
	
	void calc_z_change(double[] d_z, double[][] weights) {
		for(int i = 0; i<d_z.length; i++) {
			this.d_z[i] = 0;
		}
		for(int i = 0; i<this.d_z.length; i++) {
			for(int j = 0; j<d_z.length; j++) {
				this.d_z[i] += d_z[j] * weights[j][i] * sigmoid_deriv(this.z[i]);
				
			}
			
		}
		
		
	}
	
	void calc_d_wb(double[] a) {
		//System.out.println("hi");
		for(int i = 0; i<biases.length; i++) {
			d_biases[i] += d_z[i];
			for(int j = 0; j<a.length; j++) {
				d_weights[i][j] += d_z[i] * a[j];
			}
		}
		
		
	}
	
	void zero_d_wb() {
		for(int i = 0; i<biases.length; i++) {
			d_biases[i] = 0;
			for(int j = 0; j<weights[i].length; j++) {
				d_weights[i][j] = 0;
			}
		}
	}
	
}