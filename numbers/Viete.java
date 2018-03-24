package numbers;

import static java.lang.Math.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

public class Viete {

	public static void main(String[] args) throws IOException {
		String s = bigPi(1000).toString();
		BufferedWriter w = new BufferedWriter(new FileWriter("Z:\\000_Jannes\\Tech\\pi.txt"));
		w.write(s);
		w.flush();
		w.close();
	}
	
	static void test() {
		int scale = 500;
		BigDecimal pi = bigPi(1000,500);
		
		BigDecimal d = new BigDecimal("2");
		BigDecimal two = new BigDecimal("2");
		BigDecimal sqrt2 = sqrt(two,scale);
		int i = 0;
		long start = System.currentTimeMillis();
		while(i<750) {
			for (int k = 0; k<20; k++) {
				d = d.multiply(two);
				BigDecimal x = sqrt2;
				for(int j = 0; j<i; j++) {
					x = sqrt(two.add(x),scale);
				}
				d = d.divide(x, scale, BigDecimal.ROUND_HALF_UP);
				i++;
			}
			System.out.println(i+":\t"+check(d.toString(),pi.toString()));
		}
	}
	
	static double pi (int n) {
		double d = 2;
		int i = 0;
		long start = System.currentTimeMillis();
		while(i<n) {
			d *= 2;
			double x = Math.sqrt(2);
			for(int j = 0; j<i; j++) {
				x = Math.sqrt(2+x);
			}
			d /= x;
			i++;
		}
		return d;
	}
	
	static BigDecimal bigPi (int scale) {
		int buf = 10;
		scale += buf;
		int n = (int) (scale/0.6)+5;
		BigDecimal pi = bigPi (n,scale);
		String s = pi.toString();
		return new BigDecimal(s.substring(0, s.length()-buf));
	}
	
	static BigDecimal bigPi (int n, int scale) {
		BigDecimal d = new BigDecimal("2");
		BigDecimal two = new BigDecimal("2");
		BigDecimal sqrt2 = sqrt(two,scale);
		int i = 0;
		long start = System.currentTimeMillis();
		while(i<n) {
			d = d.multiply(two);
			BigDecimal x = sqrt2;
			for(int j = 0; j<i; j++) {
				x = sqrt(two.add(x),scale);
			}
			d = d.divide(x, scale, BigDecimal.ROUND_HALF_UP);
			i++;
		}
		return d;
	}
	
	static BigDecimal sqrt(BigDecimal a, int scale) {
		int iterations = 2+(int)(Math.log(scale) / Math.log(2));
		
		BigDecimal d = new BigDecimal(2);
		BigDecimal two = new BigDecimal(2);
		for(int i = 0; i<iterations; i++) {
			d= d.add(a.divide(d, scale, BigDecimal.ROUND_HALF_UP)).divide(two, scale, BigDecimal.ROUND_HALF_UP);
		}
			
		return d;
	}
	
	public static int check(String s, String c) {
		int i = 0;
		char[] a = s.toCharArray();
		char[] b = c.toCharArray();
		for(;;) {
			if(i >= s.length()) {
				return i-1;
			}
			if(a[i] != b[i]) {
				return i-1;
			}
			i++;
		}
	}
}
