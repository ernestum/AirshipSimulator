import java.util.ArrayList;
import java.util.Arrays;

import picking.Picker;
import processing.core.*;
import Jama.Matrix;
import airship.*;

/**
 * The painter knows how to paint things like airships, ropes or keels on a
 * PApplet.
 * 
 * @author maximilian
 */
public class Painter
{
    static private PFont	 bigFont	      = new PApplet().createFont("Arial", 72);
    static private PFont	 smallFont	      = new PApplet().createFont("Arial", 16);
    
    public static void drawGrid(PGraphics p, PVector pos, PVector span1, PVector span2, int span1Steps, int span2Steps)
    {
	p.stroke(255-0, 255-150, 255-190);
	p.strokeWeight(0.1f);
	for (int i = 0; i < span1Steps+1; i++)
	{
	    PVector start = PVector.add(pos, PVector.mult(span1, (1 / (float) span1Steps) * i));
	    PVector end = PVector.add(start, span2);
	    p.line(start.x, start.y, start.z, end.x, end.y, end.z);
	}

	for (int i = 0; i < span2Steps+1; i++)
	{
	    PVector start = PVector.add(pos, PVector.mult(span2, (1 / (float) span2Steps) * i));
	    PVector end = PVector.add(start, span1);
	    p.line(start.x, start.y, start.z, end.x, end.y, end.z);
	}
    }
    
    public static void drawRuler(PGraphics p, float length, float sectionsDistance, float SectionsSize, PVector direction)
    {
	p.fill(100);
	p.stroke(255-0, 255-150, 255-190);
	p.line(0, 0, 0, direction.x * length, direction.y * length, direction.z * length);
	p.textFont(bigFont, 0.7f);
	p.ellipseMode(p.CENTER);
	for (int i = 0; i < (int) (length / sectionsDistance)+1; i++)
	{
	    p.pushMatrix();
	    p.translate(direction.x * i * sectionsDistance, direction.y * i * sectionsDistance, direction.z * i
		    * sectionsDistance);
	    //p.rotateY(-(worldYRotation));
	    //p.rotateX(-(worldXRotation));
	    p.text(Math.round(i * sectionsDistance) + "m", 0, 0);
	    p.popMatrix();
	}
	sectionsDistance /= 5;
	for (int i = 0; i < (int) (length / sectionsDistance); i++)
	{
	    p.pushMatrix();
	    p.translate(direction.x * i * sectionsDistance, direction.y * i * sectionsDistance, direction.z * i
		    * sectionsDistance);
	    p.line(0, -.1f, 0, 0, .1f, 0);
	    p.popMatrix();
	}
    }
    
    public static void draw(PGraphics p, Airship a, int highLightedLayer, Picker picker)
    {
	p.pushMatrix();
	p.translate((float) -a.getLength() / 2, 0, 0);
	for (Keel k : a.getKeels())
	    draw(p, k);

	
	for (int i = 0; i < a.getRopePointsPerKeel(); i++)
	{
	    p.strokeWeight(1);
	    p.stroke(0);
	    if (i == highLightedLayer)
	    {
		p.stroke(0);
		p.strokeWeight(2);
	    }
	    ArrayList<Rope> layerIRopes = a.getRopesAtLayer(i);
	    for (Rope r : layerIRopes)
	    {
		draw(p, r);
	    }
	}
	
	/*for(int layer = 0; layer < a.getRopePointsPerKeel(); layer ++)
	{
	    if(layer < a.getRopePointsPerKeel()-1)
		picker.start(layer);
	    
	    for (int keel = 0; keel < a.getNumberOfKeels(); keel++)
	    {
		draw(p, a.getJointAt(keel, layer));
	    }
	}*/
	
	
	
	//draw some sheets on the outside	
	
	
	
	for(int r = 0; r < a.getRopePointsPerKeel() - 1; r++)
	{
	    p.fill(0, 255, 255, 100);
	    p.noStroke();
	
	    
	    picker.start(r);
	    /*if(r == highLightedLayer)
		p.fill(0, 255, 255, 100);*/
	    for(int keel = 0; keel < a.getNumberOfKeels(); keel++)
	    {
		//TODO: Some very ugly stuff going on here. We need a Vertex/Position/Point class, that extends Matrix
		//So we do not have to get out the coordinates in such a ugly way!
		p.beginShape();
		float x, y, z;
		double[] pos;
		pos = a.getJointAt(keel, r).getPosition().getColumn(0);
		x = (float)pos[0];
		y = (float)pos[1];
		z = (float)pos[2];
		p.vertex(x, y, z);
		
		pos = a.getJointAt(keel, r+1).getPosition().getColumn(0);
		x = (float)pos[0];
		y = (float)pos[1];
		z = (float)pos[2];
		p.vertex(x, y, z);
		
		pos = a.getJointAt(a.keelAfter(keel), r+1).getPosition().getColumn(0);
		x = (float)pos[0];
		y = (float)pos[1];
		z = (float)pos[2];
		p.vertex(x, y, z);
		
		pos = a.getJointAt(a.keelAfter(keel), r).getPosition().getColumn(0);
		x = (float)pos[0];
		y = (float)pos[1];
		z = (float)pos[2];
		p.vertex(x, y, z);
		p.endShape(p.CLOSE);
	    }
	}
	
	
	p.popMatrix();
	
	//
	/*for (Rope r : a.getRopes())
	{
	    p.strokeWeight(1);
	    p.stroke(150);
	    
	    if(highLightedRopes.contains(r))
	    {
		p.stroke(255, 0, 0);
		p.strokeWeight(3);
	    }
	    
	    draw(p, r);
	    ropeCounter++;
	}*/

	
    }

    public static void draw(PGraphics p, Joint j)
    {
	p.pushMatrix();
	float x = (float) j.getPosition().get(0, 0);
	float y = (float) j.getPosition().get(1, 0);
	float z = (float) j.getPosition().get(2, 0);
	p.translate(x, y, z);
	p.noStroke();	
	p.sphere(.2f);
	p.popMatrix();
    }

    public static void draw(PGraphics p, Keel k)
    {
	p.noFill();
	p.stroke(0);
	p.strokeWeight(3);
	p.beginShape();
	for (double d = 0; d <= 1; d += 1.0 / 100)
	{
	    Matrix point = k.getPoint(d);
	    float x = (float) point.get(0, 0);
	    float y = (float) point.get(1, 0);
	    float z = (float) point.get(2, 0);
	    p.vertex(x, y, z);
	}

	Matrix point = k.getPoint(1.0);
	float x = (float) point.get(0, 0);
	float y = (float) point.get(1, 0);
	float z = (float) point.get(2, 0);
	p.vertex(x, y, z);
	p.endShape();
    }

    public static void draw(PGraphics p, Rope r)
    {
	p.noFill();
	Matrix start = r.getStart();
	Matrix end = r.getEnd();
	float x1 = (float) start.get(0, 0);
	float y1 = (float) start.get(1, 0);
	float z1 = (float) start.get(2, 0);

	float x2 = (float) end.get(0, 0);
	float y2 = (float) end.get(1, 0);
	float z2 = (float) end.get(2, 0);

	p.line(x1, y1, z1, x2, y2, z2);
    }
}
