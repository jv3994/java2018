package numbers;

public class Zero {

	public static void main(String[] args) {
		System.out.println(biSect(0, 2, 15));
		System.out.println(falsePos(0, 2, 15));
		System.out.println(Math.pow(2, (1./3)));
	}
	
	static double biSect(double low, double high, int n) {
		boolean dir = func(high) > func(low);
		for(int i = 0; i< n; i++) {
			double c = (high + low) / 2;
			if((func(c) > 0) ^ dir) {
				low = c;
			}else {
				high = c;
			}
		}
		return (high + low) / 2;
	}
	
	static double falsePos(double low, double high, int n) {
		boolean dir = func(high) > func(low);
		for(int i = 0; i< n; i++) {
			double dx = high - low;
			double dy = func(high) - func(low);
			double c = high - func(high) / (dy/dx);
			if((func(c) > 0) ^ dir) {
				low = c;
			}else {
				high = c;
			}
		}
		double dx = high - low;
		double dy = func(high) - func(low);
		double c = high - func(high) / (dy/dx);
		return c;
	}
	
	static double func(double x) {
		return 2 - x * x * x;
	}
}
