package neural_nets;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MNIST_reader {
	
	public static void main(String[] args) throws IOException  {
		long start = System.currentTimeMillis();
		MNIST_data data = new MNIST_data();
		System.out.println("data loaded");
		NN nets[] = new NN[4];
		double[] accuracy = new double[4];
		for (int i = 0; i<nets.length; i++) {
			nets[i] = new NN(new int[] {784,20,10});
			train(nets[i], 0.05, 100, 5000, 0.001, data);
			accuracy[i] = test(nets[i], data);
			System.out.println(accuracy[i]);
		}
		double max = 0;
		int max_index = -1;
		for (int i = 0; i<nets.length; i++) {
			if(accuracy[i] > max) {
				max = accuracy[i];
				max_index = i;
			}
		}
		System.out.println(max_index);
		//train(nets[max_index], 0.04, 200, 5000, data);
		double acc = test(nets[max_index], data);
		System.out.println(acc);
		nets[max_index].write("Z:\\000_Jannes\\Tech\\nets\\digits03.dat", acc);
		System.out.println("done in " + (System.currentTimeMillis() - start));
		
		
	}
	
	static void train(NN net, double n, int batchSize, int a, double l, MNIST_data data) throws IOException {
		double[][] img = data.train_img;
		int[] labels = data.train_labels;
		double[][] y = outputVector(labels);
		while (a-- > 0) {
			int[] indices = new int[batchSize];
			for (int i = 0; i<batchSize; i++) {
				indices[i] = (int)(Math.random() * 50000);
			}
			double[][] input = new double[batchSize][];
			double[][] output = new double[batchSize][];
			for (int i = 0; i<batchSize; i++) {
				input[i] = img[indices[i]];
				output[i] = y[indices[i]];
			}
			net.backpropagateBatch(input, output, n, l);
		}
		//System.out.println(System.currentTimeMillis() - start);
	}

	static double test(NN net, double[][] input, int[] labels) {
		
		int good = 0;
		for (int i = 0; i<input.length; i++) {
			double[] out = net.output(input[i]);
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
		return (double)good / input.length;
	}
	
	static double test(NN net, MNIST_data data) throws IOException {
		double[][] img = data.test_img;
		int[] labels = data.test_labels;
		return test(net, img, labels);
	}
	
	static double testValid(NN net, MNIST_data data) throws IOException {
		double[][] img = data.valid_img;
		int[] labels = data.valid_labels;
		return test(net, img, labels);
	}
	
	//static double[][] trainingImages () throws IOException{
	//	return getImages("Z:\\000_Jannes\\Tech\\MNIST\\train-images-idx3-ubyte\\train-images.idx3-ubyte",
	//			"Z:\\000_Jannes\\Tech\\MNIST\\train-images-idx3-ubyte\\train-labels.idx1-ubyte");
	//}
	
	//static double[][][] testImages () throws IOException{
	//	return getData("Z:\\000_Jannes\\Tech\\MNIST\\train-images-idx3-ubyte\\t10k-images.idx3-ubyte",
	//			"Z:\\000_Jannes\\Tech\\MNIST\\train-images-idx3-ubyte\\t10k-labels.idx1-ubyte");
	//}
	
	static double[][] getImage(String str) throws IOException{
		File f = new File(str);
		DataInputStream input = new DataInputStream(new FileInputStream(f));
		input.readInt();
		int n = input.readInt();
		int rows = input.readInt();
		int columns = input.readInt();
		int p = rows*columns;
		double[][] images = new double[n][p];
		for (int i = 0; i<n; i++) {
			for (int j = 0; j<p; j++) {
				images[i][j] = (double)input.readUnsignedByte() / 255;
			}
		}
		input.close();
		return images;
		
	}
	
	static int[] getLabels(String str) throws IOException{
		File f = new File(str);
		DataInputStream input = new DataInputStream(new FileInputStream(f));
		input.readInt();
		int n = input.readInt();
		int[] labels = new int[n];
		for (int i = 0; i<n; i++) {
			labels[i] = input.readUnsignedByte();
		}
		input.close();
		
		double[][][] out = new double[2][][];
		return labels;
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
}
