package neural_nets;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InputTest {

	public static void main(String[] args) throws IOException  {
		MNIST_data data = new MNIST_data();
		System.out.println(data.test_labels[0]);
	}
	
}
