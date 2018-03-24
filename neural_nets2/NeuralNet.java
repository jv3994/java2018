package neural_nets2;

public class NeuralNet {
	
	Layer[] layers;
	
	public NeuralNet(Layer[] layers) {
		this.layers = layers;
	}
	
	void put(double[] input) {
		layers[0].a = input;
		for(int i = 1; i<layers.length; i++) {
			layers[i].put(layers[i-1].a);
		}
	}
	
	double[] output() {
		return layers[layers.length-1].a;
	}
	
	void backpropagateBatch(double[][] in, double[][] y, double n) {
		for(int i = 1; i<layers.length; i++) {
			layers[i].zero_d_wb();
			
		}
		for(int i = 0; i<in.length; i++) {
			backpropagate(in[i], y[i]);
			
		}
		for(int i = 1; i<layers.length; i++) {
			layers[i].apply(n);
		}
	}
	
	void backpropagate(double[] in, double[] y) {
		put(in);
		
		layers[layers.length-1].output_z_change(y);
		
		for (int i = layers.length-2; i>0; i--) {
			layers[i].calc_z_change(layers[i+1].d_z, layers[i+1].weights);
			
		}
		for (int i = 1; i<layers.length; i++) {
			layers[i].calc_d_wb(layers[i-1].a);
		}
	}
	
	
}
