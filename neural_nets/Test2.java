package neural_nets;

import java.io.IOException;

public class Test2 {

	
	public static void main(String[] args) throws IOException {
		MNIST_data data = new MNIST_data();
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
		
		NN net = new NN(new int[] {784, 30, 10});
		
		System.out.println(MNIST_reader.test(net, data));
		
		double[][] img = avr;
		int[] labels = {0,1,2,3,4,5,6,7,8,9};
		double[][] y = MNIST_reader.outputVector(labels);
		int a = 5000;
		while (a-- > 0) {
			int[] indices = new int[3];
			for (int i = 0; i<3; i++) {
				indices[i] = (int)(Math.random() * 10);
			}
			double[][] input = new double[3][];
			double[][] output = new double[3][];
			for (int i = 0; i<3; i++) {
				input[i] = img[indices[i]];
				output[i] = y[indices[i]];
			}
			net.backpropagateBatch(input, output, 0.03, 300);
		}
		
		System.out.println(MNIST_reader.test(net, data));
	}
}
