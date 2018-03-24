package numbers;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Efraq {
	
	public static void main(String[] args) {
		BigDecimal e = E.e(100000, 50000);
		for(int i = 100; i<=10000; i+=200)
			System.out.println((E.compare(e, e(i+10, 50000))-E.compare(e, e(i, 50000)))/10.);
		
	}
	
	public static int[] numbers(int l) {
		int[] x = new int[l];
		x[0] = 2;
		for(int i = 1; i<l; i++)
			x[i] = i%3 == 2 ? i/3*2+2 : 1;
		return x;
	}
	
	public static int[] fraq(int[] l) {
		int i = l.length-1;
		int t = 1;
		int n = l[i--];
		while(i>=0) {
			t += n*l[i--];
			int x = t;
			t = n;
			n = x;
			
		}
		return new int[] {n,t};
	}
	
	public static BigDecimal e(int i, int scale) {
		BigInteger[] l = fraqBig(numbers(i));
		BigDecimal t = new BigDecimal(l[0]);
		BigDecimal n = new BigDecimal(l[1]);
		return t.divide(n, scale, BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigInteger[] fraqBig(int[] l) {
		int i = l.length-1;
		BigInteger t = BigInteger.ONE;
		BigInteger n = BigInteger.valueOf(l[i--]);
		while(i>=0) {
			t = t.add(n.multiply(BigInteger.valueOf(l[i--])));
			BigInteger x = t;
			t = n;
			n = x;
			
		}
		return new BigInteger[] {n,t};
	}
	
	public static double calc(int[] l) {
		int i = l.length-1;
		double d = l[i--];
		while(i>=0)
			d = 1/d + l[i--];
		return d;
	}
}
