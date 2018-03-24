package neural_nets;

import static java.lang.Math.E;
import static java.lang.Math.pow;

public class Utils {

	public static double sigmoid(double z) {
		//return z;
		return 1/(1+pow(E,-z));
	}
	
	public static double sigmoidDeriv(double z) {
		//return 1;
		return sigmoid(z) * (1-sigmoid(z));
	}
	
	public static double cost(double[] v1, double[] v2) {
		double[] d = new double[v1.length];
		for(int i = 0; i < v1.length; i++) 
			d[i] = v2[i] - v1[i];
		double sum = 0;
		for (double x : d) 
			sum += x*x;
		return sum;
	}
	
	public static double costDeriv(double a, double y) {
		//return 2*(y-a);
		//return (a-y) / ((a-1)*a);
		return sigmoidDeriv(a-y);
		
	}
	
}
