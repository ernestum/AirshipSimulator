package airship;

import java.util.ArrayList;

import Jama.Matrix;

/**
 * Actually not an airship but just the structure of an airship.
 * 
 * @author maximilian
 */
public class Airship
{
    private ArrayList<Keel> keels;
    private ArrayList<Rope> ropes;
    private Joint[][] jointsOnKeels;

    private Matrix	  position = new Matrix(new double[] { 0, 0, 0 }, 1).transpose();

    private int	     ropePointsPerKeel;

    /**
     * Creates a new Ariship!
     * 
     * @param length
     *            The length of the airship from the tip to tail.
     * @param keelLength
     *            The length of the keel-beams of the airship
     * @param numberOfKeels
     *            You name it ...
     * @param ropePointsPerKeel
     *            The number of points where ropes are going to be attached to
     *            hold the keels in place.
     */
    public Airship(double length, double keelLength, int numberOfKeels, int ropePointsPerKeel)
    {
	assert numberOfKeels > 0;
	this.ropePointsPerKeel = ropePointsPerKeel;
	int numberOfRopes = calcNumberOfRopes(numberOfKeels, ropePointsPerKeel);

	keels = new ArrayList<Keel>(numberOfKeels);
	ropes = new ArrayList<Rope>(numberOfRopes);

	Joint jointToOrigin = new Joint() {
	    @Override
	    public Matrix getPosition()
	    {
		return position;
	    }
	};

	//Setting up the keels
	for (int i = 0; i < numberOfKeels; i++)
	{
	    Keel k = new Keel(keelLength, length, jointToOrigin);
	    double rotation = ((Math.PI * 2) / numberOfKeels) * i;
	    k.setRotationMatrix(new Matrix(new double[] { rotation, 0, 0 }, 1).transpose());
	    keels.add(k);
	}

	//setting up the joints on the keels
	jointsOnKeels = new Joint[ropePointsPerKeel][numberOfKeels];
	double distanceBetweenJoints = (double) 1 / ropePointsPerKeel;
	for (int joint = 0; joint < ropePointsPerKeel; joint++)
	    for (int keel = 0; keel < numberOfKeels; keel++)
		jointsOnKeels[joint][keel] = new JointOnKeel(keels.get(keel), distanceBetweenJoints * joint + 0.5
			* distanceBetweenJoints);

	//setting up the ropes on the joints
	for (int joint = 0; joint < ropePointsPerKeel-1; joint++)
	    for (int keel = 0; keel < numberOfKeels; keel++)
	    {
		int nextKeel = (keel + 1) % numberOfKeels;
		int prevKeel = (keel - 1) % numberOfKeels;
		prevKeel = prevKeel == -1 ? numberOfKeels-1 : prevKeel; // to fix the weird behavior of mod in java
		
		System.out.println("Next Keel: " + nextKeel);
		System.out.println("Actual Keel: " + keel);
		System.out.println("Prev Keel: " + prevKeel + "\n");
		
		ropes.add(new Rope(jointsOnKeels[joint][keel], jointsOnKeels[joint + 1][nextKeel]));
		ropes.add(new Rope(jointsOnKeels[joint][keel], jointsOnKeels[joint + 1][prevKeel]));
		/*if (joint > 0)
		{
		    ropes.add(new Rope(jointsOnKeels[joint][keel], jointsOnKeels[joint - 1][nextKeel]));
		}

		if (joint < ropePointsPerKeel - 1)
		    ropes.add(new Rope(jointsOnKeels[joint][keel], jointsOnKeels[joint + 1][nextKeel]));*/
	    }

	ropes.add(new Rope(new JointOnKeel(keels.get(0), 0), new JointOnKeel(keels.get(0), 1)));
    }

    private int calcNumberOfRopes(int nrKeels, int ropePointsPerKeel)
    {
	// we subtract 2 because the first and the last rope point only have
	// one rope attached. In the end we multiply by 2 because there are
	// ropes on both sides.
	int nrRopeEndsPerKeel = (ropePointsPerKeel * 2 - 2) * 2;
	int nrRopeEnds = nrRopeEndsPerKeel * nrKeels;
	int nrRopes = nrRopeEnds / 2;
	return nrRopes;
    }

    public ArrayList<Rope> getRopes()
    {
	return ropes;
    }

    public ArrayList<Rope> getRopesAtPos(int x)
    {
        ArrayList<Rope> ropesAtX = new ArrayList<Rope>(getNumberOfKeels());
    
        for (int keel = 0; keel < getNumberOfKeels(); keel++)
        {
            ropesAtX.add(ropes.get(x + keel));
        }
    
        //ropesAtX.add(ropes.get(2*(i*ropePointsPerKeel - i + x)));
        //ropesAtX.add(ropes.get(i*(ropePointsPerKeel*2 - 2) + 2 * x + 1));
    
        return ropesAtX;
    }
    
    public ArrayList<Rope> getRopesAtLayer(int l)
    {	
	if(l < 0 || l >= ropePointsPerKeel-1) return new ArrayList<Rope>(0);
	
	ArrayList<Rope> ropesAtL = new ArrayList<Rope>(getNumberOfKeels()*2);	
	
	int start = getNumberOfKeels()*2*l;
	for(int i = 0; i < getNumberOfKeels()*2; i++)
	{
	    ropesAtL.add(ropes.get(start+i));
	}
	return ropesAtL;
    }

    public int getRopePointsPerKeel()
    {
        return ropePointsPerKeel;
    }

    public ArrayList<Keel> getKeels()
    {
	return keels;
    }

    public double getKeelLength()
    {
        return keels.get(0).getLength();
    }

    public int getNumberOfKeels()
    {
        return keels.size();
    }

    public double getLength()
    {
	return keels.get(0).getLengthBent();
    }

    public void setLength(double l)
    {
	for (Keel k : keels)
	    k.setBentLength(l);
    }

    public double getMaxRadius()
    {
	return keels.get(0).getAmountOfBending();
    }
    
    public Joint getJointAt(int keel, int layer)
    {
	return jointsOnKeels[layer][keel];
    }

    /**
     * @return The volume of the airship.
     */
    public double calcVolume()
    {
	return (1 / 4.0) * getLength() * getNumberOfKeels() * getMaxRadius() * getMaxRadius()
		* Math.sin((2 * Math.PI) / getNumberOfKeels());
    }
}
