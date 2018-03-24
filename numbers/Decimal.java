package numbers;

import java.math.BigDecimal;

public class Decimal {

	public static void main(String[] args) {
		BigDecimal a = new BigDecimal(1);
		BigDecimal b = new BigDecimal(3);
		//a.setScale(-10);
		BigDecimal c = a.divide(b, 10, BigDecimal.ROUND_HALF_UP);
		System.out.println(c);
		
	}
	
}
