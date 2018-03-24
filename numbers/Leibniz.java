package numbers;

public class Leibniz {

	public static void main(String[] args) {
		double d = 0;
		int i = 0;
		long t = System.currentTimeMillis();
		while (System.currentTimeMillis() < t+5*1000) {
			d += 4./(i*4+1) - 4./(i*4+3);
			i++;
		}
		d += 2./(i*4+1);
		System.out.println(i);
		System.out.println(d);
		System.out.println(Math.PI);
		
	}
}
