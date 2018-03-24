package neural_nets;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MultiReader {

	public static void main(String[] args) throws IOException {
		File f = new File("Z:\\000_Jannes\\Tech\\nets\\threadgen");
		int n = f.listFiles().length;
		double max = 0;
		int maxIndex = -1;
		for (int i = 0; i<n; i++) {
			DataInputStream input = new DataInputStream(new FileInputStream(new File("Z:\\000_Jannes\\Tech\\nets\\threadgen\\" + i + ".dat")));
			double d = input.readDouble();
			if(d>max) {
				max = d;
				maxIndex = i;
			}
		}
		NN net = NN.read("Z:\\000_Jannes\\Tech\\nets\\threadgen\\" + maxIndex + ".dat");
		System.out.println(MNIST_reader.test(net, new MNIST_data()));
		
	}
}
