package neural_nets;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Oscillation {

	public static void main(String[] args) throws FileNotFoundException {
		PrintStream out = new PrintStream(new FileOutputStream("Z:\\000_Jannes\\Tech\\Java\\osc.csv"));
		
		double s1 = 0;
		double s2 = 0;
		double t = 0;
		double dt = 0.01;
		for(int i = 0; i<5000; i++) {
			s1 += dt*(100+10*s2);
			s2 += dt*(100-10*s1);
			s1 *= 0.99;
			s2 *= 0.99;
			t+=dt;
			out.println((t + ";" + s1 + ";" + s2).replace('.', ','));
		}
	}
}
