package neural_nets2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Data {

	static Data MNIST () {
		try {
			Data data = new Data();
			data.test_img = getImage("Z:\\000_Jannes\\Tech\\MNIST\\train-images-idx3-ubyte\\t10k-images.idx3-ubyte");
			data.test_labels = getLabels("Z:\\000_Jannes\\Tech\\MNIST\\train-images-idx3-ubyte\\t10k-labels.idx1-ubyte");
			double[][] train_in_img = getImage("Z:\\000_Jannes\\Tech\\MNIST\\train-images-idx3-ubyte\\train-images.idx3-ubyte");
			int[] train_in_labels = getLabels("Z:\\000_Jannes\\Tech\\MNIST\\train-images-idx3-ubyte\\train-labels.idx1-ubyte");
			data.train_img = new double[50000][784];
			data.train_labels = new int[50000];
			for(int i = 0; i<50000; i++) {
				data.train_img[i] = train_in_img[i];
				data.train_labels[i] = train_in_labels[i];
			}
			data.valid_img = new double[10000][784];
			data.valid_labels = new int[10000];
			for(int i = 0; i<10000; i++) {
				data.valid_img[i] = train_in_img[i+50000];
				data.valid_labels[i] = train_in_labels[i+50000];
			}
			return data;
		}catch (Exception e) {
			return null;
		}
		
	}
	
	double[][] train_img;
	int[] train_labels;
	double[][] test_img;
	int[] test_labels;
	double[][] valid_img;
	int[] valid_labels;
	
	private static double[][] getImage(String str) throws IOException{
		ByteBuffer input = ByteBuffer.wrap(Files.readAllBytes(Paths.get(str)));
		input.getInt();
		int n = input.getInt();
		int rows = input.getInt();
		int columns = input.getInt();
		int p = rows*columns;
		double[][] images = new double[n][p];
		for (int i = 0; i<n; i++) {
			for (int j = 0; j<p; j++) {
				int x = input.get();
				if(x<0) x+=255;
				images[i][j] = (double)x / 255;
			}
		}
		return images;
		
	}
	
	private static int[] getLabels(String str) throws IOException{
		ByteBuffer input = ByteBuffer.wrap(Files.readAllBytes(Paths.get(str)));
		input.getInt();
		int n = input.getInt();
		int[] labels = new int[n];
		for (int i = 0; i<n; i++) {
			int x = input.get();
			if(x<0) x+=255;
			labels[i] = x;
		}
		return labels;
	}
}