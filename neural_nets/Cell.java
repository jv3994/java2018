package neural_nets;

import java.util.Random;

public class Cell {
	
	public double prevA;
	public double prevZ;
	
	public static Cell getRandomCell(int n) {
		Cell c = new Cell();
		Random r = new Random();
		c.bias = r.nextGaussian();
		c.weights = new double[n];
		c.delta_w = new double[n];
		int x = n;
		while (x-->0) {
			c.weights[x] = r.nextGaussian() / Math.sqrt(n);
			c.delta_w[x] = 0;
		}
		return c;
	}
	
	double bias;
	double[] weights;
	
	double updateOutput (Cell[] c) {
		double z = bias;
		for (int i = 0; i<c.length; i++)
			z += c[i].prevA * weights[i];
		prevA = Utils.sigmoid(z);
		prevZ = z;
		return prevA;
	}
	
	void applyChange(double n) {
		for(int i=0; i<weights.length; i++) {
			weights[i] += n * delta_w[i];
			
		}
		bias += n * delta_b;
	}
	
	double delta_b;
	double[] delta_w;
	
	double delta_a;
	double delta_z() {
		return delta_a * Utils.sigmoidDeriv(prevZ);
	};
	
}