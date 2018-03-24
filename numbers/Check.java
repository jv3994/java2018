package numbers;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

public class Check {
	public static void main(String[] args) throws Throwable {
		BigDecimal two = new BigDecimal(2);
		String sqrt2 = sqrt2();
		for(int i = 0; i<20; i++) {
			BigDecimal b = sqrt(two, i);
			String s = b.toString();
			System.out.println(i+": " + check(s,sqrt2));
		}
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
	
	static String sqrt2() {
		try {
			return new BufferedReader(new FileReader("Z:\\000_Jannes\\Tech\\sqrt2.txt")).readLine();
		} catch (IOException e) {}
		return null;
	}
	
	static BigDecimal sqrt(BigDecimal a, int iterations) {
		int scale = 100000;
		
		BigDecimal d = new BigDecimal(1);
		BigDecimal two = new BigDecimal(2);
		for(int i = 0; i<iterations; i++) {
			d= d.add(a.divide(d, scale, BigDecimal.ROUND_HALF_UP)).divide(two, scale, BigDecimal.ROUND_HALF_UP);
		}
			
		return d;
	}
	
	static BigDecimal sqrt(BigDecimal a) {
		int scale = 100;
		int iterations = 32;
		
		BigDecimal d = new BigDecimal(1);
		BigDecimal two = new BigDecimal(2);
		for(int i = 0; i<iterations; i++) {
			d= d.add(a.divide(d, scale, BigDecimal.ROUND_HALF_UP)).divide(two, scale, BigDecimal.ROUND_HALF_UP);
		}
			
		return d;
	}
}
