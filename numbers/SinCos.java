package numbers;

public class SinCos {
	
	public static void main(String[] args) {
		long i = 0;
		while(facc(i)>0)
			i++;
		System.out.println(i);
		System.out.println(pow(5,7));
		System.out.println(sin(0.65,20));
		System.out.println(Math.sin(0.65));
	}
	
	private static double sin(double x, long it) {
		double sum = 0;
		int plus = 1;
		for (long i = 1; i<it; i+=2) {
			sum += plus * pow(x,i) / facc(i);
			plus *= -1;
		}
		return sum;
	}
	
	private static double cos(double x, long it) {
		double sum = 0;
		int plus = 1;
		for (long i = 0; i<it; i+=2) {
			sum += plus * pow(x,i) / facc(i);
			plus *= -1;
		}
		return sum;
	}
	
	private static long facc (long x) {
		long y = 1;
		while (x > 0)
			y *= x--;
		return y;
	}
	
	private static double pow (double x, long e) {
		double y = 1;
		while(e-->0)
			y *= x;
		return y;
	}
}
