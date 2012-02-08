package airship;


import Jama.Matrix;

public class JointOnKeel extends Joint
{
	Keel k;
	double position;
	
	public JointOnKeel(Keel k, double position)
	{
		this.k = k;
		this.position = position;
	}
	
	@Override
	public Matrix getPosition() 
	{
		return k.getPoint(position);
	}

}
