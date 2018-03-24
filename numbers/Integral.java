package numbers;

import java.math.BigDecimal;

public class Integral {

	public static void main(String[] lol) {
		double p = Math.PI;
		System.out.println(p-e(1));
		System.out.println(p-e(10));
		System.out.println(p-e(100));
		System.out.println(p-e(1000));
		System.out.println(p-e(10000));
		System.out.println(p-e(100000));
		System.out.println(p-e(1000000));
		System.out.println(p-e(10000000));
		System.out.println(p-e(100000000));
	}
	
	static BigDecimal e (int i, int scale) {
		BigDecimal sum = BigDecimal.ZERO;
		BigDecimal j = BigDecimal.ONE.divide(new BigDecimal(i).multiply(new BigDecimal(2)), scale, BigDecimal.ROUND_HALF_UP);
		while(j.compareTo(BigDecimal.ONE)<0) {
			sum = sum.add()
			j = j.add(BigDecimal.ONE.divide(new BigDecimal(i), scale, BigDecimal.ROUND_HALF_UP));
		}
	}
	
	static double e (int i) {
		
		double sum = 0;
		for(double j = 1./i/2; j<1; j+= 1./i)
			sum += Math.sqrt(1-j*j);
		sum *= 4./i;
		return sum;
	}
}
