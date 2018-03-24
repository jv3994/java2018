package numbers;

import java.math.BigDecimal;

public class Nilakantha {
	
	public static void main(String[] args) {
		
		System.out.println(pi(100));
		System.out.println(Math.PI);
	}
	
	static double pi (int i) {
		double pi = 3;
		int n = 2;
		for (int j = 0; j<i; j++) {
			pi += 4./(n*(n+1)*(n+2));
			n+=2;
			pi -= 4./(n*(n+1)*(n+2));
			n+=2;
		}
		return pi;
	}
	
}
