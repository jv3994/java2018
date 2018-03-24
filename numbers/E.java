package numbers;

import java.math.BigDecimal;
import java.math.BigInteger;

public class E {

	public static void main(String[] args) {
		int scale = 40000;
		BigDecimal e = e(10000, scale);
		//System.out.println(e);
		System.out.println((compare(e, e(8010, scale))-compare(e, e(8000, scale)))/10);
		//System.out.println(e);
	}
	
	static BigInteger[] cf(BigDecimal n, int i, int scale) {
		BigInteger[] b = new BigInteger[i];
		for(int j = 0; j<b.length; j++) {
			b[j] = n.toBigInteger();
			System.out.println(b[j]);
			n = BigDecimal.ONE.divide(n.subtract(new BigDecimal(b[j])), scale, BigDecimal.ROUND_HALF_UP);
		}
		return b;
	}
	
	static BigDecimal ucf(BigInteger[] b, int scale) {
		
	}
	
	static int compare (BigDecimal a, BigDecimal b) {
		String s1 = a.toString();
		String s2 = b.toString();
		int i = 0;
		while(true) {
			if (i>=s1.length() || i>=s2.length() || s1.charAt(i) != s2.charAt(i))
				return i;
			i++;
		}
	}
	
	static BigDecimal e (int i, int scale) {
		BigDecimal a = new BigDecimal(1);
		BigDecimal b = a;
		int n = 2;
		for (int j = 0; j<i; j++) {
			a = a.add(b);
			b = b.divide(new BigDecimal(n++), scale, BigDecimal.ROUND_HALF_UP);
		}
		return a;
	}
}
