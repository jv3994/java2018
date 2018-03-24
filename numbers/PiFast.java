package numbers;

import java.math.BigDecimal;
import java.math.BigInteger;

public class PiFast {

	public static void main(String[] args) {
		System.out.println(piBig(BigInteger.valueOf(72)));
		System.out.println(piBig(BigInteger.valueOf(73)));
		System.out.println(piBig(BigInteger.valueOf(500)));
		System.out.println(Math.PI);
	}
	
	static BigDecimal piBig2(int i) {
		
	}
	
	static BigDecimal piBig(BigInteger x) {
		
		BigInteger six = BigInteger.valueOf(6);
		BigInteger three = BigInteger.valueOf(3);
		BigInteger eight = BigInteger.valueOf(8);
		BigInteger zero = BigInteger.valueOf(0);
		BigInteger one = BigInteger.valueOf(1);
		
		BigInteger k1 = BigInteger.valueOf(545140134);
		BigInteger k2 = BigInteger.valueOf(13591409);
		BigDecimal k3= BigDecimal.valueOf(640320);
		BigInteger k4 = BigInteger.valueOf(100100025);
		BigInteger k5 = BigInteger.valueOf(327843840);
		BigDecimal k6 = BigDecimal.valueOf(53360);
		
		BigDecimal s = BigDecimal.ZERO;
		
		for (BigInteger i = zero; i.compareTo(x)<0; i=i.add(one)) {
			BigInteger t = facc(six.multiply(i)).multiply(k2.add(i.multiply(k1)));
			BigInteger n = pow(facc(i),three).multiply(facc(three.multiply(i)).multiply(pow((eight.multiply(k4.multiply(k5))),i)));
			s = s.add(new BigDecimal(t).divide(new BigDecimal(n), 1000, BigDecimal.ROUND_HALF_UP));
		}
		BigDecimal pi = k6.multiply(sqrt(k3,1000).divide(s,1000,BigDecimal.ROUND_HALF_UP));
		return pi;
	}
	
	static BigDecimal sqrt(BigDecimal a, int scale) {
		int iterations = 1000;
		
		BigDecimal d = new BigDecimal(1);
		BigDecimal two = new BigDecimal(2);
		for(int i = 0; i<iterations; i++) {
			d= d.add(a.divide(d, scale, BigDecimal.ROUND_HALF_UP)).divide(two, scale, BigDecimal.ROUND_HALF_UP);
		}
			
		return d;
	}
	
	static double piDouble(int x) {
		
		int k1 = 545140134;
		int k2 = 13591409;
		int k3 = 640320;
		int k4 = 100100025;
		int k5 = 327843840;
		int k6 = 53360;
		
		double s = 0;
		for (int i = 0; i<x; i++) {
			long t = facc(6*i)*(k2+i*k1);
			long n = pow(facc(i),3)*facc(3*i)*pow((8*k4*k5),i);
			s += t/n;
		}
		System.out.println(s);
		double pi = k6 * Math.sqrt(k3) / s;
		return pi;
	}
	
	static long facc (long l) {
		long out = 1;
		for (long i = 1; i<= l; i++) 
			out *= i;
		return out;
	}
	
	static long pow(long l, long e) {
		long out = 1;
		for(int i = 0; i<e; i++)
			out *= l;
		return out;
	}
	
	static BigInteger facc (BigInteger b) {
		BigInteger out = BigInteger.ONE;
		for (BigInteger i = BigInteger.ONE; i.compareTo(b)<=0; i=i.add(BigInteger.ONE))
			out = out.multiply(i);
		return out;
	}
	
	static BigInteger pow (BigInteger g, BigInteger e) {
		BigInteger out = BigInteger.ONE;
		for (BigInteger i = BigInteger.ZERO; i.compareTo(e)<0; i=i.add(BigInteger.ONE))
			out = out.multiply(g);
		return out;
	}
	
}
