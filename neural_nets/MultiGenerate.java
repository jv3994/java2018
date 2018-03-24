package neural_nets;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MultiGenerate {

	public static void main(String[] args) throws IOException, InterruptedException {
		MNIST_data data = new MNIST_data();
		File dir = new File("Z:\\000_Jannes\\Tech\\nets\\threadgen");
		File[] files = dir.listFiles();
	    if(files!=null) {
	        for(File f: files) {
	        	f.delete();
	        }
	    }
		Thread[] t = new Thread[3];
		for (int i = 0; i<t.length; i++) {
			t[i] = new MyThread(i, data);
			t[i].start();
		}
		int w = 0;
		while (w<t.length) {
			if(!t[w].isAlive()) {
				w++;
				continue;
			}
			Thread.sleep(500);
		}
		
		int n = t.length;
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
		double d = MNIST_reader.test(net, new MNIST_data());
		System.out.println(d);
		//net.write("Z:\\000_Jannes\\Tech\\nets\\ok.dat", d);
		
		
	}
	
	
	
}

class MyThread extends Thread{
	public void run() {
		
		try {
			
			NN net = new NN(new int[] {784, 30, 10});
			//System.out.println(MNIST_reader.test(net, data));
			MNIST_reader.train(net, 0.1, 10, 5000, 0, data);
			double d = MNIST_reader.testValid(net, data);
			//System.out.println(d);
			net.write("Z:\\000_Jannes\\Tech\\nets\\threadgen\\" + i + ".dat", d);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public MyThread(int i, MNIST_data data) {
		this.i = i;
		this.data = data;
	}
	int i;
	MNIST_data data;
}