package airship;


import Jama.Matrix;

/**
 * An abstract thing to attach something to a moving point. E.g. ropes use it to attach to the keels
 * and the keels use it to attach to each other.
 * @author maximilian
 */
public abstract class Joint 
{
	public abstract Matrix getPosition();
	
	public int getDimension()
	{
	    return getPosition().getRowDimension();
	}
}
