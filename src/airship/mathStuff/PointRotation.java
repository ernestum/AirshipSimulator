package airship.mathStuff;


import Jama.Matrix;

public class PointRotation 
{
	public static Matrix getXRotationMatrix(double angle)
	{
		double sina = Math.sin(angle);
		double cosa = Math.cos(angle);
		return new Matrix(new double[][] {
				{	1,		0,		0		},
				{	0,		cosa,	-sina	},
				{	0,		sina,	cosa	}
		});
	}
	
	public static Matrix getYRotationMatrix(double angle)
	{
		double sina = Math.sin(angle);
		double cosa = Math.cos(angle);
		return new Matrix(new double[][] {
				{	cosa,	0,	sina	},
				{	0,		1,	0		},
				{	-sina,	0,	cosa	}
		});
	}
	
	public static Matrix getZRotationMatrix(double angle)
	{
		double sina = Math.sin(angle);
		double cosa = Math.cos(angle);
		return new Matrix(new double[][] {
				{	cosa,	-sina,	0	},
				{	sina,	cosa,	0	},
				{	0,		0,		1	}
		});
	}
	
	public static Matrix rotatedAround(int axis, double angle, Matrix m)
	{
		switch(axis)
		{
		case 0:
			return m.transpose().times(getXRotationMatrix(angle)).transpose();
		case 1:
			return m.transpose().times(getYRotationMatrix(angle)).transpose();
		case 2:
			return m.transpose().times(getZRotationMatrix(angle)).transpose();
		}
		return null;
	}
	
	public static Matrix applyRotation(Matrix m, Matrix rotation)
	{
		assert(rotation.getRowDimension() == 3 && m.getRowDimension() == 3);
		
		for(int i = 0; i < 3; i++)
		{
			m = rotatedAround(i, rotation.get(i, 0), m);
		}
		
		return m;
	}
}
