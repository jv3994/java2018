package numbers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

public class Babylon {

	public static void main(String[] args) throws IOException {
		BigDecimal b = sqrt(new BigDecimal(2));
		String s = b.toString();
		
		write(s);
		System.out.println(s.length());
		System.out.println(s.charAt(s.length()-3));
		System.out.println(s.charAt(s.length()-2));
		System.out.println(s.charAt(s.length()-1));
	}
	
	static void write(String s) throws IOException {
		BufferedWriter w = new BufferedWriter(new FileWriter("Z:\\000_Jannes\\Tech\\new.txt"));
		w.write(s);
		w.flush();
		w.close();
		
	}
	
	static double sqrt(double a) {
		double d = 1;
		for(int i = 0; i<10; i++)
			d = (d+a/d)/2;
		return d;
	}
	
	static BigDecimal sqrt(BigDecimal a) {
		int scale = 1000158;
		int iterations = 32;
		
		BigDecimal d = new BigDecimal(1);
		BigDecimal two = new BigDecimal(2);
		for(int i = 0; i<iterations; i++) {
			d= d.add(a.divide(d, scale, BigDecimal.ROUND_HALF_UP)).divide(two, scale, BigDecimal.ROUND_HALF_UP);
		}
			
		return d;
	}
}
