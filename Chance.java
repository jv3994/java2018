import java.util.Random;

public class Chance {

	public static void main(String[] args) {
		int n = 100000;
		int x = 10;
		double p = 0.5;
		double[] data = new double[n];
		for(int i = 0; i<n; i++) {
			data[i] = 0;
			for(int j = 0; j<x; j++) {
				if (Math.random()<p)
					data[i]+= 1./x;
				
			}
		}
		System.out.println(mean(data));
		System.out.println(sd(data));
		
	}
	
	static double mean(double[] data) {
		double sum = 0;
		for(double i : data)
			sum += i;
		sum /= data.length;
		return sum;
	}
	
	static double sd (double[] data) {
		double mean = mean(data);
		double sum = 0;
		for (double i : data) {
			sum += (mean - i)*(mean - i);
		}
		sum /= data.length;
		sum = Math.sqrt(sum);
		return sum;
	}
	
}
