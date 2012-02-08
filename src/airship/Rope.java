package airship;


import Jama.Matrix;

/**
 * Just a straight rope.
 * @author maximilian
 */
public class Rope 
{
	private Joint joint1, joint2;
	
	/**
	 * @param joint1 The start point of the rope.
	 * @param joint2 The end point of the rope.
	 */
	public Rope(Joint joint1, Joint joint2)
	{
		this.joint1 = joint1;
		this.joint2 = joint2;
	}
	
	public Matrix getStart()
	{
		return joint1.getPosition();
	}
	
	public Matrix getEnd()
	{
		return joint2.getPosition();
	}
	
	public double calcLength()
	{
		return (getStart().minus(getEnd())).normF();
	}
}
