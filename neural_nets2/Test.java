package neural_nets2;

import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException {
		Layer[] layers = new Layer[3];
		layers[0] = new Layer(0,784);
		layers[1] = new Layer(784,30);
		layers[2] = new Layer(layers[1].outputs,10);
		NeuralNet net = new NeuralNet(layers);
		Data data = Data.MNIST();
		
		
		double[][] img = data.test_img;
		int[] labels = data.test_labels;
		
		//System.out.println(labels[0]);
		
		//net.put(img[0]);
		//for(double d : net.output())
		//	System.out.println(d);
		
		System.out.println(test(net, data));
		train(net,img,labels,10,5000,0.3);
		System.out.println(test(net, data));
		
		
		//System.out.println("----");
		//net.put(img[0]);
		//for(double d : net.output())
		//	System.out.println(d);
		
		//System.out.println(net.layers[2].d_biases[0]);
		
		/*System.out.println(test(net, data));
		train(net,img,labels,10,10000,0.1);
		double[] output = net.output();
		for(double d : output)
			System.out.println(d);
		
		net.put(img[0]);
		output = net.output();
		for(double d : output)
			System.out.println(d);*/
		
	}
	
	static void train(NeuralNet net, double[][] img, int[] labels, int size, int a, double n) {
		while (a-- > 0) {
			int[] indices = new int[size];
			double[][] y = outputVector(labels);
			for (int i = 0; i<size; i++) {
				indices[i] = (int)(Math.random() * img.length);
			}
			double[][] input = new double[size][];
			double[][] output = new double[size][];
			for (int i = 0; i<size; i++) {
				input[i] = img[indices[i]];
				output[i] = y[indices[i]];
			}
			net.backpropagateBatch(input, output, n);
		}
	}
	
	static double[][] outputVector(int[] i) {
		double[][] out = new double[i.length][10];
		for (int x = 0; x<i.length; x++) {
			for (int j = 0; j<10; j++)
				out[x][j] = 0;
			out[x][i[x]] = 1;
		}
		return out;
	}
	
	static double test(NeuralNet net, double[][] input, int[] labels) {
		int good = 0;
		for (int i = 0; i<input.length; i++) {
			net.put(input[i]);
			double[] out = net.output();
			double max = 0;
			int maxIndex = -1;
			for(int j = 0; j< out.length; j++) {
				if (out[j] > max) {
					max = out[j];
					maxIndex = j;
				}
			}
			if (maxIndex == labels[i])
				good++;
		}
		return (double)good / input.length * 100;
	}
	
	static double test(NeuralNet net, Data data) throws IOException {
		double[][] img = data.test_img;
		int[] labels = data.test_labels;
		return test(net, img, labels);
	}
	
}

