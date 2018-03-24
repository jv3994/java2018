import java.util.Random;

public class HelloWorld {

	public static void main (String[] args) {
		for(int k = 0; k<2*2; k++) {
			int dp = k%2 + k/2*24;
			System.out.println(dp);
		}
	}
	
}