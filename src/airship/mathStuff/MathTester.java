package airship.mathStuff;
import Jama.Matrix;


public class MathTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("S is: " + KeelCalculator.calculateS(100, 90, 1000));
		
		Matrix a = new Matrix(3, 1, 0);
		Matrix b = new Matrix(3, 1, 0);
		
		a.set(0, 0, 1);
		b.set(1, 0, 1);
		
		a.transpose().print(3, 2);
		
		PointRotation.rotatedAround(2, Math.PI/2, a).print(3, 2);
		
		/*System.out.println("A:");
		a.print(3, 0);
		
		System.out.println("B:");
		b.print(3, 0);
		
		System.out.println("A+B:");
		a.plus(b).print(3, 0);
		
		System.out.println("A+B length:");
		System.out.println(a.plus(b).normF());*/
	}

}
