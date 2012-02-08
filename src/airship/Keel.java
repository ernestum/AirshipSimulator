package airship;


import airship.mathStuff.AccuracyConstants;
import airship.mathStuff.KeelCalculator;
import airship.mathStuff.PointRotation;
import Jama.Matrix;

public class Keel implements AccuracyConstants
{
	private final double length;
	private double bentLength;
	private double s;
	
	private Joint joint;
	
	private Matrix rotationMatrix = new Matrix(3, 1, 0);
	
	public Keel(double length, double bentLength, Joint joint)
	{
		assert(joint != null);
		
		this.length = length;
		this.joint = joint;
		setBentLength(bentLength);
		s = KeelCalculator.calculateS(length, bentLength, ACCURACY);
	}
	
	public void setRotationMatrix(Matrix m)
	{
		assert(m.getRowDimension() == 3 && m.getColumnDimension() == 1);
		this.rotationMatrix = m;
	}
	
	/**
	 * @param position A value between 0 and 1.
	 * @return A vector with the position.
	 */
	public Matrix getPoint(double position)
	{
		assert(position >= 0 && position <= 1);
		double x =  bentLength * position;
		double y = KeelCalculator.calcY(x, s, bentLength); //s * Math.sin((Math.PI * x)/bentLength);
		
		
		Matrix point = new Matrix(joint.getDimension(), 1, 0);
		point.set(0, 0, x);
		point.set(1, 0, y);
		
		point = PointRotation.applyRotation(point, rotationMatrix);		

		Matrix origin = joint.getPosition();
		
		point.plusEquals(origin);
		
		return point;
	}
	
	/**
	 * The amount of bending will change, the length of the keel will of course stay the same!
	 * @param bl
	 */
	public void setBentLength(double bl)
	{
		if(bl == 0)
			return;

		if(bl > length)
			bl = length;
		
		this.bentLength = bl;
		this.s = KeelCalculator.calculateS(length, bl, ACCURACY);
	}

	public double getLengthBent() {
          return bentLength;
        }
	
	public double getAmountOfBending()
	{
	    return s;
	}
	
	public double getLength()
	{
	    return length;
	}
}
