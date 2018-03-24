import java.util.Random;

public class Test3 {

	public static void main(String[] args) {
		int n = 10000000;
		double sum = 0;
		Random r = new Random();
		double max = 0;
		for(int i = 0; i<n; i++) {
			double d = r.nextGaussian();
			if(Math.abs(d)>max)
				max = d;
		}
		System.out.println(max);
	}
	
}
