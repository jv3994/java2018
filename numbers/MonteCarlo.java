package numbers;

public class MonteCarlo {

	public static void main(String[] args) {
		int n = 100000000;
		int ok = 0;
		for (int i = 0; i<n; i++){
			double x = Math.random();
			double y = Math.random();
			if (x*x + y*y < 1)
				ok++;
		}
		System.out.println(4. * ok / n);
		System.out.println(Math.PI);
	}
}
