package airship.mathStuff;


/**
 * Some helper methods to deal with a bending stick.
 * The bending stick is described by a sine function:
 *       ___					
 *      /   \    <- A stick that is bending.   
 *    _|_____|_____				
 *    
 * Important variables:<br>
 * - 's' is the "amount" of bending. It is the distance between the highest point of the curve and the ground.
 * - 'l' not really the length but the distance between the two points where the beam touches the ground.
 * - 'lb' the actual length of the beam.
 * Note: lb is always >= l!
 * @author maximilian
 */
public class KeelCalculator 
{

	/**
	 * Calculates the height above ground at position x.
	 * @param x A value between 0 and l.
	 * @param s
	 * @param l
	 * @return The height above ground.
	 */
	public static double calcY(double x, double s, double l)
	{
		return s * Math.sin((Math.PI * x)/l);
	}
	
	/**
	 * Calculates the actual length of the beam based on the amount of bending and 
	 * the distance between the two points touching the ground.<br>
	 * Unfortunately there seems to be no way to calculate the length of a sine curve
	 * so we need to approximate numerically.
	 * @param s
	 * @param l
	 * @param accuracy
	 * @return
	 */
	public static double calculateLB(double s, double l, int accuracy)
	{
		double stepsize = l/accuracy;
		double lb = 0;
		double x1, x2, y1, y2;
		y2 = 0;
		for(int i = 1; i < accuracy; i++)
		{
			x1 = (i-1) * stepsize;
			x2 = i * stepsize;
			y1 = y2;
			y2 = calcY(i * stepsize, s, l);
			lb += dist(x1, y1, x2, y2);
		}
		return lb;
	}
	
	/**
	 * Calculates l based on the length of the beams and the amount of bending.<br>
	 * Since we can only numerically approximate lb, we also need to approximate l
	 * in quite a slow way. But we won't call this to often and we are runnging an
	 * i5 quad at 3Ghz so who cares?
	 * @param lb
	 * @param s
	 * @param accuracy
	 * @return
	 */
	public static double calculateL(double lb, double s, int accuracy)
	{
		double l = lb/2.0; //l must be somewhere between 0 and lb. Lets start in the middle!
		double stepsize = lb/4;
		double calculatedLB = calculateLB(s, l, accuracy);
		while(Math.abs(calculatedLB - lb) > 0.0001 && stepsize > 0.001)//kind of a binary search like thing here
		{
			System.out.println(l);
			if(calculatedLB > lb)
			{
				l -= stepsize;
				//System.out.println("calculated lb was too big going down about " + stepsize);
			}
			else
			{
				l += stepsize;
				//System.out.println("calculated lb was too small going up about " + stepsize);
			}
			calculatedLB = calculateLB(s, l, accuracy);
			stepsize /= 2.0;
		}
		return l;
	}
	
	/**
	 * Calulates the amount of bending based on the length of the keel and the bent length of the keel.
	 * Again we need to approximate in a stupid way.
	 * @param lb
	 * @param l
	 * @param accuracy
	 * @return
	 */
	public static double calculateS(double lb, double l, int accuracy)
	{
		double s = lb/4.0; //s must be somewhere between 0 and lb/2. We start in the middle
		double stepsize = lb/8;
		double calculatedLB = calculateLB(s, l, accuracy);
		while(Math.abs(calculatedLB - lb) > 0.0001 && stepsize > 0.001)//kind of a binary search like thing here
		{
			//System.out.println(s);
			if(calculatedLB > lb)
			{
				s -= stepsize;
				//System.out.println("calculated lb was too big going down about " + stepsize);
			}
			else
			{
				s += stepsize;
				//System.out.println("calculated lb was too small going up about " + stepsize);
			}
			calculatedLB = calculateLB(s, l, accuracy);
			stepsize /= 2.0;
		}
		return s;
	}
	
	public static double dist(double x1, double y1, double x2, double y2)
	{
		double a = x1 - x2;
		double b = y1 - y2;
		return Math.sqrt(a*a + b*b);
	}
}
