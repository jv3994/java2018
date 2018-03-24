import static java.lang.Math.*;

public class Test2 {

	public static void main(String[] args) {
		int n = 2000000000;
		double dx = 1./n;
		
		double x = 0;
		double y = 1;
		for(int i = 0; i<n; i++) {
			y += dx * y;
			x += dx;
		}
		System.out.println(y);
		System.out.println(Math.E);
	}
	
}